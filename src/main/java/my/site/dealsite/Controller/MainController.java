package my.site.dealsite.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import my.site.dealsite.Service.*;
import my.site.dealsite.VO.*;
import my.site.dealsite.Test;
import my.site.dealsite.DAO.Dao;
import my.site.dealsite.Enum.*;

@Controller
public class MainController {
	
	@Autowired
	MessageSource ms;
	@Autowired
	AccountSignUpValidator validator;
	@Autowired
	PostWriteValidator postWriteValidator;
	@Autowired
	LoginValidator loginValidator;
	
	//테스트용 컨트롤러
	@RequestMapping("noName")
	public String noName(Model model){
		String[] aaa = {"bbb","bbb2","bbb3"};
		System.out.println("in noName()");
		
		model.addAttribute("aaa",aaa);
		
		return "noName";
	}
	@RequestMapping("noName2")
	public String noName2(Model model) {
		
		
		return "noName";
	}
	
	//해당페이지로 넘어가게해주는 컨트롤러메소드. WEB-INF/view로 직접접근이 불가하기때문에 이 메소드를 통하여 이동한다. 
	@RequestMapping("showView")
	public String showView(@RequestParam("pageName") String pageName) {
		return pageName;
	}
	@RequestMapping("forward")
	public String forward(@RequestParam("url") String url) {
		return "forward:/"+url;
	}
	
	//로그인 처리 컨트롤러 메소드. 
	//아이디 존재하는지 확인 / 존재하면 아디비번 일치하는지 확인 / 결과를 알려줄 정보 저장.
	@RequestMapping("loginH")
	public String loginH(@ModelAttribute Account account, HttpSession session, Errors errors) {
		Account loginedAccount=null;
		
		loginValidator.validate(account, errors);
		
		if(errors.hasErrors()==true) {
			System.out.println("loginValidator errors");
			return "login";
		}
		loginedAccount = Service.getVO(AccountColumn.id,AccountColumn.pw,account.getId(),account.getPw());
		session.setAttribute("loginedAccount", loginedAccount);
		
		return "forward:/showMainBoard";
	}
	
	//logoutH
	@RequestMapping("logoutH")
	public String logoutH(HttpSession session) {
		session.removeAttribute("loginedAccount");
		
		return "login";
	}
	//회원가입페이지로 이동시켜주는 컨트롤러 메소드.
	@RequestMapping("readySignUpPage")
	public String readySignUpPage(Model model) {
		model.addAttribute(new Account());
		return "signUp";
	}
	
	//회원가입 컨트롤러 메소드.
	//회원가입시 입력한 회원정보를 AccountSignUpValidator로 검증하고 오류가있으면 다시 회원정보입력페이지로 돌려보냄.
	@RequestMapping("signUpH")
	public String sighUpH(@ModelAttribute Account account, Errors errors) {
		validator.validate(account, errors);
		if(errors.hasErrors()) {
			System.out.println("validation errors");
			return "signUp";
		}
		else {
			if(Service.insertVO(account)) {
				System.out.println("signUp completed");
				return "redirect:/showView?pageName=login";
			}
			else {
				System.out.println("errors in Dao");
				return "signUp";
			}
		}
	}

	//MyPage이동전에 여러가지 처리해주는 컨트롤러 메소드.
	//현재 회원이 작성한 글과 댓글정보를 보여주기위해 모델에 저장시켜준다. 
	@RequestMapping("readyMyPageH")
	public String readyMyPageH(Model model, HttpSession session) {
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		//게시글 VO, 댓글VO등등이 구현되어야 가능한 기능. 일단 보류.
		return "myPage";
	}

	//내 정보 수정 페이지 이동전에 여러가지 처리해주는 컨트롤러 메소드.
	//현재 로그인 되어있는 회원의 Account객체를 모델로 넘겨주어 회원정보 수정 form에 값이 출력되게 한다.
	@RequestMapping("readyAccountInfoModifyPageH")
	public String readyAccountInfoModifyPageH(Model model, HttpSession session) {
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		model.addAttribute(loginedAccount); 
		return "accountInfoModify";
	}
	
	//회원정보 수정 컨트롤러 메소드.
	//수정하고자 입력된 정보를 회원가입때 사용한 Validator를 이용해 똑같이 검증해준다. 오류있을시 다시페이지를 돌려보낸다. 
	/*@RequestMapping("accountInfoModifyH")
	public String accountInfoModifyH(@ModelAttribute Account account, Errors errors, HttpSession session) {
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		int accountNum=loginedAccount.getNum();
		
		//수정한 정보 검증.
		accountInfoModifyValidator.validate(account, errors);
		if(errors.hasErrors()) {
			System.out.println("info modify. validation error");
			return "accountInfoModify";
		}
		
		//정보 수정 시켜주는 부분.
		if(Service.modifyVO(Tables.account, account)) {
			return "accountInfoModify";
		}
		
		//세션에 다시 Account 저장
		loginedAccount= Service.getVO(AccountColumn.num,Integer.toString(accountNum));
		if(loginedAccount==null) {
			session.setAttribute("loginedAccount", loginedAccount);
			return "forward:/showMainBoard";
		}
		else {
			return "accountInfoModify";
		}
	}*/

