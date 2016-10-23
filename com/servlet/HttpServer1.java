
package com.servlet;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import com.web.Request;
import com.web.Response;
import com.web.StaticResourceProcessor;
import com.servlet.ServletProcessor1;

public class HttpServer1 {
	// �رշ�������
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public static void main(String[] args) {
		HttpServer1 server = new HttpServer1();
		// �ȴ���������
		server.await();
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			// �������׽��ֶ���
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// ѭ���ȴ�����
		while (true) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				// �ȴ����ӣ����ӳɹ��󣬷���һ��Socket����
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				// ����Request���󲢽���
				Request request = new Request(input);
				request.parse();
				// ����Ƿ��ǹرշ�������
				if (request.getUri().equals(SHUTDOWN_COMMAND)) {
					System.out.println("����ر�");
					break;
				}

				// ���� Response ����
				Response response = new Response(output);
				response.setRequest(request);

				if (request.getUri().startsWith("/servlet/")) {
					// ����uri��/servlet/��ͷ����ʾservlet����
					ServletProcessor1 processor = new ServletProcessor1();
					processor.process(request, response);
				} else {
					// ��̬��Դ����
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}

				// �ر� socket
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
