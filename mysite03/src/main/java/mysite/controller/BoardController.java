package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping(value = {"/list", "/list/{currentPage}"}, method = RequestMethod.GET)
	public String list(@PathVariable(value = "currentPage", required = false) Integer page, Model model) {
	    int currentPage = (page == null || page < 1) ? 1 : page;

	    List<BoardVo> list = boardService.getContentsPageList(currentPage);
	    List<BoardVo> allList = boardService.getContentsAllList();
	    
		int blockSize = 5;
		int pageSize = 10;
		int totalPosts = allList.size(); // 총 게시글 수
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize); // 총 페이지 수
	    int beginPage = ((currentPage - 1) / blockSize) * blockSize + 1;
	    int endPage = Math.min(beginPage + blockSize - 1, totalPages);
		
		model.addAttribute("page", currentPage);
	    model.addAttribute("boardList", list);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalPosts", totalPosts);
	    model.addAttribute("beginPage", beginPage);
	    model.addAttribute("endPage", endPage);
	    
	    return "board/list";
	}
	
	@RequestMapping(value="/write/{currentPage}/{pId}", method=RequestMethod.GET)
	public String write(
			@PathVariable(value="currentPage", required=false) Integer currentPage,
			@PathVariable(value="pId", required=false) Integer parentId,
			Model model) {
		model.addAttribute("page", currentPage);
		model.addAttribute("pId", parentId);
		return "board/write";
	}
	
	@RequestMapping(value="/write/{currentPage}/{pId}", method=RequestMethod.POST)
	public String write(
			HttpSession session,
			@PathVariable(value="currentPage", required=false) Integer currentPage, 
			@PathVariable(value="pId", required=false) Integer parentId,
			BoardVo vo, Model model) {
		
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    if (authUser == null) {
	        return "redirect:/";
	    }

	    int writerId = (int) authUser.getId(); // 현재 로그인한 유저 ID 가져오기
		int pId = (parentId != null) ? parentId : 0;
		int page = (currentPage != null) ? currentPage: 1;
		
		vo.setUserId(writerId);
		System.out.println("write vo" + vo);
		boardService.addContents(pId, vo);
		
		model.addAttribute("page", page);
		model.addAttribute("pId", pId);
		return "board/list";
	}
	
	@RequestMapping(value="/view/{page}/{pId}", method=RequestMethod.GET)
	public String view(@PathVariable("page") int page, @PathVariable("pId") int pId, Model model) {
		
		BoardVo vo = boardService.getContents(pId);
		model.addAttribute("boardView", vo);
		return "board/view";
	}


}
