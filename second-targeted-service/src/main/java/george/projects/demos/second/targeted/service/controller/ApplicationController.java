package george.projects.demos.second.targeted.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "/")
public class ApplicationController {

	@ResponseBody
	@RequestMapping(value = "/")
	public String respond() {
		return "Response from the Second Service";
	}
}
