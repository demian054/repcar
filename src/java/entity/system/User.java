/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity.system;

/**
 *
 * @author demian
 */
public class User {
    private Integer id;
    private String _name ;
    private String last_name;
    private String address;
    private String telef_1;
    private String telef_2;
    private String pass;
    private String user_name;

    public User() {
        id = 0;
        _name = "";
        last_name= "";
        address= "";
        telef_1= "";
        telef_2= "";
        pass= "";
        user_name= "";
    }
    
    public User(Integer id, String _name, String last_name, String address, String telef_1, String telef_2, String pass, String user_name) {
        this.id = id;
        this._name = _name;
        this.last_name = last_name;
        this.address = address;
        this.telef_1 = telef_1;
        this.telef_2 = telef_2;
        this.pass = pass;
        this.user_name = user_name;
    }

    
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelef_1() {
        return telef_1;
    }

    public void setTelef_1(String telef_1) {
        this.telef_1 = telef_1;
    }

    public String getTelef_2() {
        return telef_2;
    }

    public void setTelef_2(String telef_2) {
        this.telef_2 = telef_2;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    
    
    
  
  
    
}
