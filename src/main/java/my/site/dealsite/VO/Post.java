package my.site.dealsite.VO;

public class Post {
	private int num;
	private String title;
	private String sort1;
	private String sort2;
	private String content;//data url방식으로 이미지를 저장하기 때문에 크기가 약간 클 수 있다. 
	private String mainImage;
	private int viewCount;
	private int commentCount;
	private String writeTime;
	private String updateTime;
	private String writerId;
	private int writerNum;
	private int price;
	private boolean saleComplete;
	private boolean open;
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSort1() {
		return sort1;
	}
	public void setSort1(String sort1) {
		this.sort1 = sort1;
	}
	public String getSort2() {
		return sort2;
	}
	public void setSort2(String sort2) {
		this.sort2 = sort2;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public int getWriterNum() {
		return writerNum;
	}
	public void setWriterNum(int writerNum) {
		this.writerNum = writerNum;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean getSaleComplete() {
		return saleComplete;
	}
	public void setSaleComplete(boolean saleComplete) {
		this.saleComplete = saleComplete;
	}
	
}
