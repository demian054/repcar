<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>


<%@page import="entity.system.User"%>
<%@page  pageEncoding="UTF-8"%>
<%
    User session_current_user = (User) session.getAttribute("current_user");
    User current_user = new User();
    if (session_current_user==null){
        session.setAttribute("msg_error", "La session ha expirado, por favor inicie de nuevo");
        response.sendRedirect("login.jsp?msg_error=true");
    }else{
        current_user = session_current_user;  
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/styles.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/jquery.dataTables.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen" />
        
         
        
	<script src="/rep-car3/assets/js/jquery-1.9.1.js"></script>
        <script src="/rep-car3/assets/js/jquery.dataTables.js"></script>
        <script src="/rep-car3/assets/js/jquery-ui-1.10.3.custom.js"></script>
	
        
        <title>Login</title>
        
        <script>
	 $(document).ready(function(){
               // botones generales de 
		$( ".cancel" ).button({
                        label: "Cancelar",
			icons: {
				primary: "ui-icon-circle-close"
			}
		});
                $( ".aceptar" ).button({
                        label: "Aceptar",
			icons: {
				primary: "ui-icon-circle-check"
			}
		});
                
                // botones del ListAll
                $( ".detail_record" ).button({
                        text: false,
			icons: {
				primary: "ui-icon-search"
			}
		});
                
                // botones de Editar
                $( ".update_record" ).button({
                        text: false,
			icons: {
				primary: "ui-icon-pencil"
			}
		});
                $( ".update_record_" ).button({
                        text: false,
			icons: {
				primary: "ui-icon-pencil"
			}
		});
                
                
                // botones de Eliminar
                $( ".delete_record" ).button({
                        text: false,
			icons: {
				primary: "ui-icon-trash"
			}
		});
                $( ".delete_record_msg" ).button({
                        text: true,
			icons: {
				primary: "ui-icon-trash"
			}
		});
                
                //botones de Crear
                $( ".create_record" ).button({
                        text: true,
			icons: {
				primary: "ui-icon-plusthick"
			}
		});
                $('.data_table').dataTable({
                    bJQueryUI: true
                });
                
                $( ".tabs_principal" ).tabs();
                
                $( ".menu" ).menu();
                
                
	});
	</script>
        
        
    </head>
    <body>
        <table class="table_body">
            <tr id="cabecera" class="cabecera">
                <td>
                    <img src='/rep-car3/assets/images/banner2.jpg' > 

                </td>
            </tr>
            <tr><td><% out.println(current_user.getName()+" "+current_user.getLast_name());%></></tr>
             <tr id="cuerpo">
                 <td>
