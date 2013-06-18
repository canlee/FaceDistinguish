package com.invindible.facetime.database;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;


import oracle.sql.BLOB;

import com.invindible.facetime.model.Imageinfo;
import com.invindible.facetime.model.User;


public class UserDao {
	
	/**
	 * 注册所用 插入数据库
	 * @param u
	 * @param conn
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public  void doInsert(User u,Connection conn,Imageinfo im) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		PreparedStatement pst=conn.prepareStatement("insert into userinfo values(userid.nextval,?,?)");
		pst.setString(1, u.getUsername());
		pst.setString(2, u.getPassword());
		pst.executeUpdate();
		pst=conn.prepareStatement("select id from userinfo where username=? and password=?");
		pst.setString(1, u.getUsername());
		pst.setString(2, u.getPassword());
		ResultSet rs=pst.executeQuery();
		int id=rs.getInt("id");
		InputStream[] is=im.getInputstream();
		int pin,length;
		for(int i=0;i<is.length;i++){
			pin=is[i].read();
			length=0;
			while(pin!=-1)
			{
				length++;
			}
			pst=conn.prepareStatement("insert into imageinfo values("+id+",?)");
			pst.setBinaryStream(2, is[i],length);
			pst.executeUpdate();
			pst.close();
		}
	}
	
	/**
	 * 检验
	 * @param conn
	 */
	public void doSelect (Connection conn) {
		// TODO Auto-generated method stub
		try {
			Image im=null;
			PreparedStatement pst=conn.prepareStatement("select username,image from user_info");
			ResultSet rs=pst.executeQuery();	
			InputStream fos=null;
			while(rs.next()){
//				this.setUsername(rs.getString("username"));
				fos=rs.getBinaryStream("image");
				im=javax.imageio.ImageIO.read(fos);	
//				System.out.println("finish"+rs.getString("username"));
			}
			pst.close();
			rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
	public static boolean registerable(Connection conn,User u) throws SQLException{
		String username=u.getUsername();
		PreparedStatement pst=conn.prepareStatement("select username from userinfo");
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			if(rs.getString("username").equals(username))
				return false;
		}
		return true;
	}
	
	/**
	 * 一般的输入账户密码登陆
	 * @param u
	 * @param conn
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean doLogin(User u,Connection conn) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		boolean flag;
		PreparedStatement pst=conn.prepareStatement("select username,password from userinfo where username=? and password=?");
		pst.setString(1, u.getUsername());
		pst.setString(2, u.getPassword());
		ResultSet rs=pst.executeQuery();
		if(rs!=null)
			flag=true;
		else
			flag=false;
		pst.close();
		return flag;
	}
}
