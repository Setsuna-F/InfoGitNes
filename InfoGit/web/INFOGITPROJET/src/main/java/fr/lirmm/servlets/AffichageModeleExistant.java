/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.servlets;

import analysedesentiments.AnalyseDeSentiments;
import analysedesentiments.CalculAttributs;
import fr.lirmm.beans.Polarite;
import fr.lirmm.beans.Root;
import fr.lirmm.db.BaseDeDonnee;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import weka.classifiers.Classifier;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;



/**
 *
 * @author Elsa Martel
 */
@WebServlet(name = "AffichageModeleExistant", urlPatterns = {"/affichageModeleExistant"})
public class AffichageModeleExistant extends HttpServlet {
    
    public final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static final String CHAMP_TWEET = "tweetAAnalyser";
    public static final String CHAMP_FILE = "fileUpload";
    public static final String CHAMP_CHOIX = "choix";
    public static final String CHAMP_TYPE_ANALYSIS = "typeAnalysis";
    
    public static final String CHEMIN = "chemin";
    public static final int TAILLE_TAMPON = 10240;
    
    public static final String ATT_TWEET = "tweet";
    public static final String ATT_MESSAGE = "message";
    public static final String ATT_ERREUR = "erreur";
    public static final String ATT_ROOT = "root";
    public static final String ATT_CLASSE= "classe";
    public static final String ATT_DESCRIPTION= "description";
    public static final String ATT_TYPE_ANALYSIS = "typeAnalysis";
    public static final String ATT_TITRE = "titre";
    public static final String ATT_FILE_XML = "fichierXML";
    public static final String ATT_FILE_JSON = "fichierJSON";
    public static final String ATT_INFO = "information";
    
    public String UPLOAD = "isUpload";
    
    public static final String VUE = "/WEB-INF/affichageModeleExistant.jsp";
     
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        //variable pour le formulaire de saisie de tweet
        String tweet = request.getParameter(CHAMP_TWEET);
        String choix = request.getParameter(CHAMP_CHOIX);
        //System.out.println("choix "+ choix);
        
        //Au debut choix est null donc il faut l'initialiser
        if(choix == null){
            choix = "";
        }
        
        //Nom du fichier JSON
        String nomFichierJSON = ""; 
        
        //variable pour le choix du type d'analyse
        String typeAnalysis = request.getParameter(CHAMP_TYPE_ANALYSIS);
        //System.out.println("type Analyse " + typeAnalysis);
        
        //3 Modeles
        String modele1 = ""; 
        String modele2 = ""; 
        String modele3 = "";
        
        String idUser = "";
        
        //Variable de titre
        String titre = ""; 
        
        //Nom du fichier XML 
        String fxml = ""; 
        
        String information = "";
        
