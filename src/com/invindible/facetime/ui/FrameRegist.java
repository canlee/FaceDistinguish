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

import com.invindible.facetime.algorithm.LDA;
import com.invindible.facetime.algorithm.Mark;
import com.invindible.facetime.algorithm.UiAlgorithm.RegistAlgorithm;
import com.invindible.facetime.algorithm.UiAlgorithm.UiImageHandle;
import com.invindible.facetime.algorithm.feature.Features;
import com.invindible.facetime.algorithm.feature.GetFeatureMatrix;
import com.invindible.facetime.algorithm.feature.GetPcaLda;
import com.invindible.facetime.database.Oracle_Connect;
import com.invindible.facetime.database.ProjectDao;
import com.invindible.facetime.database.UserDao;
import com.invindible.facetime.model.FaceImage;
import com.invindible.facetime.model.Imageinfo;
import com.invindible.facetime.model.LdaFeatures;
import com.invindible.facetime.model.Project;
import com.invindible.facetime.model.User;
import com.invindible.facetime.model.Wopt;
import com.invindible.facetime.service.implement.CameraInterfaceImpl;
import com.invindible.facetime.service.implement.FindFaceForCameraInterfaceImpl;
import com.invindible.facetime.service.interfaces.CameraInterface;
import com.invindible.facetime.service.interfaces.FindFaceInterface;
import com.invindible.facetime.task.init.HarrCascadeParserTask;
import com.invindible.facetime.task.interfaces.Context;
import com.invindible.facetime.task.video.VideoStreamTask;
import com.invindible.facetime.util.Debug;
import com.invindible.facetime.util.image.ImageUtil;
import com.invindible.facetime.wavelet.Wavelet;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

public class FrameRegist extends JFrame implements Context{

	public static FrameRegist frameRegist;
	private JPanel contentPane;
	private JPanel panelCamera;

	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	
	private CameraInterface cif;
	public static FindFaceInterface findTask;
	
//	private int photoIndex = 1;
	private ImageIcon[] imageIcons;// = new ImageIcon[5];//5张照片
	private ImageIcon[] testIcons;//测试用照片，2张
//	private BufferedImage[] soyBufferedImages;
	private boolean[] isImageIconSelected;// = new boolean[7];//第i个照片是否要更换的标志
//	private int[] changeIndex = {1,2,3,4,5};
	private boolean startChangeSelectedIcon;// = true;//是否要更换照片的标志
	private int requestNum;// = 7;//剩余的需要更换的照片数量
//	private int testNum = 2;//测试样例的数量(默认为2)
//	private int photoNum = 5;//每个人的照片数量(默认为5)
	
//	private boolean haveSoy = false;//是否有加入“酱油”进投影Z中的标志

