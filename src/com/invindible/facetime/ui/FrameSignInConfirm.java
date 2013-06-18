package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import sun.applet.Main;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameSignInConfirm extends JFrame {

	
	static FrameSignInConfirm frameSignInConfirm;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameSignInConfirm frame = new FrameSignInConfirm();
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
	public FrameSignInConfirm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(95, 361, 406, 55);
		contentPane.add(panel);
		
		JButton buttonSign = new JButton("确认签到");
		buttonSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//显示签到成功，和签到的时间
				JOptionPane.showMessageDialog(null, "成功签到！签到的时间为："  , "提示", JOptionPane.INFORMATION_MESSAGE);
				
				FrameSignIn.frameSignIn.dispose();
				
				frameSignInConfirm.dispose();
				MainUI.frameMainUI = new MainUI();
				MainUI.frameMainUI.setVisible(true);
				
				
			}
		});
		buttonSign.setBounds(71, 10, 110, 35);
		panel.add(buttonSign);
		
		JButton buttonReturn = new JButton("返回");
		buttonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//询问是否确认返回
				//若确认返回
				if( JOptionPane.YES_OPTION == 
						JOptionPane.showConfirmDialog(null, "确认返回？", "提示", JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE))
				{
				frameSignInConfirm.dispose();
				
				FrameSignIn.frameSignIn.setVisible(true);
				}
				else
				{
					return;
				}
				
			}
		});
		buttonReturn.setBounds(231, 10, 110, 35);
		panel.add(buttonReturn);
		
		JPanel panelBox = new JPanel();
		panelBox.setBorder(new TitledBorder(null, "\u4FE1\u606F\u786E\u8BA4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBox.setBounds(10, 10, 637, 341);
		contentPane.add(panelBox);
		panelBox.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u7167\u7247", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 26, 435, 292);
		panelBox.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("暂无识别结果");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("华文细黑", Font.PLAIN, 16));
		label.setBounds(10, 10, 128, 128);
		panel_2.add(label);
		
		JLabel label_1 = new JLabel("暂无识别结果");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("华文细黑", Font.PLAIN, 16));
		label_1.setBounds(148, 10, 128, 128);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("暂无识别结果");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("华文细黑", Font.PLAIN, 16));
		label_2.setBounds(286, 10, 128, 128);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("暂无识别结果");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("华文细黑", Font.PLAIN, 16));
		label_3.setBounds(10, 154, 128, 128);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("暂无识别结果");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("华文细黑", Font.PLAIN, 16));
		label_4.setBounds(148, 154, 128, 128);
		panel_2.add(label_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "ID", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(467, 36, 140, 128);
		panelBox.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblUserId = new JLabel("你的用户名：");
		lblUserId.setFont(new Font("华文行楷", Font.PLAIN, 16));
		lblUserId.setBounds(20, 38, 110, 18);
		panel_1.add(lblUserId);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(20, 82, 125, 18);
		panel_1.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u5F53\u524D\u65F6\u95F4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(467, 174, 140, 140);
		panelBox.add(panel_3);
	}
}
