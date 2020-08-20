package my.site.dealsite.Controller;

import java.io.PrintWriter;

import javax.servlet.http.*;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		System.out.println("in interceptor");
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedAccount")==null) {
			try {
				response.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>"
	            		+ "alert('로그인이 해제되었습니다'); "
	            		+ "window.parent.document.location.href='showView?pageName=login';"
	            		+ "</script>");
	            out.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
