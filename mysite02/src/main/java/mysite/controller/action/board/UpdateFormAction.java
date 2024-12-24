package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pId = Integer.parseInt(request.getParameter("pId") != null ? request.getParameter("pId") : "0");
		int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
		
		BoardVo boardVo = new BoardDao().findOne(pId);
		request.setAttribute("boardView", boardVo);
		request.setAttribute("pId", pId);
		request.setAttribute("page", page);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
		rd.forward(request, response);

	}

}
