package in.amit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.amit.service.DashboardService;

@Controller
public class DashboardController {

	@Autowired
	private DashboardService dashboardSvc;
	
	@GetMapping("/dashboard")
	public String getQuote(Model model) {
		String quote = dashboardSvc.getQuote();
		model.addAttribute("quote", quote);
		return "dashboard";
	}
}
