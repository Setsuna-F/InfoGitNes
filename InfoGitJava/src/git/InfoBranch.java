package git;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class InfoBranch {
	
	private String name;
	private String ref;
	private int nbCommits;
	private Hashtable<String, ArrayList <InfoCommit> > commits;

	//Variables pour l'arborescence
	private ArrayList<String> debut;
	private Hashtable<ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>> parents;
	
	
	/**
	 * \brief constructeur
	 * 
	 * \param name nom de la branche.
	 * \param ref  la reference de la branche.
	 * 
	 **/
	InfoBranch(String name, String ref){
		this.name=name;
		this.ref=ref;
		this.nbCommits=0;
		this.commits=new Hashtable<String, ArrayList <InfoCommit> >();
		parents = new Hashtable<ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>>();
		debut = new ArrayList<String>();
	}
	
	
	/**
	 * \return le nom de la branche.
	 * 
	 **/
	public String getBranchName() { 
		return name; 
	}
	
	
	/**
	 * \return la reference de la branche.
	 * 
	 **/
	public String getRef() { 
		return ref; 
	}
	
	
	/**
	 * \return le nombre de commits pour la branche.
	 * 
	 **/
	public int getNbCommits() {
		return nbCommits; 
	}

	
	/**
	 * \return le début de l'arbre.
	 * 
	 **/
	public ArrayList<String> getDebut() {
		return debut;
	}


	/**
	 * \brief change le début de l'arbre.
	 * 
	 **/
	public void setDebut(ArrayList<String> debut) {
		this.debut = debut;
	}
	

	/**
	 * \return la hashtable parents.
	 * 
	 **/
	public Hashtable<ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>> getParents() {
		return parents;
	}


	/**
	 * \brief change la hashtable parents.
	 * 
	 **/
	public void setParents(ArrayList<ArrayList<String>> f, ArrayList<ArrayList<String>> pa) {
		parents.put(f, pa);

	}

	
	/**
	 * \brief regroupe une liste de nom d'utilisateurs.
	 * 
	 * \return une liste d'utilisateurs qui ont fait un commit.
	 **/
	public ArrayList<String> getTousLesNomDUtilisateur(){
		ArrayList<String> noms=new ArrayList<String>();
		Iterator it=commits.keySet().iterator();
		
		while(it.hasNext())
			noms.add(it.next().toString());

		return noms;
	}
	
	
	/**
	 * \return tous les commits avec leurs utilisateurs. 
	 *
	 **/
	public Hashtable<String, ArrayList<InfoCommit>> getCommits() {
		return commits;
	}

	
	/**
	 * \brief ajoute le commit à l'utilisateur.
	 * 
	 **/
	public void setCommits(String utilisateur, InfoCommit commits) {
		if(!this.commits.containsKey(utilisateur))
			this.commits.put(utilisateur, new ArrayList<InfoCommit>()); /*On ajoute l'utilisateur*/
		ArrayList<InfoCommit> icommits =  this.commits.get(utilisateur);
		icommits.add(commits);
		this.commits.put(utilisateur, icommits);
		nbCommits++;
	}



}
