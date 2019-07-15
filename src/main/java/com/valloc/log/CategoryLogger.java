/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.log;


import com.valloc.object.domain.ApplicationError;
import com.valloc.object.pojo.ApplicationErrorPojo;
import com.valloc.service.ApplicationService;

/**
 * 
 *
 * @author wstevens
 */
final class CategoryLogger implements Logger
{
	private Handler handler;
	private Class<?> clazz;
	private ApplicationService applicationService;

	// optimizations
	private boolean isInfo;
	private boolean isFine;
	private boolean isFiner;
	private boolean isFinest;
	
	CategoryLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void error(String message, Throwable throwable) {
		handler.error(clazz, message, throwable);
        // if the applicationService has yet been provided (after startup) then record in database
        if (applicationService != null) {
            ApplicationError applicationError = new ApplicationErrorPojo(throwable, message);
            applicationService.addApplicationErrorStats(applicationError);
        }
	}

	@Override
	public void error(String message, Throwable throwable, Object... args) {
		handler.error(clazz, message, throwable, args);
        // record in database
        ApplicationError applicationError = new ApplicationErrorPojo(throwable, String.format(message, args));
//        applicationService.addApplicationErrorStats(applicationError);
	}

	@Override
	public void warn(String message) {
		handler.warn(clazz, message);
	}

	@Override
	public void warn(String message, Object... args) {
		handler.warn(clazz, message, args);
	}

	@Override
	public void info(String message) {
		if (isInfo()) {
			handler.info(clazz, message);
		}
	}

	@Override
	public void info(String message, Object... args) {
		if (isInfo) {
			handler.info(clazz, message, args);
		}
	}

	@Override
	public void fine(String message) {
		if (isFine) {
			handler.fine(clazz, message);
		}
	}

	@Override
	public void fine(String message, Object... args) {
		if (isFine) {
			handler.fine(clazz, message, args);
		}
	}

	@Override
	public void finer(String message) {
		if (isFiner) {
			handler.finer(clazz, message);
		}
	}

	@Override
	public void finer(String message, Object... args) {
		if (isFiner) {
			handler.finer(clazz, message, args);
		}
	}

	@Override
	public void finest(String message) {
		if (isFinest) {
			handler.finest(clazz, message);
		}
	}

	@Override
	public void finest(String message, Object... args) {
		if (isFinest) {
			handler.finest(clazz, message, args);
		}
	}

	@Override
	public boolean isInfo() {
		return isInfo;
	}

	@Override
	public boolean isFine() {
		return isFine;
	}

	@Override
	public boolean isFiner() {
		return isFiner;
	}

	@Override
	public boolean isFinest() {
		return isFinest;
	}

	@Override
	public void setLevel(Level level) {
		isInfo 		= level.isLevelEnabled(Level.INFO);
		isFine 		= level.isLevelEnabled(Level.FINE);
		isFiner 	= level.isLevelEnabled(Level.FINER);
		isFinest 	= level.isLevelEnabled(Level.FINEST);
	}

	@Override
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

    @Override
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}
