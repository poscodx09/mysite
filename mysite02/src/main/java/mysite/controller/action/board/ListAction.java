package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageParam = request.getParameter("page");
		int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1; // 기본값 1
		
		List<BoardVo> list = new BoardDao().findPageList(page);
		List<BoardVo> allList = new BoardDao().findAll();
		
		int blockSize = 5;
		int pageSize = 10;
		int totalPosts = allList.size(); // 총 게시글 수
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize); // 총 페이지 수
	    int beginPage = ((page - 1) / blockSize) * blockSize + 1;
	    int endPage = Math.min(beginPage + blockSize - 1, totalPages);
		
		request.setAttribute("page", page);
		request.setAttribute("boardList", list);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalPosts", totalPosts);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);

	}

}
