/**
 * 
 */
package JDBC;

import java.sql.*;

/**
 * @author 刘欢
 *
 */
public class CURD {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		read("name1");
		//create();
		//update();
		//delete();
	}
	public static void create() throws SQLException{
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			st=conn.createStatement();
			String sql="insert into user(name,birthday,money) values('name1','1996-03-04','20.98')";
			int i=st.executeUpdate(sql);
			System.out.println("i="+i);


			
		}finally{
			JdbcUtils.free(rs, st, conn);
		}
	}
	public static void update() throws SQLException{
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			st=conn.createStatement();
			String sql="update user set money=money-10 where id=2";
			int i=st.executeUpdate(sql);
			System.out.println("i="+i);


			
		}finally{
			JdbcUtils.free(rs, st, conn);
		}
	}
	public static void read(String name) throws SQLException{
		Connection conn=null;
		//Statement st=null;
		//防止ＳＱＬ注入，进行了预处理
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			//st=conn.createStatement();			
			String sql="select id,name,birthday,money from user where name=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			//这里获得的是sql中的date类型，而业务逻辑层是使用的utils中的date类型，需要转换的。
			while(rs.next()){
				System.out.println(rs.getInt("id")+"\t"+rs.getString("name")+
				"\t"+rs.getDate("birthday")+"\t"+rs.getFloat("money"));
			}
			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}
	public static void delete() throws SQLException{
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			st=conn.createStatement();
			String sql="delete from user where id=2";
			int i=st.executeUpdate(sql);
			System.out.println("i="+i);


			
		}finally{
			JdbcUtils.free(rs, st, conn);
		}
	}

}
