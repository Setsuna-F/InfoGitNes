package git;

public class InfoFile {
	private String type;
	private String path;
	private int line;
	private String name;
	
	InfoFile(String t, String p, int l, String n)
	{
		type = t;
		path = p;
		line = l;
		name = n;
	}
	
	InfoFile(String t, String p, String n)
	{
		type = t;
		path = p;
		name = n;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
