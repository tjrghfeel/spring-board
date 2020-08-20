package my.site.dealsite.Service;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import my.site.dealsite.Enum.AccountColumn;
import my.site.dealsite.VO.Account;
import my.site.dealsite.DAO.*;

public class LoginValidator implements Validator{
	public boolean supports(Class clazz) {
		return Account.class.isAssignableFrom(clazz);
	}
	public void validate(Object object, Errors errors) {
		Account account = (Account)object;
		
		if(account.getId().length()==0) {
			errors.rejectValue("id", "empty");
		}
		else if(!account.getId().matches("^[0-9a-zA-Z]*$")) {
			errors.rejectValue("id","incorrectForm");
		}
		else if(Dao.selectVO(AccountColumn.id,account.getId())==null) {//아이디가 없으면
			errors.rejectValue("id", "incorrect");
		}
		else {//id가 있으면, pw확인
			if(account.getPw().length()==0) {
				errors.rejectValue("pw", "empty");
			}
			else if(!account.getPw().matches("^[0-9a-zA-Z]*$")) {
				errors.rejectValue("pw", "incorrectForm");
			}
			else {
				Account resultAccount = (Dao.selectVO(AccountColumn.id,AccountColumn.pw,account.getId(),account.getPw()));
				if(resultAccount==null) {//비번이 일치하지 않으면
					errors.rejectValue("pw", "incorrect");
				}
			}
		}	
	}
}
