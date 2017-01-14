/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import fr.lirmm.beans.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.lirmm.db.BaseDeDonnee;
import java.sql.SQLException;

/**
 *
 * @author Niels
 */
@WebServlet(name = "LogOn", urlPatterns = {"/logOn"})
public class LogOn extends HttpServlet {
    public String MAIL = "mail";
    public String PASSWORD = "pass";
    public String CONFIRMPW = "rePass";
    public String NOM = "Lname";
    public String PRENOM = "Fname";
    
    //Messages de sortie pour le callout
    public String EMPTY = "";
    public String SUCCESS = "You can now log.";
    public String EMAIL_EXIST = "Email already used.";
    public String SAME_PW = "Passwords must be the same.";
    public String LENGTH_PW = "Password not enough long, please use at least 5 characters.";

   public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String breadcrumbs = "<li><a href=\"/logon\">Log on</a></li>";
        request.setAttribute( "title", "Index" );
        request.setAttribute( "topMenuName", "WorkFlow" );
        request.setAttribute( "breadcrumbs", breadcrumbs );
        this.getServletContext().getRequestDispatcher( "/WEB-INF/logOn.jsp" ).forward( request, response );
   }

   public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       String mail = request.getParameter(MAIL);
       String pass = request.getParameter(PASSWORD);
       String rePass = request.getParameter(CONFIRMPW);
       String firstName = request.getParameter(PRENOM);
       String lastName = request.getParameter(NOM);
       String additionalInformation = EMPTY;
       int polarity = 0;
       BaseDeDonnee test = new BaseDeDonnee();  
     
       
       if (pass.equals(rePass)) {
            if(pass.length() >= 5){
                try {
                    if(!test.isInDataBase(mail)) // si le mail n'est pas déjà utilisé
                    {
                        User utilisateur = new User(firstName,lastName,mail,pass,false);
                        BaseDeDonnee ajout = new BaseDeDonnee();
                        ajout.ajouterUtilisateur(utilisateur);

                        polarity = 1;
                        additionalInformation = SUCCESS;
                    }
                    else{ // existe déjà
                        additionalInformation = EMAIL_EXIST;
                    }
                } catch (SQLException ex) {
                    //
                }
            }
            else {
                additionalInformation =LENGTH_PW;
            }
       }
       else { //erreur dans les mots de passe
           additionalInformation = SAME_PW;
       }
       
       request.setAttribute( "topMenuName", "WorkFlow" );       
       request.setAttribute("info", additionalInformation);
       request.setAttribute("polarity", polarity);
       
       //redirection vers la vue 
       if (polarity == 1) {
            String breadcrumbs = "<li><a href=\"/logon\">Log on</a></li>";
            request.setAttribute( "title", "Log on" );
            request.setAttribute( "breadcrumbs", breadcrumbs );
          this.getServletContext().getRequestDispatcher( "/WEB-INF/logIn.jsp" ).forward( request, response ); 
       } else{
            String breadcrumbs = "<li><a href=\"/logon\">Log in</a></li>";
            request.setAttribute( "title", "Log in" );
            request.setAttribute( "breadcrumbs", breadcrumbs );
           this.getServletContext().getRequestDispatcher( "/WEB-INF/logOn.jsp" ).forward( request, response );
       }    
   } 
}
