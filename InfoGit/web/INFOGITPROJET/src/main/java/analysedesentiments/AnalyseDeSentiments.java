
package analysedesentiments;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author amin.abdaoui
 */
public class AnalyseDeSentiments {

    /**
     * @param args the command line arguments
     */
    public static String start(String s, String m1, String m2, String m3) throws Exception {
        String tweet=s;
        Instances data = makeInstance(tweet);
        
        StringToWordVector stw = (StringToWordVector) weka.core.SerializationHelper.read("models/" + m1);
        AttributeSelection ats = (AttributeSelection)weka.core.SerializationHelper.read("models/" + m2);
        Classifier cls = (Classifier) weka.core.SerializationHelper.read("models/" + m3);
        
        String path="models/configT1.properties";
        CalculAttributs ca = new CalculAttributs(path);
        ConstructionARFF obj = new ConstructionARFF(path,ca);
        data = obj.ConstructionInstances(data);
        Instance ins = data.firstInstance();
        stw.input(ins);
        ins = stw.output();
        ats.input(ins);
        ins = ats.output();
        
        double classe=cls.classifyInstance(ins);
        
        if (classe==0) {
            System.out.println("+");
            return "+";
        }
        else if (classe==1) {
            System.out.println("-");
            return "-";
        }
        else if (classe==2) {
            System.out.println("=");
            return "=";
        }
        else {
            System.out.println("Erreur");
            return "Erreur";
        }
       //return sd; 
        
    }
    /*
    Chargement des modeles
    */
    public static StringToWordVector loadModelOne(String m1) throws Exception {
       StringToWordVector stw = (StringToWordVector) weka.core.SerializationHelper.read("models/" + m1);
       return stw; 
    }
    
    
    public static AttributeSelection loadModelTwo(String m2) throws Exception {
        AttributeSelection ats = (AttributeSelection)weka.core.SerializationHelper.read("models/" + m2);
        return ats;
    }
    
    
    public static Classifier loadModelThree(String m3) throws Exception {
        Classifier cls = (Classifier) weka.core.SerializationHelper.read("models/" + m3);
        return cls;
    }
    
    /*
    Analyse 
    */
    public static String analyse(String s, StringToWordVector stw, AttributeSelection ats, Classifier cls, CalculAttributs c) throws Exception{
        String tweet=s;
        Instances data = makeInstance(tweet);
        String path="models/configT1.properties";
        ConstructionARFF obj = new ConstructionARFF(path,c);
        data = obj.ConstructionInstances(data);
        Instance ins = data.firstInstance();
        stw.input(ins);
        ins = stw.output();
        ats.input(ins);
        ins = ats.output();
        
        double classe=cls.classifyInstance(ins);
        return ats.getOutputFormat().attribute("_class").value((int)classe);
    }
    
    public static Instances makeInstance(String tweet) {

        // Liste des attributs
        ArrayList<Attribute> atts = new ArrayList(2);
        
        // Ajouter le descripteur
        atts.add(new Attribute("_text",(ArrayList<String>)null));
        
        // Construire l'attribut de classe
        ArrayList<String> classVal = new ArrayList<String>();
        classVal.add("+");
        classVal.add("-");
        classVal.add("=");
        
        // Ajouter l'attribut de classe (nominal)
        atts.add(new Attribute("_class",classVal));
        
        // Créer l'objet Instances data ayant comme attributs atts
        Instances data = new Instances("instance",atts,0);
        
        // L'instance
        Instance ins = new DenseInstance(2);
        ins.setDataset(data);
        
        // Remplir Instance
        ins.setValue(0, tweet);
        data.add(ins);
        
        return data;
  }
    
}
