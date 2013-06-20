package com.invindible.facetime.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OracleConfig {
	public static void config(String username,String password) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    final  String url="jdbc:oracle:thin:@localhost:1521:orcl";
	    final  String user=username;
	    final  String pass=password;
	    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		Connection conn = DriverManager.getConnection(url,user,password);
	    PreparedStatement pst;
		File f=new File("oracle\\create_user.sql");
		BufferedReader br=new BufferedReader(new FileReader(f));
		String tmp="";
		String sql="";
		while((tmp=br.readLine())!=null){
			if(tmp.equals(";"))
				{
					pst=conn.prepareStatement(sql);	
					System.out.println(sql);
					pst.execute();
					sql="";
					pst.close();
				}
			else
				sql+=tmp;
		}
		br.close();		
	}
	
	public static void configtable() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
		Connection conn=Oracle_Connect.getInstance().getConn();
		File f=new File("oracle\\table.sql");
		BufferedReader br=new BufferedReader(new FileReader(f));
		String tmp="";
		String sql="";
		PreparedStatement pst;
		while((tmp=br.readLine())!=null){
			if(tmp.equals(";"))
				{
					pst=conn.prepareStatement(sql);
					System.out.println(sql);
					pst.execute();
					sql="";
					pst.close();
				}
			else
				sql+=tmp;
		}
		br.close();
	}
	
	public static void main(String args[]){
		try {
			//config();
			configtable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
