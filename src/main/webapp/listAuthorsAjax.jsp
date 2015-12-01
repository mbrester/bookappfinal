<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        <table id="authorListTable" width="500" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
            </thead>
            <tbody id="authorTblBody">
                
            </tbody>
        
        </table>
            
        <p>Click here to go back to Home Page <a href="index.html">Home</a></p>

       
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.authority"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>
    
    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
        <script src="/MainJs.js" type="text/javascript"></script>
    </body>
</html>