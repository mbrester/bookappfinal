

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
        
        <form method="POST" action="BookController?action=update">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                
                    
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${book.bookId}" name="bookId" readonly/></td>
                        </tr>         
                    
                
                
                <tr>
                    <td style="background-color: black;color:white;" align="left">Tittle</td>
                    <td align="left"><input type="text" value="${book.tittle}" name="tittle" /></td>
                </tr>

                
                            <tr>
                    <td style="background-color: black;color:white;" align="left">ISBN</td>
                    <td align="left"><input type="text" value="${book.isbn}" name="isbn" /></td>
                </tr>
                
                <tr>
                    <td style="background-color: black;color:white;" align="left">Author</td>
                    <td align="left">
                <select name="authorID">
                    
                    <c:forEach var="a" items="${authors}">
                        <c:if test="${book.authorId == a}">
                            <option value="${a.authorId}" selected="true">${a.authorName}</option>
                        </c:if>
                           <c:if test="${book.authorId != a}">
                            <option value="${a.authorId}" >${a.authorName}</option>
                        </c:if> 
                </c:forEach>
                          
                       </select>        
                   
                </td>
                </tr>
                
                
                
                <tr>
                    <input type="submit" value="Update" name="action" />
                </tr>
            </table>
        </form>
    </body>
</html>
