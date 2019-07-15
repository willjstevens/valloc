package com.valloc.web;

import com.valloc.Category;
import com.valloc.Visibility;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.ObjectConverter;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.dto.*;
import com.valloc.object.persistent.PersistentUser;
import com.valloc.service.AccountService;
import com.valloc.service.ConfigurationService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.valloc.UrlConstants.*;

/**
 * Handles requests for the application home page.
 */
//@Controller
@RequestMapping(value="/stub")
public class StubController extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(StubController.class, Category.TEST);
	private static final String ERROR_URL = "/error";
	@Autowired private ConfigurationService configurationService;
	@Autowired private UtilityService utilityService;
	@Autowired private AccountService accountService;
	@Autowired private SessionManager sessionManager;
	@Autowired private ObjectConverter objectConverter;

	@RequestMapping(value=ACCOUNT_CREATE_POST, method=RequestMethod.POST)
	@ResponseBody 
	public ResponseDto accountCreatePost(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam String username,
                                         @RequestParam String email,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String birthdate,
                                         @RequestParam String gender,
                                         @RequestParam String password,
                                         @RequestParam String passwordConfirmation,
                                         @RequestParam String passwordQuestion,
                                         @RequestParam String passwordAnswer,
                                         @RequestParam boolean inAgreement) {
		ResponseDto responseDto = null;
		ServiceResult<?> serviceResult = new ServiceResult<>();
		serviceResult.setSuccess();
		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}
	
	
	@RequestMapping(value=ACCOUNT_CREATE_POST + ERROR_URL, method=RequestMethod.POST)
	@ResponseBody 
	public ResponseDto createPostErrors(HttpServletRequest request, 
									HttpServletResponse response, 
									@RequestParam String username,
									@RequestParam String email,
									@RequestParam String firstName,
									@RequestParam String lastName,
									@RequestParam String birthdate,
									@RequestParam String gender,
									@RequestParam String password,
									@RequestParam String passwordConfirmation,									
									@RequestParam String passwordQuestion,
									@RequestParam String passwordAnswer,
									@RequestParam boolean inAgreement) 
	{
		ResponseDto responseDto = null;
		// purposely cause errors which will be validated
		username = "";
		passwordConfirmation = password + "123";
		inAgreement = false;
		// now set values and invoke service to hit validations
		AccountForm accountForm = 
				new AccountForm(username, email, firstName, lastName, birthdate, gender, password, passwordConfirmation, passwordQuestion, passwordAnswer, inAgreement);		
		ServiceResult<PersistentUser> serviceResult = accountService.addUser(accountForm);
		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}
	
	
	@RequestMapping(value=ACCOUNT_EDIT_LOAD) 
	@ResponseBody 
	public AccountDto accountEditLoad(HttpSession httpSession) {
		AccountDto accountDto = new AccountDto();
		accountDto.username = "wstevens";
		accountDto.email = "willjstevens@gmail.com";
		accountDto.firstName = "Will";
		accountDto.lastName = "Stevens";
		accountDto.birthdate = "12/28/1979";
		accountDto.gender = "female";
		accountDto.password = "password";
		accountDto.passwordConfirmation = "password";
		accountDto.passwordQuestion = "Favorite pet";
		accountDto.passwordAnswer = "Rascal";
		accountDto.inAgreement = true;
		sessionManager.createStubSession(accountDto.username, httpSession);
		return accountDto;
	}
	
	
	@RequestMapping(value=ACCOUNT_EDIT_POST, method=RequestMethod.POST)
	@ResponseBody 
	public ResponseDto editPost(@RequestParam String password,									
									@RequestParam String passwordConfirmation,									
									@RequestParam String passwordQuestion,
									@RequestParam String passwordAnswer,
									HttpSession httpSession) {
		ResponseDto responseDto = null;
		ServiceResult<?> serviceResult = new ServiceResult<>();
		serviceResult.setSuccess();
		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}
	
	
	@RequestMapping(value=ACCOUNT_EDIT_POST + ERROR_URL, method=RequestMethod.POST)
	@ResponseBody 
	public ResponseDto editPostErrors(@RequestParam String password,									
									@RequestParam String passwordConfirmation,									
									@RequestParam String passwordQuestion,
									@RequestParam String passwordAnswer,
									HttpSession httpSession) 
	{
		ResponseDto responseDto = null;
		AccountForm accountForm = new AccountForm();
		accountForm.password = password;
		accountForm.passwordConfirmation = passwordConfirmation + "X";
		accountForm.passwordQuestion = "Dear Mr. Jonathan Sarno: Oh freddled gruntbuggly thy micturations are to me As plurdled gabbleblotchits on a lurgid bee. Groop I implore thee, my foonting turlingdromes. And hooptiously drangle me with crinkly bindlewurdles, Or I will rend thee in the gobberwarts with my blurglecruncheon, see if I don't!";
//		accountForm.passwordQuestion = "Favorite Dog";
		accountForm.passwordAnswer = passwordAnswer;
		accountForm.username = sessionManager.getSessionByHttpSession(httpSession).getUsername();
		ServiceResult<?> serviceResult = accountService.editUser(accountForm);
		
		UserMessage usernameTaken = userMessage("account_username_validate_taken");
		serviceResult.addSupportingUserMessage(usernameTaken);
		
		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}
	

	@RequestMapping(value=DASHBOARD)
	public ModelAndView dashboardGet() {
		ModelAndView model = new ModelAndView();
		model.setViewName("dashboard");
//		loadCommonModelData(model);
//		loadCommonDashboardModelData(model);
        loadModel(model);

		DashboardListingPagesDto dto = new DashboardListingPagesDto();
        dto.homePath = "home";

        DashboardListingPageDto page = newDashboardListingPage();
		page.setName("Home Page");
		page.setHome(true);
        page.setHomeMessage("Current home");
        page.setPath("home");
		page.setDescription("My home page.");
        page.setDisplayPath("/wstevens/home");
		dto.pages.add(page);

        page = newDashboardListingPage();
        page.setVisibility(Visibility.PRIVATE);
        page.setName("My Personal Page");
        page.setPath("personal");
        page.setDisplayPath("/wstevens/personal");
        page.setDescription("My personsal stuff for no one elses business.");
		dto.pages.add(page);

        page = newDashboardListingPage();
        page.setName("Top-secret Work Page");
        page.setPath("work");
        page.setDisplayPath("/wstevens/work");
        page.setSharedPrivately(true);
        page.setVisibility(Visibility.PRIVATE);
        page.setDescription("Stuff shared with business partners.");
        page.setListingMessage("This page is shared privately among other guest users that only you have granted access to.");
        dto.pages.add(page);

        page = newDashboardListingPage();
        page.setUsername("acme");
        page.setName("Partner Company Research Page");
        page.setPath("research");
        page.setDisplayPath("/acme/research");
        page.setSharedPrivately(true);
        page.setGuestPage(true);
        page.setOwnerFirstName("Joe");
        page.setOwnerLastName("McFearson");
        page.setOwnerPage(false);
        page.setVisibility(Visibility.PRIVATE);
        page.setDescription("We modify our partner company research page.");
        page.setListingMessage("You are a guest of this page owned by Bill Smith. You have access to modify limited page attributes.");
        dto.pages.add(page);

        page = newDashboardListingPage();
        page.setName("Fun Stuff");
        page.setPath("fun");
        page.setDisplayPath("/wstevens/fun");
        page.setDescription("Bozo the clown crap noone cares about.");
        dto.pages.add(page);

		loadCustomMessage(model, "dashboard_pageTitle", "Will");
        loadCustomMessage(model, "dashboard_pageHeader", "Will");

		String json = utilityService.jsonStringify(dto);
		model.addObject("pld", json);
		logger.fine(json);
		
		return model;
	}
	
	public static DashboardListingPageDto newDashboardListingPage() {
        DashboardListingPageDto page = new DashboardListingPageDto();
		page.setName("Development");
		page.setPath("path");
        page.setDisplayPath("/wstevens/path");
		page.setSharedPrivately(false);
		page.setVisibility(Visibility.PUBLIC);
		page.setDescription("Links used for Development");
		page.setHome(false);
        page.setHomeMessage("Set as home");
        page.setOwnerPage(true);
        page.setGuestPage(false);
		return page;
	}

    @RequestMapping(value=PAGE_NEW)
    public ModelAndView pageNewGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("designer");
