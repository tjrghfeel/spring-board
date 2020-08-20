package my.site.dealsite.Enum;

public enum PostColumn {
	num("num"),title("title"),sort1("sort1"),sort2("sort2"),content("content"),mainImage("mainImage"),
	viewCount("viewCount"),commentCount("commentCount"),writeTime("writeTime"),updateTime("updateTime"),
	writerId("writerId"),writerNum("writerNum"),price("price"),saleComplete("saleComplete"),open("open"),
	none("");
	
	String dbName;
	
	PostColumn(String name){dbName=name;}
	
	public String getDbName() {return dbName;}
}
