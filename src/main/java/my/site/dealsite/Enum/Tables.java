package my.site.dealsite.Enum;

public enum Tables {
	post("post"), account("account"), comment("comment"), credit("credit");
	
	String dbName;
	
	Tables(String name){this.dbName=name;}
	
	public String getDbName() {return dbName;}
}