	/**
	 * Create the frame.
	 */
	public FrameRegist(final String userId, final String passWord, final User user) {
		setTitle("1.用户注册-照相识别");
		testIcons = new ImageIcon[2];
		imageIcons = new ImageIcon[5];
//		soyBufferedImages= new BufferedImage[5];
		isImageIconSelected = new boolean[7];
		startChangeSelectedIcon = true;
		requestNum = 7;
		
		
		for(int i=0; i<7; i++)
		{
			isImageIconSelected[i] = true;
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 707, 443);
		setBounds(100, 100, 921, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelCamera = new JPanel();
		panelCamera.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCamera.setBounds(19, 41, 400, 260);
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
		panelButton.setBounds(19, 409, 386, 55);
		contentPane.add(panelButton);
		panelButton.setLayout(null);
		
		JButton btnRegist = new JButton("确认注册");
		btnRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RegistAlgorithm.startDistinguish(user, imageIcons,testIcons,requestNum,isImageIconSelected,startChangeSelectedIcon);
				
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
		
		JPanel panelCapture = new JPanel();
		panelCapture.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u6CE8\u518C\u7167\u7247", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//panelInstructions.setBounds(437, 41, 244, 259);
		panelCapture.setBounds(428, 16, 434, 297);
		contentPane.add(panelCapture);
		panelCapture.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 128, 128);
		panelCapture.add(panel);
		panel.setLayout(null);
		
		btn1 = new JButton("暂无捕获头像");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn1, 0);
				
			}
		});
		btn1.setBounds(0, 0, 128, 128);
		panel.add(btn1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(153, 21, 128, 128);
		panelCapture.add(panel_1);
		panel_1.setLayout(null);
		
		btn2 = new JButton("暂无捕获头像");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn2, 1);
				
			}
		});
		btn2.setBounds(0, 0, 128, 128);
		panel_1.add(btn2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(296, 21, 128, 128);
		panelCapture.add(panel_2);
		panel_2.setLayout(null);
		
		btn3 = new JButton("暂无捕获头像");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn3, 2);
				
			}
		});
		btn3.setBounds(0, 0, 128, 128);
		panel_2.add(btn3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 159, 128, 128);
		panelCapture.add(panel_3);
		panel_3.setLayout(null);
		
		btn4 = new JButton("暂无捕获头像");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn4, 3);
				
			}
		});
		btn4.setBounds(0, 0, 128, 128);
		panel_3.add(btn4);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(153, 159, 128, 128);
		panelCapture.add(panel_4);
		panel_4.setLayout(null);
		
		btn5 = new JButton("暂无捕获头像");
		btn5.setBounds(0, 0, 128, 128);
		panel_4.add(btn5);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn5, 4);
				
			}
		});
		JLabel lblUserID = new JLabel("你的用户名：");
		lblUserID.setBounds(42, 16, 89, 15);
		contentPane.add(lblUserID);
		
		JLabel lblInstructions = new JLabel("请在截取7张图像后，点击“确认注册”\r\n\r\n。（可更换照片）");
		lblInstructions.setBounds(19, 310, 360, 85);
		contentPane.add(lblInstructions);
		
		JButton btnTakeNewPhoto = new JButton("更换选中照片");
		btnTakeNewPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<7; i++)
				{
					//若需要更换指定索引的照片
					if(isImageIconSelected[i] == true)
					{
						startChangeSelectedIcon = true;
						break;
					}
				}
				
			}
		});
		btnTakeNewPhoto.setBounds(430, 359, 116, 36);
		contentPane.add(btnTakeNewPhoto);
		
		JLabel lblUserName = new JLabel("New label");
		lblUserName.setBounds(129, 16, 54, 15);
		contentPane.add(lblUserName);
		lblUserName.setText(userId);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u6D4B\u8BD5\u7528\u7167\u7247\uFF08\u4E0D\u4FDD\u5B58\uFF0C\u4EC5\u4E3A\u6D4B\u8BD5\uFF09", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(561, 319, 297, 170);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBounds(10, 32, 128, 128);
		panel_5.add(panel_6);
		
		btn6 = new JButton("暂无捕获头像");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn6, 5);
				
			}
		});
		btn6.setBounds(0, 0, 128, 128);
		panel_6.add(btn6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBounds(159, 32, 128, 128);
		panel_5.add(panel_7);
		
		btn7 = new JButton("暂无捕获头像");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawNikeOnObject(btn7, 6);
				
			}
		});
		btn7.setBounds(0, 0, 128, 128);
		panel_7.add(btn7);
	}

	@Override
	public void onRefresh(Object... objects) {
		// TODO Auto-generated method stub
		Integer result = (Integer) objects[0];
		switch (result) {
		case VideoStreamTask.OPEN_CAMERA_SUCCESS:
			Component component = (Component) objects[1];
			System.out.println("====width:" + component.getSize().width);
			System.out.println("====height:" + component.getSize().height);
//			component.setBounds(0, 0, 314, 229);
			component.setBounds(0, 0, 387, 260);
			component.setLocation(7, 0);
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
				imgIcon = UiImageHandle.ImageHandle(imgIcon, 128, 128);
				
				if(startChangeSelectedIcon == true)
				{
					for(int i=0; i<7; i++)
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
								case 5:
									this.btn6.setIcon( imgIcon);
									this.testIcons[0] = imgIcon;
									break;
								case 6:
									this.btn7.setIcon( imgIcon);
									this.testIcons[1] = imgIcon;
									break;
							}
							
							if( i == 6)
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
				if(objectIndex < 5)
				{
					btn.setIcon(FrameWindow.drawNike(imageIcons[objectIndex]));
				}
				else
				{
					btn.setIcon(FrameWindow.drawNike(testIcons[objectIndex-5]));
				}
				isImageIconSelected[objectIndex] = true;
				requestNum++;
			}
			//若已打钩
			else
			{
				if(objectIndex < 5)
				{
					btn.setIcon(imageIcons[objectIndex]);
				}
				else
				{
					btn.setIcon(testIcons[objectIndex-5]);
				}
				isImageIconSelected[objectIndex] = false;
				requestNum--;
			}
		}
		catch(Exception ex)
		{
			;
		}
	}
	
