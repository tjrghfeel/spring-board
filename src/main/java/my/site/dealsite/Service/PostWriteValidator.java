package my.site.dealsite.Service;

import java.io.UnsupportedEncodingException;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import my.site.dealsite.VO.Post;

public class PostWriteValidator implements Validator {
	public boolean supports(Class clazz) {
		return Post.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		Post post = (Post)target;
		
		try {
			//title검증
			if(post.getTitle().length()==0) {//title이 비어있다면.
				errors.rejectValue("title","empty");
			} 
			else if(post.getTitle().getBytes("utf8").length>80) {
				errors.rejectValue("title","excessValue");
			}
			
			//sort1,2 검증
			if(post.getSort1().equals("0")) {
				errors.rejectValue("sort1","empty");
			}
			else if(post.getSort2().equals("0")){
				errors.rejectValue("sort2","empty");
			}
			
			//content검증.
			if(post.getContent().length()==0) {//content가 비어있으면,
				errors.rejectValue("content", "empty");
			}
			else if(post.getContent().getBytes("utf8").length > 16000000) {//content가 컬럼의 크기를 초과한다면
				errors.rejectValue("content", "excessValue");
			}
			
			//mainImage검증.
			if(post.getMainImage().length()==0) {//이미지를 입력하지 않았으면,
				errors.rejectValue("mainImage", "empty");
			}
			else if(post.getMainImage().getBytes("utf8").length>60) {//db컬럼의 크기를 초과했으면
				errors.rejectValue("mainImage", "excessValue");
			}
			else if(!(post.getMainImage().substring(post.getMainImage().lastIndexOf(".")).matches(".jpg|.png|.bmp|.jpeg"))) {
				//jpg, png등의 이미지 파일형식이 아닐경우,
				errors.rejectValue("mainImage", "incorrectForm");
			}
			
			//price 검증.
			if(post.getPrice()<=0) {//price가 0보다 작으면
				errors.rejectValue("price", "shortValue");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;

		
	}

}