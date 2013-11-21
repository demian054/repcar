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
    String default_table = "clientes";
    String default_schema = "bussines";
    Conexion con = new Conexion();
    String action = request.getParameter("action");
    //out.println(action);
    if (functions.isNullOrEmpty(action)){
        if (action.equalsIgnoreCase("create")){
             if (con.insert(default_table, default_schema, request, session)){
                 session.setAttribute("msg", "Registro Ingresado Correctamente");
                 response.sendRedirect("listAll.jsp?msg=true");
             }else{
                 session.setAttribute("msg_error", "Problemas al Crear Registro");
                 response.sendRedirect("create.jsp?msg_error=true");
             };
        }
    }
    views_generator vg = new views_generator(default_table);
    out.println(vg.getFormCreate());
%>

<jsp:include page="../inicio/foot.jsp" />


