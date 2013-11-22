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
    
        
      public String getListAll() {
            try {
                String sql=
                        "select * from "+schema_name+"."+table_name
                        + " where"
                        + " deleted = '0'";
                Conexion con = new Conexion();
                List<LinkedHashMap<String,String>> result = con.select(sql);
                
                String table_head = "<thead>";
                String table_rows = "<tbody>";
                Integer countRow = 0;
                if(result.isEmpty()){
                    table_head += "<tr><td>resultado</td></tr></thead>";
                    table_rows += "<tr><td>No se encontraron registros para mostrar</td></tr></tbody>";
                }else{
                    for(HashMap<String,String> row: result){
                        Iterator<Map.Entry<String, String>> it = row.entrySet().iterator();
                        if (countRow==0){
                            table_head+="<tr>";
                        }
                        table_rows+="<tr>";
                        while (it.hasNext()) {
                            Map.Entry cell = (Map.Entry)it.next();
                            if (!isSistemComumn(cell.getKey().toString())){
                                if (cell.getKey().toString().equalsIgnoreCase("id")){
                                    if (countRow==0){
                                        table_head+="<th>Acción</th>";
                                    }
                                    table_rows+="<td nowrap>"
                                            + "<button type='button' class='detail_record' onclick='window.location.href=\"detail.jsp?id="+cell.getValue()+"\"'> Ver Detalle </button>"
                                            + "<button type='button' class='update_record' onclick='window.location.href=\"update.jsp?id="+cell.getValue()+"\"'> Editar Registro </button>"
                                            + "<button type='button' class='delete_record' onclick='window.location.href=\"delete.jsp?id="+cell.getValue()+"\"'> Eliminar Registro </button>"
                                            + "</td>";
                                }else{
                                    if (countRow==0){
                                        table_head+="<th nowrap>"+cell.getKey()+"</th>";
                                    }
                                    table_rows+="<td>"+cell.getValue()+"</td>";
                                }
                            }
                        }
                        if (countRow==0){
                            table_head+="</tr>";
                        }
                        table_rows+="</tr>";
                        countRow++;
                    }
                }
                table_head += "</thead>";
                table_rows += "</tbody>";
                
                String table =  "<div class='tabs_principal'>" 
                        + "<ul><li><a href='#tabs-1'>Listado de "+table_name+"</a></li></ul>"
                        + "<div id='tabs-1'>"
                        + "<div style='text-align:left' id='toolbar' class='ui-widget-header ui-corner-all'><button type='button' class='create_record' onclick='window.location.href=\"create.jsp\"'>Crear Nuevo</button></div>"
                        + "<table class='data_table' align='center'>"+table_head+table_rows+"</table></div>"
                        + "<button type='button' class='back' onclick='window.location.href=\"../inicio/menu.jsp\"'>Volver al Menu</button>";
                return table;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
    } 
    
    public String getFormCreate() {
            try {
                Conexion con = new Conexion();
                String table_rows = "";
                List<LinkedHashMap<String,String>> informationTable = con.getInformationTable(this.table_name);
                table_rows += getForm(informationTable, false);
                table_rows+="<tr>"
                        +"<td style='text-align: right'>"
                        + "<input type='hidden' id='action' value='create' name='action'>"
                        + "<button type='submit' class ='create_record' id='aceptar' value='enviar'> Enviar </button></td>"
                        +"<td style='text-align: left'><button type='button' class ='cancel' id='cancel' onclick='window.location.href=\"listAll.jsp\"'>Cancelar</button></td>"
                        + "</tr>";
                String form = "<div style='text-align:left' id='toolbar' class='ui-widget-header ui-corner-all'><button type='button' class='create_record' onclick='window.location.href=\"create.jsp\"'>Crear Nuevo</button></div>"
                        + "<div class='tabs_principal'>" 
                        + "<ul><li><a href='#tabs-1'>Crear Nuevo Registro de "+table_name+"</a></li></ul>"
                        + "<div id='tabs-1'><form id='create_form' class='form' method='POST' action='create.jsp'>"
                        + "<table class='form_table' align='center'>"+table_rows+"</table>"
                        + "</form></div>";
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
                table_rows += getForm(informationTable, record, true);
                table_rows+="<tr>"
                        +"<td style='text-align: center' colspan='2'>"
                        + "<button type='submit'  class ='aceptar' id='aceptar' value='enviar'> aceptar </button></td>"
                        + "</tr>";
                String form = "<div class='tabs_principal'>" 
                        + "<ul><li><a href='#tabs-1'>Editar Registro de "+table_name+"</a></li></ul>"
                        + "<div id='tabs-1'><form id='detail_form' class='form' method='POST' action='listAll.jsp'>"
                        + "<table class='form_table' align='center'>"+table_head+table_rows+"</table>"
                        + "</form></div>";
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
                table_rows += getForm(informationTable, record, false);
                 table_rows+="<tr>"
                        +"<td style='text-align: right'>"
                        + "<input type='hidden' id='action' value='update' name='action'>"
                        + "<button type='submit' class='update_record_name' id='aceptar' value='enviar'> Enviar </button></td>"
                        +"<td style='text-align: left'><button type='button' class='cancel' id='cancel' onclick='window.location.href=\"listAll.jsp\"'>Cancelar</button></td>"
                        + "</tr>";
                String form = "<div class='tabs_principal'>" 
                        + "<ul><li><a href='#tabs-1'>Editar Registro de "+table_name+"</a></li></ul>"
                        + "<div id='tabs-1'><form id='update_form' class='form' method='POST' action='update.jsp'>"
                        + "<table align='center'>"+table_head+table_rows+"</table>"
                        + "</form></div>";
                return form;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "SQLException";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "ClassNotFoundException";
            }
    }  
 
      
    public String getFormDelete(String id_record) {
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
                table_rows += getForm(informationTable, record, true);
                table_rows+="<tr>"
                        +"<td style='text-align: right'>"
                        + "<input type='hidden' id='action' value='delete' name='action'>"
                        + "<button type='submit' class='delete_record_name' value='delete'> Eliminar </button></td>"
                        +"<td style='text-align: left'>"
                        + "<button type='button' class='cancel' onclick='window.location.href=\"listAll.jsp\"'>Cancelar</button></td>"
                        + "</tr>";
                String form = "<div class='tabs_principal'>" 
                        + "<ul><li><a href='#tabs-1'>Eliminar Registro de "+table_name+"</a></li></ul>"
                        + "<div id='tabs-1'><form id='delete_form' class='form' method='POST' action='delete.jsp'>"
                        + "<table class='form_table' align='center'>"+table_head+table_rows+"</table>"
                        + "</form></div>";
                return form;
            } catch (SQLException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "SQLException";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(views_generator.class.getName()).log(Level.SEVERE, null, ex);
                return "ClassNotFoundException";
            }
    }  
    
    
    public String getForm(List<LinkedHashMap<String,String>>  informationTable, boolean isReadonly){
        return getForm(informationTable, new LinkedHashMap<String,String>(), isReadonly);
    }
    
    
    public String getForm(List<LinkedHashMap<String,String>>  informationTable, LinkedHashMap<String,String> record, boolean isReadonly){
        String readonly = "";
        if (isReadonly){
            readonly = "readonly='true'";
        }
        String form_rows = "";
        for(HashMap<String,String> row: informationTable){
            String column_name = (String) functions.isNullOrEmpty(row.get("column_name"), "") ;
            
            String is_nullable = (String) functions.isNullOrEmpty(row.get("is_nullable"), "") ;
            String nullable = "";
            String nullable2 = "";
            
            String maximum_length = (String) functions.isNullOrEmpty(row.get("character_maximum_length"), "") ;
            String min_max_length = "";
            
            String column_value =  (String) functions.isNullOrEmpty(record.get(row.get("column_name")), "");
            String value = "";
            
            if (!isSistemComumn(column_name)){
                if(is_nullable.equalsIgnoreCase("NO")){
                    nullable = "data-validation='required'";
                    nullable2 = "*";
                }
                
                if(!maximum_length.equalsIgnoreCase("")){
                    min_max_length = "minlength='2' maxlength='"+maximum_length+"'";
                }
                
                if(!column_value.equalsIgnoreCase("")){
                    value = "value='"+column_value+"'";
                }
                    
                if (column_name.equalsIgnoreCase("id")){
                     form_rows+="<td><input type='hidden' id='"+column_name+"' value='"+column_value+"' name='"+column_name+"'></td>";
                }else{
                    form_rows+="<tr><td style='text-align: right; width: 50%'>"+nullable2+" "+column_name+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
                    String imputType = row.get("data_type");
                    form_rows+="<td><input "+readonly+" "+nullable+" "+min_max_length;
                    
                    if (imputType.equalsIgnoreCase("character varying")){
                        form_rows+=" type='text'";
                    }else if (imputType.equalsIgnoreCase("integer") || imputType.equalsIgnoreCase("bigint")){
                        form_rows+=" type='text' data-validation='number'";
                    }else if (imputType.equalsIgnoreCase("bit")){
                        form_rows+=" type='text'";
                    }else if (imputType.equalsIgnoreCase("timestamp without time zone")){
                        form_rows+=" type='text' class='date' data-validation='date'";
                    }
                    form_rows+="id='"+column_name+"' "+value+" name='"+column_name+"'></td>";
                }

            }
        }
        return form_rows;
    }
    
    public Boolean isSistemComumn(String column_name){
        //System.out.println(column_name);
        if (column_name.equalsIgnoreCase("created_by")){
            return true;
        } else
        if (column_name.equalsIgnoreCase("created_at")){
            return true;
        } else
        if (column_name.equalsIgnoreCase("updated_by")){
            return true;
        } else       
        if (column_name.equalsIgnoreCase("updated_at")){
            return true;
        } else      
        if (column_name.equalsIgnoreCase("deleted")){
            return true;
        } else{
           return false; 
        }
    }
        
        
    
}