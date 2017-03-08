package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtilsSingle {
	private  String url="jdbc:mysql://localhost:3306/mydatabase";
	private  String user="root";
	private  String password="";

	//该类不能继承，不能在外部创建实例
	private JdbcUtilsSingle(){
		
	}
	private static JdbcUtilsSingle instance=null;
	public static JdbcUtilsSingle getInstance(){
		if(instance==null){
			synchronized (JdbcUtilsSingle.class) {
				if(instance==null)
					instance=new JdbcUtilsSingle();
			}
		}
		return instance;
		
	}
	//注册驱动放在静态代码块中，由JVM加载进虚拟机后只执行一次
	static{
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new ExceptionInInitializerError(e);
			}
		
	}
	//建立连接
	public  Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
	//释放资源
	public  void free(ResultSet rs,Statement st,Connection conn){
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
