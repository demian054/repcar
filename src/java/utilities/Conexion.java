/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import entity.system.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.postgresql.util.PSQLException;

/**
 *
 * @author demian
 */


public class Conexion {

    private Connection con;
    private String url;
    private String driver;
    private String user;
    private String pass;
    
    
    public Conexion() throws PSQLException, SQLException, ClassNotFoundException {
       
        url = "jdbc:postgresql://localhost:5432/repcar";
        driver = "org.postgresql.Driver";
        user = "postgres";
        pass = "cocorote";
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, pass);
        
    }
    

    public Conexion(Connection con, String url, String driver, String user, String pass) {
        this.con = con;
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.pass = pass;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public boolean execute(String query){
        try {
            Statement stmt = con.createStatement();
            System.out.println("Ejecutando query "+query);
            stmt.execute(query);
            System.out.println("query ejecutado "+true);
            return true;
            //System.out.println(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public List<LinkedHashMap<String, String>> select(String query) throws SQLException{
        List<LinkedHashMap<String, String>> resultado = new ArrayList<LinkedHashMap<String, String>>();
        Statement stmt = con.createStatement();
        ResultSet rs;
        System.out.println("Ejecutando query "+query);
        rs = stmt.executeQuery(query);
        ResultSetMetaData rmtd = rs.getMetaData();
        int columnCount = rmtd.getColumnCount();
        //System.out.println(rmtd);
        boolean result = false;
        while ( rs.next()){
            result = true;
            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            for (int i = 0; i < columnCount; i++) {
                //System.out.println("columna: "+rmtd.getColumnName(i+1)+" valor: "+functions.isNullOrEmpty(rs.getString(i+1),""));
                row.put(rmtd.getColumnName(i+1), (String) functions.isNullOrEmpty(rs.getString(i+1),""));
            }
            resultado.add(row);
        }
        System.out.println("query ejecutado "+result);
        return resultado;
    }
    
    public List<LinkedHashMap<String,String>> getInformationTable(String table_name) throws SQLException{
        String sql=
              "select "
            + "column_name, "
            + "is_nullable, "
            + "data_type, "
            + "character_maximum_length, "
            + "numeric_precision, "
            + "numeric_scale, "
            + "datetime_precision "
            + "from INFORMATION_SCHEMA.COLUMNS where table_name = '"+table_name+"'"
            + "order by ordinal_position ";
        return this.select(sql);    
    }
    
    public boolean insert(String table_name, String schema_name, HttpServletRequest request, HttpSession session) throws SQLException{
        String insert_head = "";
        String insert_row = "";
        List<LinkedHashMap<String,String>> informationTable = this.getInformationTable(table_name);
        for(HashMap<String,String> insert_heads: informationTable){
           String column_name = insert_heads.get("column_name");
           String column_value = request.getParameter(column_name);
           if (functions.isNullOrEmpty(column_value)){
               //TODO: validaciones de los datos
               insert_head = functions.addWithComma(insert_head, column_name);
               insert_row = functions.addWithComma(insert_row, "'"+column_value+"'");
           }                 
        }
        
        User current_user = (User) functions.isNullOrEmpty(session.getAttribute("current_user"), new User());
        insert_head+=", created_by, created_at";
        insert_row+= ", '"+current_user.getId()+"', now()";

        String insert_sql = "INSERT INTO "+schema_name+"."+table_name+" ("+insert_head+") VALUES ("+insert_row+")";
        return this.execute(insert_sql);
    }
    
     public boolean update(String table_name, String schema_name, HttpServletRequest request) throws SQLException{
        List<LinkedHashMap<String,String>> informationTable = this.getInformationTable(table_name);
       String upate_row = "";
        String where_sql = "";
        for(HashMap<String,String> insert_heads: informationTable){
           String column_name = insert_heads.get("column_name");
           String column_value = request.getParameter(column_name);
           if (functions.isNullOrEmpty(column_value)){
               //TODO: validaciones de los datos
               if (column_name.equalsIgnoreCase("id")){
                    where_sql += " WHERE id = '"+column_value+"' ";
               }else{
                   upate_row = functions.addWithComma(upate_row, column_name+" = '"+column_value+"' ");
               }
           }                 
        }
        String upate_sql = "UPDATE "+schema_name+"."+table_name+" SET "+upate_row+where_sql;
        return this.execute(upate_sql);
    }
     
    public boolean delete(String table_name, String schema_name, String id) throws SQLException{
        String upate_sql = "UPDATE "+schema_name+"."+table_name+" SET deleted = '1' "
                + "where id = '"+id+"'";
        return this.execute(upate_sql);
    } 
     
    
    
    
}
