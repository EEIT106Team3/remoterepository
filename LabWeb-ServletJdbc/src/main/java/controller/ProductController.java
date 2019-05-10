package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.PrimitiveNumberEditor;
import model.ProductBean;
import model.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;

	@InitBinder
	public void initialize(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class,
				"make", new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
		webDataBinder.registerCustomEditor(double.class,
				"price", new PrimitiveNumberEditor(Double.class, true));
		webDataBinder.registerCustomEditor(int.class,
				new PrimitiveNumberEditor(Integer.class, true));
	}
	
	@RequestMapping("/pages/product.controller")
	public String method(ProductBean bean, BindingResult bindingResult,
			String prodaction, Model model, @RequestParam("id") String temp1) {
//接收資料
//轉換資料
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errorMsgs", errors);
		System.out.println("123");

		if(bindingResult!=null && bindingResult.hasFieldErrors()) {
			if(bindingResult.hasFieldErrors("id")) {
				errors.put("xxx1", "Id must be an integer");
			}
			if(bindingResult.hasFieldErrors("price")) {
				errors.put("xxx2", "Price must be a number");
			}
			if(bindingResult.hasFieldErrors("make")) {
				errors.put("xxx3", "Make must be a date of YYYY-MM-DD");
			}
			if(bindingResult.hasFieldErrors("expire")) {
				errors.put("xxx4", "Expire must be an integer");
			}
		}
		
//驗證資料
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(temp1==null || temp1.length()==0) {
				errors.put("xxx1", "please enter id for "+prodaction);
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.input";
		}
		
//根據model執行結果，呼叫View元件
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.select";
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert fail");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.input";
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update fail");
			} else {
				model.addAttribute("update", result);
			}
			return "product.input";
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(!result) {
				model.addAttribute("delete", 0);
			} else {
				model.addAttribute("delete", 1);
			}
			return "product.input";
		} else  {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.input";
		}

	}
}
