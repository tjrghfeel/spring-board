package my.site.dealsite.VO;

public class Message {
	private int num;
	private String sender;
	private String receiver;
	private String content;
	private String time;
	private boolean readReceipt;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isReadReceipt() {
		return readReceipt;
	}
	public void setReadReceipt(boolean readReceipt) {
		this.readReceipt = readReceipt;
	}
	

}
