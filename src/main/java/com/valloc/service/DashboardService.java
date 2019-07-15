/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.framework.ServiceResult;
import com.valloc.object.domain.User;
import com.valloc.object.dto.DashboardListingPagesDto;
import com.valloc.object.dto.PageDto;

import java.util.List;


/**
 * 
 *
 * @author wstevens
 */
public interface DashboardService extends VallocService
{
	public ServiceResult<DashboardListingPagesDto> getAccessiblePages(User user);
    public ServiceResult<List<PageDto>> getUserPagesAndSections(User user);
}
