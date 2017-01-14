/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import analysedesentiments.AnalyseDeSentiments;
import fr.lirmm.beans.Polarite;
import fr.lirmm.beans.Root;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.Part;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Elsa Martel
 */
public class AffichageAPITweet extends HttpServlet{
    
    public static final String CHAMP_TWEET = "tweetAAnalyser";
    public static final String CHAMP_FILE = "fileUpload";
    public static final String CHAMP_CHOIX = "choix";
    
    public static final String CHEMIN = "chemin";
    public static final int TAILLE_TAMPON = 10240;
    
    public static final String ATT_TWEET = "tweet";
    public static final String ATT_MESSAGE = "message";
    public static final String ATT_ERREUR = "erreur";
    public static final String ATT_ROOT = "root";
    public static final String ATT_POSITIVE = "positive";
    public static final String ATT_NEUTRE = "neutre";
    public static final String ATT_NEGATIVE = "negative";
    public static final String ATT_DESCRIPTION= "description";
    public static final String ATT_DOWNLOAD = "alternative";
   
    
    public static final String VUE = "/WEB-INF/affichageAPITweet.jsp";
    
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //variable pour le formulaire de saisie de tweet
        String tweet = request.getParameter(CHAMP_TWEET);
        String choix = request.getParameter(CHAMP_CHOIX);

        //Preparation des résultats
        Map<String, String> listeTweet = new HashMap<String, String>();
        String message = ""; 
        boolean erreur = true; 
        String resultat = "";
        
        //Initialiser les modèles
        String modele1 = "DEFT15T1STW.model";
        String modele2 = "DEFT15T1IG.model";
        String modele3 = "DEFT15T1SMO.model";
        
