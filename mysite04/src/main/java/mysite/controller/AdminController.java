package mysite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final FileUploadService fileUploadService;
	private SiteService siteService;
	private final ServletContext servletContext;
	private final ApplicationContext applicationContext;
	
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService, ServletContext servletContext, ApplicationContext applicationContext) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
		this.servletContext = servletContext;
		this.applicationContext = applicationContext;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
			@RequestParam("title") String title,
			@RequestParam("welcome") String welcome,
			@RequestParam("description") String description,
			@RequestParam("profile") String profileUrl,
			@RequestParam("file") MultipartFile multipartFile) {
		
		SiteVo siteVo = new SiteVo();
		siteVo.setTitle(title);
		siteVo.setWelcome(welcome);
		siteVo.setDescription(description);
		siteVo.setProfile(profileUrl);
		
		String profile = fileUploadService.restore(multipartFile);
		if(profile != null) {
			siteVo.setProfile(profile);
		}
		siteService.updateSite(siteVo);
		
		// update servlet context bean
		servletContext.setAttribute("siteVo", siteVo);
		
		// update application context bean
		SiteVo site = applicationContext.getBean(SiteVo.class);
		BeanUtils.copyProperties(siteVo, site);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
