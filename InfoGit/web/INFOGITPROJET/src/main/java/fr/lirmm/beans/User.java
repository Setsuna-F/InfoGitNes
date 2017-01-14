/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.beans;

/**
 *
 * @author niels
 */

public class User implements java.io.Serializable{
    public int Id;
    public String Fname;
    public String Lname;
    public String Mail;
    public String Password;
    public boolean Mod;
    
    public User( String Fname, String Lname, String Mail, String Password, boolean Mod) {
        this.Fname = Fname;
        this.Lname = Lname;
        this.Mail = Mail;
        this.Password = Password;
        this.Mod = Mod;
    }
    public User(){
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

    public void setLname(String Lname) {
        this.Lname = Lname;
    }

    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setMod(boolean Mod) {
        this.Mod = Mod;
    }

    public int getId() {
        return Id;
    }
 
    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public String getMail() {
        return Mail;
    }

    public String getPassword() {
        return Password;
    }

    public boolean isMod() {
        return Mod;
    }

    
    
}
