/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.io;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author wstevens
 */
public class IoUtil
{
	private static final Logger logger = LogManager.manager().newLogger(IoUtil.class, Category.IO);
    private static final String FILE_EXTENSION = Constants.DOT + "html";
    @Autowired private ConfigurationService configurationService;

    public JspWriterAdapter newStringWriterJspAdapter(HttpServletRequest request, HttpServletResponse response, String jspPath) {
    	StringWriterServletResponseAdapter responseAdapter = new StringWriterServletResponseAdapter(response);
    	return new JspWriterAdapter(request, responseAdapter, jspPath);
    }
    
    public JspWriterAdapter newFileWriterResponseAdapter(HttpServletRequest request, HttpServletResponse response, String jspPath, File targtFile) {
    	FileWriterServletResponseAdapter responseAdapter = new FileWriterServletResponseAdapter(response, targtFile);
    	return new JspWriterAdapter(request, responseAdapter, jspPath);
    }

    public JspWriterAdapter newUserPageFileWriterResponseAdapter(HttpServletRequest request, HttpServletResponse response, String jspPath, String username, String pagePath) {
    	Path pageFile = buildPageFilePath(username, pagePath);
    	File targetFile = pageFile.toFile();
    	if (logger.isFine()) {
			logger.fine("Created basic file object for page file creation: " + targetFile);
		}
    	FileWriterServletResponseAdapter responseAdapter = new FileWriterServletResponseAdapter(response, targetFile);    	
    	return new JspWriterAdapter(request, responseAdapter, jspPath);
    }
    
    public byte[] readPageFileIntoByteArray(String username, String pagePath) {
    	byte[] contents = null;
    	Path pageFile = retrieveFilePath(username, pagePath);
    	if (pageFile != null) {
	    	BufferedOutputStream bufferedOutputStream = null;
	    	ByteArrayOutputStream byteArrayOutputStream = null;
	    	try {
				byteArrayOutputStream = new ByteArrayOutputStream();
				bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
				Files.copy(pageFile, bufferedOutputStream);
				bufferedOutputStream.flush();
				contents = byteArrayOutputStream.toByteArray();
				if (logger.isFinest()) {
					logger.finest("Read file byte[] contents are\n: %s\n.", contents);
				}
			} catch (IOException e) {
				logger.error("File %s could not be found due to the following error: " + e, e, pageFile);
			} finally {
				try { 
					bufferedOutputStream.close();
				} catch (IOException e) {
					logger.error("Error when closing bufferedOutputStream: " + e, e);
				}
				try { 
					byteArrayOutputStream.close();
				} catch (IOException e) {
					logger.error("Error when closing byteArrayOutputStream: " + e, e);
				}
			}
    	}
    	return contents;
    }
    
    private Path buildPageFilePath(String username, String pagePath) {    	
    	// first retrieve app temp directory
    	Path appTmpDir = getAppPageFileDirectory();
    	// check and build user's home directory if necessary
    	Path usersDir = appTmpDir.resolve(username);
    	if (Files.notExists(usersDir)) {
    		// might not already exist if this is first time creating file for user 
    		try {
				Files.createDirectory(usersDir);
                if (logger.isFiner()) {
                    logger.finer("Created user directory /%s in the tmp.", username);
                }
			} catch (IOException e) {
				logger.error("Unexpected error when creating user's directory %s.", e, usersDir);
			}
    	}
    	// build user page file which should NOT already exist
        pagePath = buildPageFileName(pagePath);
    	Path pageFilePath = usersDir.resolve(pagePath);
    	if (Files.exists(pageFilePath)) {
    		logger.warn("User page file \"%s\" was found to already exist during creation. File will be deleted first.", pageFilePath);
            try {
                Files.delete(pageFilePath);
                if (logger.isFiner()) {
                	logger.finer("Deleted file that was found to already exist: %s.", pageFilePath);
                }
            } catch (IOException e) {
                logger.error("Exception when attempting to delete file %s.", e, pageFilePath);
            }
        }
    	Path pageFile = null;
		try { // now create file for user
			pageFile = Files.createFile(pageFilePath);
			if (logger.isFiner()) {
				logger.finer("Created path object for user %s and page path %s located at %s.", username, pagePath, pageFile);
			}
		} catch (IOException e) {
			logger.error("Unexpected error when creating file %s.", e, pageFile);
		}
		return pageFile;
    }
    
