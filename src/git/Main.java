package git;

import java.io.File;
import java.io.IOException;
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
		System.out.println("Le nom de la branche est : \t"		+ig2.getBranches().get(0).getBranchName());
//	    System.out.println("Le nombre de commits est de : \t"	+ig2.getBranches().get(0).getCommits().get("Doug Gregor").size());
	    System.out.println("Le nombre de user est de : \t"		+ig2.getPersonName().size());
        System.out.println("-------------------------------------");
	    System.out.println(ig2.getPersonName().toString());
//	    System.out.println(ig2.getBranches().get(0).getAllMailOf("Doug Gregor").toString());
//	    System.out.println(ig2.getBranches().get(0).getCommits().toString());
	    //System.out.println(ig2.getBranches().get(0).getCommits().get("Doug Gregorr").get(0).getDate().toString());
        System.out.println("-------------------------------------");
		
		System.out.println("Fin");
	}

}
