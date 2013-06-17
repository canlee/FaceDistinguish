package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.invindible.facetime.database.Oracle_Connect;
import com.invindible.facetime.database.UserDao;
import com.invindible.facetime.model.User;
import com.invindible.facetime.service.implement.CameraInterfaceImpl;
import com.invindible.facetime.task.interfaces.Context;
import com.invindible.facetime.task.video.VideoStreamTask;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.JTextField;

public class FrameRegist extends JFrame implements Context{

	static FrameRegist frameRegist;
	private JPanel contentPane;
	private JPanel panelCamera;


	/**
	 * Create the frame.
	 */
	public FrameRegist(final String userId, final String passWord) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 707, 443);
		setBounds(100, 100, 888, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelCamera = new JPanel();
		panelCamera.setBounds(19, 41, 399, 259);
		contentPane.add(panelCamera);
		panelCamera.setLayout(null);
		

		//开启摄像头
		
		final CameraInterfaceImpl camera = new  CameraInterfaceImpl(this);
		camera.getCamera();
		
		
		//new VideoStreamTask(this).start();
		/*
		ImageIcon cam = new ImageIcon("Pictures/Camera.jpg");
		JLabel lblCamera = new JLabel(ImageHandle(cam,  399, 259));
		lblCamera.setBounds(0, 0, 399, 259);
		panelCamera.add(lblCamera);
		*/
		
		
		JPanel panelButton = new JPanel();
		panelButton.setBounds(19, 362, 406, 55);
		contentPane.add(panelButton);
		panelButton.setLayout(null);
		
		JButton btnRegist = new JButton("确认注册");
		btnRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//此处加入注册处理，例如将注册者的图片保存进数据库之类的操作
				
				
				//设置User的用户名、密码和照片。
				//获取当前摄像头中的显示的图片，作为user的照片
				//--------------------暂时屏蔽--------------------
				//Image img =camera.getHandledPictrue();
				//----------------------------------------------
					//摄像头启动成功的话，将img保存至本地，然后再传递过去
				InputStream inputPic = this.getClass().getResourceAsStream("Pictures/facetime.jpg");
					//测试，将来将照片保存至本地，然后再传递给这个参数即可。
				
				User user = new User();
				user.setUsername(userId);
				user.setPassword(passWord);
				user.setB(inputPic);
				
				//将user信息保存进数据库
				UserDao ud = new UserDao();
				try {
					ud.doInsert(user, Oracle_Connect.getInstance().getConn());
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "注册成功！");

				frameRegist.setVisible(false);
				frameRegist.dispose();
				MainUI.frameMainUI.setVisible(true);
			}
		});
		btnRegist.setBounds(71, 10, 110, 35);
		panelButton.add(btnRegist);
		
		JButton btnReturn = new JButton("返回主界面");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameRegist.setVisible(false);
				frameRegist.dispose();
				MainUI.frameMainUI.setVisible(true);
			}
		});
		btnReturn.setBounds(231, 10, 110, 35);
		panelButton.add(btnReturn);
		
		JPanel panelInstructions = new JPanel();
		//panelInstructions.setBounds(437, 41, 244, 259);
		panelInstructions.setBounds(437, 41, 412, 259);
		contentPane.add(panelInstructions);
		panelInstructions.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 128, 128);
		panelInstructions.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(138, 0, 128, 128);
		panelInstructions.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(276, 0, 128, 128);
		panelInstructions.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 131, 128, 128);
		panelInstructions.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(138, 131, 128, 128);
		panelInstructions.add(panel_4);
		JLabel lblUserID = new JLabel("你的用户名：");
		lblUserID.setBounds(42, 16, 89, 15);
		contentPane.add(lblUserID);
		
		JLabel lblInstructions = new JLabel("文字说明区");
		lblInstructions.setBounds(437, 323, 232, 85);
		contentPane.add(lblInstructions);
		
		JButton btnTakeNewPhoto = new JButton("点击更换照片");
		btnTakeNewPhoto.setBounds(698, 347, 116, 36);
		contentPane.add(btnTakeNewPhoto);
		
		JLabel lblUserName = new JLabel("New label");
		lblUserName.setBounds(129, 16, 54, 15);
		contentPane.add(lblUserName);
		lblUserName.setText(userId);
	}
	
	//图片等比例处理方法,width和height为宽度和高度
	public ImageIcon ImageHandle(ImageIcon imageicon,int width,int height){
		Image image = imageicon.getImage();
		Image smallimage = image.getScaledInstance(width, height, image.SCALE_FAST);
		ImageIcon smallicon = new ImageIcon(smallimage);
		return smallicon;
	}

	@Override
	public void onRefresh(Object... objects) {
		// TODO Auto-generated method stub
		Integer result = (Integer) objects[0];
		switch (result) {
		case VideoStreamTask.OPEN_CAMERA_SUCCESS:
			Component component = (Component) objects[1];
			component.setBounds(0, 0, 314, 229);
			panelCamera.add(component);
			break;
			
			
		default:
			break;
		}
		
	}
}
