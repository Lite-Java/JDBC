package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtils {
	private static String url="jdbc:mysql://localhost:3306/mydatabase";
	private static String user="root";
	private static String password="";

	//���಻�ܼ̳У��������ⲿ����ʵ��
	private JdbcUtils(){
		
	}
	//ע���������ھ�̬������У���JVM���ؽ��������ִֻ��һ��
	static{
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new ExceptionInInitializerError(e);
			}
		
	}
	//��������
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
	//�ͷ���Դ
	public static void free(ResultSet rs,Statement st,Connection conn){
		try {
			if(rs!=null)
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st!=null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					if(conn!=null)
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
			
		}
		
	}
	
}
