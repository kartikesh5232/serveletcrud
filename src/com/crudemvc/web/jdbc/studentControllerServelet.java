package com.crudemvc.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/studentControllerServelet")
public class studentControllerServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private studentDbUtil studentutil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;
		
   
    @Override
	public void init() throws ServletException {
		
		super.init();
	    
		try {
			
			studentutil=new studentDbUtil(datasource);
			
			
			
		}catch(Exception exc)
		{
			throw new ServletException(exc);
		}
		
		
    }


    
	public studentControllerServelet() {
        super();

    }


	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String command=request.getParameter("command");
			if(command==null)
			{
				command="LIST";
			}
			switch(command)
			{
			case "LIST":
				listStudents(request,response);
				break;
			case "UPDATE":
				updateStudent(request,response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			case "DELETE":
				deletStudent(request,response);
				break;
			case "LOAD":
				loadStudent(request,response);
				break;
			default:
				listStudents(request,response);
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	
	}

	
	

	private void deletStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String id=request.getParameter("studentId");
		studentutil.deleteStudent(id);
		
		listStudents(request,response);
		
		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("studentID"));
		String firstname=request.getParameter("firstName");
		String lastname=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		Students student=new Students(id,firstname,lastname,email);
		
		studentutil.updateStudent(student);
		
		
		listStudents(request,response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String studentid=request.getParameter("studentID");
		
		Students student=studentutil.getStudent(studentid);
		
		request.setAttribute("STUDENT", student);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	
		
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstname=request.getParameter("firstName");
		String lastname=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		Students student=new Students(firstname,lastname,email);
		
		studentutil.addStudent(student);
		
		
		listStudents(request,response);
		
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Students> students=studentutil.getStudents();
		request.setAttribute("STUDENT_LIST",students);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
