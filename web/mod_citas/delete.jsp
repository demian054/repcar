<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>
<%@page import="utilities.views_generator"%>
<%@page import="utilities.functions"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="utilities.Conexion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../inicio/head.jsp" />
<%
    
    
    
    Conexion con = new Conexion();
    String default_table = "citas";
    String default_schema = "bussines";
    String id = request.getParameter("id");
    
    String action = request.getParameter("action");
    //out.println(action);
    if (functions.isNullOrEmpty(id)){
        if (functions.isNullOrEmpty(action)){
            if (action.equalsIgnoreCase("delete")){
                 if (con.delete(default_table, default_schema, id)){
                     session.setAttribute("msg", "Registro Eliminado Correctamente");
                     response.sendRedirect("listAll.jsp?msg=true");
                 }else{
                     session.setAttribute("msg_error", "Problemas al Eliminar Registro");
                     response.sendRedirect("delete.jsp?id="+id+"&msg_error=true");
                 };
            }
        }
    //out.println(action);
    
        views_generator vg = new views_generator(default_table,default_schema);
        out.println(vg.getFormDelete(id));
    }
%>
<jsp:include page="../inicio/foot.jsp" />


