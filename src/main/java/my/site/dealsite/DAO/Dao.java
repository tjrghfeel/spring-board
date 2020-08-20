package my.site.dealsite.DAO;

import my.site.dealsite.VO.*;
import java.sql.*;
import java.util.LinkedList;
import my.site.dealsite.Enum.*;

public class Dao {
	private static Connection config() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/dealsite?"
            		+ "characterEncoding=UTF-8&serverTimezone=UTC", "aaa", "aaa");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	//select메소드들.
		//단순 VO검색 메소드들. 인자로받은 컬럼과 그 값으로 검색. 없으면 null반환.
	public static Account selectVO(AccountColumn column, String value) {
		Connection connection = config();
		String query = null;
		Statement st = null;
		ResultSet rs=null;
		Account account = null;
		
		try {
			query = "select * from account where "+column.getDbName()+"='"+value+"'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {//아이디가 없다면
				System.out.println("in Dao.select(), there is no account");
			}
			else {
				account = new Account();
				account.setNum(rs.getInt("num"));
				account.setId(rs.getString("id"));
				account.setPw(rs.getString("pw"));
				account.setEmail(rs.getString("email"));
				account.setpNum(rs.getString("pNum"));
				account.setAddress(rs.getString("address"));
				account.setGrade(rs.getInt("grade"));
				account.setCredit(rs.getInt("credit"));
				account.setName(rs.getString("name"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return account;
	}
	public static Account selectVO(AccountColumn column1, AccountColumn column2, String value1, String value2) {
		Connection connection = config();
		String query = null;
		Statement st = null;
		ResultSet rs=null;
		Account account = null;
		
		try {
			query = "select * from account where "+column1.getDbName()+"='"+value1+"'"
					+ " and "+column2.getDbName()+"='"+value2+"'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {//아이디가 없다면
				System.out.println("in Dao.select(), there is no account");
			}
			else {
				account = new Account();
				account.setNum(rs.getInt("num"));
				account.setId(rs.getString("id"));
				account.setPw(rs.getString("pw"));
				account.setEmail(rs.getString("email"));
				account.setpNum(rs.getString("pNum"));
				account.setAddress(rs.getString("address"));
				account.setGrade(rs.getInt("grade"));
				account.setCredit(rs.getInt("credit"));
				account.setName(rs.getString("name"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return account;
	}
	public static Post selectVO(PostColumn column, String value) {
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		Post post=null;
		
		try {
			query="select * from post where "+column.getDbName()+"='"+value+"'";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				post=new Post();
				post.setNum(rs.getInt("num"));
				post.setSort1(rs.getString("sort1"));
				post.setSort2(rs.getString("sort2"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setMainImage(rs.getString("mainImage"));
				post.setViewCount(rs.getInt("viewCount"));
				post.setCommentCount(rs.getInt("commentCount"));
				post.setWriteTime(rs.getString("writeTime"));
				post.setUpdateTime(rs.getString("updateTime"));	
				post.setWriterId(rs.getString("writerId"));
				post.setWriterNum(rs.getInt("writerNum"));
				post.setPrice(rs.getInt("price"));
				post.setSaleComplete(rs.getBoolean("saleComplete"));
				post.setOpen(rs.getBoolean("open"));
			}
			else {
				System.out.println("in Dao.selectPost(), there is no post");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return post;
	}
	public static Comment selectVO(CommentColumn column, String value) {
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		Comment comment=null;
		
		try {
			query="select * from comment where "+column.getDbName()+"='"+value+"'";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				comment=new Comment();
				comment.setNum(rs.getInt("num"));
				comment.setPostNum(rs.getInt("postNum"));
				comment.setWriterId(rs.getString("writerId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("time"));
				comment.setChild(rs.getBoolean("child"));
				comment.setParrentComment(rs.getInt("parrentComment"));
			}
			else {
				System.out.println("in Dao.selectPost(), there is no post");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return comment;
	}
	public static Credit selectVO(CreditColumn column, String value) {
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		Credit credit=null;
		
		try {
			query="select * from credit where "+column.getDbName()+"='"+value+"'";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				credit=new Credit();
				credit.setNum(rs.getInt("num"));
				credit.setAccountId(rs.getString("accountId"));
				credit.setAccountNum(rs.getInt("accountNum"));
				credit.setTime(rs.getString("time"));
				credit.setContent(rs.getString("content"));
				credit.setReportAccountNum(rs.getInt("reportAccountNum"));
				credit.setReportAccountId(rs.getString("reportAccountId"));
				credit.setPostNum(rs.getInt("postNum"));
			}
			else {
				System.out.println("in Dao.selectPost(), there is no Credit");
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return credit;
	}
		
	//VO리스트 검색 및 총 개수 검색 메소드들.
		//페이지 글목록 번호와 분류로 post목록 검색하는 메소드.
		//오류시 빈 LinkedList<Post>를 반환한다. 
	public static LinkedList<Post> selectPostList(int index, PostColumn condition, String value) {
		LinkedList<Post> postList = new LinkedList<Post>();
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		
		if(condition!=PostColumn.none) {//검색할 조건이 있는경우.
			query="select num,sort1,sort2,title,writerId,writeTime,viewCount,commentCount,saleComplete "
					+"from post where "+condition.getDbName()+"='"+value+"' order by writeTime DESC limit "+((index-1)*20)+",20";
		}//index값에 따라서 1번부터 20번째까지검색, 21번부터 40번째 게시물까지 검색 과같이 해당 인덱스에 맞는 게시물들을 검색하게 설정. 
		else if(condition==PostColumn.none){//아무 검색 조건이 없는 경우.
			query="select num,sort1,sort2,title,writerId,writeTime,viewCount,commentCount,saleComplete "
					+"from post order by writeTime DESC limit "+((index-1)*20)+",20";
		}
		else {//오류. condition값이 아무것도 매칭되지 않았을경우.
			System.out.println("in Dao.selectPostList(), there is no matched condition");
			return postList;
		}
		
		//post select부분.
		try {
			st=connection.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				Post post = new Post();
				post.setNum(rs.getInt("num"));
				post.setSort1(rs.getString("sort1"));
				post.setSort2(rs.getString("sort2"));
				post.setTitle(rs.getString("title"));
				post.setWriterId(rs.getString("writerId"));
				post.setWriteTime(rs.getString("writeTime"));
				post.setViewCount(rs.getInt("viewCount"));
				post.setCommentCount(rs.getInt("commentCount"));
				post.setSaleComplete(rs.getBoolean("saleComplete"));
				
				postList.add(post);//post목록에 post저장. 
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return postList;
	}
		//LIKE를 사용하여 게시물 리시트를 검색하는 메소드. 
		//넘어온 conditions의 String들이 검색 대상인 column, patterns의 String들이 검색할 패턴. 각각의 검색대상column들에 대해 모든 검색할 패턴을
		//검색하여 하나라도 일치하면 반환해준다. 오류시 빈 LinkedList<Post>를 반환한다. 
	public static LinkedList<Post> selectPostList(int index, PostColumn[] conditions, String[] patterns){
		LinkedList<Post> postList = new LinkedList<Post>();
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		String subQuery="";
		
		//모든 검색 대상인 column들에 대해 검색하려는 모든 패턴을 검색하도록 조건 쿼리문을 만들어주는 부분. 
		for(int i=0; i<conditions.length; i++) {
			for(int j=0; j<patterns.length; j++) {
				subQuery+=" or "+conditions[i].getDbName()+" like '%"+patterns[j]+"%'";
			}
		}
		subQuery=subQuery.replaceFirst("or", " ");//조건 쿼리문에서 맨앞에 or 문자열을 빼준다. 

		//최종적인 쿼리문 생성. 
		query = "select num,sort1,sort2,title,writerId,writeTime,viewCount,commentCount,saleComplete "
				+"from post where "+subQuery+" order by writeTime DESC limit "+((index-1)*20)+",20";
		
		//post select부분.
		try {
			st=connection.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				Post post = new Post();
				post.setNum(rs.getInt("num"));
				post.setSort1(rs.getString("sort1"));
				post.setSort2(rs.getString("sort2"));
				post.setTitle(rs.getString("title"));
				post.setWriterId(rs.getString("writerId"));
				post.setWriteTime(rs.getString("writeTime"));
				post.setViewCount(rs.getInt("viewCount"));
				post.setCommentCount(rs.getInt("commentCount"));
				post.setSaleComplete(rs.getBoolean("saleComplete"));
				
				postList.add(post);//post목록에 post저장. 
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return postList;
	}
		//'댓글' 리스트 검색하는 메소드
	public static LinkedList<Comment> selectParentComment(int postNum){
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		LinkedList<Comment> commentList = new LinkedList<Comment>();
		
		try {
			query="select * from comment where parrentComment='-1' and postNum='"+postNum+"' order by time asc";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setNum(rs.getInt("num"));
				comment.setPostNum(rs.getInt("postNum"));
				comment.setWriterId(rs.getString("writerId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("time"));
				comment.setChild(rs.getBoolean("child"));
				comment.setParrentComment(rs.getInt("parrentComment"));
				
				commentList.add(comment);
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
		//'답글' 리스트 검색 메소드
	public static LinkedList<Comment> selectChildComment(int postNum, int parrentComment){
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		LinkedList<Comment> commentList = new LinkedList<Comment>();
		
		try {
			query="select * from comment where postNum='"+postNum+"' and parrentComment='"+parrentComment+"' order by time DESC";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setNum(rs.getInt("num"));
				comment.setPostNum(rs.getInt("postNum"));
				comment.setWriterId(rs.getString("writerId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("time"));
				comment.setChild(rs.getBoolean("child"));
				comment.setParrentComment(rs.getInt("parrentComment"));
				
				commentList.add(comment);
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
		//인자로 넘어온 조건에 맞는 Credit 리스트 검색해주는 메소드
		//특정 회원의 신고 내역 리스트를 가져오는 기능을 위한 메소드. 리스트를 뿌려주기만 하면 되므로 num, accountId, time컬럼만 리스트에 저장한다. 
	public static LinkedList<Credit> selectCreditList(CreditColumn condition, String value){
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		LinkedList<Credit> list=new LinkedList<Credit>();//select결과를 저장할 리스트.
		
		try {
			query="select * from credit where "+condition.getDbName()+"='"+value+"'";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()){//rx.next()를 이미한번해서 do~while문으로.
				Credit credit=new Credit();
				credit.setNum(rs.getInt("num"));
				credit.setAccountId(rs.getString("accountId"));
				credit.setTime(rs.getString("time"));
				credit.setPostNum(rs.getInt("postNum"));
				credit.setReportAccountId(rs.getString("reportAccountId"));
				
				list.add(credit);
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
		//message 리스트 가져오는 메소드
		//
	public static LinkedList<Message> selectMessageList(String accountId){
		Connection connection=config();
		Statement st;
		ResultSet rs;
		String query=null;
		LinkedList<Message> list = new LinkedList<Message>();
		
		try {
			query="select * from message where sender='"+accountId+"' or receiver='"+accountId+"' order by time desc";
			st= connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				Message message = new Message();
				message.setNum(rs.getInt("num"));
				message.setSender(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setContent(rs.getString("content"));
				message.setTime(rs.getString("time"));
				message.setReadReceipt(rs.getBoolean("readReceipt"));
				
				list.add(message);
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
		//
	public static LinkedList<Account> selectAccountList(AccountColumn column, String value){
		LinkedList<Account> accountList = new LinkedList<Account>();
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		
		if(column==AccountColumn.none) {query="select * from account order by id asc";}
		else {query="select * from account where "+column.getDbName()+"='"+value+"' order by id asc";}
		
		try {
			st=connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				Account account = new Account();
				account.setNum(rs.getInt("num"));
				account.setId(rs.getString("id"));
				account.setPw(rs.getString("pw"));
				account.setEmail(rs.getString("email"));
				account.setpNum(rs.getString("pNum"));
				account.setAddress(rs.getString("address"));
				account.setGrade(rs.getInt("grade"));
				account.setCredit(rs.getInt("credit"));
				account.setName(rs.getString("name"));
				
				accountList.add(account);
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return accountList;
	}
	
		//전체 post개수 구하는 메소드
	public static int getTotalPostNum(PostColumn condition, String value) {
		int totalPostNum=-1;
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		
		
		if(condition!=PostColumn.none) {//작성자로 검색
			query="select count(*) from post where "+condition.getDbName()+"='"+value+"'";
		}
		else if(condition==PostColumn.none) {//분류로 검색
			query="select count(*) from post";
		}
		else {//오류. condition에 맞는 조건이 없음. 
			System.out.println("in Dao.getTotalPostNum(), there is no matched condition");
			return totalPostNum;
		}
		
		try {
			st=connection.createStatement();
			rs=st.executeQuery(query);
			rs.next();
			totalPostNum=rs.getInt(1);
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return totalPostNum;
	}
		//특정 문자 검색의 경우 해당하는 전체 post개수 구하는 메소드.
	public static int getTotalPostNum(PostColumn[] conditions, String[] patterns) {
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		String subQuery="";
		int totalPostNum=-1;
		
		//모든 검색 대상인 column들에 대해 검색하려는 모든 패턴을 검색하도록 조건 쿼리문을 만들어주는 부분. 
		for(int i=0; i<conditions.length; i++) {
			for(int j=0; j<patterns.length; j++) {
				subQuery+=" or "+conditions[i].getDbName()+" like '%"+patterns[j]+"%'";
			}
		}
		subQuery=subQuery.replaceFirst("or", " ");//조건 쿼리문에서 맨앞에 or 문자열을 빼준다. 

		query = "select count(*) from post where "+subQuery;
		
		try {
			st=connection.createStatement();
			rs=st.executeQuery(query);
			rs.next();
			totalPostNum=rs.getInt(1);
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return totalPostNum;
	}
		//전체 Credit 개수 구하는 메소드
		//인자로 받은 num에 해당하는 회원의 전체 credit개수를 반환해준다.
	public static int getTotalCreditNum(int accountNum) {
		Connection connection = config();
		Statement st;
		ResultSet rs;
		String query=null;
		int totalCreditNum=-1;
		
		try {
			query="select count(*) from credit where accountNum='"+accountNum+"'";
			st=connection.createStatement();
			rs=st.executeQuery(query);
			totalCreditNum=rs.getInt(1);
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return totalCreditNum;
	}
	
	//insert 메소드들. VO를 통째로 받아 저장시켜주며, 성공시 true 실패시 false반환.
	public static boolean insertVO(Account account) {
		Connection connection = config();
		PreparedStatement ps =null;
		String query =null;
		
		try {
			query = "insert into account(id,pw,email,pNum,address,grade,name) values(?,?,?,?,?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setString(1,account.getId());
			ps.setString(2, account.getPw());
			ps.setString(3, account.getEmail());
			ps.setString(4, account.getpNum());
			ps.setString(5, account.getAddress());
			ps.setInt(6, account.getGrade());
			ps.setString(7, account.getName());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertVO(Post post) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="insert into post(title,sort1,sort2,content,mainImage, writerId, writerNum, price,saleComplete,open)"
					+"values(?,?,?,?,?,?,?,?,?,?)";
			ps=connection.prepareStatement(query);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getSort1());
			ps.setString(3, post.getSort2());
			ps.setString(4, post.getContent());
			ps.setString(5, post.getMainImage());
			ps.setString(6, post.getWriterId());
			ps.setInt(7, post.getWriterNum());
			ps.setInt(8, post.getPrice());
			ps.setBoolean(9, post.getSaleComplete());
			ps.setBoolean(10, post.getOpen());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean insertVO(Credit credit) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="insert into credit(accountId, accountNum, content, reportAccountNum, postNum, reportAccountId) values(?,?,?,?,?,?)";
			ps=connection.prepareStatement(query);
			ps.setString(1, credit.getAccountId());
			ps.setInt(2, credit.getAccountNum());
			ps.setString(3, credit.getContent());
			ps.setInt(4, credit.getReportAccountNum());
			ps.setInt(5, credit.getPostNum());
			ps.setString(6, credit.getReportAccountId());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertVO(Comment comment) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="insert into comment(postNum, writerId, content, parrentComment) values(?,?,?,?)";
			ps=connection.prepareStatement(query);
			ps.setInt(1, comment.getPostNum());
			ps.setString(2, comment.getWriterId());
			ps.setString(3, comment.getContent());
			ps.setInt(4, comment.getParrentComment());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertVO(Message message) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="insert into message(sender,receiver,content) values(?,?,?)";
			ps=connection.prepareStatement(query);
			ps.setString(1, message.getSender());
			ps.setString(2, message.getReceiver());
			ps.setString(3,message.getContent());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//update메소드들.
		//데이터 수정메소드. VO객체를 받아 통째로 update, 또는 특정 컬럼만 update. 실패시 false반환.
	public static boolean updateVO(Tables table, Object data) {
		int num;
		Connection connection=config();
		PreparedStatement ps;
		String query=null;
		
		if(table==Tables.account) {//변경하려는 데이터가 account table의 데이터이면,
			Account account=(Account)data;
			num=account.getNum();
			
			try {
				query="update account set pw=?, email=?, pNum=?, address=?, grade=?, credit=? where num = '"+account.getNum()+"'";  
				ps=connection.prepareStatement(query);
				ps.setString(1, account.getPw());
				ps.setString(2, account.getEmail());
				ps.setString(3, account.getpNum());
				ps.setString(4, account.getAddress());
				ps.setInt(5, account.getGrade());
				ps.setInt(6,account.getCredit());
				
				ps.executeUpdate();
				
				ps.close();
				connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(table==Tables.post) {//변경하려는 데이터가 '게시글' table인 경우 (아직 게시글 부분 구현안함)
			Post post = (Post)data;
			num=post.getNum();
			
			try {
				query="update post set title=?,sort1=?,sort2=?,content=?,mainImage=?,price=?,saleComplete=?,open=? "
						+"where num='"+num+"'";
				ps=connection.prepareStatement(query);
				ps.setString(1, post.getTitle());
				ps.setString(2, post.getSort1());
				ps.setString(3, post.getSort2());
				ps.setString(4, post.getContent());
				ps.setString(5, post.getMainImage());
				ps.setInt(8, post.getPrice());
				ps.setBoolean(9, post.getSaleComplete());
				ps.setBoolean(10, post.getOpen());
				ps.executeUpdate();
				
				ps.close();
				connection.close();
				return true;
			}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(table==Tables.comment) {//변경하려는 데이터가 '댓글'?table인 경우 (아직 댓글 부분 구현안함)
			
		}
		else if(table==Tables.credit) {
			
		}
		return true;
	}
	public static boolean updateVO(CommentColumn column, String value, int commentNum) {
		Connection connection  = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="update "+column.getDbName()+" set content=? where num='"+commentNum+"'";
			ps=connection.prepareStatement(query);
			ps.setString(1,value);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean updateVO(PostColumn column, String value, int postNum) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="update post set "+column.getDbName()+"="+value+" where num='"+postNum+"'";
			ps=connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		//VO의 특정 컬럼을 1만큼 증감시켜주는 메소드.
	public static boolean increaseAndDecreaseVO(AccountColumn column, int count, int accountNum) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="update account set "+column.getDbName()+"="+column.getDbName()+"+("+count+") where num='"+accountNum+"'";
			ps=connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean increaseAndDecreaseVO(PostColumn column, int count, int postNum) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="update post set "+column.getDbName()+"="+column.getDbName()+"+("+count+") where num='"+postNum+"'";
			ps=connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//delete 메소드.
	public static boolean deleteVO(Tables table, int num) {
		Connection connection = config();
		PreparedStatement ps;
		String query=null;
		
		try {
			query="delete from "+table.getDbName()+" where num=?";
			ps=connection.prepareStatement(query);
			ps.setInt(1, num);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
}
