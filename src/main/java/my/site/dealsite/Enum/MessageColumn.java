package my.site.dealsite.Enum;

public enum MessageColumn {
	num("num"),sender("sender"),receiver("receiver"),content("content"),time("time"),readReceipt("readReceipt");
	
	String dbName;
	
	MessageColumn(String name){dbName=name;}
	
	public String getDbName() {return dbName;}

}
