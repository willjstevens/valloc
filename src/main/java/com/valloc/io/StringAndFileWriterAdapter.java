/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.io;

import com.valloc.ApplicationException;
import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import java.io.*;
import java.util.Locale;

/**
 *
 *
 * 
 * @author wstevens
 */
final class StringAndFileWriterAdapter extends PrintWriter implements StringResponseWriter
{
	private static final Logger logger = LogManager.manager().newLogger(StringAndFileWriterAdapter.class, Category.IO);
	private File file;
	private PrintWriter filePrintWriter;
	
	private StringWriter targetStringWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter stringPrintWriter;
        
	public StringAndFileWriterAdapter(File file) {
		super(new StringWriter());

		// setup file related writers
		this.file = file;
        if (file.exists()) {
        	logger.warn("The file \"%s\" was found to already exist.", file.getAbsoluteFile());
        } else {
            try {
                file.createNewFile();
                logger.finer("Created file \"%s\" for string and file writer.", file.getAbsoluteFile());
            } catch (IOException e) {
                String msg = String.format("Problem creating file: %s", file.getAbsoluteFile());
                logger.error(msg, e);
                throw new ApplicationException(msg, e);
            }
        }
		try {
			filePrintWriter = new PrintWriter(file, Constants.DEFAULT_CHARSET_STR);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
        	String msg = String.format("Problem creating print writer for file: %s", file.getAbsoluteFile());
        	logger.error(msg, e);
        	throw new ApplicationException(msg, e);
		}  
		
		// setup string related writers
        targetStringWriter = new StringWriter();
        bufferedWriter = new BufferedWriter(targetStringWriter);
        stringPrintWriter = new PrintWriter(bufferedWriter);   
	}

	@Override
	public void flush() {
		try {
			filePrintWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush file %s", e, file.getAbsoluteFile());
		}
		try {
			stringPrintWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush stringPrintWriter", e);
		}
		try {
			bufferedWriter.flush();
		} catch (Exception e) {
        	logger.warn("Problem when attempting to flush bufferedWriter");
		}
		try {
			targetStringWriter.flush();
		} catch (Exception e) {
        	logger.error("Problem when attempting to flush targetStringWriter", e);
		}
	}

	@Override
	public void close() {
		try {
			filePrintWriter.close();
		} catch (Exception e) {
        	logger.error("Problem when attempting to close file %s", e, file.getAbsoluteFile());
		}
		try {
			stringPrintWriter.close();
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
	public boolean checkError() {
		return filePrintWriter.checkError();
	}

	@Override
	protected void setError() {
		super.setError();
	}

	@Override
	protected void clearError() {
		super.clearError();
	}

	@Override
	public void write(int c) {
		filePrintWriter.write(c);
		stringPrintWriter.write(c);
	}

	@Override
	public void write(char[] buf, int off, int len) {
		filePrintWriter.write(buf, off, len);
		stringPrintWriter.write(buf, off, len);
	}

	@Override
	public void write(char[] buf) {
		filePrintWriter.write(buf);
		stringPrintWriter.write(buf);
	}

	@Override
	public void write(String s, int off, int len) {
		filePrintWriter.write(s, off, len);
		stringPrintWriter.write(s, off, len);
	}

	@Override
	public void write(String s) {
		filePrintWriter.write(s);
		stringPrintWriter.write(s);
	}

	@Override
	public void print(boolean b) {
		filePrintWriter.print(b);
		stringPrintWriter.print(b);
	}

	@Override
	public void print(char c) {
		filePrintWriter.print(c);
		stringPrintWriter.print(c);
	}

	@Override
	public void print(int i) {
		filePrintWriter.print(i);
		stringPrintWriter.print(i);
	}

	@Override
	public void print(long l) {
		filePrintWriter.print(l);
		stringPrintWriter.print(l);
	}

	@Override
	public void print(float f) {
		filePrintWriter.print(f);
		stringPrintWriter.print(f);
	}

	@Override
	public void print(double d) {
		filePrintWriter.print(d);
		stringPrintWriter.print(d);
	}

	@Override
	public void print(char[] s) {
		filePrintWriter.print(s);
		stringPrintWriter.print(s);
	}

	@Override
	public void print(String s) {
		filePrintWriter.print(s);
		stringPrintWriter.print(s);
	}

	@Override
	public void print(Object obj) {
		filePrintWriter.print(obj);
		stringPrintWriter.print(obj);
	}

	@Override
	public void println() {
		filePrintWriter.println();
		stringPrintWriter.println();
	}

	@Override
	public void println(boolean x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(char x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(int x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(long x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(float x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(double x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(char[] x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(String x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public void println(Object x) {
		filePrintWriter.println(x);
		stringPrintWriter.println(x);
	}

	@Override
	public PrintWriter printf(String format, Object... args) {
		stringPrintWriter.printf(format, args);
		return filePrintWriter.printf(format, args);
	}

	@Override
	public PrintWriter printf(Locale l, String format, Object... args) {
		stringPrintWriter.printf(l, format, args);
		return filePrintWriter.printf(l, format, args);
	}

	@Override
	public PrintWriter format(String format, Object... args) {
		stringPrintWriter.format(format, args);
		return filePrintWriter.format(format, args);
	}

	@Override
	public PrintWriter format(Locale l, String format, Object... args) {
		stringPrintWriter.format(l, format, args);
		return filePrintWriter.format(l, format, args);
	}

	@Override
	public PrintWriter append(CharSequence csq) {
		stringPrintWriter.append(csq);
		return filePrintWriter.append(csq);
	}

	@Override
	public PrintWriter append(CharSequence csq, int start, int end) {
		stringPrintWriter.append(csq, start, end);
		return filePrintWriter.append(csq, start, end);
	}

	@Override
	public PrintWriter append(char c) {
		stringPrintWriter.append(c); 
		return filePrintWriter.append(c);
	}

    @Override
    public String getContents() {
        return targetStringWriter.toString();
    }

    @Override
    public byte[] getContentsBytes() {
        return targetStringWriter.toString().getBytes(Constants.CHARSET_UTF_8);
    }
}
