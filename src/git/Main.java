package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

public class Main {

	public static void main(String []args) throws IOException, GitAPIException{
		System.out.println("Debut");

		File dir = new File("./INFOGIT/");
		if (dir.exists()) {
			try {
				tools.recursifDelete(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		InfoGit ig2=new InfoGit("https://github.com/ddeboer/GuzzleBundle.git", dir);

		ig2.collectBranches();	
		
	    System.out.println("Le nombre de branche est de : \t"+ig2.getNbBranches());
        System.out.println("-------------------------------------");
	    ArrayList<InfoBranch> listeBranches = ig2.getBranches();
	    for(int i = 0; i < listeBranches.size(); i++){
	    	System.out.println("Nom de la branche :" +listeBranches.get(i).getBranchName());
	    	System.out.println("Ref de la branche :" +listeBranches.get(i).getRef());
	    	
	    	Hashtable<String, ArrayList<InfoCommit>> listeCommits = listeBranches.get(i).getCommits();
	    	for (String mapKey : listeCommits.keySet()) {
				System.out.println( mapKey + " a fait " + listeCommits.get(mapKey).size() + " commits");
			}
	        System.out.println("-------------------------------------");
	        
	        System.out.println("****Arborescence****");
	        String deb = listeBranches.get(i).getDebut();
	        ArrayList<String> p = listeBranches.get(i).getParents().get(deb);
			tools.affichageParent(p, listeBranches.get(i));
	        System.out.println("****Fin Arborescence****");
	        System.out.println("-------------------------------------");

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

}
