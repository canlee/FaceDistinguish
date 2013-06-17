package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameIdPwd extends JFrame {

	static FrameIdPwd frameIdPwd;
	private JPanel contentPane;
	private JTextField txtUserId;
	private JTextField txtPassWd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frameIdPwd = new FrameIdPwd();
					frameIdPwd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameIdPwd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 132, 281, 78);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton buttonEnter = new JButton("确认");
		buttonEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frameIdPwd.setVisible(false);
				//获取用户名和密码
				String userId = txtUserId.getText();
				String passWord = txtPassWd.getText();
				
				//数据库验证用户名是否存在
				
				//将用户名和密码作为参数，传给下个窗体的构造函数
				FrameRegist.frameRegist = new FrameRegist(userId, passWord);
				FrameRegist.frameRegist.setVisible(true);
				
				
			}
		});
		buttonEnter.setBounds(10, 25, 110, 35);
		panel.add(buttonEnter);
		
		JButton buttonReturn = new JButton("返回");
		buttonReturn.setBounds(170, 25, 110, 35);
		panel.add(buttonReturn);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 26, 293, 96);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtUserId = new JTextField();
		txtUserId.setFont(new Font("宋体", Font.PLAIN, 16));
		txtUserId.setColumns(10);
		txtUserId.setBounds(124, 10, 102, 21);
		panel_1.add(txtUserId);
		
		JLabel label = new JLabel("用户名：");
		label.setFont(new Font("华文行楷", Font.PLAIN, 16));
		label.setBounds(44, 13, 70, 18);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("华文行楷", Font.PLAIN, 16));
		label_1.setBounds(44, 54, 60, 21);
		panel_1.add(label_1);
		
		txtPassWd = new JTextField();
		txtPassWd.setFont(new Font("宋体", Font.PLAIN, 16));
		txtPassWd.setColumns(10);
		txtPassWd.setBounds(124, 53, 102, 21);
		panel_1.add(txtPassWd);
	}
}
