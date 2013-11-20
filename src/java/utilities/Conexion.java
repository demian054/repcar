/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
    
    
    public Conexion() throws SQLException, ClassNotFoundException {
       
        url = "jdbc:postgresql://localhost:5432/repcar";
        driver = "org.postgresql.Driver";
        user = "postgres";
        pass = "abc123";
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
    
    
    
}
