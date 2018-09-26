package com.example.project.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.var;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.project.models.Available_Items;
import com.example.project.models.Items;
import com.example.project.models.WidenDetails;
import com.example.project.models.Widens;
import com.example.project.models.crud.Available_Items_Crud;
import com.example.project.models.crud.Items_Crud;
import com.example.project.models.crud.WidenCrud;
import com.example.project.models.crud.WidenDetailCrud;

@RestController
public class Widen_Controller {
	
	@Autowired private WidenCrud widen;
	@Autowired private WidenDetailCrud widen_detail;
	@Autowired private Items_Crud items;
	@Autowired private Available_Items_Crud available_items;
	
	//For Use CI
	@CrossOrigin
	@GetMapping("/getwidenUser")
	public Iterable<Widens> getAllWidenUser2(
			@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "item_code") String select,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value,
			@RequestParam int user
	) {
		Iterable<Widens> widen_query;
		if(!name.isEmpty()) {
			switch (select) {
				case "item_code"   : widen_query = widen.findWidenByCodeUser(user,name, search); break;
				case "item_name"   : widen_query = widen.findWidenByNameUser(user,name, search); break;
				case "item_model"  : widen_query = widen.findWidenByModelUser(user, name, search); break;
				case "item_detail" : widen_query = widen.findWidenByDetailUser(user, name, value, search); break;
				default            : widen_query = widen.findWidenBySearchUser(user,search); break;
			}
			return widen_query;
		}else 
			
			return widen.findWidenBySearchUser(user,search);	
	}
	
	@CrossOrigin
	@GetMapping("/getDateWidenUser")
	public List<Integer> getDateWidenUser(@RequestParam int user_id , @RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				var temp = widen.findAllbetweenMonthUser(user_id, year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>) Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
	
	@CrossOrigin
	@GetMapping("/getNumDateWiden")
	public List<Integer> getNumDateWiden(@RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				var temp = widen.findAllbetweenMonth(year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>) Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
	
	@CrossOrigin
	@GetMapping("/WidenNumCount")
	public List<Integer> widenNum(){
		
		var wait  = widen.findWidenByState(0).size();
		var app   = widen.findWidenByState(1).size();
		var noApp = widen.findWidenByState(2).size();
		
		var temp = Arrays.asList(wait,app,noApp);
		
		return temp;
	}
	
	@CrossOrigin
	@GetMapping("/getAllWiden")
	public Iterable<Widens> getAllWiden(
			@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "item_code") String select,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value
	) {
		Iterable<Widens> widen_query;
		if(!name.isEmpty()) {
			switch (select) {
				case "user_name" : widen_query = widen.findWidenByUsername(name, search);break;
				case "item_code" : widen_query = widen.findWidenByCode(name, search);break;
				case "item_name" : widen_query = widen.findWidenByName(name, search);break;
				default : widen_query = widen.findWidenBySearch(search);break;
			}
			return widen_query;
		}else
			return widen.findWidenBySearch(search);
	}
	
	@CrossOrigin
	@PutMapping("/noApprove")
	public List<Widens> noApprove(@RequestParam String widen_id) {
		var widen_query = new ArrayList<Widens>();

		var decodedBytes = Base64.decodeBase64(widen_id + "");
		var decodedString = new String(decodedBytes);
		var parseDecode = (decodedString.isEmpty())? -1 : Integer.parseInt(decodedString);
		
		var timestamp = new Timestamp(System.currentTimeMillis());
		
		var changeWiden = widen.findById(parseDecode).get();
		changeWiden.setWiden_status(2);
		changeWiden.setDate_res(timestamp.toString());
		
		widen.save(changeWiden);
		
		var wd = widen_detail.findFDByWidenId(parseDecode );
		
		wd.forEach(data->{
			autoSelectItem(data.getItem_code());
		});
		
		widen_query.add(changeWiden);
		return widen_query;
	}
	
	@CrossOrigin
	@PutMapping("/approve")
	public List<Widens> approve(@RequestParam String widen_id) {
		var widen_query = new ArrayList<Widens>();
		var decodedBytes = Base64.decodeBase64(widen_id + "");
		var decodedString = new String(decodedBytes);
		var parseDecode = (decodedString.isEmpty())? -1 : Integer.parseInt(decodedString);
		
		var timestamp = new Timestamp(System.currentTimeMillis());
		
		var changeWiden = widen.findById(parseDecode).get();
		changeWiden.setWiden_status(1);
		changeWiden.setDate_res(timestamp.toString());
		
		widen.save(changeWiden);
		
		var wd = widen_detail.findFDByWidenId(parseDecode);
		
		var user_id = changeWiden.getUser_id();
		
		wd.forEach(data->{
			addToAvailable(data, user_id, timestamp);
		});
		
		widen_query.add(changeWiden);

		return widen_query;
	}
	
	public void addToAvailable(WidenDetails data,int user_id,Timestamp timestamp) {
		
		var avai = new Available_Items();
		avai.setUser_id(user_id);
		avai.setItem_code(data.getItem_code());
		avai.setStart_date(timestamp.toString());
		
		available_items.save(avai);
	}
	
	public void autoSelectItem(String item_code) {
		var query = items.findByCodeLimit(item_code).get();
		var status = query.getItem_status();
		
		status = (status == 0)? 1 : 0;
		query.setItem_status(status);
		
		items.save(query);
		
	}
	
	@CrossOrigin
	@GetMapping("/findItemsByWiden/{id}")
	public List<Items> findItemsByWiden(@PathVariable int id) {
		var it = new ArrayList<Items>();
		
		widen_detail.findFDByWidenId(id).forEach(data->{
			var item_code =  data.getItem_code();
			var temp = items.findByCodeLimit(item_code).get();
			it.add(temp);
		});
		
		return it;
	}
	
	//For Use All Project
	@CrossOrigin
	@GetMapping("/findAllWiden")
	public Iterable<Widens> findAllWiden(){		
		return widen.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findWiden/{id}")
	public Widens findWiden(@PathVariable(name="id") int id) {
		return widen.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateWiden/{id}")
	public RedirectView updateWiden(@PathVariable(name="id")int id,@RequestParam Widens temp) {
		
		var wid = widen.findById(id).get();
		wid.setDate_res(temp.getDate_res());
		wid.setUser_id(temp.getUser_id());
		wid.setWiden_date(temp.getWiden_date());
		wid.setWiden_status(temp.getWiden_status());
	
		widen.save(wid);
		
		return new RedirectView("/findWiden/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createWiden/{id}")
	public RedirectView createWiden(@PathVariable(name="id")int id,@RequestParam Widens temp) {
		
		var wid = new Widens();
		wid.setDate_res(temp.getDate_res());
		wid.setUser_id(temp.getUser_id());
		wid.setWiden_date(temp.getWiden_date());
		wid.setWiden_status(temp.getWiden_status());
		
		widen.save(wid);
		return new RedirectView("/findAllWiden");
	}
	
	@CrossOrigin
	@DeleteMapping("/delWiden/{delId}")
	public boolean delWiden(@PathVariable int delId)  {
		try {
			widen.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/findAllWidenDetail")
	public Iterable<WidenDetails> findAllWidenDetail(){		
		return widen_detail.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findWidenDetail/{id}")
	public WidenDetails findWidenDetail(@PathVariable(name="id") int id) {
		return widen_detail.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateWidenDetail/{id}")
	public RedirectView updateWidenDetail(@PathVariable(name="id")int id,@RequestParam WidenDetails temp) {
		
		var wid = widen_detail.findById(id).get();
		wid.setItem_code(temp.getItem_code());
		wid.setWiden_id(temp.getWiden_id());
		
		widen_detail.save(wid);
		
		return new RedirectView("/findWidenDetail/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createWidenDetail/{id}")
	public RedirectView createWidenDetail(@PathVariable(name="id")int id,@RequestParam WidenDetails temp) {
		
		var wid = new WidenDetails();
		wid.setItem_code(temp.getItem_code());
		wid.setWiden_id(temp.getWiden_id());
		
		widen_detail.save(wid);
		return new RedirectView("/findAllWiden");
	}
	
	@CrossOrigin
	@DeleteMapping("/delWidenDetail/{delId}")
	public boolean delWidenDetail(@PathVariable int delId)  {
		try {
			widen_detail.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
