package my.site.dealsite.VO;

public class Credit {
	private int num;
	private String accountId;
	private int accountNum;
	private String time;
	private String content;
	private int reportAccountNum;
	private int postNum;
	private String reportAccountId;
	
	public String getReportAccountId() {
		return reportAccountId;
	}
	public void setReportAccountId(String reportAccountId) {
		this.reportAccountId = reportAccountId;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public int getReportAccountNum() {
		return reportAccountNum;
	}
	public void setReportAccountNum(int reportAccountNum) {
		this.reportAccountNum = reportAccountNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
