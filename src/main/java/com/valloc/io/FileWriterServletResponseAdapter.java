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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
* @author wstevens
*/
class FileWriterServletResponseAdapter extends HttpServletResponseWrapper implements HttpServletResponseWriterAdapter, StringResponseWriter
{
	private static final Logger logger = LogManager.manager().newLogger(FileWriterServletResponseAdapter.class, Category.IO);
    private StringAndFileWriterAdapter writerAdapter;
    
    FileWriterServletResponseAdapter(HttpServletResponse response, File file) {
        super(response);
        writerAdapter = new StringAndFileWriterAdapter(file);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writerAdapter;
    }

	@Override
	public void flushBuffer() throws IOException {
		super.flushBuffer();
		flush();
	}

	@Override
	public void close() throws IOException {
		try {
			writerAdapter.close();
		} catch (Exception e) {
        	logger.error("Problem when attempting to close file.", e);
		}
	}

	@Override
	public void flush() throws IOException {
		try {
			writerAdapter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush file.", e);
		}
	}

	@Override
	public String getContents() {
		return writerAdapter.getContents();
	}

	@Override
	public byte[] getContentsBytes() {
		return writerAdapter.getContents().getBytes(Constants.CHARSET_UTF_8);
	}

}
