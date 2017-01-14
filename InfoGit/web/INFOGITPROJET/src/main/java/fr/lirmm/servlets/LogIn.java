/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;
import fr.lirmm.beans.User;
import fr.lirmm.db.BaseDeDonnee;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author niels
 */
@WebServlet(name = "LogIn", urlPatterns = {"/LogIn"})
public class LogIn extends HttpServlet {
    public String BADREQUEST = "The email and/or password entered do not allow you to connect.";
    public String MAIL = "mail";
    public String PASSWORD = "pass";
    public String NOM = "nom";
    public String PRENOM = "prenom";
    public String BOOLEANLOG = "isLog";
    public String UPLOAD = "isUpload";
    public String FIC_INFO = "ficInfo";
    public String FIC_NOM = "ficNom";

    
    

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //deconnexion
        HttpSession session = request.getSession();
        session.invalidate();
        
        String breadcrumbs = "<li><a href=\"/logIn\">Log in</a></li>";
        request.setAttribute( "title", "Log in" );
        request.setAttribute( "topMenuName", "WorkFlow" );
        request.setAttribute( "breadcrumbs", breadcrumbs );
    
        this.getServletContext().getRequestDispatcher( "/WEB-INF/logIn.jsp" ).forward( request, response );
       
    }
public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
     
        BaseDeDonnee utilisateur = new BaseDeDonnee();
        String mail = request.getParameter(MAIL);
        String pass = request.getParameter(PASSWORD);
        User connecter = utilisateur.recupererUtilisateurs(mail,pass);
        if(connecter.Fname != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute(NOM, connecter.Lname);
            session.setAttribute(PRENOM, connecter.Fname);
            session.setAttribute(MAIL, connecter.Mail);
            session.setAttribute(FIC_INFO, "");
            session.setAttribute(FIC_NOM, "");
            session.setAttribute(BOOLEANLOG, "1");
            //session.setAttribute(UPLOAD, "0");
            
            //Colonne IsUpload remis à false quand connexion
            BaseDeDonnee bd = new BaseDeDonnee();
            String[] res = new String[2];
            try {
                res = bd.getUserIsUpload(connecter.Mail + "");
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
             //Recupere les valeurs que l'on a besoin 
            String id = res[0];
            //String idUser = id;
            String isUpload = res[1];
            System.out.println("id LogIn " + id );
            System.out.println(isUpload);
            bd.setIsUpload(id, "false");
            
            request.setAttribute("utilisateur", utilisateur.recupererUtilisateurs(mail,pass));
            String breadcrumbs = "<li><a href=\"/index\">Index</a></li>";
            request.setAttribute( "title", "Index" );
            request.setAttribute( "topMenuName", "WorkFlow" );
            request.setAttribute( "breadcrumbs", breadcrumbs );
            
            this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
        }
        else{
            //session
            HttpSession session = request.getSession();
            session.invalidate();
            //preparation des chaînes
            String additionalInformation = BADREQUEST;
            String breadcrumbs = "<li><a href=\"/logIn\">Log in</a></li>";
            //polatité de l'information 0 = alert, 1 = neutre ou positive
            int polarity = 0; 
            //envoie des attribut dans la requête
            request.setAttribute( "title", "Log in" );
            request.setAttribute( "topMenuName", "WorkFlow" );
            request.setAttribute( "breadcrumbs", breadcrumbs );
            request.setAttribute("info", additionalInformation);
            request.setAttribute("polarity", polarity);
            //redirection vers la jsp
            this.getServletContext().getRequestDispatcher( "/WEB-INF/logIn.jsp" ).forward( request, response );
        }
    }
}    
