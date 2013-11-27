<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="head.jsp" />
<%session.invalidate();%>

                    <form id="login_form" action="login_validate.jsp" method="post">
                    <h1>Login</h1>
                    <table border="1" align="center">
                        <tr><td>User: <input type="text" id="user" name="user"></td></tr>
                        <tr><td>Pass: <input type="text" id="pass" name="pass"></td></tr>
                        <tr><td><button type="submit" value="enviar"> Enviar </button>
                        <button type="reset" value="cancelar"> Cancelar </button></td></tr>
                    </table>
                    </form>
<jsp:include page="foot.jsp" />