//	/**
//	 * 点击注册后
//	 * 尝试用2张测试样例识别5张用户照片
//	 * 若识别成功，则将数据存入数据库中
//	 * 不成功，则给出提示
//	 */
//	private void startDistinguish(User user)
//	{
//		//此处加入注册处理，例如将注册者的图片保存进数据库之类的操作
//		
//		
//		//设置User的用户名、密码和5张照片。
//		//用户名 userId
//		//密码 passWord
//		//5张照片 imageIcons
//		
//		//设置人数，和每人的照片数（此处默认每人5张）
//		int peopleNum = 2;//2是暂定的，需要根据数据库进行修改
//		int photoNum = 5;
//		
//		//WoptT矩阵
//		double[][] WoptT;
//		//Project数据
//		Project pr;
//		//保存从数据库获取的图片的数组
//		BufferedImage[] bImages = null;
////		//临时保存单人图片的数组
////		BufferedImage[] tempForOneManBImages = null;
//		//用来PCA、LDA计算的数组
//		ImageIcon[] icon = null;// = new ImageIcon[2*5];//[peopleNum*photoNum]
//		
//		Connection conn = null;
//		try
//		{
//			conn = Oracle_Connect.getInstance().getConn();
//			//1.首先判断数据库中是否已有WoptT矩阵的数据（即看是否已经有样本数据）
//			
//			//若存在WoptT
//			if( ProjectDao.firstORnot(conn) == false)
//			{
//				//读取所有样本数据，以便对所有样本和自己进行训练
//				
////				//读取WoptT矩阵
////				WoptT = ProjectDao.doselectWopt(conn);
//				
//				//(读取所有样本的投影Z 和 id)
//				//读取所有样本的图片
//				BufferedImage[] bimg = UserDao.doSelectAll(conn);
//				
//				for(int i=0; i<bimg.length; i++)
//				{
//					int[] le = ImageUtil.getPixes(bimg[i]);
//
//					System.out.println("长度:" + le.length);
//				}
////				ImageIcon[] tempImageIcons = new ImageIcon[bimg.length];
////				for(int i=0; i<bimg.length; i++)
////				{
////					tempImageIcons
////				}
////				pr = ProjectDao.doselectProject(conn);
//				
//				//------------------------------peopleNum需要从数据库中获取---------------------------------------------
//				//获取peopleNum
//				peopleNum = bimg.length / 5;
//				peopleNum++;//因为注册多了一个人，所以peopleN+1
//				//实例化bImages图片数组
//				bImages = new BufferedImage[peopleNum * photoNum];
////				icon = new ImageIcon[peopleNum * photoNum];
//				
//				for(int i=0; i<bimg.length; i++)
//				{
//					bImages[i] = bimg[i];
//				}
//				for(int i=0; i<5; i++)
//				{
////					icon[bimg.length + i] = imageIcons[i];
//					Image img = imageIcons[i].getImage();
//					bImages[bimg.length + i] = ImageUtil.ImageToBufferedImage(img);
//				}
//				
//			}
//			//若不存在，则直接对自己的数据进行训练
//			else
//			{
//				haveSoy = true;
//				//设置peopleNum为2（酱油&自己)
//				peopleNum = 2;
////				//实例化icon图片数组
////				icon = new ImageIcon[peopleNum * photoNum];//[2 * 5]
//				//实例化bImages图片数组
//				bImages = new BufferedImage[peopleNum * photoNum];
////				tempForOneManBImages = new BufferedImage[photoNum];
//				
////				读取Pictures文件夹里面的"酱油"的图片，并赋值给bImages[0-4];
//				String source = "Pictures/none/";
//				for(int i=0; i<5; i++)
//				{
//					String source2 = "after37-" + (i+1) + ".jpg";
//					ImageIcon imageIcon = new ImageIcon(source + source2);
//					//将“酱油”的图片扩大
////					imageIcon = ImageHandle(imageIcon, 128, 128);
//					Image img = imageIcon.getImage();
//					bImages[i] = ImageUtil.ImageToBufferedImage(img);
//					soyBufferedImages[i] = ImageUtil.ImageToBufferedImage(img);
//				}
//				
//				//将自己的图片赋值给bImages[5-9];
//				for(int i=5; i<10; i++)
//				{
////					icon[i] = imageIcons[i-5];
//					Image img = imageIcons[i-5].getImage();
//					bImages[i] = ImageUtil.ImageToBufferedImage(img);
////					bImages[i] = ImageUtil.ImageToBufferedImage(img);
//				}
//				
//			}
//			
//			
//		}
//		catch(Exception e1)
//		{
//			e1.printStackTrace();
//		}
//		
////		//16348维，128*128
////		int[] vector1 = GetFeatureMatrix.getPixes(bImages[0]);
////		System.out.println("维数：" + vector1.length);
//		
//		//对bImages[]的图片进行小波变换
//		BufferedImage[] waveBImages;
//		waveBImages = Wavelet.Wavelet(bImages);
//		
//		//2.训练（将 本人的5张照片 和 数据库中的所有照片（每人5张） 投影到WoptT上)
//		GetPcaLda.getResult(waveBImages);
//		
//		//1024维，32*32
//		int[] vector = GetFeatureMatrix.getPixes(waveBImages[0]);
//		System.out.println("维数：" + vector.length);
//		
//		double[][] modelP=new double[peopleNum*photoNum][peopleNum-1];
//		for(int i=0;i<peopleNum*photoNum;i++){
//			modelP[i]=LDA.getInstance().calZ(waveBImages[i]);//投影
//		}
//		
////		验证需要5个数据，前3个都是double[][]
////		1.(为了计算<2>所用)WoptT（从单例中获取）
//		//double[] WoptT
////		2.2张照片的投影（将拍到的图片，通过Wopt投影后,转成double[][]）
//		double[][] testZ = new double[testNum][peopleNum-1];//[测试用例数量][C-1]
////		3.训练样例的投影（上面的modelP）
//		//double[][] modelP
////		4.<2>的均值（从<2>处理）
//		double[] testZMean = new double[peopleNum-1];
////		5.（投影Z的）N个人的，类内均值（每个人都有一个均值)
//		double[][] modelMean=new double[peopleNum][peopleNum-1];
////		6.（投影Z的）总体均值
//		double[] allMean=new double[peopleNum-1];
//		
//		//1.WoptT（从单例中获取）
//		WoptT = LdaFeatures.getInstance().getLastProjectionT();
//		
//		//2.2张照片的投影（将拍到的图片，通过Wopt投影后,转成double[][]）
//		//首先，将ImageIcon[2] testIcons转换成BufferedImage[2]
//		BufferedImage[] tempForTestBImages = new BufferedImage[testNum];
//		for(int i=0; i<testNum; i++)
//		{
//			Image img = testIcons[i].getImage();
//			tempForTestBImages[i] = ImageUtil.ImageToBufferedImage(img);
//		}
//		
//		//然后，对tempForTestBImages进行小波变换，转成BufferedImage[2]
//		BufferedImage[] waveTestBImages = Wavelet.Wavelet(tempForTestBImages);
//		
//		//计算2张经小波变换的测试图waveTestBImages的投影Z
//		for(int i=0; i<testNum; i++)
//		{
//			testZ[i]=LDA.getInstance().calZ(waveTestBImages[i]);
//		}
//		
//		//4.<2>的均值（从<2>处理）
//		for(int i=0; i<(peopleNum-1); i++)
//		{
//			for(int j=0; j<testNum; j++)
//			{
//				testZMean[i] += testZ[j][i];
//			}
//			testZMean[i] /= testNum;
//		}
//		
//		//5.（投影Z的）N个人的，类内均值（每个人都有一个均值)
//		//6.（投影Z的）总体均值
//		for(int i=0;i<peopleNum;i++){
//			for(int k=0;k<peopleNum-1;k++){
//				for(int j=0;j<photoNum;j++){
//					modelMean[i][k]+=modelP[photoNum*i+j][k];
//				}
//				allMean[k]+=modelMean[i][k];
//				modelMean[i][k]/=photoNum;
//			}			
//		}
//		
//		for(int i=0;i<peopleNum-1;i++)
//			allMean[i]/=peopleNum*photoNum;
//		
//		
//		//验证（尝试识别，识别失败则需要重新获取图片）
//		if( Mark.domark(testZ, modelP, testZMean, modelMean, allMean) == false)
//		{
//			JOptionPane.showMessageDialog(null, "照片样例识别失败！正在重新获取所有图片", "提示", JOptionPane.INFORMATION_MESSAGE);
//			//将数据初始化，以开始重新获取图片
//			requestNum = 7;
//			for(int i=0; i<7;i++)
//			{
//				isImageIconSelected[i] = true;
//			}
//			startChangeSelectedIcon = true;
//		}
//		//若可以，则注册成功，将用户名、密码、5张照片存入数据库
//		else{
////			JOptionPane.showMessageDialog(null, "注册成功！正在将数据存入数据库中。", "注册成功", JOptionPane.INFORMATION_MESSAGE);
//			ProgressBarSignIn.frameProgressBarSignIn = new ProgressBarSignIn();
//			System.out.println("1!!!!!!!!!!!!!!!");
//			ProgressBarSignIn.frameProgressBarSignIn.setVisible(true);
//			System.out.println("1!2222222222222222!!");
//			try
//			{
//				conn = Oracle_Connect.getInstance().getConn();
//				//封装double[][] Wopt 进 Wopt wopt
//				Wopt wopt = new Wopt();
//				wopt.setWopt(WoptT);
//				
//				
//				//将Wopt插入数据库中
//				ProjectDao.doinsertWopt(conn, wopt);
//				
//				//第一次,增加进度条
//				ProgressBarSignIn.frameProgressBarSignIn.startAddProgressBar();
//				
//				//将总体均值m插入数据库中
//				double[] m = LdaFeatures.getInstance().getAveVector();
//				ProjectDao.doinsertmean(conn, m);
//
//				
//				//第二次,增加进度条
//				ProgressBarSignIn.frameProgressBarSignIn.startAddProgressBar();
//				
//				//将插入数据库的图片,封装进inputStream
//				Imageinfo imageInfo = new Imageinfo();
//				InputStream[] inputStream = new InputStream[5];
//				
//				int[] userIds;
//				
//				//如果数据库中没人,先插酱油的
//				if (haveSoy)
//				{
//					//把酱油封装进User
//					User userSoy = new User();
//					userSoy.setUsername("none");
//					userSoy.setPassword("123456");
//					
//					//将ImageIcon转成InpustStream
//					for(int i=0; i<5; i++)
//					{
//						//inputStream[i] = imageIcons[i];
////						Image img = imageIcons[i].getImage();
////						BufferedImage tempBImg = ImageUtil.ImageToBufferedImage(img);
//						ByteArrayOutputStream os = new ByteArrayOutputStream();   
//						ImageIO.write(soyBufferedImages[i], "jpg", os);   
//						int[] le2 = ImageUtil.getPixes(soyBufferedImages[i]);
//						System.out.println("酱油图片的长度:" + le2.length);
//						inputStream[i] = new ByteArrayInputStream(os.toByteArray());  
//					}
//					imageInfo.setInputstream(inputStream);
//					
//					//插入账户、密码和图片（返回插入的id）
//					userIds = UserDao.doInsert(userSoy, conn, imageInfo);
//				}
//
//				
//				//将ImageIcon转成InpustStream
//				for(int i=0; i<5; i++)
//				{
//					//inputStream[i] = imageIcons[i];
//					Image img = imageIcons[i].getImage();
//					BufferedImage tempBImg = ImageUtil.ImageToBufferedImage(img);
//					ByteArrayOutputStream os = new ByteArrayOutputStream();   
//					ImageIO.write(tempBImg, "jpg", os);   
//					inputStream[i] = new ByteArrayInputStream(os.toByteArray());  
//				}
//				imageInfo.setInputstream(inputStream);
//				
//				//插入用户的账户、密码和图片（返回插入的id）
//				userIds = UserDao.doInsert(user, conn, imageInfo);
//				
//				
//				//将每个图像的差值图像[像素][n] 转置成 [n][像素]
//				double[][] mAveDeviation = LdaFeatures.getInstance().getAveDeviationDouble();
//				double[][] mAveDeviationTrans = Features.matrixTrans(mAveDeviation);
//				//将转置后的每个图像的差值图像存进数据库中
//				ProjectDao.doinsertPeoplemean(conn, mAveDeviationTrans, userIds);
//				
//				
//				//将每类的差值图像 [像素][n/num] 转置成 [n/num][像素]
//				double[][] mi = LdaFeatures.getInstance().getAveDeviationEach();
//				double[][] miTrans = Features.matrixTrans(mi);
//				//将转置后的mi存进数据库中
//				ProjectDao.doinsertclassmean(conn, miTrans, userIds);
//				
//				
//				//第三次,增加进度条
//				ProgressBarSignIn.frameProgressBarSignIn.startAddProgressBar();
//				
////				//若有“酱油”，则将“酱油”的投影移除
////				double[][] insertModelP = new double[modelP.length-5][modelP[0].length];
////				if( haveSoy == true)
////				{
////					int cloneLength = modelP.length-5;
////					for(int i=0; i<cloneLength; i++)
////					{
////						insertModelP[i] = modelP[i+5];
////					}
////				}
//				
//				//封装用户Id和投影Z 进 Project
//				Project project = new Project();
//				project.setId(userIds);
////				if(haveSoy == true)
////				{
//////					project.setProject(insertModelP);
////				}
////				else
//				{
//					project.setProject(modelP);
//				}
//				//插入所有投影
//				ProjectDao.doinsertProject(conn, project);
//				
//				//第四次,增加进度条
//				ProgressBarSignIn.frameProgressBarSignIn.startAddProgressBar();
//				
//			}
//			catch(Exception e1)
//			{
//				e1.printStackTrace();
//			}
//			
//			//提示数据库操作成功
//			JOptionPane.showMessageDialog(null, "数据已插入数据库中！", "操作成功", JOptionPane.INFORMATION_MESSAGE);
//			
//			//关闭进度条窗口
//			ProgressBarSignIn.frameProgressBarSignIn.dispose();
//			
//			frameRegist.dispose();
//			MainUI.frameMainUI = new MainUI();
//			MainUI.frameMainUI.setVisible(true);
//			
//			//最终注册成功后，将寻找人脸的方法暂停
//			findTask.stop();
//		}
//		
//		
//		
//	}
		
}
