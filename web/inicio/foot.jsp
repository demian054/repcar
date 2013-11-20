<%-- 
    Document   : login
    Created on : 19/11/2013, 09:03:32 PM
    Author     : demian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <% 
 String msg_error = request.getParameter("msg_error"); 
 String msg = request.getParameter("msg");
 %>
        </td>
            </tr>
            
             <tr id="pie" style="height: 70px">
                <td>
                   <%
                   if(msg_error!=null){
                      try{
                        String msg_error_string = (String) session.getAttribute("msg_error");
                        out.println("Error: "+msg_error_string);
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                   }
                   if(msg!=null){
                      try{
                        String msg_error_string = (String) session.getAttribute("msg");
                        out.println("Aviso: "+msg_error_string);
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                   }
                   %> 
                </td>
            </tr>
            
            
        </table>
    </body>
</html>
