/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.io;

import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.Flushable;

/**
 *
 *
 * 
 * @author wstevens
 */
public interface HttpServletResponseWriterAdapter extends HttpServletResponse, Flushable, Closeable, StringResponseWriter 
{
}