        //Chargement des variables, modèles suivant le type d'analyse
        if(typeAnalysis.equals("FrenchTweetsPolarity")){
            //Initialiser les modèles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Tweets Polarity";

            //Fichier XML 
            fxml = "tweetPolarity.xml";
        }
        else if(typeAnalysis.equals("FrenchTweetsSubjectivity"))
        {            
            //Initialiser les modèles A changer quand on aura les modeles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Tweets Subjectivity";

            //Fichier XML 
            fxml = "tweetSubjectivity.xml";
        }
        else if(typeAnalysis.equals("FrenchTweetsEmotions"))
        {            
            //Initialiser les modèles A changer quand on aura les modeles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Tweets Emotions";

            //Fichier XML 
            fxml = "tweetEmotion.xml";
        }
        else if(typeAnalysis.equals("FrenchProductReviews"))
        {            
            //Initialiser les modèles A changer quand on aura les modeles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Product Reviews";

            //Fichier XML 
            fxml = "productReview.xml";
        }
        else if(typeAnalysis.equals("FrenchVideosGames"))
        {            
            //Initialiser les modèles A changer quand on aura les modeles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Videos Games";

            //Fichier XML 
            fxml = "videoGames.xml";
        }
        else if(typeAnalysis.equals("FrenchParlementaryDebates"))
        {            
            //Initialiser les modèles A changer quand on aura les modeles
            modele1 = "DEFT15T1STW.model";
            modele2 = "DEFT15T1IG.model";
            modele3 = "DEFT15T1SMO.model";

            //Initialiser le titre de la page 
            titre = "French Parlementary Debates";

            //Fichier XML 
            fxml = "parlementaryDebates.xml";
        }
        
        
        //Preparation des résultats
        //Map<String, String> listeTweet = new HashMap<String, String>();
        String resultatSaisie[] = new String[2];  
        String message = ""; 
        //boolean erreur = true; 
        int erreur = 0; 
        String resultat = "";
        
        
        //on utilise le formulaire de saisie de texte
        if(choix.equals("saisieTexte"))
        {
            System.out.println("tweet " + tweet);
            //il n'y a pas de tweet à traiter
            if(tweet == null || tweet.isEmpty() ){
                message = "No tweet";
                //erreur = true;
                erreur = 0;
            }
            //on a un tweet à traiter
            else if (tweet != null){
                message = "Analysis tweet";
                //erreur = false;
                erreur = 1;
                
                AnalyseDeSentiments a = new AnalyseDeSentiments();

                try {
                    resultat = a.start(tweet, modele1, modele2, modele3);
                    resultatSaisie[0] = tweet;
                    resultatSaisie[1] = resultat; 
                    //listeTweet.put(tweet, resultat);
                } catch (Exception ex) {
                    Logger.getLogger(AffichageAPITweet.class.getName()).log(Level.SEVERE, null, ex);
                }
 
            }
        }
        //on utilise le formulaire d'upload de fichier
        else if(choix.equals("uploadFile")){
            
            //Recuperer le prenom et nom de l'utilisateur 
            HttpSession session = request.getSession();
            Object prenom = session.getAttribute("prenom");
            Object nom = session.getAttribute("nom");
            Object email = session.getAttribute("mail");

            Part p = request.getPart(CHAMP_FILE);
            InputStream is = request.getPart(p.getName()).getInputStream();
            int i = is.available();
            byte[] b = new byte[i];
            System.out.println("byte " + b.length);
            //A limiter la taille  
            //A faire
            is.read(b);


            String fileName = getFileName(p);

            //On a pas mis de fichiers
            if(fileName.equals("")){
                message = "No tweet";
                //erreur = true;
                erreur = 0;
            }
            //on a uploadé un fichier
            else{
                
                //recupération de l'id et du isUpload
                BaseDeDonnee bd = new BaseDeDonnee();
                String[] res = new String[2];
                try {
                    res = bd.getUserIsUpload(email + "");
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
                
                //Recupere les valeurs que l'on a besoin 
                String id = res[0];
                idUser = id;
                String isUpload = res[1];
                
                if(isUpload.equals("f")){
                    //Nom du fichier texte
                    String nomFichier = id + "" + prenom + "" + nom + ".txt"; 
                    nomFichierJSON = id + "-" + typeAnalysis + ".json";

                    
                    bd.setIsUpload(id, "true");

                    System.out.println("On rentre dans le else et on écrit");
                    message = "Analysis tweet";
                    erreur = 2;

                    FileOutputStream os = new FileOutputStream("./fichiers/" + nomFichier);
                    os.write(b); 
                    os.close();
                    is.close();
                    

                    System.out.println("Fin d'écriture");

                    try{
                        //Lecture du fichier
                        InputStream ips = new FileInputStream("./fichiers/" + nomFichier); 
                        InputStreamReader ipsr = new InputStreamReader(ips);
                        BufferedReader br= new BufferedReader(ipsr);

                        System.out.println("lecture du fichier");

                        //Objet pour l'analyse
                        AnalyseDeSentiments a = new AnalyseDeSentiments();

                        //Chargement des modèles
                        StringToWordVector stw = a.loadModelOne(modele1);
                        AttributeSelection ats = a.loadModelTwo(modele2);
                        Classifier cls = a.loadModelThree(modele3);
                        CalculAttributs c = new CalculAttributs("models/configT1.properties");
                        
                        //Fichier JSON
                        FileOutputStream fos = new FileOutputStream(new File("./fichiers/" + nomFichierJSON));

                        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
                        JsonGenerator generator = factory.createGenerator(fos);
                        generator.writeStartArray();

                        //Parcours du fichier ouvert en lecture
                        String ligne;
                        while ((ligne=br.readLine())!=null){
                            //System.out.println(ligne);

                            //changement encodage 
                            byte[] somebyte = ligne.getBytes();
                            String encoding = "UTF-8"; //ANSI Cp1252 ISO-8859-1
                            String sortie = new String(somebyte, encoding);
                            //System.out.println("sortie " +sortie);

                            //resultat = a.start(ligne, modele1, modele2, modele3);
                            resultat = a.analyse(sortie, stw, ats, cls, c);
                            //listeTweet.put(sortie, resultat);
                            
                            generator.writeStartObject().write("phrase", sortie).
                                write("classe", resultat).writeEnd().flush();            
                         
                        }
                        br.close(); 
                        ipsr.close();
                        generator.writeEnd().close();

                        System.out.println("fin de lecture");

                    }
                    catch (Exception e){
                            System.out.println(e.toString());
                    }
                    
                    //Suppression du fichier .txt
                    File f = new File("./fichiers/" + nomFichier);
                    f.delete();

                                   
                    //modele pour les threads
                    /*final String m1 = modele1;
                    final String m2 = modele2;
                    final String m3 = modele3;
                    final String ficJson = nomFichierJSON;*/
                    
                    //thread pipe
                    /*final PipedOutputStream outputLecture = new PipedOutputStream();
                    final PipedInputStream  inputCalcul  = new PipedInputStream(outputLecture);
                    final PipedOutputStream outputCalcul = new PipedOutputStream();
                    final PipedInputStream inputEcriture = new PipedInputStream(outputCalcul);*/

                    //Thread de lecture de fichier
                    /*Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Lecture du fichier
                                InputStream ips = new FileInputStream("./fichiers/" + nomFichier); 
                                InputStreamReader ipsr = new InputStreamReader(ips);
                                BufferedReader br= new BufferedReader(ipsr);

                                //System.out.println("thread lecture du fichier");

                                //Parcours du fichier ouvert en lecture
                                String ligne;
                                while ((ligne=br.readLine())!=null){
                                    //System.out.println(ligne);
                                    //changement encodage 
                                    byte[] somebyte = ligne.getBytes();
                                    String encoding = "UTF-8"; //ANSI Cp1252 ISO-8859-1
                                    String sortie = new String(somebyte, encoding);
                                    //System.out.println("sortie " +sortie);
                                    
                                    //caractère de fin de ligne
                                    char c = '\003';
                                    byte b = (byte)c;
                                    
                                    //preparation de la chaine pour ecriture dans le tube
                                    byte[] tab = sortie.getBytes();
                                    int taille = sortie.length();
                                    tab[taille-1] = b;
                                    
                                    //ecriture
                                    outputLecture.write(tab);
                                }
                                br.close(); 
                                ipsr.close();
                                //System.out.println("thread fin de lecture");
                                //Suppression du fichier .txt
                                File f = new File("./fichiers/" + nomFichier);
                                f.delete();
                                outputLecture.close();

                            } catch (IOException e) {
                                System.out.println(e);
                                File file = new File("");
                                System.out.println(file.getAbsolutePath());
                            }
                        }
                    });*/

                    //Thread de calcul
                    /*Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("on est dans le thread 2");
                                //Objet pour l'analyse
                                AnalyseDeSentiments a = new AnalyseDeSentiments();

                                //Chargement des modèles
                                StringToWordVector stw = a.loadModelOne(m1);
                                AttributeSelection ats = a.loadModelTwo(m2);
                                Classifier cls = a.loadModelThree(m3);
                                
                                String resultat;
                                
                                String chaine = "";                               
                                int data = inputCalcul.read();
                                while(data != -1){
                                    
                                    char c = (char)data;
                                    chaine += c;
                                    //System.out.println(c);
                                    if(c == '\003'){
                                        //System.out.println("thread 2 fin de phrase");
                                        System.out.println("thread 2 chaine finale " + chaine);
                                        resultat = a.analyse(chaine, stw, ats, cls);
                                        
                                        System.out.println("res "+ resultat);
                                        //caractère de fin de ligne
                                        char sep = '\003';
                                        byte b = (byte)sep;
                                        
                                        String chFinal = chaine + "" + resultat;
                                        System.out.println("final " + chFinal);
                                        
                                        boolean valeur = chFinal.contains("\003");
                                        boolean vl = chFinal.contains("=");
                                        System.out.println("valeur " + valeur + " " + vl);

                                        //preparation de la chaine pour ecriture dans le tube
                                        byte[] tab = chFinal.getBytes();
                                        int taille = chFinal.length();
                                        System.out.println("taille " + taille);
                                        tab[taille-1] = b;
                                        
                                        //outputCalcul.write(tab);
                                        //int t = resultat.length();
                                        //byte[] tabR = resultat.getBytes();                                        
                                        //tabR[t-1] = b;
                                        
                                        String v = new String(tab , "UTF-8");
                                        System.out.println("taille de envoie " + v.length());
                                        System.out.println("envoie " + v); 
                                        outputCalcul.write(tab);

                                        chaine = "";
                                    }
                                    //System.out.print((char) data);
                                    data = inputCalcul.read();
                                }
                                inputCalcul.close();
                                outputCalcul.close();
                            } catch (IOException e) {
                                System.out.println(e);                               
                            } catch (Exception ex) {
                                Logger.getLogger(AffichageModeleExistant.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });*/
                    
                    
                    /*Thread thread3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                
                                FileOutputStream fos = new FileOutputStream(new File("./fichiers/" + ficJson));

                                JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
                                JsonGenerator generator = factory.createGenerator(fos);
                                generator.writeStartArray();

                                int i = 0;
                                String tweet = "";
                                String chaine = "";                               
                                int data = inputEcriture.read();
                                while(data != -1){                                  
                                    char c = (char)data;
                                    chaine += c;
                                    System.out.println(c);
                                    if((c == '\003') && (i % 2 == 0)){
                                        //System.out.println("thread 3 fin de phrase");
                                        System.out.println("thread 3 chaine finale " + chaine);
                                        tweet = chaine;
                                        chaine = "";
                                        i++;
                                    }
                                    else if((c == '\003') && (i % 2 == 1))
                                    {
                                        System.out.println(" json " +  tweet + " " + chaine);
                                        generator.writeStartObject().write("phrase", tweet).
                                            write("classe", chaine).writeEnd();
                                        tweet = "";
                                        chaine = "";
                                        i++;
                                    }
                                    //System.out.print((char) data);
                                    data = inputEcriture.read();
                                }
                                generator.writeEnd().close();
                                fos.close();
                                inputEcriture.close();

                            } catch (IOException e) {
                                System.out.println(e);      
                            } catch (Exception ex) {
                                Logger.getLogger(AffichageModeleExistant.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    thread1.start();
                    thread2.start();
                    thread3.start();*/

                    //Changement dans la base de données
                    bd.setIsUpload(id, "false");
                    
                }                              
                else{
                    information = "You already have an upload";
                }
            }
        }
          
        //Valeur pour Root
        Root root = new Root();
        root.setSample(valeurXml(fxml, "/modele/root/sample"));
        root.setMicrofmeasure(valeurXml(fxml, "/modele/root/microfmeasure"));
        root.setMacrofmeasure(valeurXml(fxml, "/modele/root/macrofmeasure"));
        root.setMicroprecision(valeurXml(fxml, "/modele/root/microprecision"));
        root.setMacroprecision(valeurXml(fxml, "/modele/root/macroprecision"));
        root.setMicrorecall(valeurXml(fxml, "/modele/root/microrecall"));
        root.setMacrorecall(valeurXml(fxml, "/modele/root/macrorecall"));
        
        
        //Recuperer les id des éléments de type classe
        String[] classe = valeurClasseXml(fxml, "/modele/classe/@id");
        ArrayList<Polarite> listeClasse = new ArrayList<Polarite>(); 
        
        //Recuperer les valeurs samples, f-measure, precision, recall
        for(int i = 0; i < classe.length; i++)
        {
            //System.out.println("nom classe :" + classe[i]);
            Polarite p = new Polarite();
            p.setClasse(classe[i]);
            p.setSample(valeurXml(fxml, "/modele/classe[@id='"+ classe[i] +"']/sample"));
            p.setFmeasure(valeurXml(fxml, "/modele/classe[@id='"+ classe[i] +"']/fmeasure"));
            p.setPrecision(valeurXml(fxml, "/modele/classe[@id='"+ classe[i] +"']/precision"));
            p.setRecall(valeurXml(fxml, "/modele/classe[@id='"+ classe[i] +"']/recall"));
            listeClasse.add(p);
        }
        
        
        //Description 
        String description = valeurXml(fxml, "/modele/description");
        
        //URL 
        String[] url = valeurUrlXml(fxml, "/modele/url");
        for(int i = 0; i < url.length; i++)
        {
            //System.out.println(url[i]);
            String lien = "";
            if(url[i].contains("2007")){
                lien = "<a href=\" "+url[i] + " \">DEFT 2007</a>";
                description = description.replace("DEFT 2007", lien);
            }
            else if(url[i].contains("2015"))
            {
                lien = "<a href=\" "+url[i] + " \">DEFT 2015</a>";
                description = description.replace("DEFT 2015", lien);
            }
        }
        
        
        request.setAttribute( "title", "Tweet" );
        request.setAttribute(ATT_TWEET, resultatSaisie);
        request.setAttribute(ATT_MESSAGE, message);
        request.setAttribute(ATT_ERREUR, erreur);
        request.setAttribute(ATT_ROOT, root);
        request.setAttribute(ATT_CLASSE, listeClasse);
        request.setAttribute(ATT_DESCRIPTION, description);
        request.setAttribute(ATT_TYPE_ANALYSIS, typeAnalysis);
        request.setAttribute(ATT_TITRE, titre);
        request.setAttribute(ATT_FILE_XML, fxml);
        request.setAttribute(ATT_FILE_JSON, nomFichierJSON);
        request.setAttribute(ATT_INFO, information);
        request.setAttribute("id", idUser);
        
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
    
    
    //Fonction qui renvoie le nom du fichier uploader
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
    public String valeurXml(String f, String expression){
        String valeur = "";
        try{
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
    
    //Fonction qui recupere les URL 
    public String[] valeurUrlXml(String f,String expression){
        String[] valeur = null;
        try{
            // Permet de lire le fichier XML directement depuis le war (marche partout comme ça)
            String path = Thread.currentThread().getContextClassLoader().getResource(f).getPath();
            File file = new File(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(file);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile(expression);
            NodeList listNode = (NodeList) expr.evaluate(xmlDocument, XPathConstants.NODESET);
            valeur = new String[listNode.getLength()];
            
            for (int i = 0; i< listNode.getLength(); i++){
                //System.out.println("taille de id " + listNode.getLength() );
                Node classe = listNode.item(i).getChildNodes().item(0);
                valeur[i] = classe.getNodeValue();
                //System.out.println(valeur[i]);
            }
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
    
    //Fonction qui recupere les classes des modèles
    public String[] valeurClasseXml(String f,String expression){
        String[] valeur = null;

        try{
            // Permet de lire le fichier XML directement depuis le war (marche partout comme ça)
            String path = Thread.currentThread().getContextClassLoader().getResource(f).getPath();
            File file = new File(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(file);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile(expression);
            NodeList listNode = (NodeList) expr.evaluate(xmlDocument, XPathConstants.NODESET);
            valeur = new String[listNode.getLength()];
            
            for (int i = 0; i< listNode.getLength(); i++){
                //System.out.println("taille de id " + listNode.getLength() );
                Node classe = listNode.item(i).getChildNodes().item(0);
                valeur[i] = classe.getNodeValue();
                //System.out.println(valeur[i]);
            }
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


/*<c:if test="${t.value.equals('+')}" >
                                            <span class="success label"><i class="fi-plus"></i> Positif</span>
                                        </c:if>
                                        <c:if test="${t.value.equals('-')}" >
                                            <span class="alert label"><i class="fi-minus"></i> Negatif</span>
                                        </c:if>
                                        <c:if test="${t.value.equals('=')}" >
                                            <span class="info label"><i class="fi-list"></i> Neutre</span>
                                        </c:if>*/