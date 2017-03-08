package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Datetest {

	public static void main(String[] args) throws SQLException {
		//create("name2",new Date(),300.8f);
		read("name2");

	}
	public static void create(String name,Date birthday,float money) throws SQLException{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="insert into user(name,birthday,money) values(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDate(2, new java.sql.Date(birthday.getTime()));
			ps.setFloat(3, money);
			
			
			int i=ps.executeUpdate();
			System.out.println("i="+i);


			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}
	public static void read(String name) throws SQLException{
		Connection conn=null;
		//Statement st=null;
		//��ֹ�ӣѣ�ע�룬������Ԥ����
		Date birthday=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			//st=conn.createStatement();			
			String sql="select id,name,birthday,money from user where name=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			//�����õ���sql�е�date���ͣ���ҵ���߼�����ʹ�õ�utils�е�date���ͣ���Ҫת���ġ�
			while(rs.next()){
				birthday=new Date(rs.getDate("birthday").getTime());
				System.out.println(birthday);
			}
			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}

}
