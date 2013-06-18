package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.invindible.facetime.database.Oracle_Connect;
import com.invindible.facetime.database.UserDao;
import com.invindible.facetime.model.FaceImage;
import com.invindible.facetime.model.User;
import com.invindible.facetime.service.implement.CameraInterfaceImpl;
import com.invindible.facetime.service.implement.FindFaceForCameraInterfaceImpl;
import com.invindible.facetime.service.interfaces.CameraInterface;
import com.invindible.facetime.service.interfaces.FindFaceInterface;
import com.invindible.facetime.task.init.HarrCascadeParserTask;
import com.invindible.facetime.task.interfaces.Context;
import com.invindible.facetime.task.video.VideoStreamTask;
import com.invindible.facetime.util.Debug;
import com.invindible.facetime.util.image.ImageUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JTextField;

public class FrameRegist extends JFrame implements Context{

	static FrameRegist frameRegist;
	private JPanel contentPane;
	private JPanel panelCamera;

	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	
	private CameraInterface cif;
	private FindFaceInterface findTask;
	
	private int photoIndex = 1;
	private ImageIcon[] imageIcons;// = new ImageIcon[5];//5张照片
	private boolean[] isImageIconSelected;// = new boolean[5];//第i个照片是否要更换的标志
//	private int[] changeIndex = {1,2,3,4,5};
	private boolean startChangeSelectedIcon;// = true;//是否要更换照片的标志
	private int requestNum;// = 5;//剩余的需要更换的照片数量

