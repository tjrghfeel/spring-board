package my.site.dealsite.VO;

import javax.validation.constraints.Size;

public class Account {
	private int num;
	private String id;
	private String pw;
	private String email;
	private String pNum;
	private String address;
	private int grade;
	private int credit;
	private String name;
	
	public Account() {}
	public Account(String id, String pw, String email, String pNum, String address, int grade, String name) {
		this.id=id; this.pw=pw; this.email=email; this.pNum=pNum; this.address=address; this.grade=grade; this.name=name;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getpNum() {
		return pNum;
	}
	public void setpNum(String pNum) {
		this.pNum = pNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	
	
}
