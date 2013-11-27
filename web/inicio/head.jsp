<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>


<%@page import="entity.system.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User session_current_user = (User) session.getAttribute("current_user");
    User current_user = new User();
    
    if (session_current_user!=null &&  session_current_user.getUser_name()!=""){
         current_user = session_current_user;  
    }else{
        session.setAttribute("msg_error", "La session ha expirado, por favor inicie de nuevo");
        response.sendRedirect("/rep-car3/inicio/login.jsp?msg_error=true");
        //out.print("error de sesion");
        //return;
       
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/styles.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/jquery.dataTables.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/jquery.multiselect.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="/rep-car3/assets/css/jquery.multiselect.filter.css" media="screen" />
        
         
        
	<script src="/rep-car3/assets/js/jquery-1.9.1.js"></script>
        <script src="/rep-car3/assets/js/jquery.dataTables.js"></script>
        <script src="/rep-car3/assets/js/jquery-ui-1.10.3.custom.js"></script>
        <script src="/rep-car3/assets/js/form-validator/jquery.form-validator.js"></script>
        <script src="/rep-car3/assets/js/jquery.multiselect.js"></script>
        <script src="/rep-car3/assets/js/jquery.multiselect.filter.js"></script>
	
        
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
                $( ".update_record_name" ).button({
                        text: true,
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
                $( ".delete_record_name" ).button({
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

                 $( ".back" ).button({
                        text: true,
			icons: {
				primary: "ui-icon ui-icon-arrowreturnthick-1-w"
			}
		});
                
                $('.data_table').dataTable({
                    bJQueryUI: true
                });
                
                $(".tabs_principal" ).tabs();
                $(".menu" ).menu();
                //$(".form").validate();
                
                $(".date").datepicker({
                    dateFormat: 'dd/mm/yy',
                    onSelect: function(dateText, inst) {
                        //lanzamos el onblur para que el validador ataje el evento y valide
                        $(this).blur();
                    }
                });
                
                $(".select").multiselect({
                    multiple: false,
                    header: "Seleccione una Opción",
                    noneSelectedText: "Seleccione una Opción",
                    selectedList: 1
                });
                
                $('.input').addClass("ui-corner-all");
                
                var myLanguage = {
                    errorTitle : 'Error de Validación en el Formulario !',
                    requiredFields : 'Los campos son Obligatorios',
                    badTime : 'El campo debe contener una Fecha valida',
                    badEmail : 'You have not given a correct e-mail address',
                    badTelephone : 'You have not given a correct phone number',
                    badSecurityAnswer : 'You have not given a correct answer to the security question',
                    badDate : 'You have not given a correct date',
                    tooLongStart : 'You have given an answer longer than ',
                    tooLongEnd : ' characters',
                    tooShortStart : 'You have given an answer shorter than ',
                    tooShortEnd : ' characters',
                    badLength : 'You have to give an answer between ',
                    notConfirmed : 'Values could not be confirmed',
                    badDomain : 'Incorrect domain value',
                    badUrl : 'The answer you gave was not a correct URL',
                    badCustomVal : 'You gave an incorrect answer',
                    badInt : 'El campo solo debe contener Números',
                    badSecurityNumber : 'Your social security number was incorrect',
                    badUKVatAnswer : 'Incorrect UK VAT Number',
                    badStrength : 'The password isn\'t strong enough',
                    badNumberOfSelectedOptionsStart : 'You have to choose at least ',
                    badNumberOfSelectedOptionsEnd : ' answers',
                    badAlphaNumeric : 'The answer you gave must contain only alphanumeric characters ',
                    badAlphaNumericExtra: ' and ',
                    wrongFileSize : 'The file you are trying to upload is too large',
                    wrongFileType : 'The file you are trying to upload is of wrong type'
                };

                $.validate({
                    //validateOnBlur : false, // disable validation when input looses focus
                    errorMessagePosition : 'top',
                    language : myLanguage,
                    decimalSeparator : ','
                });
                
                
	});
	</script>
        
        
    </head>
    <body>
        <table class="table_body">
            <tr id="cabecera" class="cabecera">
                <td colspan="2">
                    <img src='/rep-car3/assets/images/banner2.jpg' > 
                </td>
            </tr>
           
            <tr>
                <td style="text-align: left; width: 20% ">
                     <% if(current_user.getUser_name()!=""){ %>Usuario: <% out.println(current_user.getName()+" "+current_user.getLast_name());%>  <% } %></td>
                <td style="text-align: right; width: 80%"><button type='button' class='back' onclick="window.location.href='/rep-car3/inicio/login.jsp'">Cerrar Sesión</button></td>
            </tr>
            
            <tr style="vertical-align: top;">
                <td id="menu" style="width: 20%">
                    <jsp:include page="menu.jsp"/>
                </td>
                 <td id="cuerpo" style="width: 80%">