    private Path retrieveFilePath(String username, String pagePath) {  
    	Path retval = null;
    	// first check for app temp directory
    	Path appTmpDir = getAppPageFileDirectory();
    	if (Files.notExists(appTmpDir)) {
    		String msg = String.format("App page file directory \"%s\" was not found to exist.", configurationService.getPageFileDir());
    		IllegalStateException e = new IllegalStateException(msg);
    		logger.error(msg, e);
    		throw e;
    	}
    	// check and build user's home directory if necessary
    	Path usersDir = appTmpDir.resolve(username);
    	if (Files.exists(usersDir)) {
            String fileName = buildPageFileName(pagePath);
    		Path userPagePath = usersDir.resolve(fileName);
    		if (Files.exists(userPagePath)) {
    			retval = userPagePath;
    		} 
    	}
		if (logger.isFiner()) {
			logger.finer("Retrieving found the following Path object for username %s and page path %s: %s.", username, pagePath, retval);
		}
		return retval;
    }
    
    public void writeByteArrayToOutputStream(byte[] source, OutputStream target) {
    	try {
    		target.write(source);
    	} catch (IOException e) {
    		logger.error("Error while writing byte array to output stream: " + e, e);
    	}
    }
    
    public void deletePageFile(String username, String pagePath) {
    	try {
    		Path pageFilePath = retrieveFilePath(username, pagePath);
			if (pageFilePath != null) {
				Files.delete(pageFilePath);
				if (logger.isFiner()) {
					logger.finer("Deleted user (%s) file for page /%s with Path object: %s.", username, pagePath, pageFilePath);
				}
			} else {
                if (logger.isFiner()) {
                	logger.finer("Could not delete the following file since the username %s and page path %s could not be resolved: %s.", username, pagePath, pageFilePath);
                }
            }
		} catch (Exception e) {
			logger.error("Error when attempting to delete page file for username %s and page path %s.", e, username, pagePath);
		}
    }

    public Path getAppPageFileDirectory() {
        Path pageFileDir = null;
        String userHome = System.getProperty("user.home");
        String pageFileDirStr = configurationService.getPageFileDir();
        if (logger.isFiner()) {
            logger.finer("user.home is: \"%s\" and configured page file dir is \"%s\".", userHome, pageFileDirStr);
        }
        pageFileDir = Paths.get(userHome, pageFileDirStr);
        if (logger.isFiner()) {
            logger.finer("Page file directory was reconciled to \"%s\".", pageFileDir);
        }
        // if the temp directory does not exist, then now is good time to create it
        if (Files.notExists(pageFileDir)) {
            if (logger.isInfo()) {
                logger.info("App page file directory \"%s\" was not found to exist so it will be created.", configurationService.getPageFileDir());
            }
            try {
                Files.createDirectory(pageFileDir);
                if (logger.isInfo()) {
                    logger.info("Created temp directory /%s.", pageFileDir);
                }
            } catch (IOException e) {
                logger.error("Unexpected error when creating temp directory %s.", e, pageFileDir);
            }
        }
        return pageFileDir;
    }

    private String buildPageFileName(String path) {
        StringBuilder returnPath = new StringBuilder(path);
        if (path.startsWith(Constants.FORWARD_SLASH_STR)) {
            returnPath.delete(0, 1); // chop off forward slash
        }
        returnPath.append(FILE_EXTENSION);
        return returnPath.toString();
    }
}
