package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

// ne pas toucher les imports jackson meme si eclipse dit qu'ils ne servent pas
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonView;

public class Main {

	public static void main(String []args) throws IOException, GitAPIException{
		System.out.println("Debut");

		File dir = new File("./INFOGITPROJET/");
		if (dir.exists()) {
			try {
				tools.recursifDelete(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File dir2 = new File("./branches/");
		dir2.mkdir();
		
		InfoGit ig2=new InfoGit("https://github.com/ddeboer/GuzzleBundle.git", dir);
		//InfoGit ig2=new InfoGit("https://github.com/apple/swift", dir);
		ig2.collectBranches();	
		
	    //System.out.println("Le nombre de branche est de : \t"+ig2.getNbBranches());
        //System.out.println("-------------------------------------");
	    ArrayList<InfoBranch> listeBranches = ig2.getBranches();
	    int nb = 1;
	    for (InfoBranch infoBranch : listeBranches) {
			
	    	//System.out.println("Nom de la branche :" +listeBranches.get(i).getBranchName());
	    	//System.out.println("Ref de la branche :" +listeBranches.get(i).getRef());
	    	
	    	//Hashtable<String, ArrayList<InfoCommit>> listeCommits = listeBranches.get(i).getCommits();
	    	//for (String mapKey : listeCommits.keySet()) {
				//System.out.println( mapKey + " a fait " + listeCommits.get(mapKey).size() + " commits");
				//tojson(listeCommits.get(mapKey), i);
			//}
	       // System.out.println("-------------------------------------");
	        
	       // System.out.println("****Arborescence****");
	        //String deb = listeBranches.get(i).getDebut();
	        //ArrayList<String> p = listeBranches.get(i).getParents().get(deb);
			//tools.affichageParent(p, listeBranches.get(i));
	        //System.out.println("****Fin Arborescence****");
	       // System.out.println("-------------------------------------");
	        
	        tojson(infoBranch, nb++);

	    }
	    
        /*System.out.println("-------------------------------------");
		System.out.println("Le nom de la branche est : \t"		+ig2.getBranches().get(0).getBranchName());
		System.out.println("Le nombre de commits est de : \t"	+ig2.getBranches().get(0).getCommits().get("Doug Gregor").size());
	    System.out.println("Le nombre de user est de : \t"		+ig2.getPersonName().size());
        System.out.println("-------------------------------------");
	    System.out.println(ig2.getPersonName().toString());
	    System.out.println(ig2.getBranches().get(0).getAllMailOf("Doug Gregor").toString());
	    System.out.println(ig2.getBranches().get(0).getCommits().toString());
	    System.out.println(ig2.getBranches().get(0).getCommits().get("Doug Gregorr").get(0).getDate().toString());
        System.out.println("-------------------------------------");*/
		
		System.out.println("Fin");
	}
	public static void tojson(Object infos, int i) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		//String jsonInString = mapper.writeValueAsString(infos);
		mapper.writeValue(new File("./branches/branche_"+i+".json"), infos);
		//System.out.println(jsonInString);
	}

}
