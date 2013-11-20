<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="utilities.Conexion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../inicio/head.jsp" />
<%

 String sql=
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
            table_rows+="<td>"+cell.getValue()+"</td>";
        }
        if (countRow==0){
            table_head+="</tr>";
        }
        table_rows+="</tr>";
        countRow++;
    }
    String table = "<table border ='1' align='center'>"+table_head+table_rows+"</table>";
    out.println(table);
%>
                    <table border="1" align="center">

                    </table>
<jsp:include page="../inicio/foot.jsp" />
