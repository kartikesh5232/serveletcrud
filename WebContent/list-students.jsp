<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>


<title>Student Lists
</title>


<link type="text/css" rel="stylesheet" href="css/style.css">

</head>


<body>


<div id="wrapper">
<div id="header">
<h2>Student List</h2>
</div>

</div>

<div id="container">
<div id="content">
<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" 
class="add-student-button">

<table>



<tr>
<th>First Name</th>
<th>Last Name</th>
<th>Email</th>
<th>Action</th>
</tr>


<c:forEach var="student" items="${STUDENT_LIST}">

<c:url var="link" value="studentControllerServelet">
<c:param name="command" value="LOAD"/>
<c:param name="studentID" value="${student.id}"/>

</c:url>

<c:url var="deleteLink" value="studentControllerServelet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${student.id}" />
					</c:url>


<tr>
<td> ${student.firstname} </td>

<td> ${student.lastname} </td>

<td> ${student.email} </td>

<td><a href="${link}">Update</a>|

<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a></td>
</tr>

</c:forEach>


</table>
</div>

</div>


</body>
</head>


</html>