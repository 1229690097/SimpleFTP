package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JProgressBar;

public class FileClient extends Thread {

	private String ip;
	private int port;
	private String filepath;
	private JProgressBar progressBar;

	public FileClient(String ip, int port, String filepath, JProgressBar progressBar) {
		this.ip = ip;
		this.port = port;
		this.filepath = filepath;
		this.progressBar = progressBar;
	}

	public void run() {
		try {
			File file = new File(filepath);

			Socket socket = new Socket(ip, port);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();

			//给服务器传递 文件名字和长度 用\t分开
			String str1 = file.getName() + "\t" + file.length();
			output.write(str1.getBytes());
			output.flush();

			
			//读取服务器数据 
			byte[] b = new byte[10];
			int len = input.read(b);
			String str2 = new String(b, 0, len);
			//判断服务器给的是 ok 还是 no   
			if (str2.equalsIgnoreCase("ok")) {//如果是ok  就开始读取本地文件 然后传输到服务器上
				FileInputStream filein = new FileInputStream(file);

				//先设置进度条最大值  然后慢慢跟进
				progressBar.setMaximum((int) (file.length() / 10000));
				long size = 0;

				byte[] bb = new byte[1024 * 8];
				while (filein.available() != 0) {
					len = filein.read(bb);
					output.write(bb, 0, len);
					output.flush();
					size += len;
					progressBar.setValue((int) (size / 10000));
				}
				filein.close();
				javax.swing.JOptionPane.showMessageDialog(null, "发送完毕!");
			} else {
				javax.swing.JOptionPane.showMessageDialog(null, "对方拒绝接受文件!");
			}

			output.close();
			input.close();
			socket.close();
		} catch (UnknownHostException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "UnknownHostException!");
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "IOException!");
		}

	}

}
