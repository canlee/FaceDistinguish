package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.invindible.facetime.ui.widget.image.ImagePanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowSatisfiedPicture extends JFrame {

	private JPanel contentPane;
	private int pageNum;
	private JButton btnPageUp;
	private JButton btnPageDown;
	private ImagePanel ipObjectOriginal;
	private ImagePanel ipObjectFound;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowSatisfiedPicture frame = new ShowSatisfiedPicture();
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
	public ShowSatisfiedPicture() {
		
		pageNum = 1;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5BF9\u8C61  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 17, 438, 263);
		contentPane.add(panel);
		panel.setLayout(null);
		
		ipObjectOriginal = new ImagePanel();
		ipObjectOriginal.setBounds(45, 75, 128, 128);
		panel.add(ipObjectOriginal);
		
		ipObjectFound = new ImagePanel();
		ipObjectFound.setBounds(257, 75, 128, 128);
		panel.add(ipObjectFound);
		
		JLabel lblObjectNum = new JLabel("1");
		lblObjectNum.setFont(new Font("宋体", Font.PLAIN, 16));
		lblObjectNum.setBounds(35, 0, 54, 15);
		panel.add(lblObjectNum);
		
		JLabel lblNewLabel = new JLabel("对象原图：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setBounds(43, 35, 87, 30);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("视频中的截图：");
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(257, 35, 113, 30);
		panel.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("截图时间：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(83, 213, 199, 15);
		panel.add(lblNewLabel_1);
		
		JLabel label_1 = new JLabel("0时 0分 0秒");
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(137, 238, 199, 15);
		panel.add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(483, 66, 140, 133);
		contentPane.add(panel_1);
		
		JLabel lblPage = new JLabel("第 [1] 页");
		lblPage.setFont(new Font("宋体", Font.PLAIN, 16));
		lblPage.setBounds(25, 5, 83, 19);
		panel_1.add(lblPage);
		
		btnPageUp = new JButton("上一页");
		btnPageUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pageNum++;
				btnPageDown.setEnabled(true);
				
				if(pageNum == 9)
				{
					btnPageDown.setEnabled(false);
				}
				RefreshUi();
			}
		});
		btnPageUp.setFont(new Font("宋体", Font.PLAIN, 14));
		btnPageUp.setEnabled(false);
		btnPageUp.setBounds(25, 34, 83, 28);
		panel_1.add(btnPageUp);
		
		btnPageDown = new JButton("下一页");
		btnPageDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pageNum--;
				btnPageUp.setEnabled(true);
				
				if(pageNum == 1)
				{
					btnPageUp.setEnabled(false);
				}
				
			}
		});
		btnPageDown.setFont(new Font("宋体", Font.PLAIN, 14));
		btnPageDown.setBounds(25, 82, 83, 28);
		panel_1.add(btnPageDown);
	}
	
	/**
	 * 根据页数，刷新界面，显示对象
	 */
	private void RefreshUi()
	{
		switch(pageNum)
		{
			case 1:
				break;
				ipObjectOriginal.setBufferImage(bufferImage);
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			default :
				break;
		}
	}
}
