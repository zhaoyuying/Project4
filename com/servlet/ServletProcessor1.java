
package com.servlet;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.web.Constants;
import com.web.Request;
import com.web.Response;

public class ServletProcessor1 {

	public void process(Request request, Response response) {

		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		
		System.out.println("webroot:"+Constants.WEB_ROOT);
		System.out.println("web_service_root"+Constants.WEB_SERVLET_ROOT);

		// ������������ڴ�ָ��JAR�ļ���Ŀ¼������
		URLClassLoader loader = null;
		try {
			// �����������
			loader = new URLClassLoader(new URL[] { new URL(null, Constants.WEB_SERVLET_ROOT) });
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		Class<?> myClass = null;
		try {
			// ���ض�Ӧ��servlet��
			myClass = loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		Servlet servlet = null;

		try {
			// ����servletʵ��
			servlet = (Servlet) myClass.newInstance();
			// ִ��ervlet��service����
			servlet.service((ServletRequest) request,
					(ServletResponse) response);
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}

	}

}
