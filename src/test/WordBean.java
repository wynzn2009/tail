package test;

public class WordBean {
	private String tword;
	private String cword;

	public WordBean() {
		super();
	}

	public WordBean(String tword, String cword) {
		super();
		this.tword = tword;
		this.cword = cword;
	}

	public String getTword() {
		return tword;
	}

	public void setTword(String tword) {
		this.tword = tword;
	}

	public String getCword() {
		return cword;
	}

	public void setCword(String cword) {
		this.cword = cword;
	}

	public String getValue() {
		return tword + "," + cword;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