	//글쓰기 페이지 가기전에 처리해주는 메소드. 
	@RequestMapping("readyWritePostH")
	public String readyWritePostH(Model model) {
		Post post = new Post();
		model.addAttribute(post);
		return "writePost";
	}

	//글쓰기 처리 메소드. 
	//입력한 글 데이터 검증과 db에 저장해주는 기능. 
	@RequestMapping("writePostH")
	public String writePostH(@ModelAttribute Post post, Errors errors, @RequestParam("mainImageFile") MultipartFile file, HttpSession session) {
		String newFileName=null;
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		post.setMainImage(file.getOriginalFilename());
		post.setWriterId(loginedAccount.getId());
		post.setWriterNum(loginedAccount.getNum());
		
		//글쓰기 입력값 검증.
		postWriteValidator.validate(post,errors);
		if(errors.hasErrors()) {//검증 오류가있으면 페이지를 돌려보냄.
			System.out.println("postWriteValidation errors");
			return "writePost";
		}

		//Service의 svaeMainImage()로 mainImage 저장 해주는 부분. 
		try {
			newFileName=Service.saveMainImage(file.getInputStream(),file.getOriginalFilename(), loginedAccount.getId());
			post.setMainImage(newFileName);//유저가 올린 파일이름을 새로만든 파일이름으로 변경해 저장. 
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//post를 db에 저장해주는 부분. 저장이 안되면 페이지를 다시 돌려보냄.
		if(!Service.insertVO(post)) {return "writePost";}
		
		return "redirect:/showPostListH";
	}

	//게시글 목록 보여주기위한 처리 메소드.
	//pageIndex(글목록번호?)와 게시물 분류에 따라 게시물을 시간순으로 20개 가져와 model에 저장해준다. 
	@RequestMapping("showMainBoard")
	public String showMainBoard() {
		return "mainBoard";
	}
	
	@RequestMapping("showPostListH")
	public String showPostListH(@RequestParam(value="pageIndex", required=false, defaultValue="1") int index, 
			@RequestParam(value="condition", required=false, defaultValue="none") String condition, 
			@RequestParam(value="value", required=false, defaultValue="-1") String value, Model model) {
		LinkedList<Post> postList;
		int[] indexes;
		
		indexes=Service.getIndexes(index,condition, value);//index[3]을 반환. 순서대로 startIndex,lastIndex,currentIndex가 들어있음.
		postList = Service.showMainBoard(indexes[2], condition, value);
		
		model.addAttribute("startIndex",indexes[0]);
		model.addAttribute("lastIndex",indexes[1]);
		model.addAttribute("currentIndex",indexes[2]);//현재 인덱스를 저장시켜줌.
		model.addAttribute("postList",postList);//검색한 해당 게시물 리스트를 저장. 
		model.addAttribute("condition",condition);
		model.addAttribute("value",value);//어떤 조건으로 post목록을 검색하는지 condition과 value값을 model에 넣어서 
											//이 조건을 유지하면서 page index 이동을 할 수 있게 한다. 
		return "postList";
	}

	//게시글 보기 페이지 이동 메소드.
	//
	@RequestMapping("showPost")
	public String showPost(@RequestParam("postNum") int postNum, Model model) {
		Post post = Service.getVO(PostColumn.num,Integer.toString(postNum));//postNum에 해당하는 post를 가져옴.
		Account writerAccount = Service.getVO(AccountColumn.num,Integer.toString(post.getWriterNum()));//가져온 post의 작성자 account정보를 가져옴.
		LinkedList<LinkedList<Comment>> commentList = Service.getCommentList(postNum);
		Service.increaseAndDecreaseVO(PostColumn.viewCount,1,post.getNum());
		
		model.addAttribute("post",post);
		model.addAttribute("writerAccount",writerAccount);
		model.addAttribute("commentList",commentList);
		
		return "post";
	}
	
	//게시물 삭제 컨트롤러 메소드
	@RequestMapping("deletePostH")
	public String deletePostH(@RequestParam("postNum") int postNum) {
		
		Service.deleteVO(Tables.post,postNum);
		
		return "redirect:/showPostListH";
	}
	
	//댓글 작성 처리 메소드.
	//
	@RequestMapping("writeCommentH")
	public String writeCommentH(@RequestParam("content") String content, @RequestParam("postNum") int postNum,
			@RequestParam(value="parrentComment", required=false, defaultValue="-1") int parrentComment,
			HttpSession session) {
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		Service.writeComment(postNum, content, parrentComment, loginedAccount.getId());
		Service.increaseAndDecreaseVO(PostColumn.commentCount,1,postNum);
		
		return "redirect:/showPost?postNum="+postNum;
	}
	
	//Credit 신고하는 페이지 이동시켜주는 메소드. 
	@RequestMapping("readyReportCreditH")
	public String readyReportCreditH(@RequestParam("postNum") String postNum, Model model, HttpSession session) {
		Credit credit = new Credit();
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		Post post=Service.getVO(PostColumn.num,postNum);//신고 대상인 게시글을 가져옴.
		
		credit.setAccountNum(post.getWriterNum());
		credit.setAccountId(post.getWriterId());
		credit.setReportAccountNum(loginedAccount.getNum());
		credit.setReportAccountId(loginedAccount.getId());
		credit.setPostNum(Integer.parseInt(postNum));
		
		model.addAttribute("credit",credit);
		
		return "reportCredit";
	}
	//신고 내용 처리해주는 메소드.
	//reportCredit.jsp에서 작성한 신고내용을 저장시켜주고 페이지를 신고한 게시물로 돌려보내준다. 
	@RequestMapping("reportCreditH")
	public String reportCreditH(@ModelAttribute Credit credit) {
		int postNum = credit.getPostNum();//다시 돌아갈 게시물 번호를 저장. 
		int accountNum = credit.getAccountNum();
		System.out.println("in reportCreditH");
		Service.insertVO(credit);//신고 내용을 저장. 
		Service.increaseAndDecreaseVO(AccountColumn.credit, 1, accountNum);
		
		return "redirect:/showPost?postNum="+postNum;
	}
	//신고내역 보여주는 페이지 이동 메소드.
	//Service.getCreditList()를 호출하여 인자로 넘어온 account num에 해당하는 credit 리스트를 반환받는다. 이를 모델에 저장하여 넘겨준다. 
	@RequestMapping("showCreditList")
	public String showCreditList(@RequestParam("accountNum") int accountNum,Model model) {
		LinkedList<Credit> list=Service.getCreditList(CreditColumn.accountNum, Integer.toString(accountNum));
		
		model.addAttribute("creditList",list);
		
		return "creditList";
	}
	//해당 신고건에 대한 세부 내용을 보여주는 처리 메소드.
	//넘겨받은 credit num으로 해당 credit내용을 db에서 가져와 model에 저장시켜주고 credit 세부내용보기페이지 creditDetail.jsp로 넘겨준다. 
	@RequestMapping("showCreditH")
	public String showCreditH(@RequestParam("creditNum") int creditNum, Model model) {
		Credit credit=Service.getVO(CreditColumn.num,Integer.toString(creditNum));
		
		model.addAttribute("credit",credit);
		
		return "creditDetail";
	}
	
	//쪽지함을 열어주는 컨트롤러 메소드.
	//
	@RequestMapping("showMessageListH")
	public String showMessageListH(Model model, HttpSession session) {
		ArrayList<LinkedList<Message>> messageList;//메세지 리스트를 순서에 맞게 저장할 리스트.
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		messageList=Service.getMessageList(loginedAccount.getId());//현재 로그인된 계정과 송수신내역이 있는 메세지들을 '순서에 맞게' 가져옴.
		
		model.addAttribute("messageList",messageList);
		
		return "messageList";
	}
	
	@RequestMapping("sendMessageH")
	public String sendMessageH(@RequestParam("sender") String sender, @RequestParam("receiver") String receiver,
								@RequestParam("content") String content) {
		Message message = new Message();
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setContent(content);
		
		Service.insertVO(message);
		
		return "redirect:/showMessageListH";
	}
	
	//
	@RequestMapping("deleteCommentH")
	public String deleteCommentH(@RequestParam("num") int commentNum, @RequestParam("postNum") int postNum,HttpSession session) {
		Comment comment = Service.getVO(CommentColumn.num, Integer.toString(commentNum));
		String writerId = comment.getWriterId();
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		if(loginedAccount.getId().equals(writerId)) {//삭제하려는 댓글의 작성자와 현재 로그인된 회원이 동일해야 삭제. 
			Service.deleteVO(Tables.comment,commentNum);
			Service.increaseAndDecreaseVO(PostColumn.commentCount,-1,postNum);
		}
		
		return "redirect:/showPost?postNum="+postNum;
	}
	
	@RequestMapping("saleCompleteH")
	public String saleCompleteH(@RequestParam("postNum") int postNum) {
		Service.modifyVO(PostColumn.saleComplete, "true", postNum);
		return "redirect:/showPost?postNum="+postNum;
	}

	/*
	//
	@RequestMapping("readyManagerPageH")
	public String readyManagerPageH(HttpSession session) {
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		if(loginedAccount.getGrade()!=1) {
			return "forward:/showMainBoard";
		}
		return "managerPage";
	}
	
	//
	@RequestMapping("showAccountListH")
	public String showAccountListH(@RequestParam(value="condition", required=false, defaultValue="none") String condition,
								@RequestParam(value="value", required=false, defaultValue="-1") String value, Model model) {
		LinkedList<Account> accountList = new LinkedList<Account>();
		AccountColumn column = AccountColumn.valueOf(condition);
		
		accountList = Service.getAccountList(column, value);
		
		model.addAttribute("accountList", accountList);
		
		return "accountList";
	}
	
	//
	@RequestMapping("deleteAccountH")
	public String deleteAccountH(@RequestParam("accountNum") int num) {
		Service.deleteVO(Tables.account, num);
		
		return "forward:/showAccountListH";
	}
	*/
	

}

