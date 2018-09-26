package com.example.project.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.var;
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

import com.example.project.models.Reclaim;
import com.example.project.models.crud.Available_Items_Crud;
import com.example.project.models.crud.Items_Crud;
import com.example.project.models.crud.ReclaimCrud;


@RestController
public class Reclaim_Controller {
	
	@Autowired private ReclaimCrud reclaim;
	@Autowired private Available_Items_Crud available;
	@Autowired private Items_Crud items;
	
	//For Use CI
	@CrossOrigin
	@GetMapping("/all_Reclaim")
	public Iterable<Reclaim> all_Reclaim(
			@RequestParam(defaultValue = "item_code") String select,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value
			) {
		Iterable<Reclaim> reclaim_query;
		
		if(!name.isEmpty()) {
			switch(select) {
				case "item_code"  : reclaim_query = reclaim.findByItemCode(name); 		break;
				case "item_name"  : reclaim_query = reclaim.findByName(name);     		break;
				case "itm_model"  : reclaim_query = reclaim.findByModel(name);    		break;
				case "user"       : reclaim_query = reclaim.findByUser(name, value);  	break;
				case "item_detail": reclaim_query = reclaim.findByDetail(name,value);   break;
				default : reclaim_query = reclaim.findAllDesc(); break;
			}
			
			return reclaim_query;
		}
		reclaim_query =  reclaim.findAllDesc();
		
		
		return reclaim_query;
	}
	
	@CrossOrigin
	@GetMapping("/userall_Reclaim")
	public Iterable<Reclaim> userall_Reclaim(
			
		   @RequestParam(defaultValue = "item_code") String select,
		   @RequestParam(defaultValue = "") String name,
		   @RequestParam(defaultValue = "") String value,
		   @RequestParam int user
	) {
		Iterable<Reclaim> reclaim_query;
		
		if(!name.isEmpty()) {
			switch(select) {
				case "item_code"  : reclaim_query = reclaim.findByItemCodeUser(name, user); 	   break;
				case "item_name"  : reclaim_query = reclaim.findByNameUser(name, user);     	   break;
				case "itm_model"  : reclaim_query = reclaim.findByModelUser(name, user);    	   break;
				case "item_detail": reclaim_query = reclaim.findByDetailUser(name, value, user);   break;
				default : reclaim_query =  reclaim.findAllUserDesc(user); break;
			}
			
			return reclaim_query;
		}
		reclaim_query =  reclaim.findAllUserDesc(user);
		
		return reclaim_query;
	}

	@CrossOrigin
	@PutMapping("/changeupdateState")
	public boolean changeupdateState(@RequestParam int reclaimId , @RequestParam int state) {
		try {
			 var time = new Timestamp(System.currentTimeMillis());
			 var re = reclaim.findById(reclaimId).get();
			 var item_code = re.getItem_code();
			 
			 re.setResponse_date(time.toString());
			 re.setReclaim_status(state);
			 reclaim.save(re);
			 
			 var avai = available.findAvailableByCode(item_code).get(0);
			if(state == 1) {
				available.delete(avai);
				var it = items.findOneItemCode(item_code).get();
				it.setItem_status(0);
				items.save(it);
			}else if(state == 2) {
				avai.setAvailable_state(0);
				available.save(avai);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}	
	
	//For Use All Project
	@CrossOrigin
	@GetMapping("/findAllReclaim")
	public Iterable<Reclaim> findAllReclaim(){
		return reclaim.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findReclaim/{id}")
	public Reclaim findWiden(@PathVariable(name="id") int id) {
		return reclaim.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateReclaim/{id}")
	public RedirectView updateReclaim(@PathVariable(name="id")int id, @RequestParam Reclaim re) {
		
		var temp = reclaim.findById(id).get();
		temp.setItem_code(re.getItem_code());
		temp.setReclaim_date(re.getReclaim_date());
		temp.setReclaim_status(re.getReclaim_status());
		temp.setResponse_date(re.getResponse_date());
		temp.setUser_id(re.getUser_id());
		
		reclaim.save(temp);
		
		return new RedirectView("/findReclaim/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createReclaim")
	public RedirectView createReclaim(@RequestParam Reclaim re) {
		
		var temp = new Reclaim();
		temp.setItem_code(re.getItem_code());
		temp.setReclaim_date(re.getReclaim_date());
		temp.setReclaim_status(re.getReclaim_status());
		temp.setResponse_date(re.getResponse_date());
		temp.setUser_id(re.getUser_id());
	
		reclaim.save(temp);
		
		return new RedirectView("/findAllReclaim");
	}
	
	@CrossOrigin
	@DeleteMapping("/delReclaim/{delId}")
	public boolean delReclaim(@PathVariable int delId)  {
		try {
			reclaim.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/getDateReclaimUser")
	public List<Integer> getDateReclaimUser(@RequestParam int user_id , @RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				var temp = reclaim.findAllbetweenMonthUser(user_id, year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>) Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
	
	@CrossOrigin
	@GetMapping("/getNumDateReclaim")
	public List<Integer> getNumDateReclaim(@RequestParam String year){
		var numYear = new ArrayList<Integer>();
		try {
			for(int i = 1;i <= 12;i++) {
				var temp = reclaim.findAllbetweenMonth(year+"-"+i+"-"+1);
				numYear.add(temp.size());
			}
		}catch (Exception e) {
			numYear = (ArrayList<Integer>)Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
			return numYear;
		}
		return numYear;
	}
}