        //on utilise le formulaire de saisie de texte
        if(choix.equals("saisieTexte"))
        {
            System.out.println("tweet " + tweet);
            //il n'y a pas de tweet à traiter
            if(tweet == null || tweet.isEmpty() ){
                message = "No tweet";
                erreur = true;
            }
            //on a un tweet à traiter
            else if (tweet != null){
                message = "Analysis tweet";
                erreur = false;
                /*String delim = "\n";
                String[] tokens = tweet.split(delim);*/
                AnalyseDeSentiments a = new AnalyseDeSentiments();

                /*for(int i = 0; i < tokens.length; i++){*/
                    try {
                        resultat = a.start(tweet, modele1, modele2, modele3);
                        listeTweet.put(tweet, resultat);
                    } catch (Exception ex) {
                        Logger.getLogger(AffichageAPITweet.class.getName()).log(Level.SEVERE, null, ex);
                    }
               // }  
            }
        }
        //on utilise le formulaire d'upload de fichier
        else if(choix.equals("uploadFile")){
            Part p = request.getPart(CHAMP_FILE);
            InputStream is = request.getPart(p.getName()).getInputStream();
            int i = is.available();
            byte[] b = new byte[i];
            is.read(b);
            String fileName = getFileName(p);
            
            //On a pas mis de fichiers
            if(fileName.equals("")){
                message = "No tweet";
                erreur = true;
            }
            //on a uploadé un fichier
            else{
                message = "Analysis tweet";
                erreur = false;
                FileOutputStream os = new FileOutputStream("./fichiers/" + fileName);
                os.write(b); 
                
                //Lecture de fichier uploader
                String chaine = "";
                try{
                    InputStream ips = new FileInputStream("./fichiers/" + fileName); 
                    InputStreamReader ipsr = new InputStreamReader(ips);
                    BufferedReader br= new BufferedReader(ipsr);
                    String ligne;
                    while ((ligne=br.readLine())!=null){
                            System.out.println(ligne);
                            chaine+=ligne+"\n";
                    }
                    br.close(); 
                    ipsr.close();
                    
                    String delim = "\n\n";
                    String[] tokens = chaine.split(delim);
                    AnalyseDeSentiments a = new AnalyseDeSentiments();

                    for(int j = 0; j < tokens.length; j++){
                        try {
                            resultat = a.start(tokens[j], modele1, modele2, modele3);
                            listeTweet.put(tokens[j], resultat);
                        } catch (Exception ex) {
                            Logger.getLogger(AffichageAPITweet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }  
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
                os.close();
            }
            is.close();
            
            File f = new File("./fichiers/" + fileName);
            f.delete();
        }
        
        //Création du json
        
        
        //Valeur pour Root
        Root root = new Root();
        root.setMicrofmeasure(valeurXml("/tweet/root/microfmeasure"));
        root.setMacrofmeasure(valeurXml("/tweet/root/macrofmeasure"));
        root.setMicroprecision(valeurXml("/tweet/root/microprecision"));
        root.setMacroprecision(valeurXml("/tweet/root/macroprecision"));
        root.setMicrorecall(valeurXml("/tweet/root/microrecall"));
        root.setMacrorecall(valeurXml("/tweet/root/macrorecall"));
        
        //Valeur pour Positive
        Polarite positive = new Polarite();
        positive.setFmeasure(valeurXml("/tweet/positive/fmeasure"));
        positive.setPrecision(valeurXml("/tweet/positive/precision"));
        positive.setRecall(valeurXml("/tweet/positive/recall"));
        
        //Valeur pour Neutre
        Polarite neutre = new Polarite();
        neutre.setFmeasure(valeurXml("/tweet/neutre/fmeasure"));
        neutre.setPrecision(valeurXml("/tweet/neutre/precision"));
        neutre.setRecall(valeurXml("/tweet/neutre/recall"));
        
        //Valeur pour Negative
        Polarite negative = new Polarite();
        negative.setFmeasure(valeurXml("/tweet/negative/fmeasure"));
        negative.setPrecision(valeurXml("/tweet/negative/precision"));
        negative.setRecall(valeurXml("/tweet/negative/recall"));
        
        //Description 
        String description = valeurXml("/tweet/description");
        
        String breadcrumbs = "<li><a href=\"/index\">Home</a></li>";
        request.setAttribute( "title", "Tweet" );
        request.setAttribute( "topMenuName", "WorkFlow" );
        request.setAttribute( "breadcrumbs", breadcrumbs );
        request.setAttribute(ATT_TWEET, listeTweet);
        request.setAttribute(ATT_MESSAGE, message);
        request.setAttribute(ATT_ERREUR, erreur);
        request.setAttribute(ATT_ROOT, root);
        request.setAttribute(ATT_POSITIVE, positive);
        request.setAttribute(ATT_NEUTRE, neutre);
        request.setAttribute(ATT_NEGATIVE, negative);
        request.setAttribute(ATT_DESCRIPTION, description);
        request.setAttribute(ATT_DOWNLOAD, "alternative");

        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
    
    
    //Fonction qui permet d'extraire le nom du fichiers du post
    private String getFileName(Part part) { 
        String partHeader = part.getHeader("content-disposition"); 
        System.out.println("Part Header = " + partHeader); 
        for (String cd : part.getHeader("content-disposition").split(";")) { 
          if (cd.trim().startsWith("filename")) { 
            return cd.substring(cd.indexOf('=') + 1).trim() 
                .replace("\"", ""); 
          } 
        } 
        return null;
    }

    
    //Fonction qui va chercher les valeurs dans le fichier xml
    public String valeurXml(String expression){
        String valeur = "";
        try{
            // Permet de lire le fichier XML directement depuis le war (marche partout comme ça)
            String path = Thread.currentThread().getContextClassLoader().getResource("tweet.xml").getPath();
            File file = new File(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(file);
            Element root = xmlDocument.getDocumentElement();
            XPath xPath =  XPathFactory.newInstance().newXPath();
            valeur = xPath.evaluate(expression,root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }catch (XPathExpressionException e) {
            e.printStackTrace();
        } 
        return valeur;

    }

} 

//<%@page import="analysedesentiments.AnalyseDeSentiments"%>
