package JDBC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClobTest {

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
			String sql="insert into clob_test(clob_test) values(?)";
			ps=conn.prepareStatement(sql);
			File file= new File("src/JDBC/JdbcUtils.java");
			Reader reader=new BufferedReader(new FileReader(file));
			
			ps.setCharacterStream(1, reader, (int)file.length());;
			int i=ps.executeUpdate();
			reader.close();
			System.out.println("i="+i);			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}
	public static void read() throws SQLException, IOException{
		Connection conn=null;
		//Statement st=null;
		//��ֹ�ӣѣ�ע�룬������Ԥ����
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			//st=conn.createStatement();			
			String sql="select clob_test from clob_test where ID=2";
			ps=conn.prepareStatement(sql);
			//ps.setString(1, name);
			rs=ps.executeQuery();
			//�����õ���sql�е�date���ͣ���ҵ���߼�����ʹ�õ�utils�е�date���ͣ���Ҫת���ġ�
			while(rs.next()){
				File file=new File("JdbcUtils_bak.java");
				Clob clob=rs.getClob(1);
				
				Reader reader=clob.getCharacterStream();
				//reader=rs.getCharacterStream(1);
				Writer writer=new BufferedWriter(new FileWriter(file));
				char[] buff=new char[1024];
				for(int i=0;(i=reader.read(buff))>0;){
					writer.write(buff, 0, i);
					writer.flush();
				}
				writer.close();
				reader.close();
			}
			
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
