package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
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
		int pId = Integer.parseInt(request.getParameter("pId") != null ? request.getParameter("pId") : "0");
		int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
		
		request.setAttribute("pId", pId);
		request.setAttribute("page", page);
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setHit(0);
		vo.setUserId(writerId);
		
		int newPostId = new BoardDao().insert(pId, vo);
		
		response.sendRedirect(request.getContextPath() + "/board?a=view&page=" + page + "&pId=" + newPostId);
	}

}
