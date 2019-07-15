/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.io;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
* @author wstevens
*/
public class JspWriterAdapter implements StringResponseWriter
{
    private static final Logger logger = LogManager.manager().newLogger(JspWriterAdapter.class, Category.IO);
    private HttpServletRequest request;
    private HttpServletResponseWriterAdapter response;
    private String jspPath;

    private ModelAndView modelStore;

    public JspWriterAdapter(HttpServletRequest request, HttpServletResponseWriterAdapter response, String jspPath) {
        this.request = request;
        this.response = response;
        this.jspPath = jspPath;
    }

    public boolean write() {
    	boolean wasSuccessfulWrite = false;
        try {
            if (logger.isFiner()) {
                logger.finer("About to write to alternative response output for JSP %s.", jspPath);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
            // if model store is set and populated for forward then load values into request
            if (modelStore != null) {
                Map<String, Object> modelMap = modelStore.getModel();
                for (String key : modelMap.keySet()) {
                    Object value = modelMap.get(key);
                    request.setAttribute(key, value);
                }
            }

            // issue forward
            dispatcher.forward(request, response);

            response.flush();
            wasSuccessfulWrite = true;
            if (logger.isFiner()) {
                logger.finer("Successfull write to alternative output source for JSP %s.", jspPath);
            }
            if (logger.isFinest()) {
				logger.finest("String contents are: " + getContents());
			}
            if (logger.isFinest()) {
				logger.finest("byte[] contents (as %s String) are: %s.", Constants.CHARSET_UTF_8, new String(getContentsBytes(), Constants.CHARSET_UTF_8));
			}
        } catch (Exception e) {
            logger.error("Error occurred during alternative response output writing: %s.", e, e);
        } finally {
        	close();
        }
        return wasSuccessfulWrite;
    }

	@Override
	public String getContents() {
		return response.getContents();
	}

	@Override
	public byte[] getContentsBytes() {
		return response.getContentsBytes();
	}
    
    public void setJspAttribute(String key, Object value) {
    	request.setAttribute(key, value);
    }

    public void close() {
        try {
            response.close();
        } catch (IOException e) {
            // log and continue on
            logger.error("Error when attempting to close response on JSP %s.", e, jspPath);
        }
    }

    public ModelAndView getModelStore() {
        return modelStore;
    }

    public void setModelStore(ModelAndView modelStore) {
        this.modelStore = modelStore;
    }
}
