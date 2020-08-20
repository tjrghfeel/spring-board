package my.site.dealsite.Service;

import java.util.StringTokenizer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import my.site.dealsite.VO.*;
import my.site.dealsite.DAO.*;
/*
public class AccountInfoModifyValidator implements Validator{
	
	public boolean supports(Class clazz) {
		System.out.println("in supports()");
		return Account.class.isAssignableFrom(clazz);
	}
	public void validate(Object target, Errors errors) {
		Account inputAccountInfo = (Account)target;
		int accountNum=inputAccountInfo.getNum();
		Account oldAccountInfo = (Account)Dao.selectVO("account","num",Integer.toString(accountNum));
		
		//pw검증.
		if(inputAccountInfo.getPw().length()==0) {
			errors.rejectValue("pw", "empty");
		}
		else if(inputAccountInfo.getPw().length()<8) {
			errors.rejectValue("pw", "shortValue");
		}
		
		//email검증.
		StringTokenizer emailTokenizer = new StringTokenizer(inputAccountInfo.getEmail(),"@");
		if(emailTokenizer.countTokens()!=2) {//이메일 형식이 아니면
			errors.rejectValue("email", "incorrectForm");
		}
		else if(inputAccountInfo.getEmail().length()==0) {
			errors.rejectValue("email", "empty");
		}
		else if(!(inputAccountInfo.getEmail().equals(oldAccountInfo.getEmail()))){
			if(Dao.checkColumnDupli("email", inputAccountInfo.getEmail())) {
				errors.rejectValue("email", "duplicated");
			}
		}
		
		//휴대폰 번호 검증.
		StringTokenizer pNumTokenizer = new StringTokenizer(inputAccountInfo.getpNum(),"-");
		if(inputAccountInfo.getpNum().length()==0) {
			errors.rejectValue("pNum", "empty");
		}
		else if(pNumTokenizer.countTokens()!=3) {//000-000-000형식처럼 -을 기준으로 세부분으로 나뉘지 않았으면 오류저장.
			errors.rejectValue("pNum", "incorrectForm");
		}
		else{//000-0000-0000 와 같이 번호의 개수가 맞지 않으면 오류저장. 
			String[] tokens = new String[3];
			tokens[0]=pNumTokenizer.nextToken();
			tokens[1]=pNumTokenizer.nextToken();
			tokens[2]=pNumTokenizer.nextToken();
			if(tokens[0].length()!=3 || tokens[1].length()!=4 || tokens[2].length()!=4) {
				errors.rejectValue("pNum", "incorrectFrom");
			}
		}
		if(!(inputAccountInfo.getpNum().equals(oldAccountInfo.getpNum()))){
			if(Dao.checkColumnDupli("pNum", inputAccountInfo.getpNum())) {
				errors.rejectValue("pNum", "duplicated");
			}
		}
		
		
		//address검증
		if(inputAccountInfo.getAddress().length()==0) {
			errors.rejectValue("address", "empty");
		}
		else if(!(inputAccountInfo.getAddress().equals(oldAccountInfo.getAddress()))){
			if(Dao.checkColumnDupli("address", inputAccountInfo.getAddress())) {
				errors.rejectValue("address", "duplicated");
			}
		}
		
		
	}
}*/
