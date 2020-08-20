package my.site.dealsite.Controller;

import java.io.PrintWriter;

import javax.servlet.http.*;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import my.site.dealsite.VO.Account;

public class ManagerCheckInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		System.out.println("int ManagerCheckInterceptor");
		HttpSession session = request.getSession();
		Account loginedAccount = (Account)session.getAttribute("loginedAccount");
		
		if(loginedAccount==null) {
			try {
				response.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('로그인이 해제되었습니다'); location.href='showView?pageName=login';</script>");
	            out.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(loginedAccount.getGrade()!=1) {
			try {
				response.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('관리자만 접근 가능합니다'); location.href='showMainBoard';</script>");
	            out.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
