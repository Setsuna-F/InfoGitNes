package fr.lirmm.servlets;

import static fr.lirmm.servlets.AffichageAPITweet.VUE;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Elsa Martel
 */
public class Download extends HttpServlet {

    public static final String VUE = "/WEB-INF/download.jsp";
    public static final int TAILLE_TAMPON = 10240; // 10ko
    public static final String CHAMP_FILE = "filename";
    public static final String CHAMP_FOLDER = "folder";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       String filename=null;
       String folder = null;

        try
        {
            filename = request.getParameter(CHAMP_FILE);      
            folder = request.getParameter(CHAMP_FOLDER);
            
            if(filename == null || filename.equals(""))
            {
                throw new ServletException("File Name can't be null or empty");
            }

            String filepath = "./"+ folder +"/"+filename;   //change your directory path

            File file = new File(filepath);
            if(!file.exists())
            {
                throw new ServletException("File doesn't exists on server.");
            }

            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 

            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath);

            int i; 
            while ((i=fileInputStream.read()) != -1) 
            {
                 response.getWriter().write(i); 
            } 
            fileInputStream.close();
        }
        catch(Exception e)
        {
            System.err.println("Error while downloading file["+filename+"]"+e);
        }
    }

}