/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @author azaz1
 */
public class ChoixSentimentAnalysis extends HttpServlet {
    
    public static final String ATT_TweetPolarity = "tweetPolarity"; 
    public static final String ATT_TweetSubjectivity = "tweetSubjectivity"; 
    public static final String ATT_TweetEmotion = "tweetEmotion"; 
    public static final String ATT_ProductReviews = "productReviews"; 
    public static final String ATT_VideosGames= "videosGames"; 
    public static final String ATT_ParlementaryDebates = "parlementaryDebates"; 

    
    
    public static final String VUE = "/WEB-INF/choixSentimentAnalysis.jsp";
     
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        String expression = "/modele/description";
        
        //TweetPolarity
        String descriptionTweetPolarity = valeurXml("tweetPolarity.xml", expression);
        
        //TweetSubjectivity
        String descriptionTweetSubjectivity = valeurXml("tweetSubjectivity.xml", expression);

        //TweetEmotion
        String descriptionTweetEmotion = valeurXml("tweetEmotion.xml", expression);

        //ProductReviews
        String descriptionProductReviews = valeurXml("productReview.xml", expression);
 
        //VideosGames
        String descriptionVideosGames = valeurXml("videoGames.xml", expression);

        //ParlementaryDebates
        String descriptionParlementaryDebates = valeurXml("parlementaryDebates.xml", expression);

        request.setAttribute(ATT_TweetPolarity, descriptionTweetPolarity);
        request.setAttribute(ATT_TweetSubjectivity, descriptionTweetSubjectivity);
        request.setAttribute(ATT_TweetEmotion, descriptionTweetEmotion);
        request.setAttribute(ATT_ProductReviews, descriptionProductReviews);
        request.setAttribute(ATT_VideosGames, descriptionVideosGames);
        request.setAttribute(ATT_ParlementaryDebates, descriptionParlementaryDebates);

        request.setAttribute( "title", "Tweet" );
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
    
    
        //Fonction qui va chercher les valeurs dans le fichier xml
    public String valeurXml(String f, String expression){
        String valeur = "";
        try{
            // Permet de lire le fichier XML directement depuis le war (marche partout comme ça)
            String path = Thread.currentThread().getContextClassLoader().getResource(f).getPath();
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
