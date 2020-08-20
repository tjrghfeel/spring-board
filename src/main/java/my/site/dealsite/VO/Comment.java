package my.site.dealsite.VO;

public class Comment {
	private int num;
	private int postNum;
	private String writerId;
	private String content;
	private String time;
	private boolean child;
	private int parrentComment;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
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
	public boolean isChild() {
		return child;
	}
	public void setChild(boolean child) {
		this.child = child;
	}
	public int getParrentComment() {
		return parrentComment;
	}
	public void setParrentComment(int parrentComment) {
		this.parrentComment = parrentComment;
	}
	
}
