package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	public static void main(String[] args) throws Exception {

		// HTTP 协议
		Socket socket = new Socket("127.0.0.1", 8989);
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();

		output.write("你好，有房间吗？".getBytes());

		byte[] b = new byte[1024];
		int len = input.read(b);
		System.out.println(new String(b, 0, len));

		input.close();
		output.close();
		socket.close();

	}

}
