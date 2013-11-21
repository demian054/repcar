<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>

<%@page import="utilities.views_generator"%>
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
    views_generator vg = new views_generator(default_table,default_schema);
    out.println(vg.getListAll());

 /*String sql=
            "select * from bussines.inventarios "
            + "where"
            + " deleted = '0'";
    Conexion con = new Conexion();
    List<LinkedHashMap<String,String>> result = con.select(sql);
    
    String table_head = "";
    String table_rows = "";
    Integer countRow = 0;
    
    for(HashMap<String,String> row: result){
        Iterator<Entry<String, String>> it = row.entrySet().iterator();
        if (countRow==0){
            table_head+="<tr>";
        }
        table_rows+="<tr>";
        while (it.hasNext()) {
            Entry cell = (Entry)it.next();
            if (countRow==0){
                table_head+="<th>"+cell.getKey()+"</th>";
            }
            if (cell.getKey().toString().equalsIgnoreCase("id")){
                table_rows+="<td><a href='detail.jsp?id="+cell.getValue()+"'>Detalle</a>-"
                        + "<a href='update.jsp?id="+cell.getValue()+"'>Editar</a>-"
                        + "<a href='delete.jsp?id="+cell.getValue()+"'>Eliminar</a></td>";
            }else{
                table_rows+="<td>"+cell.getValue()+"</td>";
            }
            
        }
        if (countRow==0){
            table_head+="</tr>";
        }
        table_rows+="</tr>";
        countRow++;
    }
    String table = "<table border ='1' align='center'>"+table_head+table_rows+"</table>";*/
    
    
    
%>
<jsp:include page="../inicio/foot.jsp" />
