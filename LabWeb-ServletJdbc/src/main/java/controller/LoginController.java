package controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.CustomerBean;
import model.CustomerService;

@Controller
@SessionAttributes(names={"user"})
public class LoginController {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ApplicationContext context;
	
	
	@RequestMapping(path={"/secure/login.controller"})
	public String method(String username, String password, Model model) {
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println("locale="+locale);
//接收資料
//驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);
		
		if(username==null || username.length()==0) {
			errors.put("username",
					context.getMessage("login.username.required", null, locale));
		}
		if(password==null || password.length()==0) {
			errors.put("password",
					context.getMessage("login.password.required", null, locale));
		}
		
		if(errors!=null && !errors.isEmpty()) {
			System.out.println("123");
			return "login.input";
		}
				
//呼叫model
		CustomerBean bean = customerService.login(username, password);
				
//根據model執行結果，導向view
		if(bean==null) {
			errors.put("password", "Login failed, please try again. (MVC)");
			return "login.input";
		} else {
			model.addAttribute("user", bean);
			return "index";
		}
	}
}
