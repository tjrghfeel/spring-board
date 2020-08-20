package my.site.dealsite.Service;

import java.util.StringTokenizer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import my.site.dealsite.VO.*;
import my.site.dealsite.DAO.*;
import my.site.dealsite.Enum.AccountColumn;

public class AccountSignUpValidator implements Validator{
	public boolean supports(Class clazz) {
		System.out.println("in supports()");
		return Account.class.isAssignableFrom(clazz);
	}
	public void validate(Object target, Errors errors) {
		Account account = (Account)target;
		
		try {
			//아이디 검증.
			if(account.getId().length()==0) {
				errors.rejectValue("id", "empty");
			}
			else if(account.getId().length()<8) {//id는 최소 8자. 
				errors.rejectValue("id", "shortValue");
			}
			else if(account.getId().getBytes("utf8").length>40) {//id의 크기가 db컬럼의 크기를 초과할 경우. 
				errors.rejectValue("id","excessValue");
			}
			else if(!(account.getId().matches("^[0-9a-zA-Z]*$"))) {//영문자와 숫자로만 이루어지지 않았을 경우. 
				errors.rejectValue("id","incorrectForm");
			}
			else if(Dao.selectVO(AccountColumn.id,account.getId())!=null) {//아이디 중복이 있으면
				errors.rejectValue("id","duplicated");
			}
			
			//pw검증.
			if(account.getPw().length()==0) {
				errors.rejectValue("pw", "empty");
			}
			else if(account.getPw().length()<8) {
				errors.rejectValue("pw", "shortValue");
			}
			else if(account.getPw().getBytes("utf8").length>40) {//pw가 db컬럼의 크기를 초과한 경우
				errors.rejectValue("pw", "excessValue");
			}
			else if(!(account.getPw().matches("^[0-9a-zA-Z]*$"))){
				errors.rejectValue("pw","incorrectForm");
			}
			
			//email검증.
			StringTokenizer emailTokenizer = new StringTokenizer(account.getEmail(),"@");
			if(account.getEmail().length()==0) {
				errors.rejectValue("email", "empty");
			}
			else if(account.getEmail().getBytes("utf8").length>40) {//db의 email컬럼 크기 초과한 경우. 
				errors.rejectValue("email", "excessValue");
			}
			else if(emailTokenizer.countTokens()!=2) {//이메일 형식이 아니면
				errors.rejectValue("email", "incorrectForm");
			}
			else if(Dao.selectVO(AccountColumn.email, account.getEmail())!=null) {//이메일 중복된 경우.
				errors.rejectValue("email", "duplicated");
			}
			
			//휴대폰 번호 검증.
			StringTokenizer pNumTokenizer = new StringTokenizer(account.getpNum(),"-");
			if(account.getpNum().length()==0) {
				errors.rejectValue("pNum", "empty");
			}
			else if(pNumTokenizer.countTokens()!=3) {//000-000-000형식처럼 -을 기준으로 세부분으로 나뉘지 않았으면 오류저장.
				errors.rejectValue("pNum", "incorrectForm");
			}
			else {//000-0000-0000 와 같이 번호의 개수가 맞지 않으면 오류저장. 
				String[] tokens = new String[3];
				tokens[0]=pNumTokenizer.nextToken();
				tokens[1]=pNumTokenizer.nextToken();
				tokens[2]=pNumTokenizer.nextToken();
				if(tokens[0].length()!=3 || tokens[1].length()!=4 || tokens[2].length()!=4) {
					errors.rejectValue("pNum", "incorrectForm");
				}
			}
			if(Dao.selectVO(AccountColumn.pNum, account.getpNum())!=null) {
				errors.rejectValue("pNum", "duplicated");
			}
			
			//address검증
			if(account.getAddress().length()==0) {
				errors.rejectValue("address", "empty");
			}
			else if(Dao.selectVO(AccountColumn.address, account.getAddress())!=null) {
				errors.rejectValue("address", "duplicated");
			}
			
			//name검증
			if(account.getName().length()==0) {
				errors.rejectValue("name", "empty");
			}
			else if(account.getName().length()>10) {
				errors.rejectValue("name", "excessValue");
			}
			else if(!(account.getName().matches("^[0-9a-zA-Z가-힣]*$"))){
				errors.rejectValue("name", "incorrectForm");
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
