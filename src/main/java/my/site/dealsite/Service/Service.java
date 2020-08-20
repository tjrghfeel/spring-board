package my.site.dealsite.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.springframework.validation.Errors;

import my.site.dealsite.DAO.*;
import my.site.dealsite.VO.*;
import my.site.dealsite.Enum.*;

//컨트롤러 메소드와 굳이 나누는 이유가 모호하긴한데, 주된 로직을 이 클래스를 통해 처리하자는 목적. 컨트롤러메소드에도 약간의 로직이 들어가긴함. 
public class Service {

	//단순 VO select 메소드.
	public static Account getVO(AccountColumn column, String value) {
		Account account = Dao.selectVO(column, value);
		if(account==null) {
			System.out.println("in Service.getAccount(), Dao.selectVO() return null");
		}
		return account;
	}
	public static Account getVO(AccountColumn column1, AccountColumn column2, String value1, String value2) {
		Account account = Dao.selectVO(column1,column2,value1,value2);
		if(account==null) {
			System.out.println("in Service.getAccount(), Dao.selectVO() return null");
		}
		return account;
	}
	public static Post getVO(PostColumn column, String value) {
		Post post = Dao.selectVO(column, value);
		if(post==null) {
			System.out.println("in Service.getPost(), Dao.selectPost() return null");
		}
		return post;
	}
	public static Comment getVO(CommentColumn column, String value) {
		Comment comment = Dao.selectVO(column, value);
		if(comment==null) {
			System.out.println("in Service.getComment(), Dao.selectComment() return null");
		}
		return comment;
	}
	public static Credit getVO(CreditColumn column, String value) {
		Credit credit = Dao.selectVO(column, value);
		if(credit==null) {
			System.out.println("in Service.getCredit(), Dao.selectCredit() return null");
		}
		return credit;
	}

	//단순 VO추가 메소드.
	public static boolean insertVO(Account account) {
		boolean result  = Dao.insertVO(account);
		if(!result) {System.out.println("in Service.signUp(), Dao.createVO() return false");}
		return result;
	}
	public static boolean insertVO(Post post) {
		boolean result  = Dao.insertVO(post);
		if(!result) {System.out.println("in Service.signUp(), Dao.createVO() return false");}
		return result;
	}
	public static boolean insertVO(Credit credit) {
		boolean result  = Dao.insertVO(credit);
		if(!result) {System.out.println("in Service.signUp(), Dao.createVO() return false");}
		return result;
	}
	public static boolean insertVO(Comment comment) {
		boolean result  = Dao.insertVO(comment);
		if(!result) {System.out.println("in Service.signUp(), Dao.createVO() return false");}
		return result;
	}
	public static boolean insertVO(Message message) {
		boolean result  = Dao.insertVO(message);
		if(!result) {System.out.println("in Service.signUp(), Dao.createVO() return false");}
		return result;
	}
	
	//VO삭제 메소드.
	public static boolean deleteVO(Tables table, int num) {
		boolean result = Dao.deleteVO(table,num);
		
		if(result==false) {
			System.out.println("in Service.deleteVO(), Dao.deleteVO() return false");
		}
		return result;
	}

	//MyPage이동 전에 준비시켜주는 메소드.
	//보류.
	
	//VO데이터 변경 메소드.
	public static boolean modifyVO(Tables table, Object data) {
		boolean result=Dao.updateVO(table, data);
		if(result==false) {
			System.out.println("in Service.modifyData(), Dao.updateVO() return false.");
		}
		return result;
	}
	public static boolean modifyVO(CommentColumn column, String value, int commentNum) {
		boolean result=Dao.updateVO(column, value, commentNum);
		if(result==false) {
			System.out.println("in Service.updateVO(), Dao.updateVO() return false");
		}
		return result;
	}
	public static boolean modifyVO(PostColumn column, String value, int postNum) {
		boolean result=Dao.updateVO(column, value, postNum);
		if(result==false) {
			System.out.println("in Service.updateVO(), Dao.updateVO() return false");
		}
		return result;
	}

