/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

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
                        + "<ul><li><a href='#tabs-1'>Consultar Registro de "+table_name+"</a></li></ul>"
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

        String form_rows = "";
        for(HashMap<String,String> columnDescriptor: informationTable){

            String column_name = (String) functions.isNullOrEmpty(columnDescriptor.get("column_name"), "") ;
            String column_value =  (String) functions.isNullOrEmpty(record.get(columnDescriptor.get("column_name")), "");
            //String value = "";     
            if (!isSistemComumn(column_name)){
                if (column_name.equalsIgnoreCase("id")){
                    form_rows+= createInputRow("hidden", column_value, isReadonly, columnDescriptor);
                }else{
                    if (column_name.contains("_id")){

                        form_rows+= createSingleSelectRow(column_value, isReadonly, columnDescriptor);
                    }else{
                        form_rows+= createInputRow("text", column_value, isReadonly, columnDescriptor);
                    } 
                }
            }
        }
        return form_rows;
    }
    
    private String createSingleSelectRow(String value, boolean isReadonly, HashMap<String,String> columnDescriptor){
        
        String select = "";
        
        String column_name = (String) functions.isNullOrEmpty(columnDescriptor.get("column_name"), "") ;        

        String validate = "";
        if (isReadonly){
            validate = "disabled='true'";
        }else{
            validate = createValidate(columnDescriptor);
        }
        ArrayList<String> values = new ArrayList<String>();
        values.add(value);
        String is_nullable = "";
        if (functions.isNullOrEmpty(columnDescriptor.get("is_nullable"))){
            is_nullable = "* ";
        };
        select+="<tr><td style='text-align: right; width: 50%'> "+is_nullable+column_name+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
        select+="<td><select class='select' "+validate+" id='"+column_name+"' name='"+column_name+"'>"
                + createOptionsSelect(column_name, values)
                + "</select></td>";
        return select;
    }
    
    private String createOptionsSelect(String column_name, ArrayList<String> values){
            
            String current_table_name = column_name.split("_id")[0];
            String sql=
                "select id, _name from "+schema_name+"."+current_table_name
                + " where"
                + " deleted = '0'";
             String optionsList = "<option value ='' >Seleccione...</option>";
            try {

                Conexion con = new Conexion();
                List<LinkedHashMap<String,String>> optionsData = con.select(sql);
                for(HashMap<String,String> optionData: optionsData){
                    String id = optionData.get("id");
                    String name = optionData.get("_name");
                    String selected = "";
                    if (values.contains(id)){
                        selected = "selected = 'selected' ";
                    }
                    optionsList += "<option value ='"+id+"' "+selected+">"+name+"</option>";
                }
                return optionsList;    
            } catch (Exception e) {
                e.printStackTrace();
            }
            return optionsList;
            
    }
    
    private String createInputRow(String type, String value, boolean isReadonly, HashMap<String,String> columnDescriptor){

        String imput = "";
        String column_name = (String) functions.isNullOrEmpty(columnDescriptor.get("column_name"), "") ;

        if(!value.equalsIgnoreCase("")){
            value = "value='"+value+"'";
        }
        if(type.equalsIgnoreCase("hidden")){
            imput+="<td colspan='2' ><input type='"+type+"'";
            imput+=" id='"+column_name+"' "+value+" name='"+column_name+"'></td>";
        }else{
            String validate = "";
            if (isReadonly){
                validate = "readonly='true'";
            }else{
                validate = createValidate(columnDescriptor);
            }
            String is_nullable = "";
            if (functions.isNullOrEmpty(columnDescriptor.get("is_nullable"))){
                is_nullable = "* ";
            };
            
            imput+="<tr><td style='text-align: right; width: 50%'> "+is_nullable+column_name+" :</td>";// <td>*"+row.get("data_type")+"*</td>";
            imput+="<td><input data-validation='required' class='input' type='"+type+"' "+validate;
            imput+=" id='"+column_name+"' "+value+" name='"+column_name+"'></td>";
        }
        return imput;
    }
    
    
    
    private String createValidate(HashMap<String,String> columnDescriptor){
       
        String dataValidation = "";
        String dataValidationLength = "";
        String dataValidationAllowing = "";
        String dataValidationOptional = "";
        String dataValidationFormat = "";
        String dataValidationExtra = "";
        
        String is_nullable = (String) functions.isNullOrEmpty(columnDescriptor.get("is_nullable"), "") ;
        if(is_nullable.equalsIgnoreCase("NO")){
            dataValidation = functions.addWithComma(dataValidation, "required") ;
        }else{
            dataValidationOptional = "data-validation-optional='true' ";
        }

        String maximum_length = (String) functions.isNullOrEmpty(columnDescriptor.get("character_maximum_length"), "");
        String minimum_length = (String) functions.isNullOrEmpty(columnDescriptor.get("character_minimum_length"), "");
        
        if (!maximum_length.equalsIgnoreCase("") && !maximum_length.equalsIgnoreCase("")){
            dataValidation = functions.addWithComma(dataValidation, "length") ;
            dataValidationLength = functions.addWithComma(dataValidationLength, minimum_length+"-"+maximum_length) ;
        }else{
            if(!maximum_length.equalsIgnoreCase("")){
                dataValidation = functions.addWithComma(dataValidation, "length");
                dataValidationLength = functions.addWithComma(dataValidationLength, "max"+maximum_length) ;
            }
            if(!maximum_length.equalsIgnoreCase("")){
                dataValidation = functions.addWithComma(dataValidation, "length") ;
                dataValidationLength = functions.addWithComma(dataValidationLength, "min"+minimum_length) ;
            }
        }
        //System.out.println("columnDescriptor.get(\"numeric_precision\") = "+columnDescriptor.get("numeric_precision"));
        String numeric_precision = (String) functions.isNullOrEmpty(columnDescriptor.get("numeric_precision"), "");
         //System.out.println("numeric_precision = "+numeric_precision);
        if (!numeric_precision.equalsIgnoreCase("")){
             //System.out.println("number");
             dataValidation =  "number";
             //Math.pow(256,(Integer.valueOf(numeric_precision)/8));
             //dataValidationLength = functions.addWithComma(dataValidationLength, "max"+numeric_precision);
        }
        
        String datetime_precision = (String) functions.isNullOrEmpty(columnDescriptor.get("datetime_precision"), "");
         //System.out.println("numeric_precision = "+numeric_precision);
        if (!datetime_precision.equalsIgnoreCase("")){
             //System.out.println("number");
            
            
            dataValidation =  "date";
            dataValidationFormat = "dd/mm/yyyy";
            dataValidationExtra+= "class='date'";
             //Math.pow(256,(Integer.valueOf(numeric_precision)/8));
             //dataValidationLength = functions.addWithComma(dataValidationLength, "max"+numeric_precision);
        }
        
        
        dataValidation =  "data-validation='"+dataValidation+"' ";
        dataValidationLength =  "data-validation-length='"+dataValidationLength+"' ";
        dataValidationAllowing =  "data-validation-allowing='"+dataValidationAllowing+"' ";
        dataValidationFormat = "data-validation-format='"+dataValidationFormat+"' ";

        return dataValidation+
                dataValidationLength+
                dataValidationAllowing+
                dataValidationOptional+
                dataValidationFormat+
                dataValidationExtra;
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