package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class tools {
	
	
	/**
	 * \brief supprime le dossier s'il existe deja.
	 **/
	public static void recursifDelete(File path) throws IOException {
		if (!path.exists())
			throw new IOException("File not found '" + path.getAbsolutePath() + "'");
		if (path.isDirectory()) {
			File[] children = path.listFiles();
			for (int i = 0; children != null && i < children.length; i++)
				recursifDelete(children[i]);
			if (!path.delete())
				throw new IOException("No delete path '" + path.getAbsolutePath() + "'");
		} else if (!path.delete())
			throw new IOException("No delete file '" + path.getAbsolutePath() + "'");
	}
	
	
	/**
	 * \brief affichage des parents.
	 **/
	public static void affichageParent(ArrayList<String> p, InfoBranch g){
		for(int i = 0; i < p.size(); i++){
			String s = "";
			if(i == 1){
				s+= " ";
			}
			System.out.println(s + "* " + p.get(i));
			affichageParent(g.getParents().get(p.get(i)), g);
		}
		
		
	}
	
}
