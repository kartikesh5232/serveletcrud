package com.crudemvc.web.jdbc;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class studentDbUtil {
	
	DataSource datasource;

	public studentDbUtil(DataSource datasource) {
		
		this.datasource=datasource;
	}
	
	
	public List<Students> getStudents() throws Exception{
	List<Students> students =new ArrayList<Students>();
	
	Connection conn=null;
	Statement stmnt=null;
	ResultSet rslt=null;
	
	
	
	try {
		conn=datasource.getConnection();
		String sql="select * from student order by last_name";
		
		stmnt=conn.createStatement();
		
		rslt=stmnt.executeQuery(sql);
		
		while(rslt.next())
		{
			int id=rslt.getInt("id");
			String firstname=rslt.getString("first_name");
			String lastname=rslt.getString("last_name");
			String email=rslt.getString("email");
			
			
			Students student=new Students(id,firstname,lastname,email);
			
			students.add(student);
		}
		
		
		
	}finally{
		close(conn,stmnt,rslt);
		
	}
	
		return students;
		
	}


	private void close(Connection conn, Statement stmnt, ResultSet rslt) {
		
		try {
			if(conn!=null)
			{
				conn.close();
			}
			
			if(stmnt!=null)
			{
				stmnt.close();
			}
			
			if(rslt!=null)
			{
				rslt.close();
			}
			
		}catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
	}


	public void addStudent(Students student) throws Exception {
		Connection conn=null;
		PreparedStatement stmnt=null;
		
		try {
			conn=datasource.getConnection();
			String sql="insert into student "+ "(first_name,last_name,email) "+"values (?, ?, ?)";
			
			stmnt=conn.prepareStatement(sql);
			
			stmnt.setString(1, student.getFirstname());
			stmnt.setString(2, student.getLastname());
			stmnt.setString(3, student.getEmail());
			 
			stmnt.execute();
			
		}finally {
			close(conn,stmnt,null);
			
		}
		
	}


	public Students getStudent(String studentid) throws Exception {
		Students student=null;
		Connection conn=null;
		PreparedStatement stmnt=null;
		ResultSet rslt=null;
		int id=Integer.parseInt(studentid);
		
		
		try {
			
			conn=datasource.getConnection();
			String sql="select * from student where id=?";
			stmnt=conn.prepareStatement(sql);
			stmnt.setInt(1,id);
			
			
			rslt=stmnt.executeQuery();
			
			if(rslt.next())
			{
				
				
				String firstname=rslt.getString("first_name");
				String lastname=rslt.getString("last_name");
				String email=rslt.getString("email");
				
				student=new Students(id,firstname,lastname,email);
				

				
			}else
			{
				throw new Exception("Coult not find student id: "+id);
			}
			
			return student;
			
			
		}finally {
			close(conn,stmnt,rslt);
			
			
		}
		
		
	
	}


	public void updateStudent(Students student) throws Exception {
	
		Connection conn = null;
		PreparedStatement stmnt = null;

		try {
			
			conn = datasource.getConnection();
		
			String sql = "update student "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			
			stmnt = conn.prepareStatement(sql);
			
			
			stmnt.setString(1, student.getFirstname());
			stmnt.setString(2, student.getLastname());
			stmnt.setString(3, student.getEmail());
			stmnt.setInt(4, student.getId());
			
			
			stmnt.execute();
		}
		finally {
			
			close(conn, stmnt, null);
		}
	
		
		
	}


	public void deleteStudent(String id) throws Exception {
		
		Connection conn = null;
		PreparedStatement stmnt = null;
		int studentID=Integer.parseInt(id);
		
		

		try {
			
			conn = datasource.getConnection();
		
			String sql ="delete from student where id=?";
			
			stmnt = conn.prepareStatement(sql);
			
			stmnt.setInt(1,studentID);
			stmnt.execute();
		}
		
		
		
		finally {
			
			close(conn, stmnt, null);
		}
		
		
	
	}
	

}
