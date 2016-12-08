package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
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
	
	
	
	InfoGit(String url, File dir) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
		this.url 		= url;	
		this.person		= new Hashtable<String, ArrayList<String> >();
		this.branches 	= new ArrayList<InfoBranch>();
		
		
		//Clonage du git A changer
		//git = Git.cloneRepository()
		//			.setURI(url)
		//			.setDirectory(dir)
		//			.call();
		//repo = git.getRepository();
		
		try {
			repo = new FileRepository("/Users/S-Setsuna-F/Documents/Master2/Évo. et Restruct/GIT TEST/jawgrind/.git");
			git = new Git(repo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		walk = new RevWalk(repo);
	}
	
	void collectBranches() throws IOException, GitAPIException{
    	InfoBranch infoBranch;
	    List<Ref> lBranches = git.branchList().setListMode(ListMode.REMOTE).call();

	    for (Ref branch : lBranches) {
	        String branchName = branch.getName();
	        infoBranch = new InfoBranch(branchName, branch.getObjectId().getName());
	        
	        collectCommitByBranch(infoBranch);
	        branches.add(infoBranch);
	    }
	}
	

	public void collectCommitByBranch(InfoBranch infoBranch) throws GitAPIException, IOException{
		Iterable<RevCommit> commits = git.log().add(repo.resolve(infoBranch.getBranchName())).call();
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
        	infoBranch.setCommits(commit.getAuthorIdent().getName(), new InfoCommit(commit.getName(), new Date(commit.getCommitTime()), commit.getFullMessage()));
        }
	}

	/** 
	 * \brief tous les utilisateurs avec leurs adresses mail
	 * 
	 */
	public Hashtable<String, ArrayList<String>> getPerson() {
		return person;
	}

	/**
	 * \brief regroupe une liste de nom d'utilisateus.
	 * 
	 * \return une list d'utilisateurs qui ont fait un commit.
	 * */
	public ArrayList<String> getPersonName(){
		ArrayList<String> noms=new ArrayList<String>();
		Iterator it=person.keySet().iterator();
		
		while(it.hasNext())
			noms.add(it.next().toString());
		
		return noms;
	}
	
	
	/**
	 * \brief retourne tous les mails d'un utilisateur donnée.
	 * 
	 * \return une arraylist d'adresse mail. 
	 */
	public ArrayList<String> getAllMailOf(String nom){
		return person.get(nom);
	}
	
	public void setPerson(String utilisateur, String mail) {
		/*Si l'utilisateur n'est pas encore present dans la hashtable*/
		if(!this.person.containsKey(utilisateur))
			this.person.put(utilisateur, new ArrayList<String>()); /*On ajoute l'utilisateur*/
		/*Si l'utilisateur est present mais pas son adresse mail*/
		if(!this.person.get(utilisateur).contains(mail)) {
			ArrayList<String> mel =  this.person.get(utilisateur);
			mel.add(mail);
			this.person.put(utilisateur, mel);/*On ajoute son adresse mail*/
			//this.utilisateurs.get(utilisateur).add(mail); 
		}
	}
	
	public String getUrl() {
		return url;
	}

	public ArrayList<InfoBranch> getBranches() {
		return branches;
	}

	public int getNbBranches(){
		return branches.size();
	}

}
