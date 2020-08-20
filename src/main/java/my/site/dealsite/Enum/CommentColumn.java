package my.site.dealsite.Enum;

public enum CommentColumn {
	num("num"),postNum("postNum"),writerId("writerId"),content("content"),time("time"),child("child"),
	parrentComment("parrentComment"),none("");
	
	String dbName;
	
	CommentColumn(String name){this.dbName=name;}
	
	public String getDbName() {return dbName;}
}
