package com.invindible.facetime.util.system;

import java.util.Properties;

public class SystemUtil {

	/**
	 * 获取当前系统的位数，根据所装的jdk位数确定。
	 * @return
	 */
	public static int getSystemBit() {
		Properties props=System.getProperties();
		String bitStr = props.getProperty("sun.arch.data.model");
		return Integer.parseInt(bitStr);
	}
	
}
