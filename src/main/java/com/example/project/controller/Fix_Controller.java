package com.example.project.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.project.models.Fix;
import com.example.project.models.Fix_Detail;
import com.example.project.models.collection.Fix_Detail_Admin;
import com.example.project.models.crud.Available_Items_Crud;
import com.example.project.models.crud.FixCrud;
import com.example.project.models.crud.Fix_Detail_Crud;
import com.example.project.models.crud.Items_Crud;

@RestController
public class Fix_Controller {	
	
	@Autowired private FixCrud fix;
	@Autowired private Fix_Detail_Crud fix_detail;
	@Autowired private Items_Crud items;
	@Autowired private Available_Items_Crud item_available;
	
	//For Use CI
	@CrossOrigin
	@GetMapping("/getAllFix")
	public Iterable<Fix> getAllFix(
	       @RequestParam String name,
	       @RequestParam String value,
	       @RequestParam String select,
	       @RequestParam int type
			) {
		
		if(type == -1) {
			if(!name.isEmpty()) {
				Iterable<Fix> fix_query;
				
				switch(select) {
					case "item_code" : fix_query = fix.findByCode(name);  break;
					case "item_name" : fix_query = fix.findByName(name);  break;
					case "item_mode" : fix_query = fix.findByModel(name); break;
					case "user"		 : fix_query = fix.findByUser(name);  break;
					case "item_detail" : fix_query = fix.findByDetail(name, value);break;
					default : fix_query = fix.findAllDesc(); break;
				}
				
				return fix_query;
			}
		}else {
			if(!name.isEmpty()) {
				Iterable<Fix> fix_query;
		
				switch(select) {
					case "item_code" : fix_query = fix.findByTypeCode(name,type);  break;
					case "item_name" : fix_query = fix.findByTypeName(name,type);  break;
					case "item_mode" : fix_query = fix.findByTypeModel(name,type); break;
					case "user"		 : fix_query = fix.findByTypeUser(name,type);  break;
					case "item_detail" : fix_query = fix.findByTypeDetail(name, value,type);break;
					default : fix_query = fix.findAllTypeDesc(type); break;
				}
				
				return fix_query;
			}else 
				return fix.findAllTypeDesc(type);
		}
		
		return fix.findAllDesc();
	}
	
