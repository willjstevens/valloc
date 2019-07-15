/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import static com.valloc.SpecConstants.*;

/**
 *
 *
 * 
 * @author wstevens
 */
public class SpecDto
{
	/*
	 * Account related
	 */
    @JsonProperty(value=USERNAME_MIN_SIZE_KEY)
	public int usernameMinSize = USERNAME_MIN_SIZE_VALUE;

    @JsonProperty(value=USERNAME_MAX_SIZE_KEY)
	public int usernameMaxSize = USERNAME_MAX_SIZE_VALUE;

    @JsonProperty(value=USERNAME_PATTERN_KEY)
	public String usernamePattern = USERNAME_PATTERN_VALUE;
    
    @JsonProperty(value=EMAIL_MIN_SIZE_KEY)
	public int emailMinSize = EMAIL_MIN_SIZE_VALUE;

    @JsonProperty(value=EMAIL_MAX_SIZE_KEY)
	public int emailMaxSize = EMAIL_MAX_SIZE_VALUE;

    @JsonProperty(value=FIRST_NAME_MIN_SIZE_KEY)
	public int firstNameMinSize = FIRST_NAME_MIN_SIZE_VALUE;

    @JsonProperty(value=FIRST_NAME_MAX_SIZE_KEY)
	public int firstNameMaxSize = FIRST_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=LAST_NAME_MIN_SIZE_KEY)
	public int lastNameMinSize = LAST_NAME_MIN_SIZE_VALUE;

    @JsonProperty(value=LAST_NAME_MAX_SIZE_KEY)
	public int lastNameMaxSize = LAST_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_MIN_SIZE_KEY)
	public int passwordMinSize = PASSWORD_MIN_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_MAX_SIZE_KEY)
	public int passwordMaxSize = PASSWORD_MAX_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_QUESTION_MIN_SIZE_KEY)
	public int passwordQuestionMinSize = PASSWORD_QUESTION_MIN_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_QUESTION_MAX_SIZE_KEY)
	public int passwordQuestionMaxSize = PASSWORD_QUESTION_MAX_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_ANSWER_MIN_SIZE_KEY)
	public int passwordAnswerMinSize = PASSWORD_ANSWER_MIN_SIZE_VALUE;

    @JsonProperty(value=PASSWORD_ANSWER_MAX_SIZE_KEY)
	public int passwordAnswerMaxSize = PASSWORD_ANSWER_MAX_SIZE_VALUE;

    /*
     * Designer related
     */
    @JsonProperty(value=PAGE_NAME_MIN_SIZE_KEY)
    public int pageNameMinSize = PAGE_NAME_MIN_SIZE_VALUE;

    @JsonProperty(value=PAGE_NAME_MAX_SIZE_KEY)
    public int pageNameMaxSize = PAGE_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=PAGE_PATH_MIN_SIZE_KEY)
    public int pagePathMinSize = PAGE_PATH_MIN_SIZE_VALUE;

    @JsonProperty(value=PAGE_PATH_MAX_SIZE_KEY)
    public int pagePathMaxSize = PAGE_PATH_MAX_SIZE_VALUE;

    @JsonProperty(value=PAGE_DESCRIPTION_MIN_SIZE_KEY)
    public int pageDescriptionMinSize = PAGE_DESCRIPTION_MIN_SIZE_VALUE;

    @JsonProperty(value=PAGE_DESCRIPTION_MAX_SIZE_KEY)
    public int pageDescriptionMaxSize = PAGE_DESCRIPTION_MAX_SIZE_VALUE;

    @JsonProperty(value=SECTION_NAME_MIN_SIZE_KEY)
    public int sectionNameMinSize = SECTION_NAME_MIN_SIZE_VALUE;

    @JsonProperty(value=SECTION_NAME_MAX_SIZE_KEY)
    public int sectionNameMaxSize = SECTION_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=SECTION_NOTE_MIN_SIZE_KEY)
    public int sectionNoteMinSize = SECTION_NOTE_MIN_SIZE_VALUE;

    @JsonProperty(value=SECTION_NOTE_MAX_SIZE_KEY)
    public int sectionNoteMaxSize = SECTION_NOTE_MAX_SIZE_VALUE;

    @JsonProperty(value=LINK_NAME_MIN_SIZE_KEY)
    public int linkNameMinSize = LINK_NAME_MIN_SIZE_VALUE;

    @JsonProperty(value=LINK_NAME_MAX_SIZE_KEY)
    public int linkNameMaxSize = LINK_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=LINK_URL_MIN_SIZE_KEY)
    public int linkUrlMinSize = LINK_URL_MIN_SIZE_VALUE;

    @JsonProperty(value=LINK_URL_MAX_SIZE_KEY)
    public int linkUrlMaxSize = LINK_URL_MAX_SIZE_VALUE;

    @JsonProperty(value=LINK_NOTE_MIN_SIZE_KEY)
    public int linkNoteMinSize = LINK_NOTE_MIN_SIZE_VALUE;

    @JsonProperty(value=LINK_NOTE_MAX_SIZE_KEY)
    public int linkNoteMaxSize = LINK_NOTE_MAX_SIZE_VALUE;

    /*
     * Feedback related
     */
    @JsonProperty(value=FEEDBACK_NAME_MAX_SIZE_KEY)
    public int feedbackNameMaxSize = FEEDBACK_NAME_MAX_SIZE_VALUE;

    @JsonProperty(value=FEEDBACK_EMAIL_MAX_SIZE_KEY)
    public int feedbackEmailMaxSize = FEEDBACK_EMAIL_MAX_SIZE_VALUE;

    @JsonProperty(value=FEEDBACK_COMMENT_MAX_SIZE_KEY)
    public int feedbackCommentMaxSize = FEEDBACK_COMMENT_MAX_SIZE_VALUE;
}