//        loadCommonModelData(mav);
//        loadCommonDesignerModelData(mav);
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value=PAGE_EDIT_GET)
    public ModelAndView pageEditGet(@PathVariable String path) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("designer");
//        mav.setViewName("designer-test-this");

//        loadCommonModelData(mav);
//        loadCommonDesignerModelData(mav);
        loadModel(mav);

        // before refactoring on 20130729
//        PageDesignerDto dto = newPageEditDto();
//        dto.setOriginalPath(dto.getFullPath());
//        String json = utilityService.jsonStringify(dto);
//        mav.addObject("pld", json);
//        logger.fine(json);

        PageDesignerDto pageDesignerDto = new PageDesignerDto();
        PageDto pageDto = newPageEditDto();
        pageDesignerDto.setOriginalPath(pageDto.getPath());
        pageDesignerDto.setIsEdit(true);
        String json = utilityService.jsonStringify(pageDesignerDto);
        mav.addObject("pld", json);
        logger.fine(json);


        return mav;
    }

//        public static PageDesignerDto newPageEditDto() {
//        PageDesignerDto page = new PageDesignerDto();
        public static PageDto newPageEditDto() {
        PageDto page = new PageDto();
        page.setUsername("will");
        page.setName("Development");
        page.setPath("/dev");
        page.setPath("/will/dev");
        page.setSharedPrivately(false);
        page.setVisibility(Visibility.PRIVATE);
        page.setDescription("Links used for Development");
        page.setHome(true);

        // first column data
        LinkDto linkFacebook = new LinkDto();
        linkFacebook.setName("Facebook");
        linkFacebook.setUrl("http://www.facebook.com");
        LinkDto linkTwitter = new LinkDto();
        linkTwitter.setName("Twitter");
        linkTwitter.setUrl("http://www.twitter.com");
        SectionDto sectionSocial = new SectionDto();
        sectionSocial.setName("Social");
        sectionSocial.addLink(linkFacebook);
        sectionSocial.addLink(linkTwitter);
        ColumnDto col1 = new ColumnDto();
        col1.addSection(sectionSocial);
        page.addColumn(col1);

        // second column data
        LinkDto linkGoogle = new LinkDto();
        linkGoogle.setName("Google");
        linkGoogle.setUrl("http://www.google.com");
        SectionDto sectionStandard = new SectionDto();
        sectionStandard.setName("Standard");
        sectionStandard.addLink(linkGoogle);
        ColumnDto col2 = new ColumnDto();
        col2.addSection(sectionStandard);
        page.addColumn(col2);

        // third column data
        LinkDto linkHome = new LinkDto();
        linkHome.setName("Valloc");
        linkHome.setUrl("http://www.valloc.com/~will");
        LinkDto linkDev = new LinkDto();
        linkDev.setName("Valloc Dev");
        linkDev.setUrl("http://www.valloc.com/~will/dev");
        SectionDto sectionHome = new SectionDto();
        sectionHome.setName("Home");
        sectionHome.addLink(linkHome);
        sectionHome.addLink(linkDev);
        LinkDto pariLinkHome = new LinkDto();
        pariLinkHome.setName("Pariveda Home");
        pariLinkHome.setUrl("http://www.valloc.com/~will/pariveda");
        LinkDto pariOptier = new LinkDto();
        pariOptier.setName("OpTier");
        pariOptier.setUrl("http://www.valloc.com/~will/optier");
        SectionDto pariSectionHome = new SectionDto();
        pariSectionHome.setName("Pariveda");
        pariSectionHome.addLink(pariLinkHome);
        pariSectionHome.addLink(pariOptier);
        ColumnDto col3 = new ColumnDto();
        col3.addSection(sectionHome);
        col3.addSection(pariSectionHome);
        page.addColumn(col3);

        PageGuestDto alice = new PageGuestDto();
        alice.setUsername("aable");
        alice.setFirstName("Alice");
        alice.setLastName("Able");
        alice.setCanModify(false);
        page.addPageGuest(alice);

        PageGuestDto bob = new PageGuestDto();
        bob.setUsername("bbarker");
        bob.setFirstName("Bob");
        bob.setLastName("Barker");
        bob.setCanModify(true);
        page.addPageGuest(bob);

        PageGuestDto chris = new PageGuestDto();
        chris.setUsername("cclyde");
        chris.setFirstName("Chris");
        chris.setLastName("Clyde");
        chris.setCanModify(true);
        page.addPageGuest(chris);

        PageGuestDto dan = new PageGuestDto();
        dan.setUsername("ddaniel");
        dan.setFirstName("Dan");
        dan.setLastName("Daniel");
        dan.setCanModify(false);
        page.addPageGuest(dan);

        return page;
    }

}