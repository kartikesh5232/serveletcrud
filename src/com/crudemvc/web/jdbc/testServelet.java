package com.crudemvc.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/testServelet")
public class testServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;
       
   
    public testServelet() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out=response.getWriter();
		
		response.setContentType("text/plain");
		
		
		
		Connection conn=null;
		Statement stmnt=null;
		ResultSet rslt=null;
		
		
		
		try {
			conn=datasource.getConnection();
			
			String sql="select * from student";
			
			stmnt=conn.createStatement();
			
			rslt=stmnt.executeQuery(sql);
	
			
			while(rslt.next()) {
				String email=rslt.getString("email");
				out.print(email);
			}
			
		}catch(Exception exc) {
			out.println(exc.toString());
		}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
