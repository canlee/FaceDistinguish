package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;

import com.invindible.facetime.database.OnAndOff;
import com.invindible.facetime.org.eclipse.wb.swing.FocusTraversalOnArray;
import com.invindible.facetime.service.implement.CameraInterfaceImpl;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainUI extends JFrame{
	static JPanel contentPane;
	static MainUI frameMainUI;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frameMainUI = new MainUI();
					frameMainUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		
		setTitle("\u4EBA\u8138\u8BC6\u522B\u7A0B\u5E8F");
		//setUndecorated(true);
		//getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
//		setExtendedState(JFrame.ICONIFIED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPic = new JPanel();
		panelPic.setBounds(0, 0, 564, 345);
		
		
		contentPane.add(panelPic);
		panelPic.setLayout(null);
		
		JPanel panelMessage = new JPanel();
		panelMessage.setBounds(268, 0, 247, 129);
		panelMessage.setOpaque(false);//设置成透明的
		panelPic.add(panelMessage);
		panelMessage.setLayout(null);
		
		JLabel label = new JLabel("\u6587\u5B57\u8BF4\u660E\u533A");
		label.setBounds(149, 31, 60, 15);
		panelMessage.add(label);
		
		JPanel panelButton = new JPanel();
		panelButton.setBounds(39, 238, 476, 62);
		panelButton.setOpaque(false);
		panelPic.add(panelButton);
		panelButton.setLayout(null);
		
		//启动数据库
		OnAndOff.getInstance().Start();
		
		
		JButton btnRegist = new JButton("注册");
		btnRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameMainUI.dispose();
//				frameMainUI.setVisible(false);
				try{
				FrameIdPwd.frameIdPwd = new FrameIdPwd();
				FrameIdPwd.frameIdPwd.setVisible(true);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnRegist.setBounds(56, 15, 115, 32);
		panelButton.add(btnRegist);
		
		final JButton btnEnter = new JButton("登陆签到");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameMainUI.dispose();
//				frameMainUI.setVisible(false);
				FrameSignIn.frameSignIn = new FrameSignIn();
				FrameSignIn.frameSignIn.setVisible(true);
			}
		});
		btnEnter.setBounds(193, 15, 115, 32);
		panelButton.add(btnEnter);
		
		JButton btnVideo = new JButton("视频监视");
		btnVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameMainUI.dispose();
				
			}
		});
		btnVideo.setBounds(325, 15, 115, 32);
		panelButton.add(btnVideo);
		
		JLabel lblWallPaper = new JLabel("New label");
		lblWallPaper.setBounds(0, 0, 564, 345);
		int num = (int) (Math.random()*4);//产生[0,4)的随机数.
		
		JPanel panelProgramIcon = new JPanel();
		panelProgramIcon.setBounds(91, 85, 159, 121);
		panelPic.add(panelProgramIcon);
		panelProgramIcon.setLayout(null);
		
		JLabel lblProgramIcon = new JLabel("");
		lblProgramIcon.setBounds(0, 0, 159, 121);
		lblProgramIcon.setIcon(ImageHandle(new ImageIcon("Pictures/facetime.jpg"), 159, 121));
		panelProgramIcon.add(lblProgramIcon);
		lblWallPaper.setIcon(ImageHandle(new ImageIcon("Pictures/wallpaper/" + num + ".jpg"), 564, 345));
		panelPic.add(lblWallPaper);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panelMessage, panelPic, btnRegist, btnEnter, panelButton}));
		panelPic.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panelMessage}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, panelPic, lblWallPaper, panelButton, btnRegist, btnEnter, panelMessage}));
	}
	
	//图片等比例处理方法,width和height为宽度和高度
	public ImageIcon ImageHandle(ImageIcon imageicon,int width,int height){
		Image image = imageicon.getImage();
		Image smallimage = image.getScaledInstance(width, height, image.SCALE_FAST);
		ImageIcon smallicon = new ImageIcon(smallimage);
		return smallicon;
	}
}
