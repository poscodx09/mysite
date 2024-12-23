package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.dao.GuestbookDao;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
	    UserVo authUser = (UserVo) session.getAttribute("authUser");

	    if (authUser == null) {
	        response.sendRedirect(request.getContextPath() + "/user?a=loginform");
	        return;
	    }

	    int writerId = (int) authUser.getId(); // 현재 로그인한 유저 ID 가져오기
		
		String gIdParam = request.getParameter("gId");
		String oIdParam = request.getParameter("oId");
		String depthParam = request.getParameter("depth");
		int gId = (gIdParam != null && !gIdParam.isEmpty()) ? Integer.parseInt(gIdParam) : 1; // 기본값 1
		int oId = (oIdParam != null && !oIdParam.isEmpty()) ? Integer.parseInt(oIdParam) : 1; // 기본값 1
		int depth = (depthParam != null && !depthParam.isEmpty()) ? Integer.parseInt(depthParam) : 0; // 기본값 0
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		
		vo.setTitle(title);
		vo.setContent(content);
		vo.setHit(0);
		vo.setGroupNo(gId);
		vo.setOrderNo(oId);
		vo.setDepth(depth);
		vo.setUserId(writerId);
		
		
		int pId = new BoardDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board?a=" + pId);
	}

}
