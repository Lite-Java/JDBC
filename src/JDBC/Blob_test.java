package JDBC;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Blob_test {

	public static void main(String[] args) throws SQLException, IOException {
		//create();
		read();

	}
	public static void create() throws SQLException, IOException{
		Connection conn=null;
		//Statement st=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="insert into blob_test2 (blob_test) values(?)";
			ps=conn.prepareStatement(sql);
			//File file= new File("JdbcUtils_bak.java");
			File file= new File("1.jpg");
			InputStream in=new FileInputStream(file);	
			byte[] buff=new byte[in.available()];
			in.read(buff, 0, in.available());
			ps.setBytes(1, buff);
			//ps.setBinaryStream(1, in, in.available());
			int i=ps.executeUpdate();
			in.close();
			System.out.println("i="+i);			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}
	public static void read() throws SQLException, IOException{
		Connection conn=null;
		//Statement st=null;
		//防止ＳＱＬ注入，进行了预处理
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			//st=conn.createStatement();			
			String sql="select blob_test from blob_test2 where id=6";
			ps=conn.prepareStatement(sql);
			//ps.setString(1, name);
			rs=ps.executeQuery();
			//这里获得的是sql中的date类型，而业务逻辑层是使用的utils中的date类型，需要转换的。
			while(rs.next()){
				File file=new File("my_test.jpg");
				Blob blob=rs.getBlob(1);
				InputStream in=blob.getBinaryStream();
				//Reader reader=clob.getCharacterStream();
				//reader=rs.getCharacterStream(1);
				OutputStream out=new BufferedOutputStream(new FileOutputStream(file));
				byte[] buff=new byte[1024];
				for(int i=0;(i=in.read(buff))>0;){
					out.write(buff, 0, i);
				}
				out.close();
				in.close();
			}
			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}

}
