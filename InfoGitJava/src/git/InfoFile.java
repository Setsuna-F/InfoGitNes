package git;

public class InfoFile {
	
	private String type;
	private String path;
	private int line;
	private String name;
	
	
	/**
	 * \brief constructeur
	 * 
	 * \param t  type
	 * \param p  chemin
	 * \param l  nombre de ligne
	 * \param n  le nom
	 * 
	 **/
	InfoFile(String t, String p, int l, String n){
		type = t;
		path = p;
		line = l;
		name = n;
	}
	
	
	/**
	 * \brief constructeur
	 * 
	 * \param t  type
	 * \param p  chemin
	 * \param n  le nom
	 * 
	 **/
	InfoFile(String t, String p, String n){
		type = t;
		path = p;
		name = n;
	}

	
	/*Getter*/
	public String getType() { return type; }
	public String getPath() { return path; }
	public int 	  getLine() { return line; }
	public String getName() { return name; }
	
	
	/*Setter*/
	public void setType(String type) { this.type = type; }
	public void setPath(String path) { this.path = path; }
	public void setLine(int line) 	 { this.line = line; }
	public void setName(String name) { this.name = name; }

}
