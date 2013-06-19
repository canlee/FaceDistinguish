package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class ProgressBarSignIn extends JFrame {

	static ProgressBarSignIn progressBarSignIn;
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblNewLabel;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressBarSignIn frame = new ProgressBarSignIn();
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
	public ProgressBarSignIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 128, 338, 29);
		contentPane.add(progressBar);
		
		lblNewLabel = new JLabel("注册成功！请稍等，");
		lblNewLabel.setFont(new Font("华文行楷", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 323, 55);
		contentPane.add(lblNewLabel);
		
		label = new JLabel("正在将数据插入数据库中.");
		label.setFont(new Font("华文行楷", Font.PLAIN, 20));
		label.setBounds(73, 63, 275, 55);
		contentPane.add(label);
	}
	
	public void addProgressBar()
	{
		this.progressBar.setValue( progressBar.getValue() + 25);
	}
}
