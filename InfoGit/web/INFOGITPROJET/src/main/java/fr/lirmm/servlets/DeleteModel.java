/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import fr.lirmm.db.BaseDeDonnee;
import fr.lirmm.utilities.Utilities;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Niels
 */
public class DeleteModel extends HttpServlet {

    public static final String ID = "fileId";
    public static final String NOM = "fileName";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String id_file = request.getParameter(ID);
        String nom_file = request.getParameter(NOM);
         
        HttpSession session = request.getSession();
        String user_mail = session.getAttribute("mail").toString();
        BaseDeDonnee bd = new BaseDeDonnee();
        try {
            bd.deleteFileUser(user_mail, id_file);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path = Utilities.generatePath(user_mail, nom_file);
        File aSupprimer = new File(path);
        System.out.println(path);
        Utilities.deleteAll(aSupprimer);
        //request.setAttribute("fileName", fileName);
        request.setAttribute( "title", "Result" );
        request.setAttribute( "topMenuName", "WorkFlow" );

        response.sendRedirect("manage");
    }
    
    
}
