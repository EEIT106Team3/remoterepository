package controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.ProductBean;
import model.ProductDAO;

@RestController
public class ProductRESTService {
	@Autowired private ServletContext application;
	@Autowired private ProductDAO productDao;
	
	@GetMapping("/products")
	public ResponseEntity<?> findAll() {
		System.out.println("method1()");
		List<ProductBean> result = productDao.findAll();
		return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> findByPrimaryKey(@PathVariable(value="id") String temp) {
		System.out.println("method2() id=" + temp);
		try {
			int id = Integer.parseInt(temp);
			ProductBean result = productDao.findByPrimaryKey(id);
			if(result!=null) {
				return ResponseEntity.ok(result);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	@PostMapping(
			path={"/products"},
			consumes={"application/x-www-form-urlencoded"}
	)
	public ResponseEntity<?> method30(@ModelAttribute ProductBean messageBody) {
		System.out.println("method30() messageBody: " + messageBody);
		ProductBean result = productDao.insert(messageBody);
		if(result!=null) {
			URI uri = URI.create(
					application.getContextPath()+"/products/"+messageBody.getId());
			return ResponseEntity.created(uri).body(result);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping(
			path={"/products"},
			consumes={"application/json", "application/xml"}
	)
	public ResponseEntity<?> create(@RequestBody ProductBean messageBody) {
		System.out.println("method31() messageBody: " + messageBody);
		ProductBean result = productDao.insert(messageBody);
		if(result!=null) {
			URI uri = URI.create(application.getContextPath()+"/products/"+messageBody.getId());
			return ResponseEntity.created(uri).body(result);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping(
			path={"/products/{id}"},
			consumes={"application/json", "application/xml"}
	)
	public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody ProductBean messageBody) {
		System.out.println("method4():"+id+", "+messageBody);
		ProductBean result = productDao.update(messageBody.getName(),
				messageBody.getPrice(), messageBody.getMake(), messageBody.getExpire(), id);
		if(result!=null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> remove(@PathVariable(value = "id") int id) {
		System.out.println("method5():"+id);
		boolean result = productDao.delete(id);
		if(result) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
