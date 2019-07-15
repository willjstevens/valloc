package com.valloc.service;

import com.valloc.*;
import com.valloc.Locale;
import com.valloc.dao.AccountDao;
import com.valloc.framework.*;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.User;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.persistent.PersistentUser;
import com.valloc.web.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.valloc.Constants.REPLACEMENT_TOKEN_0;
import static com.valloc.Constants.REPLACEMENT_TOKEN_1;
import static com.valloc.SpecConstants.*;

/**
 * Standard implementation for account operations.
 *
 * @author wstevens
 */
@Service
public class AccountServiceImpl extends AbstractServiceImpl implements AccountService
{
	private static final Logger logger = LogManager.manager().newLogger(AccountServiceImpl.class, Category.SERVICE_ACCOUNT);

    private static final Set<String> excludedUsernames = new HashSet<>();
	private final DateFormat birthdateFormatter = new SimpleDateFormat(SpecConstants.BIRTHDATE_PATTERN_SERVER_VALUE);
	private long verificationPeriodDaysAllowanceMillis;
	@Autowired private AccountDao accountDao;
	@Autowired private ValidationService validationService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private UtilityService utilityService;
	@Autowired private SessionManager sessionManager;
	
//	@PostConstruct
	@Override
	public void init() {
		loadVerificationPeriodDaysAllowance();
        excludedUsernames.addAll(Arrays.asList(Constants.USERNAME_EXCLUSIONS));
	}
	
	private void loadVerificationPeriodDaysAllowance() {
		final int daysAllowance = configurationService.getAccountServiceVerificationPeriodAllowanceDays();
		verificationPeriodDaysAllowanceMillis = daysAllowance * 24 * 60 * 60 * 1000;
	}
	
	
	@Transactional
	@Override
	public ServiceResult<PersistentUser> addUser(AccountForm accountForm) {
		ServiceResult<PersistentUser> serviceResult = new ServiceResult<PersistentUser>();
		try {	
			// validate creation-only fields
			validateCreationFields(serviceResult, accountForm);
			// validate editable fields shared between creation and editing
			validateEditableFields(serviceResult, accountForm);
			// if no business or validation errors, then proceed:
			boolean successfulValidation = !serviceResult.hasSupportingUserMessages();  
			if (successfulValidation) {
				// setup and insert new user to database
				String verificationCode = generateVerificationCode();
				Date verificationCodeIssuedTimestamp = Util.now();

				PersistentUser newUser = new PersistentUser();

				// TEMP FILL VALUE to SIMPLIFY REGISTRATION
//				Date birthdate = birthdateFormatter.parse(accountForm.birthdate);
				Date birthdate = birthdateFormatter.parse("01/01/1899");
				newUser.setBirthdate(birthdate);

				newUser.setUsername(accountForm.username);
				newUser.setEmail(accountForm.email);
				newUser.setFirstName(accountForm.firstName);
				newUser.setLastName(accountForm.lastName);

                // TEMP FILL VALUE to SIMPLIFY REGISTRATION
//				newUser.setGender(accountForm.gender);
				newUser.setGender("FILL");

				newUser.setInAgreement(accountForm.inAgreement);
				newUser.setPassword(accountForm.password);

				// TEMP FILL VALUE to SIMPLIFY REGISTRATION
//				newUser.setPasswordQuestion(accountForm.passwordQuestion);
				newUser.setPasswordQuestion("FILL");


				// TEMP FILL VALUE to SIMPLIFY REGISTRATION
//				newUser.setPasswordAnswer(accountForm.passwordAnswer);
				newUser.setPasswordAnswer("FILL");


				newUser.setVerificationCode(verificationCode);
				newUser.setVerificationCodeIssuedTimestamp(verificationCodeIssuedTimestamp);
				newUser.setLocale(Locale.EN_US.name()); // hard-set for now until I18N is implemented
				newUser.setEnabled(false); // false until account is verified



				accountDao.saveOrUpdate(newUser);
				serviceResult.setObject(newUser);
				serviceResult.setSuccess();

                // send me an email!
                sendAdminNewUserEmail(newUser);
			}
		} catch (Exception e) {
            setUserMessage(serviceResult, "account_create_save_failure");
			logger.error("Error occured adding account form info: %s.", e, accountForm);
			ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
			throw throwMe;	
		}
		
		return serviceResult;
	}

