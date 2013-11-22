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
    String id = request.getParameter("id");
    //out.println(action);
    if (functions.isNullOrEmpty(id)){
        views_generator vg = new views_generator(default_table,default_schema);
        out.println(vg.getFormDetail(id));
    }
%>
<jsp:include page="../inicio/foot.jsp" />


