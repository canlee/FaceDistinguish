package com.invindible.facetime.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.invindible.facetime.model.Project;
import com.invindible.facetime.model.Wopt;

public class ProjectDao {
	/**
	 * 插入wopt矩阵
	 * @param conn
	 * @param wopt
	 * @throws SQLException
	 */
	public void doinsertWopt(Connection conn,Wopt wopt) throws SQLException{
		clean(conn);
		String save=procedure(wopt.getWopt());
		PreparedStatement pst=conn.prepareStatement("insert into project values(?)");
		pst.setString(1, save);
		pst.executeUpdate();
		pst.close();
	}
	
	/**
	 * 更新前，先把原来的删除
	 * @param conn
	 * @throws SQLException
	 */
	public void clean(Connection conn) throws SQLException{
		PreparedStatement pst=conn.prepareStatement("select * from Wopt");
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			pst=conn.prepareStatement("delete from Wopt");
		}
	}
	
	public String procedure(double[][] array){
		String save=null;
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				save+=String.valueOf(array[i][j])+"p";
			}
		}
		return save;
	}
	
	
	public void doinsertProject(Connection conn,Project project){
		
	}
}
