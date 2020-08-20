package my.site.dealsite.Enum;

public enum AccountColumn {
	num("num"),id("id"),pw("pw"),email("email"),pNum("pNum"),address("address"),grade("grade"),credit("credit"),
	name("name"),none("");
	
	String dbName;
	
	AccountColumn(String name){dbName=name;}
	
	public String getDbName() {return dbName;}

}
