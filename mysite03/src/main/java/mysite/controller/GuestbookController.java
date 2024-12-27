package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String join(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String join(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
		model.addAttribute("id", id);
		return "guestbook/deleteform";
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, @RequestParam("password") String password, Model model) {
		int count = guestbookService.deleteContents(id, password);
		if (count > 0) {
            return "redirect:/guestbook/list"; // 삭제 후 메인 페이지로 이동
        } else {
        	model.addAttribute("message", "비밀번호가 틀렸습니다.");
            return "guestbook/deleteform"; // 실패 시 폼으로 다시 이동
        }
	}
	

}
