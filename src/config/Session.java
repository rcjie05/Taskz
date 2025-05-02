/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author PC15
 */
public class Session {
    
    private static Session instance;
    private int t_id;
    private int p_id;
    private int u_id;
    private String u_fname;
    private String u_lname;
    private String u_email;
    private String u_username;
    private String u_contact;
    private String u_gender;
    private String u_type;
    private String u_status;
    
    private Session(){
        //private cons. prevents intance
    }

    public static synchronized Session getInstance() {
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public static boolean isInstanceEmpty() {
        return instance == null;
    }
    public int getT_id() {
        return t_id;
    }
    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
     public int getP_id() {
        return p_id;
    }
    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
        public int getU_id() {
        return u_id;
    }

    public static void setInstance(Session instance) {
        Session.instance = instance;
    }
    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_fname() {
        return u_fname;
    }
    public void setU_fname(String u_fname) {
        this.u_fname = u_fname;
    }
    public String getU_lname() {
        return u_lname;
    }
    public void setU_lname(String u_lname) {
        this.u_lname = u_lname;
    }
    public String getU_email() {
        return u_email;
    }
    public void setU_email(String u_email) {
        this.u_email = u_email;
    }
    public String getU_username() {
        return u_username;
    }
    public void setU_username(String u_username) {
        this.u_username = u_username;
    }
    public String getU_contact() {
        return u_contact;
    }
    public void setU_contact(String u_contact) {
        this.u_contact = u_contact;
    }
    public String getU_gender() {
        return u_gender;
    }
    public void setU_gender(String u_gender) {
        this.u_gender = u_gender;
    }
    public String getU_type() {
        return u_type;
    }
    public void setU_type(String u_type) {
        this.u_type = u_type;
    }
    public String getU_status() {
        return u_status;
    }
    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

}

