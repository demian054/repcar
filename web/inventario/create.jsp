<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>
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
 String sql=
            "select "
         + "column_name, "
         + "is_nullable, "
         + "data_type, "
         + "character_maximum_length "
         + "from INFORMATION_SCHEMA.COLUMNS where table_name = 'inventarios'"
         + "order by ordinal_position ";
 
    Conexion con = new Conexion();
    List<LinkedHashMap<String,String>> result = con.select(sql);
    
    String action = request.getParameter("action");
    //out.println(action);
    if (functions.isNullOrEmpty(action)){
        if (action.equalsIgnoreCase("create")){

            String insert_head = "";
            String insert_row = "";
             for(HashMap<String,String> insert_heads: result){
                String column_name = insert_heads.get("column_name");
                String column_value = request.getParameter(column_name);
                if (functions.isNullOrEmpty(column_value)){
                    //TODO: validaciones
                    insert_head = functions.addWhithComma(insert_head, column_name);
                    insert_row = functions.addWhithComma(insert_row, "'"+column_value+"'");
                }                 
             }
             String insert_sql = "INSERT INTO bussines.inventarios("+insert_head+") VALUES ("+insert_row+")";
             if (con.execute(insert_sql)){
                 session.setAttribute("msg", "Registro Ingresado Correctamente");
                 response.sendRedirect("listAll.jsp?msg=true");
             }else{
                 session.setAttribute("msg_error", "Problemas al Crear Registro");
                 response.sendRedirect("create.jsp?msg_error=true");
             };
        }
    }

  //  String user_name = request.getParameter("user");
  //  String pass = request.getParameter("pass");
    String table_head = "";
    String table_rows = "";
    
    for(HashMap<String,String> row: result){

        table_rows+="<tr><td style='text-align: right; width: 50%'>"+row.get("column_name")+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
        String imputType = row.get("data_type");
        
        if (imputType.equalsIgnoreCase("character varying")){
            table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='' name='"+row.get("column_name")+"'></td>";
        }else
        if (imputType.equalsIgnoreCase("integer") || imputType.equalsIgnoreCase("bigint")){
            table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='' name='"+row.get("column_name")+"'></td>";
        }else
        if (imputType.equalsIgnoreCase("bit")){
            table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='' name='"+row.get("column_name")+"'></td>";
        }else
        if (imputType.equalsIgnoreCase("timestamp without time zone")){
            table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='' name='"+row.get("column_name")+"'></td>";
        }
    }
    table_rows+="<tr>"
                    +"<td style='text-align: right'>"
                        + "<input type='hidden' id='action' value='create' name='action'>"
                        + "<button type='submit' value='enviar'> Enviar </button></td>"
                    +"<td style='text-align: left'><button type='reset' value='cancelar'> Cancelar </button></td>"
                + "</tr>";
    String table = "<form id='create_form' method='POST' action='create.jsp'>"
            + "<table border ='1' align='center'>"+table_head+table_rows+"</table>"
            + "</form>";
    out.println(table);
%>

<jsp:include page="../inicio/foot.jsp" />


