
package com.web;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletResponse;
import javax.servlet.ServletOutputStream;

public class Response implements ServletResponse{
	
	private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;
    PrintWriter writer;
    
    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    
  //��web�ļ�д�뵽OutputStream�ֽ�����
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI */
            File file = new File(Constants.WEB_ROOT, request.getUri());
            fis = new FileInputStream(file);
            /*
             * HTTP Response = Status-Line(( general-header | response-header |
             * entity-header ) CRLF) CRLF [ message-body ] Status-Line =
             * HTTP-Version SP Status-Code SP Reason-Phrase CRLF
             */
            int ch = fis.read(bytes, 0, BUFFER_SIZE);
            while (ch != -1) {
                output.write(bytes, 0, ch);
                ch = fis.read(bytes, 0, BUFFER_SIZE);
            }
        } catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                    + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
                    + "\r\n" + "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        } finally {
            if (fis != null)
                fis.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {
    	// TODO Auto-generated method stub
	
    }

    @Override
    public int getBufferSize() {
    	// TODO Auto-generated method stub
    	return 0;
    }

    @Override
    public String getCharacterEncoding() {
    	// TODO Auto-generated method stub
    	return null;
    }

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}
}