    private void sendAdminNewUserEmail(User newUser) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h4>Username: ").append(newUser.getUsername()).append("</h4>");
        emailBody.append("<h4>Name: ").append(newUser.getFirstName()).append(" ").append(newUser.getLastName()).append("</h4>");
//        emailBody.append("Birthdate: ").append(newUser.getBirthdate());
        String body = emailBody.toString();
        String subject = "Valloc.com NEW USER: " + newUser.getUsername();
        try {
            utilityService.sendHtmlEmail("willjstevens@gmail.com", subject, body);
        } catch (Exception e) {
            logger.error("Problem when attempting to send admin a notification when adding a new user: %s.", e, e);
        }
    }

	@Transactional
	@Override
	public ServiceResult<?> editUser(AccountForm accountForm) {
		ServiceResult<?> serviceResult = new ServiceResult<PersistentUser>();
		try {	
			// validate editable fields shared between creation and editing
			validateEditableFields(serviceResult, accountForm);
			// if no business or validation errors, then proceed:
			boolean successfulValidation = !serviceResult.hasSupportingUserMessages();  
			if (successfulValidation) {
				// query here to get existing user object, set new values in and save/update
				PersistentUser user = accountDao.findUserByUsername(accountForm.username);
				user.setPassword(accountForm.password);
				user.setPasswordQuestion(accountForm.passwordQuestion);
				user.setPasswordAnswer(accountForm.passwordAnswer);
				accountDao.saveOrUpdate(user);
                // update user in session manager
				sessionManager.updateUser(user);
                // add success message and flag
                setUserMessage(serviceResult, "account_edit_save_success");
				serviceResult.setSuccess();
			}
		} catch (Exception e) {
            setUserMessage(serviceResult, "account_edit_save_failure");
			logger.error("Error occured editing account form info: %s.", e, accountForm);
			ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
			throw throwMe;	
		}
		
		return serviceResult;
	}

	@Transactional
	@Override
	public ServiceResult<?> verifyAccount(final String email, final String verificationCode) {
		ServiceResult<?> serviceResult = new ServiceResult<PersistentUser>();
		try {	
			PersistentUser user = accountDao.findUserByEmail(email);
			if (user != null) {
				if (user.getVerificationCodeCompletedTimestamp() == null) { // not yet verified so proceed
					Date now = Util.now();
					long nowMillis = now.getTime();
					long issuedTimestampMillis = user.getVerificationCodeIssuedTimestamp().getTime();
					long differenceMillis = nowMillis - issuedTimestampMillis;
					boolean isWithinAllowancePeriod = differenceMillis <= verificationPeriodDaysAllowanceMillis;
					if (isWithinAllowancePeriod) {
						boolean verificationCodesMatch = user.getVerificationCode().equals(verificationCode);
						if (verificationCodesMatch) { // verification codes match
							user.setEnabled(true);
							user.setVerificationCodeCompletedTimestamp(now);
							accountDao.saveOrUpdate(user);
							serviceResult.setViewName("success");
							serviceResult.setSuccess();
							if (logger.isFine()) {
								logger.fine("Successfully verified user account with username %s.", user.getUsername());
							}
						} else { // verification codes do not match
							serviceResult.setViewName("badVerification");
							if (logger.isInfo()) {
								logger.info("User account with username %s had verification codes which did not match.", user.getUsername());
							}
						}
					} else { // the user surpassed the allowable period for verification
						serviceResult.setViewName("expiredCode");
						if (logger.isInfo()) {
							logger.info("User account with username %s let the allowable period expire for account verification.", user.getUsername());
						}
					}
				} else { // the user has already verified the email address
					serviceResult.setViewName("verifyCompleted");
					if (logger.isInfo()) {
						logger.info("User account with username %s has already completed account verification.", user.getUsername());
					}
				}
			} else { // user could not be found by way of email
				serviceResult.setViewName("badVerification");
				if (logger.isInfo()) {
					logger.info("Could not locate user object for email %s.", email);
				}
			} 
		} catch (Exception e) {			
			logger.error("Error occured while verifying the account: " + e, e);
			ServerErrorPageResultException throwMe = getServerErrorPageResultException();
			throw throwMe;	
		}
		
		return serviceResult;
	}
	
	@Transactional
	@Override
	public ServiceResult<PersistentUser> findUserByUsername(String username) {
		ServiceResult<PersistentUser> serviceResult = new ServiceResult<PersistentUser>();
		try {	
			// query here to get existing user object, set new values in and save/update
			PersistentUser user = accountDao.findUserByUsername(username);
			serviceResult.setObject(user);
			serviceResult.setSuccess();
		} catch (Exception e) {			
			logger.error("Error occured while searching for user object for username: %s.", e, username);
			ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
			throw throwMe;	
		}
		return serviceResult;
	}

	@Transactional
	@Override
	public ServiceResult<Session> loginByUsernamePassword(String username, String password) {		
		ServiceResult<Session> serviceResult = new ServiceResult<>();
		try {
            username = username.toLowerCase();
			User user = accountDao.findUserByUsername(username);
			if (user != null) {
				boolean verificationConfirmed = user.getVerificationCodeCompletedTimestamp() != null;
				if (verificationConfirmed) { // if verification was confirmed
					// User found and confirmed email; now check for password validity.
					String sourcePassword = user.getPassword();
					if (sourcePassword.equals(password)) { // passwords match so success.
						Session session = sessionManager.createAndLoadPreliminarySession(user);
						if (user.getCookieValue() == null) { // load a new cookie value if not already set
							loadUserCookieValue(user);
							((PersistentUser) user).setUpdateTimestamp(Util.now());
							sessionManager.getSessionByUser(user).setUser(user); // reset user
						}

						((PersistentUser) user).setLastLoginTimestamp(Util.now());
						accountDao.saveOrUpdate(user);

						serviceResult.setObject(session);
						serviceResult.setSuccess();
						if (logger.isFine()) {
							logger.fine("Successfully logged username \"%s\" in by username and password.", user.getUsername());
						}
					} else { // password entered was invalid.
						addSupportingUserMessage(serviceResult, "login_validate_generalMismatch");
						if (logger.isFine()) {
							logger.fine("Password mismatch on login attempt for username %s. Submitted the incorrect password of %s but valid password is %s.", username, password, user.getPassword());
						}
					}
				} else { // verification was never confirmed
					addSupportingUserMessage(serviceResult, "account_verification_failure_verificationNeeded");
					if (logger.isFine()) {
						logger.fine("User with username \"%s\" never confirmed email account and attempted to login.", user.getUsername());
					}
				}
			} else { // username was not found
                addSupportingUserMessage(serviceResult, "login_validate_generalMismatch");
                if (logger.isFine()) {
                    logger.fine("Username on login attempt could not be found for submitted (incorrect) username %s.", username);
                }
			}
		} catch (Exception e) {			
			logger.error("Error occured during login for username: %s.", e, username);
			ServerErrorPageResultException throwMe = getServerErrorPageResultException();
			throw throwMe;	
		}
		return serviceResult;
	}

	// only called by page load service
	@Transactional
	@Override
	public ServiceResult<Session> loginByCookie(String cookieValue) {
		ServiceResult<Session> serviceResult = new ServiceResult<>();
		try {
			User user = accountDao.findUserByCookieValue(cookieValue);
			if (user != null) {
				Session session = sessionManager.createAndLoadPreliminarySession(user);

				((PersistentUser) user).setLastLoginTimestamp(Util.now());
				accountDao.saveOrUpdate(user);

				serviceResult.setObject(session);
				serviceResult.setSuccess();
                if (logger.isFine()) {
                	logger.fine("Successfully logged username \"%s\" in by cookie value \"%s\".", user.getUsername(), cookieValue);
                }
			} else {
                if (logger.isFine()) {
                	logger.fine("Could not login in unknown user by submitted cookie value of \"%s\".", cookieValue);
                }
            }
		} catch (Exception e) {			
			logger.error("Error occured during login for cookie value: %s.", e, cookieValue);
			ServerErrorPageResultException throwMe = getServerErrorPageResultException();
			throw throwMe;	
		}
		return serviceResult;
	}

	@Transactional
	@Override
	public ServiceResult<?> logout(String username) {
		ServiceResult<Session> serviceResult = new ServiceResult<>();
		try {	
			Session session = sessionManager.getSessionByUsername(username);
			sessionManager.removeSession(session);

            // 20130927 - These lines are commented out otherwise, if a user logs out on any one device,
            //      then it will cause the other device's cookie values to be obsolete and consequently
            //      trigger another login on the other devices when hitting a secure page. Removing this
            //      logic to set the cookie to null, will avoid having to re-create on next login and should
            //      allow for a far better user experience since he/she will not need to constantly re-login.
            //      Temporarily left for context or possible revert.
			// remove cookie value in database
//			User user = accountDao.findUserByUsername(username);
//			user.setCookieValue(null);
//			accountDao.saveOrUpdate(user);

			// TODO: logic to clear from page load service?
            if (logger.isFine()) {
            	logger.fine("Successfully logged out username %s.", username);
            }
		} catch (Exception e) {			
			logger.error("Error occured during logout for username: %s.", e, username);
			ServerErrorPageResultException throwMe = getServerErrorPageResultException();
			throw throwMe;	
		}
		return serviceResult;
	}

	private void loadUserCookieValue(User user) {
		String cookieValue = utilityService.newUuidString();
		user.setCookieValue(cookieValue);
	}

    @Override
    public String formatBirtdate(Date birthdate) {
        return birthdateFormatter.format(birthdate);
    }

	private void validateCreationFields(ServiceResult<PersistentUser> serviceResult, AccountForm accountForm) {
		// Validate username:
		final boolean usernameNullOrEmpty = validationService.isNullOrEmpty(accountForm.username); 
		if (usernameNullOrEmpty) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_username_validate_required"));
		} else {
            final String username = accountForm.username.toLowerCase();
			boolean isInvalidSize = !validationService.isValidSize(username, USERNAME_MIN_SIZE_VALUE, USERNAME_MAX_SIZE_VALUE);
			if (isInvalidSize) {
				UserMessage userMessage = applicationService.getUserMessage("account_username_validate_length");
				String customizedMessage = Util.replaceTokenWithInt(userMessage.getMessage(), REPLACEMENT_TOKEN_0, USERNAME_MIN_SIZE_VALUE);
				customizedMessage = Util.replaceTokenWithInt(customizedMessage, REPLACEMENT_TOKEN_1, USERNAME_MAX_SIZE_VALUE);
				userMessage = userMessage.toCustomizedInstance(customizedMessage);
				serviceResult.addSupportingUserMessage(userMessage);
			}
			boolean isInvalidFormat = !validationService.isValidFormat(username, USERNAME_PATTERN_VALUE);
			if (isInvalidFormat) {
                addSupportingUserMessage(serviceResult, "account_username_validate_format");
			}
            boolean containsExclusion = excludedUsernames.contains(username);
            if (containsExclusion) {
                addSupportingUserMessage(serviceResult, "account_username_validate_exclusionsList");
            }
		}
		// Validate email:
		final boolean emailNullOrEmpty = validationService.isNullOrEmpty(accountForm.email);
		if (emailNullOrEmpty) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_email_validate_required"));
		} else {
			boolean isInvalidSize = !validationService.isValidSize(accountForm.email, EMAIL_MIN_SIZE_VALUE, EMAIL_MAX_SIZE_VALUE);
			if (isInvalidSize) {
				UserMessage userMessage = applicationService.getUserMessage("account_email_validate_length");
				String customizedMessage = Util.replaceTokenWithInt(userMessage.getMessage(), REPLACEMENT_TOKEN_0, EMAIL_MIN_SIZE_VALUE);
				customizedMessage = Util.replaceTokenWithInt(customizedMessage, REPLACEMENT_TOKEN_1, EMAIL_MAX_SIZE_VALUE);
				userMessage = userMessage.toCustomizedInstance(customizedMessage);
				serviceResult.addSupportingUserMessage(userMessage);
			}
			boolean isInvalidFormat = !validationService.isValidEmailFormat(accountForm.email);
			if (isInvalidFormat) {
				serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_email_validate_format"));
			}
		}
		// Validate username and email availability (do in one, for one database query hit
		if (!usernameNullOrEmpty && !emailNullOrEmpty) {
			confirmUsernameAndEmailAvailability(serviceResult, accountForm.username, accountForm.email);
		}
		// Validate first name:
		if (validationService.isNullOrEmpty(accountForm.firstName)) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_firstName_validate_required"));
		} else {
			boolean isInvalidSize = !validationService.isValidSize(accountForm.firstName, FIRST_NAME_MIN_SIZE_VALUE, FIRST_NAME_MAX_SIZE_VALUE);
			if (isInvalidSize) {
				UserMessage userMessage = applicationService.getUserMessage("account_firstName_validate_length");
				String customizedMessage = Util.replaceTokenWithInt(userMessage.getMessage(), REPLACEMENT_TOKEN_0, FIRST_NAME_MIN_SIZE_VALUE);
				customizedMessage = Util.replaceTokenWithInt(customizedMessage, REPLACEMENT_TOKEN_1, FIRST_NAME_MAX_SIZE_VALUE);
				userMessage = userMessage.toCustomizedInstance(customizedMessage);
				serviceResult.addSupportingUserMessage(userMessage);
			}
			boolean isInvalidFormat = !validationService.isValidFormat(accountForm.firstName, NAME_PATTERN_VALUE);
			if (isInvalidFormat) {
				UserMessage userMessage = applicationService.getUserMessage("account_firstName_validate_format");
				serviceResult.addSupportingUserMessage(userMessage);
			}
		}
		// Validate last name:
		if (validationService.isNullOrEmpty(accountForm.lastName)) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_lastName_validate_required"));
		} else {
			boolean isInvalidSize = !validationService.isValidSize(accountForm.lastName, LAST_NAME_MIN_SIZE_VALUE, LAST_NAME_MAX_SIZE_VALUE);
			if (isInvalidSize) {
				UserMessage userMessage = applicationService.getUserMessage("account_lastName_validate_length");
				String customizedMessage = Util.replaceTokenWithInt(userMessage.getMessage(), REPLACEMENT_TOKEN_0, LAST_NAME_MIN_SIZE_VALUE);
				customizedMessage = Util.replaceTokenWithInt(customizedMessage, REPLACEMENT_TOKEN_1, LAST_NAME_MAX_SIZE_VALUE);
				userMessage = userMessage.toCustomizedInstance(customizedMessage);
				serviceResult.addSupportingUserMessage(userMessage);
			}
			boolean isInvalidFormat = !validationService.isValidFormat(accountForm.lastName, NAME_PATTERN_VALUE);
			if (isInvalidFormat) {
				UserMessage userMessage = applicationService.getUserMessage("account_lastName_validate_format");
				serviceResult.addSupportingUserMessage(userMessage);
			}
		}
		// Validate in-agreement:
		boolean inAgreement = accountForm.inAgreement;
		if (!inAgreement) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_inAgreement_validate_required"));
		}
	}
	
	private void validateEditableFields(ServiceResult<?> serviceResult, AccountForm accountForm) {
		// Validate password:
		if (validationService.isNullOrEmpty(accountForm.password)) {
			serviceResult.addSupportingUserMessage(applicationService.getUserMessage("account_password_validate_required"));
		} else {
			boolean isInvalidSize = !validationService.isValidSize(accountForm.password, PASSWORD_MIN_SIZE_VALUE, PASSWORD_MAX_SIZE_VALUE);
			if (isInvalidSize) {
				UserMessage userMessage = applicationService.getUserMessage("account_password_validate_length");
				String customizedMessage = Util.replaceTokenWithInt(userMessage.getMessage(), REPLACEMENT_TOKEN_0, PASSWORD_MIN_SIZE_VALUE);
				customizedMessage = Util.replaceTokenWithInt(customizedMessage, REPLACEMENT_TOKEN_1, PASSWORD_MAX_SIZE_VALUE);
				userMessage = userMessage.toCustomizedInstance(customizedMessage);
				serviceResult.addSupportingUserMessage(userMessage);
			}
		}
	}

	private void confirmUsernameAndEmailAvailability(ServiceResult<PersistentUser> serviceResult, String username, String email) {
		validationService.assertNotNullAndNotEmpty(username);
		validationService.assertNotNullAndNotEmpty(email);
		
		// condition arguments
		username = username.toLowerCase();
		email = email.toLowerCase();
		try {
			PersistentUser user = accountDao.findUserByUsernameOrEmail(username, email);
			if (user == null) { // user is available since not in database
				if (logger.isFine()) {
					logger.fine("Both username \"%s\" and email \"%s\" are available for use.", username, email);
				}
			} else { // another user already has either the username or email taken
				String existingUsername = user.getUsername();
				if (existingUsername.equals(username)) {
					UserMessage userMessage = applicationService.getUserMessage("account_username_validate_taken");
					String customizedMessage = userMessage.getMessage().replace(Constants.REPLACEMENT_TOKEN_0, username);
					userMessage = userMessage.toCustomizedInstance(customizedMessage);
					serviceResult.addSupportingUserMessage(userMessage);
					if (logger.isFine()) {
						logger.fine("Username \"%s\" was found to be taken by existing user: %s.", username, user);
					}
				}
				String existingEmail = user.getEmail();
				if (existingEmail.equals(email)) {
					UserMessage userMessage = applicationService.getUserMessage("account_email_validate_taken");
					String customizedMessage = userMessage.getMessage().replace(Constants.REPLACEMENT_TOKEN_0, email);
					userMessage = userMessage.toCustomizedInstance(customizedMessage);
					serviceResult.addSupportingUserMessage(userMessage);
					if (logger.isFine()) {
						logger.fine("Email \"%s\" was found to be taken by existing user: %s.", email, user);
					}
				}
			}
		} catch (Exception e) {
			String msg = String.format("Exception in validateUsernameAndEmailAvailability for username=%s and email=%s: %s.",
										username, email, e);
			logger.error(msg, e);
			serviceResult.setUserMessage(applicationService.getServerErrorUserMessage());
		}
	}

    @SuppressWarnings("restriction")
	private String generateVerificationCode() {
		String verificationCode = "UNINITIALIZED";
		MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    } catch(NoSuchAlgorithmException e) {
	        logger.error("Could not generate MessageDigest object with SHA-1 algorithm. Is it supported?", e);
	        throw new RuntimeException(e);
	    } 
	    
	    String uuid = UUID.randomUUID().toString();
	    byte[] uuidBytes = md.digest(uuid.getBytes());
	    String uuidBytesString = new String(uuidBytes);
	    if (logger.isFine()) {
	    	logger.fine("Converted UUID string \"%s\" into bytes: \"%s\".", uuid, uuidBytesString);
	    }
	    
	    try {
			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
		    verificationCode = encoder.encodeToString(uuidBytes);
		    if (logger.isFine()) {
				logger.fine("Successfully generated verification code: \"%s\".", verificationCode);
			}
	    } catch (Throwable t) {
	    	logger.error("Problem while using Sun's encoding library, possibly due to restricution warning: " + t, t);
	        throw new RuntimeException(t);
	    }
	    
	    return verificationCode;
	}

}
