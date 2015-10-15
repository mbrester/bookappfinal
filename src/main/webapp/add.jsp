

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
        
        <form method="POST" action="AuthorController?action=new">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="" name="authorId" readonly/></td>
                        </tr>         
                    
              
                <tr>
                    <td style="background-color: black;color:white;" align="left">Name</td>
                    <td align="left"><input type="text" value="" name="authorName" /></td>
                </tr>
                
               
                               
                   
                
                <tr>
                    
                    <input type="submit" value="Add" name="action" />
                </tr>
            </table>
        </form>
    </body>
</html>
