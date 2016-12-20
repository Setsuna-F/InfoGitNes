package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;


public class InfoGit {

	private String url;
	private Hashtable<String, ArrayList<String>> person;
	private ArrayList<InfoBranch> branches;
	
	private Repository repo;
	private RevWalk walk;
	private Git git;
	
	/**
	 * \brief constructeur
	 * 
	 * \param url  url du depot git.
	 * \param dir  repertoire ou le cloner 
	 * 
	 **/
	InfoGit(String url, File dir) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
		this.url 		= url;	
		this.person		= new Hashtable<String, ArrayList<String> >();
		this.branches 	= new ArrayList<InfoBranch>();
		
		//Clonage du git
		git = Git.cloneRepository()
					.setURI(url)
					.setDirectory(dir)
					.call();
		repo = git.getRepository();
		
		/*try {
			repo = new FileRepository("/Users/S-Setsuna-F/Documents/Master2/. et Restruct/GIT TEST/jawgrind/.git");
			git = new Git(repo);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		walk = new RevWalk(repo);
	}
	
	
	/**
	 * \brief collecte toutes les branches du git.
	 * 
	 **/
	public void collectBranches() throws IOException, GitAPIException{
    	InfoBranch infoBranch;
	    List<Ref> lBranches = git.branchList().setListMode(ListMode.REMOTE).call();

	    for (Ref branch : lBranches) {
	        String branchName = branch.getName();
	        infoBranch = new InfoBranch(branchName, branch.getObjectId().getName());
	        
	        collectCommitByBranch(infoBranch);
	        branches.add(infoBranch);
	    }
	}
	
	
	/**
	 * \brief collecte tous les commit de la branche passee en parametre.
	 * 
	 **/
	public void collectCommitByBranch(InfoBranch infoBranch) throws GitAPIException, IOException{
		Iterable<RevCommit> commits = git.log().add(repo.resolve(infoBranch.getBranchName())).call();
		int cpt = 0;
	
        for (RevCommit commit : commits) {
        	InfoCommit infoCommit = new InfoCommit(commit.getName(),  new Date(commit.getCommitTime()), commit.getFullMessage());
        	this.setPerson(commit.getAuthorIdent().getName(), commit.getAuthorIdent().getEmailAddress());	

        	if(commit.getParents().length != 0){
            	RevCommit parent = walk.parseCommit(commit.getParent(0).getId());

        		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
            	df.setRepository(repo);
            	df.setDiffComparator(RawTextComparator.DEFAULT);
            	df.setDetectRenames(true);
            	List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());
            	for (DiffEntry diff : diffs) {
            		if(diff.getChangeType().name().equals("MODIFY"))
            			infoCommit.setModify();
            		if(diff.getChangeType().name().equals("ADD"))
            			infoCommit.setAdd();
            		if(diff.getChangeType().name().equals("RENAME"))
            			infoCommit.setRename();
            		if(diff.getChangeType().name().equals("DELETE"))
            			infoCommit.setDelete();
               	}
        	}
        	
        	//Arborescence
        	if(cpt == 0){
        		String s =  commit.getName() + " " + commit.getShortMessage();
        		infoBranch.setDebut(s);;
        	}
        	cpt++;

        	String fils = commit.getName() + " " + commit.getShortMessage();
        	ArrayList<String> pa = new ArrayList<String>();
        	for(int i = 0; i < commit.getParentCount(); i++){
        		pa.add(commit.getParent(i).getName() + " " + commit.getParent(i).getShortMessage());
        	}
        	infoBranch.setParents(fils, pa);
        	//Fin Arborescence
        	
        	infoBranch.setCommits(commit.getAuthorIdent().getName(), new InfoCommit(commit.getName(), new Date(commit.getCommitTime()), commit.getFullMessage()));
        }
	}

	
	/** 
	 * \return tous les utilisateurs avec leurs adresses mail.
	 * 
	 **/
	public Hashtable<String, ArrayList<String>> getPerson() {
		return person;
	}

	
	/**
	 * \return une liste contenant le nom d'utilisateurs qui ont fait un commit.
	 **/
	public ArrayList<String> getPersonName(){
		ArrayList<String> noms=new ArrayList<String>();
		Iterator it=person.keySet().iterator();
		
		while(it.hasNext())
			noms.add(it.next().toString());
		
		return noms;
	}
	
	
	/**
	 * \brief retourne tous les mails d'un utilisateur donnee.
	 * 
	 * \return une arraylist d'adresse mail. 
	 **/
	public ArrayList<String> getAllMailOf(String nom){
		return person.get(nom);
	}
	
	
	/**
	 * \brief ajoute l'utilisateur et son email à la liste person.
	 **/
	public void setPerson(String utilisateur, String mail) {
		/*Si l'utilisateur n'est pas encore present dans la hashtable*/
		if(!this.person.containsKey(utilisateur))
			this.person.put(utilisateur, new ArrayList<String>()); /*On ajoute l'utilisateur*/
		/*Si l'utilisateur est present mais pas son adresse mail*/
		if(!this.person.get(utilisateur).contains(mail)) {
			ArrayList<String> mel =  this.person.get(utilisateur);
			mel.add(mail);
			this.person.put(utilisateur, mel);/*On ajoute son adresse mail*/
		}
	}
	
	
	/**
	 * \return l'url du git.
	 **/
	public String getUrl() {
		return url;
	}

	
	/**
	 * \return toutes les branches du git.
	 **/
	public ArrayList<InfoBranch> getBranches() {
		return branches;
	}

	
	/**
	 * \return le nombre de branches.
	 **/
	public int getNbBranches(){
		return branches.size();
	}

}
