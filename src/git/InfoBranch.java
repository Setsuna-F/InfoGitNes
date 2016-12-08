package git;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class InfoBranch {
	private String name;
	private String ref;
	private int nbCommits;
	private Hashtable<String, ArrayList <InfoCommit> > commits;
	//private ArrayList<Couple> merges;
	//private ArrayList<Couple> newBranches;
	
	
	InfoBranch(String name, String ref){
		this.name=name;
		this.ref=ref;
		this.nbCommits=0;
		this.commits=new Hashtable<String, ArrayList <InfoCommit> >();
	}
	
	public String getBranchName() {
		return name;
	}
	
	public String getRef() {
		return ref;
	}

	/**
	 * \brief regroupe une liste de nom d'utilisateus.
	 * 
	 * \return une list d'utilisateurs qui ont fait un commit.
	 * */
	public ArrayList<String> getTousLesNomDUtilisateur(){
		ArrayList<String> noms=new ArrayList<String>();
		Iterator it=commits.keySet().iterator();
		
		while(it.hasNext())
			noms.add(it.next().toString());

		return noms;
	}
	
	/**
	 * \brief retourne tous les commits avec leurs utilisateurs. 
	 *
	 */
	public Hashtable<String, ArrayList<InfoCommit>> getCommits() {
		return commits;
	}

	public void setCommits(String utilisateur, InfoCommit commits) {
		if(!this.commits.containsKey(utilisateur))
			this.commits.put(utilisateur, new ArrayList<InfoCommit>()); /*On ajoute l'utilisateur*/
		ArrayList<InfoCommit> icommits =  this.commits.get(utilisateur);
		icommits.add(commits);
		this.commits.put(utilisateur, icommits);
		nbCommits++;
	}

	public String getName() {
		return name;
	}

	public int getNbCommits() {
		return nbCommits;
	}

}
