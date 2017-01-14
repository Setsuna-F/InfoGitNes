package git;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;

// ne pas toucher les imports jackson meme si eclipse dit qu'ils ne servent pas
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonView;

public class Main {

	public static void main(String []args) throws IOException, GitAPIException{
		
		File nes = new File("./processus.nes");
		if (nes.exists()) {
			try {
				Tools.recursifDelete(nes);
				FileWriter fw = new FileWriter (nes);
		        fw.write ("0");
		        fw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File dir = new File("./INFOGITPROJET/");
		if (dir.exists()) {
			try {
				Tools.recursifDelete(dir);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File dir2 = new File("./branches/");
		if (dir2.exists()) {
			try {
				Tools.recursifDelete(dir2);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		dir2.mkdir();
		
		try{
			InfoGit ig2=new InfoGit(args[0], dir);
			ig2.collectBranches();
			Tools.tojson(ig2, 1);
			Tools.tojson(ig2.lsGit());
			
		    try
		    {
		        FileWriter fw = new FileWriter (nes);
		        fw.write ("1");
		        fw.close();
		    }
		    catch (IOException exception)
		    {
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch(ArrayIndexOutOfBoundsException a)
		{
			try
		    {
		        FileWriter fw = new FileWriter (nes);
		        fw.write ("-1");
		        fw.close();
		    }
		    catch (IOException exception)
		    {
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
	}
	
	
}
