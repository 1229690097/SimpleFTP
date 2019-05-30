package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class FileServer extends Thread {

	private Socket socket;
	private JProgressBar progressBar_1;

	public FileServer(Socket socket, JProgressBar progressBar_1) {
		this.socket = socket;
		this.progressBar_1 = progressBar_1;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();

			
			//读取客户端发来的信息
			byte[] b = new byte[1024];
			int len = input.read(b);
			String str1 = new String(b, 0, len);
			//分别对应文件名以及大小
			String[] strs = str1.split("\t");
			String filename = strs[0];
			long length = Long.parseLong(strs[1]);

			int i = javax.swing.JOptionPane.showConfirmDialog(null,
					socket.getInetAddress().getHostAddress() + " 对方给你发送了一个文件!\n文件名称：" + filename + "\n大小:" + length);
			if (i == JOptionPane.OK_OPTION) {
				output.write("ok".getBytes());
				output.flush();

				//选择文件存储位置
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showSaveDialog(null);

				progressBar_1.setMaximum((int) (length / 10000));

				//文件填充
				FileOutputStream fileOutputStream = new FileOutputStream(
						new File(fileChooser.getSelectedFile(), filename));
				b = new byte[1024 * 8];
				long size = 0;
				//分片读取 直至完成
				while ((len = input.read(b)) != -1) {
					fileOutputStream.write(b, 0, len);
					size += len;
					progressBar_1.setValue((int) (size / 10000));
				}
				fileOutputStream.close();

			} else {
				output.write("no".getBytes());
				output.flush();
			}
			output.close();
			input.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ServerSocket server = null;

	public static void openServer(int port,JProgressBar progressBar_1) {
		new Thread() {
			public void run() {
				try {
					if (server != null && !server.isClosed()) {
						server.close();
					}
					server = new ServerSocket(port);
					while (true) {
						new FileServer(server.accept(),progressBar_1).start();
					}
				} catch (java.net.BindException e) {
					javax.swing.JOptionPane.showMessageDialog(null, "端口占用了");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	public static void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
