<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>
  <style>
  #menu_principal { width: 400px; }
  </style>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="head.jsp" />
    <div class='tabs_principal'> 
    <ul><li><a href='#tabs-1'>Modulos</a></li></ul>
    <div id='tabs-1'>
        <div style="margin-left: 300px">
            <lu class="menu" id = "menu_principal">
                <li><a href="../mod_inventario/listAll.jsp"> Inventario</a></li>    
                <li><a href="../mod_articulo/listAll.jsp"> Articulos</a></li>   
                <li><a href="../mod_clientes/listAll.jsp"> Clientes</a></li>   
                <li><a href="../mod_citas/listAll.jsp"> citas</a></li>
            </lu>
        </div>
    </div>
<jsp:include page="foot.jsp" />
