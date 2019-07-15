/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.framework.ServiceResult;
import com.valloc.io.JspWriterAdapter;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.User;
import com.valloc.object.dto.PageDesignerDto;
import com.valloc.object.dto.PageDto;
import com.valloc.object.dto.PageGuestDto;

import java.io.InputStream;


/**
 * 
 *
 * @author wstevens
 */
public interface PageService extends VallocService
{
    public ServiceResult<PageGuestDto> searchForPageGuest(PageGuestDto pageGuestDto);
    public ServiceResult<PageDesignerDto> loadPageNewPage();
    public ServiceResult<PageDesignerDto> loadPageEditPage(User user, String path);
    public ServiceResult<PageDesignerDto> loadPageGuestEditPage(User guestUser, String ownerUsername, String path);
    public ServiceResult<PageDesignerDto> savePageGuestEdit(PageDto pageDto, User guestUser, JspWriterAdapter jspWriterAdapter);
    public ServiceResult<PageDesignerDto> newPage(PageDesignerDto pageDto, User user, JspWriterAdapter jspWriterAdapter);
    public ServiceResult<PageDesignerDto> editPage(PageDesignerDto pageDto, User user, JspWriterAdapter jspWriterAdapter);
    public ServiceResult<?> removePage(User user, String path);
    public ServiceResult<PageDesignerDto> importPage(User user, InputStream inputStream);
    public ServiceResult<?> generatePageFile(String username, String path, JspWriterAdapter jspWriterAdapter);
    public ServiceResult<?> pinLink(User user, String pagePath, String sectionName, String linkTitle, String linkUrl, String linkNote, JspWriterAdapter jspWriterAdapter);
    public ServiceResult<Page> findGuestModifiablePage(User guestUser, String ownerUsername, String pagePath);
}
