package in.amit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.amit.binding.LoginForm;
import in.amit.binding.RegisterForm;
import in.amit.binding.ResetPwdForm;
import in.amit.constants.AppConstants;
import in.amit.entity.User;
import in.amit.properties.AppProps;
import in.amit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private AppProps appProps;
	
	@GetMapping("/register")
	public String register(Model model) {
		Map<Integer, String> country = userSvc.getCountry();
		model.addAttribute("countries", country);
		model.addAttribute("register", new RegisterForm());
		return "register";
	}
	
	@PostMapping("/register")
	public String createUser(@ModelAttribute("register") RegisterForm formObj ,Model model) {
		boolean user = userSvc.getUser(formObj.getEmail());
		if(user) {
			model.addAttribute(AppConstants.ERR_MSG, appProps.getMessages().get("emailExist"));
			Map<Integer, String> country = userSvc.getCountry();
			model.addAttribute("countries", country);
			model.addAttribute("register", new RegisterForm());
			return "register";
		}
		boolean status = userSvc.saveUser(formObj);
		if(status) {
			model.addAttribute(AppConstants.SUCC_MSG, appProps.getMessages().get("regSuccess"));
		}else {
			model.addAttribute(AppConstants.ERR_MSG, appProps.getMessages().get("regFailure"));
		}
		
		Map<Integer, String> country = userSvc.getCountry();
		model.addAttribute("countries", country);
		model.addAttribute("register", new RegisterForm());
		return "register";
	}
	
	@GetMapping("/getStates")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId")Integer cid) {
		return userSvc.getState(cid);
	}
	
	@GetMapping("/getCities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer sid){
		return userSvc.getCity(sid);
	}
	
	@GetMapping("/")
	public String index(@ModelAttribute("login") LoginForm login, Model model) {
		//model.addAttribute("login", new LoginForm());
		return "index";
	}
	
	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("login") LoginForm login, Model model) {
		User user = userSvc.login(login);
		if(user== null) {
		model.addAttribute(AppConstants.ERR_MSG, appProps.getMessages().get("invalidLogin"));
		return "index";
		}
		if(user.getPwdUpdated().equals("NO")) {
			ResetPwdForm resetPwdForm = new ResetPwdForm();
			resetPwdForm.setId(user.getUserId());
			model.addAttribute("reset", resetPwdForm);
			return "reset_pwd";
		}
		return "redirect:dashboard";
	}
	
	@PostMapping("/reset-pwd")
	public String resetPwd(@ModelAttribute("reset") ResetPwdForm formObj, Model model) {
		if(!formObj.getNewPwd().equals(formObj.getConfirmPwd())) {
			model.addAttribute(AppConstants.ERR_MSG, appProps.getMessages().get("invalidPwds"));
			return "reset_pwd";
		}
		System.out.println(formObj.getNewPwd());
		System.out.println(formObj.getId());
		boolean resetPwd = userSvc.resetPwd(formObj.getNewPwd(), formObj.getId());
		if(resetPwd) {
			return "redirect:dashboard";
		}
		model.addAttribute(AppConstants.ERR_MSG, appProps.getMessages().get("pwdUpdateFailed"));
		return "reset_pwd";
	}
	
	
}
