/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.lirmm.db.BaseDeDonnee;
/**
 *
 * @author Niels
 */
@WebServlet(name = "profile", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {
    //classe pour le callout
    public String SUCCESS = "success";
    public String ALERT = "alert";
    //chine d'information du callout
    public String SETTINGS_CHANGED = "Settings has been changed.";
    public String SETTINGS_EMAIL_CHANGED = "Settings has been changed, please use the new email for the next connection.";
    public String EMAIL_EXIST = "This eMail already exist.";
    public String PW_CHANGED = "Password has been changed, please use it for the next connection. ";
    public String PW_LENGTH = "Password not enough long, please use at least 5 characters.";
    public String PW_SAME = "Passwords must be the same.";
    public String PW_EMPTY = "Passwords can't be empty.";
    
    public String info = ""; //chaîne d'information pour le callout
    public String classe = "";//classe du callout 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String edit = request.getParameter("edit");
        if (edit.equals("settings")) {
            request.setAttribute("edit",1); //permet de dire à la vue que l'on veux changer des parametres.
        }
        else if (edit.equals("password")) {
            request.setAttribute("edit",2); //permet de dire à la vue que l'on veux changer des parametres.
        }
        //ici on peut attraper le valeur cancel mais cela ne sert à rien car les lignes
        //suivante font ce qu'il faut par defaut 
        String breadcrumbs = "<li><a href=\"/logIn\">Profile</a></li>";
        request.setAttribute( "title", "Profile" );
        request.setAttribute( "topMenuName", "WorkFlow" );
        request.setAttribute( "breadcrumbs", breadcrumbs );
    
        this.getServletContext().getRequestDispatcher( "/WEB-INF/profile.jsp" ).forward( request, response );
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String lMail = (String) session.getAttribute("mail");        
        String change = request.getParameter("change");
     
        if (change.equals("settings")) { 
        //ici on va changer le nom le prenom et le mail, 
        //on utilise lMail qui est l'ancien mail
        //et nMail qui est le nouveau mail
        // on ne s'occupe pas de savoir ques qui a changé 
            String nMail = request.getParameter("Mail");
            String Fname = request.getParameter("Fname");
            String Lname = request.getParameter("Lname");
            BaseDeDonnee name = new BaseDeDonnee();
            
            try{
                if(nMail.equals(lMail)) // on ne veux pas changer le mail
                {
                    name.alterUtilisateur(lMail, nMail, Lname, Fname);
                    session.setAttribute("nom", Lname);
                    session.setAttribute("prenom", Fname);
                    session.setAttribute("mail", nMail);
                    info = SETTINGS_CHANGED ;
                    classe = SUCCESS;
                }
                else{ //on veux changer le mail, dans ce cas il faut tester si il n'existe pas déjà
                    if (!name.isInDataBase(nMail)) {
                        name.alterUtilisateur(lMail, nMail, Lname, Fname);
                        session.setAttribute("nom", Lname);
                        session.setAttribute("prenom", Fname);
                        session.setAttribute("mail", nMail);
                        info = SETTINGS_EMAIL_CHANGED;
                        classe = SUCCESS;
                    }else{
                        info = EMAIL_EXIST;
                        classe  = ALERT;
                    }
                }
                
                
            }catch(Exception e){}
        }
        else if (change.equals("password")) {
            String pass = request.getParameter("pass");
            String rePass = request.getParameter("rePass");
     
            if (pass.equals(rePass) && !pass.equals("")) {
                if(pass.length() >= 5){
                    BaseDeDonnee name = new BaseDeDonnee();
                    try{
                        name.alterPassword(lMail, pass);
                    }catch(Exception e){}
                    info  = PW_CHANGED;
                    classe = SUCCESS;
                }
                else {
                    info = PW_LENGTH;
                    classe = ALERT;
                    request.setAttribute("edit",2); //on reste sur la vue pour changer de mot de passe
                } 
            }
            else{
                if(pass.equals("") || rePass.equals("")){
                    info = PW_EMPTY;
                    classe = ALERT;
                    request.setAttribute("edit",2);
                }
                else{
                    info = PW_SAME;
                    classe = ALERT;
                    request.setAttribute("edit",2);
                }
            }
        }
        
        String breadcrumbs = "<li><a href=\"/logIn\">Profile</a></li>";
        request.setAttribute( "title", "Profile" );
        request.setAttribute( "topMenuName", "WorkFlow" );
        request.setAttribute( "breadcrumbs", breadcrumbs );
        request.setAttribute( "info", info );
        request.setAttribute( "classe", classe );
        this.getServletContext().getRequestDispatcher( "/WEB-INF/profile.jsp" ).forward( request, response );  
    }           
}
