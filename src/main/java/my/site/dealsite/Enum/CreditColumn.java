package my.site.dealsite.Enum;

public enum CreditColumn {
	num("num"),accountId("accountId"),accountNum("accountNum"),time("time"),content("content"),
	reportAccountNum("reportAccountNum"), postNum("postNum"),reportAccountId("reportAccountId"),none("");
	
	String dbName;
	
	CreditColumn(String name){dbName=name;}
	
	public String getDbName() {return dbName;}

}
