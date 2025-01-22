package mysite.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class WhitelabelErrorController implements ErrorController {
	
	@RequestMapping("/404")
	public String _404() {
		return "errors/404";
	}
	
	@ResponseBody
	@RequestMapping("")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "errors/404";
			}
		}
		
		return "errors/unknown";
	}

}
