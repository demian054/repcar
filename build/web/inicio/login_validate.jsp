<%-- 
    Document   : login_validate
    Created on : 19/11/2013, 10:08:55 PM
    Author     : demian
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="entity.system.User"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utilities.Conexion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String user_name = request.getParameter("user");
    String pass = request.getParameter("pass");
    
    //System.out.println(user_name+pass);
    
    String sql=
            "select * from system.user "
            + "where"
            + " user_name = '"+user_name+"' "
            + " and pass = '"+pass+"' ";
    
    
    Conexion con = new Conexion();
    List<LinkedHashMap<String,String>> result = con.select(sql);
    
    if (result==null || result.isEmpty()){
        session.setAttribute("msg_error", "Usuario o Password Incorrectos");
        response.sendRedirect("login.jsp?msg_error=true");
    }else{
        LinkedHashMap<String,String> row = result.get(0);
        User user = new User();
        
        user.setId(Integer.parseInt(row.get("id")));
        user.setUser_name(row.get("user_name"));
        user.setName(row.get("_name"));
        user.setLast_name(row.get("last_name"));
        
        session.setAttribute("current_user", user);
        response.sendRedirect("blank.jsp");
    }
            
    
    
        




%>