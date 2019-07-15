/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.io;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
* @author wstevens
*/
class StringWriterServletResponseAdapter extends HttpServletResponseWrapper implements HttpServletResponseWriterAdapter
{
	private static final Logger logger = LogManager.manager().newLogger(StringWriterServletResponseAdapter.class, Category.IO);
	private StringWriter targetStringWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    
    StringWriterServletResponseAdapter(HttpServletResponse response) {
        super(response);
        targetStringWriter = new StringWriter();
        bufferedWriter = new BufferedWriter(targetStringWriter);
        printWriter = new PrintWriter(bufferedWriter);        
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

	@Override
	public void flushBuffer() throws IOException {
		super.flushBuffer();
		flush();
	}

	@Override
    public String getContents() {
    	return targetStringWriter.toString();
    }

	@Override
	public void close() throws IOException {
		try {
			printWriter.close();
        } catch (Exception e) {
            logger.error("Error when attempting to close printWriter on JSP.", e);
        }
		try {
			bufferedWriter.close();
        } catch (Exception e) {
            logger.error("Error when attempting to close bufferedWriter on JSP.", e);
        }
		try {
			targetStringWriter.close();
        } catch (Exception e) {
            logger.error("Error when attempting to close targetStringWriter on JSP.", e);
        }
	}

	@Override
	public void flush() throws IOException {
		try {
			printWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush printWriter", e);
		}
		try {
            // Exception was being thrown here for stream-already closed
//			bufferedWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush printWriter", e);
		}
		try {
			targetStringWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush printWriter", e);
		}
	}

	@Override
	public byte[] getContentsBytes() {
		return targetStringWriter.toString().getBytes(Constants.CHARSET_UTF_8);
	}
}
