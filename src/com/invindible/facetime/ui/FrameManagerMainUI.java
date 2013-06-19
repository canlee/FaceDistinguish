package com.invindible.facetime.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;

public class FrameManagerMainUI extends JFrame {

	static FrameManagerMainUI frameManagerMainUI;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameManagerMainUI frame = new FrameManagerMainUI();
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
	public FrameManagerMainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 664, 439);
		tabbedPane.setFont(new Font("宋体", Font.PLAIN, 16));
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("员工签到情况", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panelTimePick = new JPanel();
		panelTimePick.setBounds(0, 0, 244, 101);
		panel_1.add(panelTimePick);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("用户列表", null, panel, null);
		panel.setLayout(null);
	}
}
