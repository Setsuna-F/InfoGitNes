package git;

import java.io.File;
import java.io.IOException;

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
	
}
