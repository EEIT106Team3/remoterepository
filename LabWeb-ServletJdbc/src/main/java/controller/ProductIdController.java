package controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.ProductBean;
import model.ProductService;

@Controller
public class ProductIdController {
	@Autowired
	private ProductService productService;

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException ex) {
		ex.printStackTrace();
		return this.method(-2);
    }
	@RequestMapping(
			path= {"/pages/product.view"}
	)
	@ResponseBody
	public ResponseEntity<?> method(
			@RequestParam(name="id", defaultValue="-1",	required=false) Integer id) {
		JSONArray output = new JSONArray();
//接收資料
//驗證資料、轉換資料
		if(id.equals(-1)) {
			JSONObject obj = new JSONObject();
			obj.put("text", "Id是必要欄位");
			obj.put("hasMoreData", false);
			output.put(obj);
		} else if(id.equals(-2)) {
			JSONObject obj = new JSONObject();
			obj.put("text", "Id必需是數字");
			obj.put("hasMoreData", false);
			output.put(obj);
		}

//呼叫Model
		if(output==null || output.isEmpty()) {
			ProductBean bean = new ProductBean();
			bean.setId(id);
			List<ProductBean> result = productService.select(bean);

			if(result==null || result.isEmpty()) {
				JSONObject obj = new JSONObject();
				obj.put("text", "Id不存在");
				obj.put("hasMoreData", false);
				output.put(obj);
			} else {
				JSONObject obj = new JSONObject();
				obj.put("text", "Id存在");
				obj.put("hasMoreData", true);
				output.put(obj);
				
				ProductBean data = result.get(0);
				JSONObject product = new JSONObject();
				product.put("id", data.getId());
				product.put("name", data.getName());
				product.put("price", data.getPrice());
				product.put("make", data.getMake().toString());
				product.put("expire", data.getExpire());
				output.put(product);
			}
		}
		
//輸出結果
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(output.toString());		
	}
}
