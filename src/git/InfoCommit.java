package git;

import java.util.Date;

public class InfoCommit {

	private String 	ID;
	private Date 	date;
	private String	message;
	
	private  int nbAdd; 
	private  int nbDelete; 
	private  int nbModify; 
	private  int nbRename; 

	/**
	 * \brief constructeur
	 * 
	 * \param id identifiant du commit qui a ete depose.
	 * \param d  date du commit qui a ete depose.
	 * \param m  message du commit qui a ete depose.
	 * 
	 */
	InfoCommit(String id, Date d, String m){ 
		this.ID 	 = id;
		this.date 	 = d;
		this.message = m;
		
		this.nbAdd 	  = 0;
		this.nbDelete = 0;
		this.nbModify = 0;
		this.nbRename = 0;
	}

	/*Getter-Setter*/
	public String getID() 		{ return ID; }
	public Date   getDate() 	{ return date; }
	public String getMessage()  { return message; }
	public int 	  getNbAdd()	{ return nbAdd; }
	public int 	  getNbDelete()	{ return nbDelete; }
	public int 	  getNbModify()	{ return nbModify; }
	public int 	  getNbRename()	{ return nbRename; }
	
	public void   setAdd() 	  { nbAdd++; }
	public void   setDelete() { nbDelete++; }
	public void   setModify() { nbModify++; }
	public void   setRename() { nbRename++; }
	
}