	/**
	 * Create the frame.
	 */
	public FrameRegist(final String userId, final String passWord) {
		imageIcons = new ImageIcon[5];
		isImageIconSelected = new boolean[5];
		startChangeSelectedIcon = true;
		requestNum = 5;
		
		
		for(int i=0; i<5; i++)
		{
			isImageIconSelected[i] = true;
		}
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
		new HarrCascadeParserTask(this).start();
		cif = new  CameraInterfaceImpl(this);
		cif.getCamera();
		findTask = new FindFaceForCameraInterfaceImpl(this);
		findTask.start();
		
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
				
				
				//设置User的用户名、密码和5张照片。
				//用户名 userId
				//密码 passWord
				//5张照片 imageIcons
				
				
				//读取所有数据库中的样本
				
				//训练（将 本人的照片 和 数据库中的所有照片 投影到WoptT上
				
				//验证（尝试识别，是被失败则需要重新获取图片）
//				if( 识别 == false)
//				{
//					//将数据初始化，以开始重新获取图片
//					requestNum = 5;
//					for(int i=0; i<5;i++)
//					{
//						isImageIconSelected[i] = true;
//					}
//					startChangeSelectedIcon = true;
//				}
				//若可以，则注册成功，将用户名、密码、5张照片存入数据库
//				else{
//					
//				}
				
				
				
				//----------------------------------------------
					//摄像头启动成功的话，将img保存至本地，然后再传递过去
				//InputStream inputPic = this.getClass().getResourceAsStream("Pictures/facetime.jpg");
					//测试，将来将照片保存至本地，然后再传递给这个参数即可。
				
//				User user = new User();
//				user.setUsername(userId);
//				user.setPassword(passWord);
//				user.setB(inputPic);
//				
//				//将user信息保存进数据库
//				UserDao ud = new UserDao();
//				try {
//					ud.doInsert(user, Oracle_Connect.getInstance().getConn());
//				} catch (InstantiationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IllegalAccessException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				JOptionPane.showMessageDialog(null, "注册成功！");

				frameRegist.setVisible(false);
				frameRegist.dispose();
				MainUI.frameMainUI.setVisible(true);
				
				//最终注册成功后，将寻找人脸的方法暂停
				findTask.stop();
			}
		});
		btnRegist.setBounds(71, 10, 110, 35);
		panelButton.add(btnRegist);
		
		JButton btnReturn = new JButton("返回主界面");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//询问是否确认放弃注册，并返回主界面
				if(JOptionPane.YES_OPTION == 
						JOptionPane.showConfirmDialog(null, "放弃本次注册？", "提示", JOptionPane.YES_NO_CANCEL_OPTION))
				{
					
					frameRegist.dispose();
					MainUI.frameMainUI = new MainUI();
					MainUI.frameMainUI.setVisible(true);
					
					//点击返回后，将寻找人脸的方法暂停
					findTask.stop();
				}
				else
				{
					return;
				}
				
				
				
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
		panel.setLayout(null);
		
		btn1 = new JButton("New button");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn1, 0);
				
			}
		});
		btn1.setBounds(0, 0, 128, 128);
		panel.add(btn1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(138, 0, 128, 128);
		panelInstructions.add(panel_1);
		panel_1.setLayout(null);
		
		btn2 = new JButton("New button");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn2, 1);
				
			}
		});
		btn2.setBounds(0, 0, 128, 128);
		panel_1.add(btn2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(276, 0, 128, 128);
		panelInstructions.add(panel_2);
		panel_2.setLayout(null);
		
		btn3 = new JButton("New button");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn3, 2);
				
			}
		});
		btn3.setBounds(0, 0, 128, 128);
		panel_2.add(btn3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 131, 128, 128);
		panelInstructions.add(panel_3);
		panel_3.setLayout(null);
		
		btn4 = new JButton("New button");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn4, 3);
				
			}
		});
		btn4.setBounds(0, 0, 128, 128);
		panel_3.add(btn4);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(138, 131, 128, 128);
		panelInstructions.add(panel_4);
		panel_4.setLayout(null);
		
		btn5 = new JButton("New button");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn5, 4);
				
			}
		});
		btn5.setBounds(0, 0, 128, 128);
		panel_4.add(btn5);
		JLabel lblUserID = new JLabel("你的用户名：");
		lblUserID.setBounds(42, 16, 89, 15);
		contentPane.add(lblUserID);
		
		JLabel lblInstructions = new JLabel("文字说明区");
		lblInstructions.setBounds(437, 323, 232, 85);
		contentPane.add(lblInstructions);
		
		JButton btnTakeNewPhoto = new JButton("点击更换照片");
		btnTakeNewPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<5; i++)
				{
					//若需要更换指定索引的照片
					if(isImageIconSelected[i] == true)
					{
						startChangeSelectedIcon = true;
					}
				}
				
			}
		});
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
			while(true) {
				Image image = cif.getHandledPictrue();
				if(image != null) {
					findTask.findFace(image);
					break;
				}
			}
			break;
			
		case FindFaceInterface.FIND_FACE_SUCCESS:
			FaceImage fi = (FaceImage) objects[1];
			if(fi.getFacesRgb().size() > 0) {
				BufferedImage img = ImageUtil.getImgByRGB(fi.getFacesRgb().get(0).getRgbMat());
//				Icon icon = (Icon) img;
//				Image image = img;
				ImageIcon imgIcon = new ImageIcon(img);
				imgIcon = ImageHandle(imgIcon, 128, 128);
				
				if(startChangeSelectedIcon == true)
				{
					for(int i=0; i<5; i++)
					{
						if(isImageIconSelected[i] == true)
						{
							isImageIconSelected[i] = false;
							switch(i)
							{
								case 0:
									this.btn1.setIcon( imgIcon);
									this.imageIcons[0] = imgIcon;
									break;
								case 1:
									this.btn2.setIcon( imgIcon);
									this.imageIcons[1] = imgIcon;
									break;
								case 2:
									this.btn3.setIcon( imgIcon);
									this.imageIcons[2] = imgIcon;
									break;
								case 3:
									this.btn4.setIcon( imgIcon);
									this.imageIcons[3] = imgIcon;
									break;
								case 4:
									this.btn5.setIcon( imgIcon);
									this.imageIcons[4] = imgIcon;
									break;
							}
							
							if( i == 4)
							{
								startChangeSelectedIcon = false;
								System.out.println("修改了startChangesSelected");
							}
							
							requestNum--;
							if(requestNum == 0)
							{
								startChangeSelectedIcon = false;
								System.out.println("修改了startChangesSelected");
							}
								
							break;
						}
						
					}
				}

//				//若拍照索引已经超过5，则停止截图
//				if( photoIndex < 6)
//				{
//					photoIndex++;
//				}
//				
////				imagePanel.setBufferImage(img);
//				Debug.print(img.getWidth() + ", " + img.getHeight());
//				Calendar c = Calendar.getInstance();
//				ImageUtil.saveImage(img, "E:/" + c.getTimeInMillis() + ".jpg");
			}
//			imagePanel.setBounds(originPanel.getWidth() + 10, 0, img.getWidth(), img.getHeight());
			findTask.findFace(cif.getHandledPictrue());
			break;
			
		case HarrCascadeParserTask.PARSER_SUCCESS:
			Debug.print("读取adaboost文件成功！");
			break;
			
		default:
			break;
		}	
	}
	
	//为按钮打钩
	public void drawNikeOnObject(JButton btn, int objectIndex)
	{
		//判断是否已经打钩
		//若未打钩
		try
		{
			if(isImageIconSelected[objectIndex] == false)
			{
				btn.setIcon(FrameWindow.drawNike(imageIcons[objectIndex]));
				isImageIconSelected[objectIndex] = true;
				requestNum++;
			}
			//若已打钩
			else
			{
				btn.setIcon(imageIcons[objectIndex]);
				isImageIconSelected[objectIndex] = false;
				requestNum--;
			}
		}
		catch(Exception ex)
		{
			;
		}
	}
		
}