	@CrossOrigin
	@PutMapping("/changStatusFix")
	public boolean changStatusFix(@RequestParam int fixId,@RequestParam int stateVal) {
		try {
			var timestamp = new Timestamp(System.currentTimeMillis());
			
			if(stateVal == 2){
	            var fd  = fix_detail.findFDByFixId(fixId);
	            fd.forEach(v ->{
	            	var item_code = v.getItem_code();
	            	var avai = item_available.findAvailableByCode(item_code);
	            	var temp = avai.get(0);
	            	temp.setAvailable_state(0);
	        
	            	item_available.save(temp);
	            });
	        }
			
			var f = fix.findById(fixId).get();
			f.setFix_status(stateVal);
			f.setFix_approve(timestamp.toString());
			fix.save(f);
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/allFix")
	public Iterable<Fix> allFix(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value,
			@RequestParam(defaultValue = "item_code") String select,
			@RequestParam int user
			) {
		if(!name.isEmpty()) {
			Iterable<Fix> fix_query;
			
			switch(select) {
				case "item_code" : fix_query = fix.findByCodeUser(name,user);  break;
				case "item_name" : fix_query = fix.findByNameUser(name,user);  break;
				case "item_mode" : fix_query = fix.findByModelUser(name,user); break;
				case "item_detail" : fix_query = fix.findByDetailUser(name, value,user);break;
				default : fix_query = fix.findAllDescUser(user); break;
			}
			
			return fix_query;
		}
		
		return fix.findAllDescUser(user);
	}
	
	@CrossOrigin
	@GetMapping("/getDescriptionFix")
	public List<Fix_Detail_Admin> getDescriptionFix(@RequestParam int fix_id) {
		var query = fix_detail.findFDByFixId(fix_id);
		var fix_query = fix.findById(fix_id).get();
		
		var fix_detail = new ArrayList<Fix_Detail_Admin>();
		
		query.forEach(data ->{
			var temp = new Fix_Detail_Admin();
			var item_code = data.getItem_code();
			
			var item = items.findByCodeLimit(item_code).get();
			temp.setItem(item);
			temp.setFix_detail_id(data.getId());
			temp.setFix_detail_status(data.getFix_detail_status());
			temp.setFix_description(data.getFix_description());
			temp.setUser_id(fix_query.getUser_id());
			
			fix_detail.add(temp);
		});
		
		return fix_detail;
	}
	
	@CrossOrigin
	@PutMapping("/changeStatusFixDetail")
	public int changeStatusFixDetail(@RequestParam int id,@RequestParam int status)  {
		try {
			var fd_query = fix_detail.findById(id).get();
			fd_query.setFix_detail_status(status);
			fix_detail.save(fd_query);
			
			var item_code = fix_detail.findById(id).get().getItem_code();
			
			if(status == 2) {
				var available_Items = item_available.findAvailableByCode(item_code).get(0);
				available_Items.setAvailable_state(0);
				item_available.save(available_Items);
			}else {
				var available_Items = item_available.findAvailableByCode(item_code).get(0);
				available_Items.setAvailable_state(1);
				item_available.save(available_Items);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@CrossOrigin
	@PutMapping("/updateDescFixItem")
	public int updateDescFixItem(@RequestParam int fixDetail_id,@RequestParam String fixDetail)  {
		try {
			var fix = fix_detail.findById(fixDetail_id).get();
			fix.setFix_description(fixDetail);
			fix_detail.save(fix);
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@CrossOrigin
	@GetMapping("/loadFixDDesc")
	public Fix_Detail  loadFixDDesc(@RequestParam int id)  {
		var detail =	fix_detail.findById(id).get();
		return detail;
	}
	
	@CrossOrigin
	@PostMapping("/addToFixStore")
	public boolean addToFixStore(@RequestParam int user_id,@RequestParam String items) throws JSONException {
		try {
			var time = new Timestamp(System.currentTimeMillis());
			
			var arr = new JSONArray(items);
			var json = JsonParserFactory.getJsonParser();

			if(arr.length() > 0) {
				var createFix = new Fix();
				
				createFix.setUser_id(user_id);
				createFix.setFix_date(time.toString());
				createFix.setFix_status(0);
				
				var afterAdd = fix.save(createFix);
				
				var id = afterAdd.getId();
				
				for(int i = 0;i < arr.length();i++) {
					
					var temp = json.parseMap(arr.getString(i));
					var item_code = (String)temp.get("item_code");
					var avaiId       = Integer.parseInt((String)temp.get("available_Item_id"));
					
					var nfd = new Fix_Detail();
					nfd.setFix_id(id);
					nfd.setItem_code(item_code);
					
					fix_detail.save(nfd);

					var avaialble = item_available.findById(avaiId).get();
					avaialble.setAvailable_state(1);
					item_available.save(avaialble);
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	//For Use All Project
	@CrossOrigin
	@GetMapping("/findAllFix")
	public Iterable<Fix> findAllFix(){
		return fix.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findFix/{id}")
	public Fix findFix(@PathVariable(name="id") int id) {
		return fix.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateFix/{id}")
	public RedirectView updateFix(@PathVariable(name="id")int id, @RequestParam Fix temp) {
		
		var f = fix.findById(id).get();
		
		f.setUser_id(temp.getUser_id());
		f.setFix_status(temp.getFix_status());
		f.setFix_date(temp.getFix_date());
		f.setFix_approve(temp.getFix_approve());
		
		fix.save(f);
		
		return new RedirectView("/findFix/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createFix")
	public RedirectView createFix(@RequestParam Fix temp) {
		
		var f = new Fix();
		
		f.setUser_id(temp.getUser_id());
		f.setFix_status(temp.getFix_status());
		f.setFix_date(temp.getFix_date());
		f.setFix_approve(temp.getFix_approve());
	
		fix.save(f);
		return new RedirectView("/findAllFix");
	}
	
	@CrossOrigin
	@GetMapping("/delFix/{id}")
	public boolean delFix(@PathVariable(name="id") int id) {
		try {
			fix.deleteById(id);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/findAllFixDetail")
	public Iterable<Fix_Detail> findAllFixDetail(){
		return fix_detail.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findFixDetail/{id}")
	public Fix_Detail findFixDetail(@PathVariable(name="id") int id) {
		return fix_detail.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateFixDetail/{id}")
	public RedirectView updateFixDetail(@PathVariable(name="id")int id, @RequestParam Fix_Detail temp) {
		
		var f = fix_detail.findById(id).get();
		f.setFix_description(temp.getFix_description());
		f.setFix_detail_status(temp.getFix_detail_status());
		f.setFix_id(temp.getFix_id());
		f.setItem_code(temp.getItem_code());
		
		fix_detail.save(f);
		
		return new RedirectView("/findFixDetail/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createFixDetail")
	public RedirectView createFixDetail(@RequestParam Fix_Detail temp) {
		
		var f = new Fix_Detail();
		
		f.setFix_description(temp.getFix_description());
		f.setFix_detail_status(temp.getFix_detail_status());
		f.setFix_id(temp.getFix_id());
		f.setItem_code(temp.getItem_code());
		
		fix_detail.save(f);
		
		return new RedirectView("/findAllFixDetail");
	}
	
	@CrossOrigin
	@GetMapping("/delFixDetail/{id}")
	public boolean delFixDetail(@PathVariable(name="id") int id) {
		try {
			fix_detail.deleteById(id);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/getDateFixUser")
	public List<Integer> getDateWidenUser(@RequestParam int user_id , @RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				List<Fix> temp = fix.findAllbetweenMonthUser(user_id, year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>) Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
	
	@CrossOrigin
	@GetMapping("/getNumDateFix")
	public List<Integer> getNumDateWiden(@RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				List<Fix> temp = fix.findAllbetweenMonth(year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>) Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
}
