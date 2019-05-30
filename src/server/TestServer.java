package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8989);

		while (true) {
			Socket socket = server.accept();

			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();

			byte[] b = new byte[1000];
			int len = input.read(b);
			System.out.println(new String(b, 0, len));

			output.write("��ÿͻ��� ��".getBytes());
			output.close();
			input.close();
			socket.close();

		}

	}
}