	//mainImage파일 저장 메소드. 
	//넘어온 파일을 이름 중복 처리를 해서 저장해주고 중복처리된 바뀐 이름을 반환해준다. 
	public static String saveMainImage(InputStream in, String fileName, String writerId) {
		String time = Long.toString(System.currentTimeMillis());//이름중복방지를 위해 파일이름에 붙일 시간값. 
		String newFileName = writerId+time+fileName;//원래의 파일이름 앞에 글쓴이 id와 시간을 붙여서 새이름을 만듦. 
		
		//파일 저장부분. 
		try {
			//FileOutputStream out = new FileOutputStream("C:\\Apache\\Apache24\\htdocs"+File.separator+newFileName);
														//이미지파일은 아파치 서버에 저장할 것임. 
			FileOutputStream out = new FileOutputStream("/var/www/html"+File.separator+newFileName);
			byte[] buffer = new byte[1024];
			int len;
			
			while((len=in.read(buffer))!=-1) {//파일저장.
				out.write(buffer,0,len);
			}
			
			in.close();
			out.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return newFileName;//새로만든 이름을 반환해줌.
	}

	//페이지 글목록 번호와 분류를 인자로 받아서 이에 해당하는 post목록 20개를 반환해주는 메소드. 
	public static LinkedList<Post> showMainBoard(int index, String inputCondition, String value) {
		LinkedList<Post> postList = new LinkedList<Post>();
		
		//제목+내용에 대해 특정 내용포함 검색을 하는 경우.
		if(inputCondition.equals("titleAndContent")) {
			StringTokenizer tokenizer = new StringTokenizer(value," ");
			PostColumn[] conditions = {PostColumn.title,PostColumn.content};
			if(tokenizer.countTokens()==0) {
				String[] patterns = {""};//검색 문자열이 비어있는경우 빈문자열을 검색패턴으로 넘겨 그냥 모든 게시물이 검색되도록.
				postList = Dao.selectPostList(index, conditions, patterns);
			}
			else {
				String[] patterns = new String[tokenizer.countTokens()];
				for(int i=0; tokenizer.hasMoreTokens(); i++) {
					/*!!!토큰 검증 필요. 
					 * 특수문자 검증.
					 * 
					 */
					patterns[i] = tokenizer.nextToken();
					System.out.println("in Service.getTotalPostNum(), search token : "+patterns[i]);
				}
				postList = Dao.selectPostList(index, conditions, patterns);
			}
		}
		else if(inputCondition.equals("title")) {
			StringTokenizer tokenizer = new StringTokenizer(value," ");
			PostColumn[] conditions = {PostColumn.title};
			if(tokenizer.countTokens()==0) {
				String[] patterns = {""};//검색 문자열이 비어있는경우 빈문자열을 검색패턴으로 넘겨 그냥 모든 게시물이 검색되도록.
				postList = Dao.selectPostList(index, conditions, patterns);
			}
			else {
				String[] patterns = new String[tokenizer.countTokens()];
				for(int i=0; tokenizer.hasMoreTokens(); i++) {
					/*!!!토큰 검증 필요. 
					 * 특수문자 검증.
					 * 
					 */
					patterns[i] = tokenizer.nextToken();
					System.out.println("in Service.getTotalPostNum(), search token : "+patterns[i]);
				}
				postList = Dao.selectPostList(index, conditions, patterns);
			}
		}
		else {//그 외에 게시물 분류, 작성자 등으로 검색을 하는 경우. 
			PostColumn condition = PostColumn.valueOf(inputCondition);
			postList=Dao.selectPostList(index, condition, value);
		}
		
		return postList;
	}
	
	//인자로 넘어온 조건에 맞는 post의 총 개수 구하는 메소드. 
	//인자로 넘긴 조건에 맞는 post의 총 개수를 구해주는 메소드인 Dao.getTotalPostNum()을 내부적으로 호출한다. 
	//inputCondition : 검색할 조건을 나타내는 문자열 / value : 조건의 값.
	public static int getTotalPostNum(String inputCondition, String value) {
		int totalPostNum=-1;
		
		//inputCondition에 따라 다르게 Dao.getTotalPostNum()을 사용해준다. 
		//제목+내용에 대해 특정 내용포함 검색을 하는 경우.
		if(inputCondition.equals("titleAndContent")) {
			PostColumn[] conditions = {PostColumn.title,PostColumn.content};
			StringTokenizer tokenizer = new StringTokenizer(value," ");//공백문자 " "을 기준으로 문자열 토큰분리.
			if(tokenizer.countTokens()==0) {//토큰이 존재하지 않는경우.
				String[] patterns = {""};//검색 문자열이 비어있는경우 빈문자열을 검색패턴으로 넘겨 그냥 모든 게시물이 검색되도록.
				totalPostNum = Dao.getTotalPostNum(conditions, patterns);//문자열 패턴으로 검색하는 Dao.getTotalPostNum()호출.
			}
			else {//토큰이 존재하는 경우.
				String[] patterns = new String[tokenizer.countTokens()];
				for(int i=0; tokenizer.hasMoreTokens(); i++) {
					/*!!!토큰 검증 필요. 
					 * 특수문자 검증.
					 * 
					 */
					patterns[i] = tokenizer.nextToken();//토큰들을 배열에 넣어줌.
					System.out.println("in Service.getTotalPostNum(), search token : "+patterns[i]);
				}
				totalPostNum = Dao.getTotalPostNum(conditions, patterns);
			}
		}
		//제목만으로 검색하는 경우.
		else if(inputCondition.equals("title")) {
			PostColumn[] conditions = {PostColumn.title};
			StringTokenizer tokenizer = new StringTokenizer(value," ");
			
			if(tokenizer.countTokens()==0) {
				String[] patterns = {""};//검색 문자열이 비어있는경우 빈문자열을 검색패턴으로 넘겨 그냥 모든 게시물이 검색되도록.
				totalPostNum = Dao.getTotalPostNum(conditions, patterns);
			}
			else {
				String[] patterns = new String[tokenizer.countTokens()];
				for(int i=0; tokenizer.hasMoreTokens(); i++) {
					/*!!!토큰 검증 필요. 
					 * 특수문자 검증.
					 * 
					 */
					patterns[i] = tokenizer.nextToken();
					System.out.println("in Service.getTotalPostNum(), search token : "+patterns[i]);
				}
				totalPostNum = Dao.getTotalPostNum(conditions, patterns);
			}
		}
		//그 외에 기준으로 검색하는 경우.(작성자 등으로 검색을 하는 경우) 
		else {
			PostColumn condition = PostColumn.valueOf(inputCondition);//inputCondition의 값과 같은 이름을 가진 enum PostColumn의 인스턴스를 얻음.
			totalPostNum=Dao.getTotalPostNum(condition, value);
		}
		
		return totalPostNum;
	}
	
	//mainBoard.jsp에서 글목록index값을 계산해주는 메소드.
	//내부적으로 Service.getTotalPostNum()을 호출하여 넘어온 조건값에 해당하는 총 post수를 구하고, 이 총 개수와 inputIndex를 계산하여
	//int indexs[3]을 반환한다. 배열안에 순서대로 startIndex, lastIndex, currentIndex.
	public static int[] getIndexes(int inputIndex, String condition, String value) {
		int[] indexes= new int[3];//순서대로 startIndex, lastIndex, currentIndex를 담아서 반환할 배열.
		int totalPostNum;
		int maxIndex;//totalPostNum에 따라 가능한 가장 큰 index 번호. 
		int currentIndex=-1;//현재 index
		int lastIndex=-1;//현재 페이지 상에 보여질 index의 끝번호. maxIndex를 넘지 않는다면 10,20같이 10단위로 끝난다. 
		int startIndex=-1;//현재 페이지 상에 보여질 index의 시작번호. 11,31 등등. 
		
		totalPostNum = Service.getTotalPostNum(condition, value);//
		if(totalPostNum==-1) {//db오류인 경우.
			
		}
		else if(totalPostNum==0) {//검색된 게시물이 없는 경우
			currentIndex=1;
			startIndex=1;
			lastIndex=1;//????
		}
		else {
			maxIndex = (totalPostNum%20==0)? totalPostNum/20 : (totalPostNum/20)+1;
			
			if(inputIndex>maxIndex) {currentIndex=maxIndex;	}//'다음'버튼을 눌러서 inputIndex가 최대index를 넘은 경우를 처리.
			else if(inputIndex<1) {currentIndex=1;}//'이전'버튼을 눌러서 inputIndex가 음수가 되는 경우를 처리.
			else { currentIndex=inputIndex;}
			startIndex=(currentIndex%10==0)? (currentIndex-9) : (((currentIndex/10)*10)+1);//현재index가 10으로 나누어떨어지면 -9한게 index시작번호,아니면 계산. 
			lastIndex=( maxIndex > (startIndex+9) )? (startIndex+9):(maxIndex);
			
			
		}
		indexes[0]=startIndex;
		indexes[1]=lastIndex;
		indexes[2]=currentIndex;
		
		return indexes;
	}
	
	//인자로넘어온 조건과 그 값에 맞는 Credit을 검색하는 Dao메소드를 호출하는 메소드. 
	public static LinkedList<Credit> getCreditList(CreditColumn condition, String value){
		LinkedList<Credit> result = Dao.selectCreditList(condition, value);
		if(result==null) {
			System.out.println("in Service.getCreditList(), Dao.selectCreditList() return null");
		}
		return result;
	}

	//댓글 목록 가져와주는 메소드.
	//중첩? linkedList를 사용하여 댓글들을 구조화할 것이다. 
	/* list1 -> 첫번째댓글 -> 첫번째 댓글의 답글 -> 첫번째댓글의 그다음 답글 -> ...
	 * list2 -> 두번째 댓글 -> 두번째 댓글의 답글 ->...
	 * list3 -> 세번째 댓글 (답글없음)
	 * ...
	 * 위와같은 구조로 list1,2,3,...들을 또하나의 리스트에 순서대로 넣어서 반환.
	 */
	public static LinkedList<LinkedList<Comment>> getCommentList(int postNum){
		//childComment값이 false인 댓글들을 일단 모두 리스트의 첫번째에 저장하고, 그 각각의 '덧글'들을 검색하여 뒤에 쭉 add해주면된다. 
		LinkedList<LinkedList<Comment>> commentList=new LinkedList<LinkedList<Comment>>();
		LinkedList<Comment> parrentCommentList;//부모 댓글들을 저장할 리스트.
		LinkedList<Comment> childCommentList;//자식 댓글들을 저장할 리스트.
		
		parrentCommentList=Dao.selectParentComment(postNum);//부모댓글들 가져옴.
		//댓글들을 위 내용대로 구조화함. 
		Iterator<Comment> iterator = parrentCommentList.iterator();
		while(iterator.hasNext()) {
			LinkedList<Comment> list = new LinkedList<Comment>();//n번째 댓글->n번째 댓글의 덧글1->덧글2->... 형식으로구조화할 리스트 하나.
			Comment parrentComment = iterator.next();
			list.add(parrentComment);//일단 댓글1을 먼저 넣어준다. 
			//아래는 댓글1다음에 덧글1,2,...들을 순서대로 넣어주는 과정.
			int parrentCommentNum = parrentComment.getNum();
			childCommentList = Dao.selectChildComment(postNum, parrentCommentNum);
			Iterator<Comment> childIterator=childCommentList.iterator();
			while(childIterator.hasNext()) {
				list.add(childIterator.next());
			}
			
			commentList.add(list);//n번째 댓글->n번째 댓글의 덧글1->덧글2->... 형식으로 만든다음 이를 반환할 리스트에 추가. 
		}
		//모든 반복이 끝나면 댓글들 구성완료.
		return commentList;
	}

	//Comment 저장 메소드.
	public static boolean writeComment(int postNum, String content, int parrentNum, String writerId) {
		boolean result;
		Comment comment = new Comment();
		comment.setPostNum(postNum);
		comment.setContent(content);
		comment.setParrentComment(parrentNum);
		comment.setWriterId(writerId);
		
		result= Dao.insertVO(comment);
		
		if(result==false) {
			System.out.println("in Service.writeComment(), Dao.createComment() return false");
		}
		return result;
	}

	//message 리스트 가져오는 메소드.
	/*Dao로부터 받아온 Message리스트를 '순서대로'ArrayList에 저장해 반환한다.
	 * '순서대로'란, ArrayList의 요소하나가 한명의 상대방과의 대화창을 의미하며 그 안에 LinkedList<Message>는
	 * 그 상대방과의 대화내역을 시간순으로 저장한 것을 말한다. 
	 */
	public static ArrayList<LinkedList<Message>> getMessageList(String loginedId){
		ArrayList<LinkedList<Message>> resultMessageList=new ArrayList<LinkedList<Message>>();//최종반환할 Message VO들이 '순서대로'저장된 리스트.
		LinkedList<Message> wholeMessageList = Dao.selectMessageList(loginedId);//db로부터 받아온 Message리스트. 
														//인자 loginedId가 수신자이거나 송신자인 Message객체를 time DESC순으로 저장되어있다.
		Message tempMessage;
		String partnerId;
		ArrayList<String> partnerIdList=new ArrayList<String>();//로직의 편의를 위해만든 리스트. 
							//reulstMessageLIst와 1:1대응해서 각 인덱스위치에 어떤 id를가진 상대방과 대화내역이 저장되어있는지를 나타낸다. 
		
		loop:while(!wholeMessageList.isEmpty()){//Dao에서 가져온 리스트가 빌때까지 반복.
			tempMessage=wholeMessageList.poll();//Dao에서 가져온 리스트에서 맨 앞의 것을 꺼냄.
			
			//꺼낸 Message VO안에 sender와 receiver중에 하나가 상대방 Id일 것이므로 그걸 partnerId에 넣어줌.
			if(tempMessage.getSender().equals(loginedId)) {partnerId=tempMessage.getReceiver();}
			else {partnerId=tempMessage.getSender();}
			
			//꺼낸 Message VO를 resultMessageList에서 대화상대가 같은 리스트에 넣어주는 과정. 
			for(int i=0; i<partnerIdList.size(); i++) {
				if(partnerIdList.get(i).equals(partnerId)) {
					resultMessageList.get(i).add(tempMessage);
					continue loop;//Message Vo를 resultMessageList에 넣어주면 다음반복으로.
				}
			}
			
			//resultMessageList에 대화상대가 같은 리스트가 없을경우, 새로운 리스트를 만들어준다.
			LinkedList<Message> newList = new LinkedList<Message>();
			newList.add(tempMessage);
			resultMessageList.add(newList);
			
			partnerIdList.add(partnerId);//
		}
		
		return resultMessageList;
	}

	//
	public static LinkedList<Account> getAccountList(AccountColumn column, String value){
		LinkedList<Account> accountList = new LinkedList<Account>();
		
		accountList = Dao.selectAccountList(column, value);
		
		return accountList;
	}

	public static boolean increaseAndDecreaseVO(PostColumn column, int count, int postNum) {
		return Dao.increaseAndDecreaseVO(column, count, postNum);
	}
	public static boolean increaseAndDecreaseVO(AccountColumn column, int count, int postNum) {
		return Dao.increaseAndDecreaseVO(column, count, postNum);
	}
	

}
