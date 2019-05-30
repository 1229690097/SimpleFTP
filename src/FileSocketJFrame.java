
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import client.FileClient;
import server.FileServer;

public class FileSocketJFrame extends JFrame {

	private JTextField textField_4;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileSocketJFrame frame = new FileSocketJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	final JProgressBar progressBar_1 = new JProgressBar();

	/**
	 * Create the frame
	 */
	public FileSocketJFrame() {
		super();
		setTitle("文件局域网传输");
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(100, 100, 490, 283);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "文件接受服务器", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("微软雅黑", Font.PLAIN, 12), null));
		panel.setBounds(10, 10, 464, 99);
		getContentPane().add(panel);

		final JLabel label = new JLabel();
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setText("端口:");
		label.setBounds(22, 29, 66, 27);
		panel.add(label);

		textField = new JTextField("8989");
		textField.setBounds(79, 28, 87, 28);
		panel.add(textField);

		final JToggleButton toggleButton = new JToggleButton();
		toggleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (toggleButton.isSelected()) {
					toggleButton.setText("关闭接受服务器");
					textField.setEditable(false);
					FileServer.openServer(Integer.parseInt(textField.getText()), progressBar_1);

				} else {
					toggleButton.setText("启动接受服务器");
					textField.setEditable(true);
					FileServer.closeServer();
				}

			}
		});
		toggleButton.setText("启动接受服务器");
		toggleButton.setBounds(291, 28, 144, 28);
		panel.add(toggleButton);

		progressBar_1.setBounds(20, 65, 414, 23);
		panel.add(progressBar_1);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "文件发送", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("微软雅黑", Font.PLAIN, 12), null));
		panel_1.setBounds(10, 122, 464, 126);
		getContentPane().add(panel_1);

		final JLabel label_1 = new JLabel();
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setText("文件:");
		label_1.setBounds(23, 21, 53, 28);
		panel_1.add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(75, 22, 292, 29);
		panel_1.add(textField_1);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.showOpenDialog(FileSocketJFrame.this);
				textField_1.setText(fileChooser.getSelectedFile().getPath());
			}
		});
		button.setText("...");
		button.setBounds(378, 22, 53, 28);
		panel_1.add(button);

		final JLabel ipLabel = new JLabel();
		ipLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		ipLabel.setText("IP:");
		ipLabel.setBounds(23, 59, 53, 28);
		panel_1.add(ipLabel);

		textField_2 = new JTextField("192.168.0.128");
		textField_2.setBounds(75, 59, 120, 28);
		panel_1.add(textField_2);

		final JLabel ipLabel_1 = new JLabel();
		ipLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		ipLabel_1.setText("端口:");
		ipLabel_1.setBounds(201, 59, 53, 28);
		panel_1.add(ipLabel_1);

		textField_4 = new JTextField("8989");
		textField_4.setBounds(249, 60, 60, 28);
		panel_1.add(textField_4);

		final JProgressBar progressBar = new JProgressBar();
		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				FileClient fileClient = new FileClient(textField_2.getText(), Integer.parseInt(textField_4.getText()),
						textField_1.getText(), progressBar);
				fileClient.start();

			}
		});
		button_1.setText("发送");
		button_1.setBounds(325, 60, 106, 28);
		panel_1.add(button_1);

		progressBar.setBounds(23, 93, 408, 23);
		panel_1.add(progressBar);
		//
	}

}
