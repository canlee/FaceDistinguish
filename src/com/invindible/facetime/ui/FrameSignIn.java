package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import com.invindible.facetime.model.FaceImage;
import com.invindible.facetime.service.implement.CameraInterfaceImpl;
import com.invindible.facetime.service.implement.FindFaceForCameraInterfaceImpl;
import com.invindible.facetime.service.interfaces.CameraInterface;
import com.invindible.facetime.service.interfaces.FindFaceInterface;
import com.invindible.facetime.task.init.HarrCascadeParserTask;
import com.invindible.facetime.task.interfaces.Context;
import com.invindible.facetime.task.video.VideoStreamTask;
import com.invindible.facetime.util.Debug;
import com.invindible.facetime.util.image.ImageUtil;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class FrameSignIn extends JFrame implements Context{

	static FrameSignIn frameSignIn;
	
	private JPanel contentPane;
	
	private JPanel panelCamera;
	private JPanel panelCapture;
	private JPanel panelResult;
	private JButton btnSignIn;
	private JLabel labelResult;
	private JButton buttonCapture1;
	private JButton buttonCapture2;
	
	private CameraInterface cif;
	private FindFaceInterface findTask;
	
	private ImageIcon[] imageIconCaptures;//保存摄像头捕获的头像
	private ImageIcon imageIconResult;//保存识别的头像
	private boolean[] isImageIconSelected;//第i个照片是否要更换的标志
	private boolean changePhoto;//是否要更换照片的标志
	private int requestNum;//剩余的需要更换的照片数量

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameSignIn frame = new FrameSignIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameSignIn() {
		imageIconCaptures = new ImageIcon[2];
		imageIconResult = new ImageIcon();
		isImageIconSelected = new boolean[2];
		changePhoto = true;
		requestNum = 2;
		
		for(int i=0; i<2; i++)
		{
			isImageIconSelected[i] = true;
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelCamera = new JPanel();
		panelCamera.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCamera.setBounds(27, 24, 399, 259);
		contentPane.add(panelCamera);
		panelCamera.setLayout(null);
		
		JLabel label_1 = new JLabel("文字说明区");
		label_1.setBounds(189, 311, 232, 85);
		contentPane.add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(20, 315, 159, 55);
		panel_1.setLayout(null);
		contentPane.add(panel_1);
		
		JButton buttonReturn = new JButton("返回主界面");
		buttonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findTask.stop();
				frameSignIn.dispose();
				MainUI.frameMainUI = new MainUI();
				MainUI.frameMainUI.setVisible(true);
			}
		});
		buttonReturn.setBounds(22, 10, 110, 35);
		panel_1.add(buttonReturn);
		
		JPanel panelCaptureBox = new JPanel();
		panelCaptureBox.setBorder(new TitledBorder(null, "\u6355\u6349\u7684\u5934\u50CF", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelCaptureBox.setBounds(437, 24, 312, 211);
		contentPane.add(panelCaptureBox);
		panelCaptureBox.setLayout(null);
		
		panelCapture = new JPanel();
		panelCapture.setBounds(10, 43, 128, 128);
		panelCaptureBox.add(panelCapture);
		panelCapture.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCapture.setLayout(null);
		
		buttonCapture1 = new JButton("暂无捕获头像");
		buttonCapture1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(buttonCapture1, 0);
			}
		});
		buttonCapture1.setBounds(0, 0, 128, 128);
		panelCapture.add(buttonCapture1);
		
		JButton btnCapture = new JButton("重新捕捉");
		btnCapture.setBounds(99, 176, 105, 27);
		panelCaptureBox.add(btnCapture);
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<2; i++)
				{
					if(isImageIconSelected[i] == true)
					{
						changePhoto = true;
						break;
					}
				}
				
				System.out.println(changePhoto);
				
			}
		});
		btnCapture.setFont(new Font("宋体", Font.PLAIN, 16));
		
		JPanel panelCapture1 = new JPanel();
		panelCapture1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCapture1.setLayout(null);
		panelCapture1.setBounds(167, 43, 128, 128);
		panelCaptureBox.add(panelCapture1);
		
		buttonCapture2 = new JButton("暂无捕获头像");
		buttonCapture2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(buttonCapture2, 1);
			}
		});
		buttonCapture2.setBounds(0, 0, 128, 128);
		panelCapture1.add(buttonCapture2);
		
		JPanel panelResultBox = new JPanel();
		panelResultBox.setBorder(new TitledBorder(null, "\u8BC6\u522B\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelResultBox.setBounds(436, 245, 319, 173);
		contentPane.add(panelResultBox);
		panelResultBox.setLayout(null);
		
		JLabel label_2 = new JLabel("识别结果：");
		label_2.setBounds(93, 2, 115, 21);
		panelResultBox.add(label_2);
		label_2.setFont(new Font("华文细黑", Font.PLAIN, 16));
		
		panelResult = new JPanel();
		panelResult.setBounds(22, 33, 128, 128);
		panelResultBox.add(panelResult);
		panelResult.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelResult.setLayout(null);
		
		labelResult = new JLabel("暂无识别结果");
		labelResult.setHorizontalAlignment(SwingConstants.CENTER);
		labelResult.setBounds(0, 0, 128, 128);
		panelResult.add(labelResult);
		labelResult.setFont(new Font("华文细黑", Font.PLAIN, 16));
		
		btnSignIn = new JButton("签到");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameSignIn.setVisible(false);
				
				FrameSignInConfirm.frameSignInConfirm = new FrameSignInConfirm();
				FrameSignInConfirm.frameSignInConfirm.setVisible(true);
				
			}
		});
		btnSignIn.setBounds(175, 78, 105, 27);
		panelResultBox.add(btnSignIn);
		btnSignIn.setFont(new Font("宋体", Font.PLAIN, 16));
		//设置“签到”按钮默认无法点击
		btnSignIn.setEnabled(false);
		
		//开启摄像头
		new HarrCascadeParserTask(this).start();
		cif = new  CameraInterfaceImpl(this);
		cif.getCamera();
		findTask = new FindFaceForCameraInterfaceImpl(this);
		findTask.start();
		
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
//						Icon icon = (Icon) img;
//						Image image = img;
						ImageIcon imgIcon = new ImageIcon(img);
						imgIcon = FrameRegist.ImageHandle(imgIcon, 128, 128);
						
						if(changePhoto == true)
						{
							for(int i=0; i<2; i++)
							{
								if(isImageIconSelected[i] == true)
								{
									isImageIconSelected[i] = false;
									switch(i)
									{
										case 0:
											this.buttonCapture1.setIcon(imgIcon);
											this.imageIconCaptures[0] = imgIcon;
											break;
										case 1:
											this.buttonCapture2.setIcon(imgIcon);
											this.imageIconCaptures[1] = imgIcon;
											break;
									}
									
									if( i == 1)
									{
										changePhoto = false;
										System.out.println("changePhoto = false;");
									}
									
									requestNum--;
									//判断是否已经2张照片都捕获完，都捕获完则进行识别
									if(requestNum == 0)
									{
										changePhoto = false;
										System.out.println("changePhoto = false;");
										
//										//从数据库中读取所有样本数据和WoptT
//										
//										//开始识别
//										
////									//若识别成功
//										if( 识别成功 == true)
//										{
//											//设置“签到按钮”可以点击
//										}
//										//若失败识别，则重新捕获2张照片。
//										else
//										{
//											
//										}
									}
									
									break;
								}
							}
							

							
						}
						
					}
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
					btn.setIcon(FrameWindow.drawNike(imageIconCaptures[objectIndex]));
					isImageIconSelected[objectIndex] = true;
					requestNum++;
				}
				//若已打钩
				else
				{
					btn.setIcon(imageIconCaptures[objectIndex]);
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
