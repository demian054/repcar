/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author demian
 */
public class views_generator {
        String table_name;
        String schema_name;

    public views_generator(String table_name) {
        this.table_name = table_name;
    }
    
        public views_generator(String table_name, String schema_name) {
        this.table_name = table_name;
        this.schema_name = schema_name;
    }
    

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getSchema_name() {
        return schema_name;
    }

    public void setSchema_name(String schema_name) {
        this.schema_name = schema_name;
    }
    
    public String getFormCreate() {
            try {
                Conexion con = new Conexion();
                String table_head = "";
                String table_rows = "";
                List<LinkedHashMap<String,String>> informationTable = con.getInformationTable(this.table_name);
                for(HashMap<String,String> row: informationTable){
                    
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
                String form = "<form id='create_form' method='POST' action='create.jsp'>"
                        + "<table border ='1' align='center'>"+table_head+table_rows+"</table>"
                        + "</form>";
                return form;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "SQLException";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "ClassNotFoundException";
            }
    }  
    
    public String getFormDetail(String id_record) {
            try {
                Conexion con = new Conexion();
                String sql=
                    "select * from "+schema_name+"."+table_name
                    + " where"
                    + " deleted = '0'"
                    + " and id = "+id_record;
                
                List<LinkedHashMap<String,String>> result = con.select(sql);
                
                if (result==null || result.isEmpty()){
                    return "false";
                }
                
                LinkedHashMap<String,String> record = result.get(0);
                String table_head = "";
                String table_rows = "";
                List<LinkedHashMap<String,String>> informationTable = con.getInformationTable(this.table_name);
                for(HashMap<String,String> row: informationTable){
                    
                    table_rows+="<tr><td style='text-align: right; width: 50%'>"+row.get("column_name")+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
                    String imputType = row.get("data_type");
                    
                    if (imputType.equalsIgnoreCase("character varying")){
                        table_rows+="<td><input type='text' readonly='true' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("integer") || imputType.equalsIgnoreCase("bigint")){
                        table_rows+="<td><input type='text' readonly='true' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("bit")){
                        table_rows+="<td><input type='text' readonly='true' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("timestamp without time zone")){
                        table_rows+="<td><input type='text' readonly='true' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }
                }
                table_rows+="<tr>"
                        +"<td style='text-align: right'>"
                        + "<button type='submit' value='enviar'> aceptar </button></td>"
                        + "</tr>";
                String form = "<form id='detail_form' method='POST' action='listAll.jsp'>"
                        + "<table border ='1' align='center'>"+table_head+table_rows+"</table>"
                        + "</form>";
                return form;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "SQLException";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "ClassNotFoundException";
            }
    }  
        
    public String getFormUpdate(String id_record) {
            try {
                Conexion con = new Conexion();
                String sql=
                    "select * from "+schema_name+"."+table_name
                    + " where"
                    + " deleted = '0'"
                    + " and id = "+id_record;
                
                List<LinkedHashMap<String,String>> result = con.select(sql);
                
                if (result==null || result.isEmpty()){
                    return "false";
                }
                
                LinkedHashMap<String,String> record = result.get(0);
                String table_head = "";
                String table_rows = "";
                List<LinkedHashMap<String,String>> informationTable = con.getInformationTable(this.table_name);
                for(HashMap<String,String> row: informationTable){
                    
                    table_rows+="<tr><td style='text-align: right; width: 50%'>"+row.get("column_name")+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
                    String imputType = row.get("data_type");
                    
                    if (imputType.equalsIgnoreCase("character varying")){
                        table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("integer") || imputType.equalsIgnoreCase("bigint")){
                        table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("bit")){
                        table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }else
                    if (imputType.equalsIgnoreCase("timestamp without time zone")){
                        table_rows+="<td><input type='text' id='"+row.get("column_name")+"' value='"+record.get(row.get("column_name"))+"' name='"+row.get("column_name")+"'></td>";
                    }
                }
                 table_rows+="<tr>"
                        +"<td style='text-align: right'>"
                        + "<input type='hidden' id='action' value='update' name='action'>"
                        + "<button type='submit' value='enviar'> Enviar </button></td>"
                        +"<td style='text-align: left'><button type='reset' value='cancelar'> Cancelar </button></td>"
                        + "</tr>";
                String form = "<form id='update_form' method='POST' action='update.jsp'>"
                        + "<table border ='1' align='center'>"+table_head+table_rows+"</table>"
                        + "</form>";
                return form;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "SQLException";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "ClassNotFoundException";
            }
    }  
    
      public String getListAll() {
            try {
                String sql=
                        "select * from "+schema_name+"."+table_name
                        + " where"
                        + " deleted = '0'";
                Conexion con = new Conexion();
                List<LinkedHashMap<String,String>> result = con.select(sql);
                
                String table_head = "";
                String table_rows = "";
                Integer countRow = 0;
                
                for(HashMap<String,String> row: result){
                    Iterator<Map.Entry<String, String>> it = row.entrySet().iterator();
                    if (countRow==0){
                        table_head+="<tr>";
                    }
                    table_rows+="<tr>";
                    while (it.hasNext()) {
                        Map.Entry cell = (Map.Entry)it.next();
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
                String table = "<a href='create.jsp'>Crear Nuevo</a>"
                        + "<table border ='1' align='center'>"+table_head+table_rows+"</table>"
                        + "<a href='../inicio/menu.jsp'>Volver al Menu</a>";
                return table;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
    }  
        
        
    
}
