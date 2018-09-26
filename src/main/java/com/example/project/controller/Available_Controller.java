package com.example.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.val;
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

import com.example.project.models.Available_Items;
import com.example.project.models.Items;
import com.example.project.models.Reclaim;
import com.example.project.models.Users;
import com.example.project.models.collection.User_Available;
import com.example.project.models.crud.Available_Items_Crud;
import com.example.project.models.crud.Items_Crud;
import com.example.project.models.crud.ReclaimCrud;
import com.example.project.models.crud.Users_Crud;

@RestController
public class Available_Controller {
	
	@Autowired private Available_Items_Crud item_available;
	@Autowired private Items_Crud items;
	@Autowired private Users_Crud user;
	@Autowired private ReclaimCrud reclaim;
	
	//For Use In CI
	@CrossOrigin
	@GetMapping("/allAvailable")
	public List<User_Available> allAvailable(
			
		   @RequestParam(defaultValue = "") String name,
		   @RequestParam(defaultValue = "") String value,
		   @RequestParam(defaultValue = "item_code") String select,
		   @RequestParam int user
			) {

		var user_Availables = new ArrayList<User_Available>();
		List<Available_Items> available;
		if(!name.isEmpty()) {
			switch(select) {
				case "item_code"  : available = item_available.findAvailableByCodeUser(name, user); break;
				case "item_name"  : available = item_available.findAvailableByNameUser(name, user); break;
				case "item_model" : available = item_available.findAvailableByModelUser(name, user); break;
				case "item_detail": available = item_available.findAvailableByDetailUser(name, value, user); break;
				default :           available = item_available.findAvailableUser(user);  break;
			}
		}else 
			available = item_available.findAvailableUser(user);
		
		available.forEach(data->{
			   var add_item = new User_Available();
			   var item_code = data.getItem_code();
			   var temp = items.findListByCode(item_code);
			   
			   if(temp.size() != 0) {
				   var item = temp.get(0);
				   add_item.setItem(item);
				   add_item.setAvailable_Item_id(data.getAvailable_Item_id());
				   add_item.setAvailable_state(data.getAvailable_state());
			   }
			   
			   user_Availables.add(add_item);
		});

		return user_Availables;
	}
	
	@CrossOrigin
	@GetMapping("/admin_Available")
	public Iterable<Users> allAvailable(
		   @RequestParam(defaultValue = "") String name,
		   @RequestParam(defaultValue = "") String value,
		   @RequestParam(defaultValue = "item_code") String select
			) {
		Iterable<Users> user_query;
		if(!name.isEmpty()) {
			switch(select) {
				case "fullname"   :  user_query  = user.findAvailableFullname(name, value); break;
				case "item_code"  :  user_query  = user.findAvailableCode(name);break;
				case "item_name"  :  user_query  = user.findAvailableName(name);break;
				case "item_model" :  user_query  = user.findAvailableModel(name);break;
				case "item_detail":  user_query  = user.findAvailableDetail(name, value);break;
				default           :  user_query  = user.findAll();break;
			}
			return user_query;
		}
		return user.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/getAvaiItemUser")
	public Iterable<Items> getAvaiItemUser(@RequestParam int id) {
		return items.getAvaiItem(id);
	}
	
	@CrossOrigin
	@GetMapping("/getAvaiUser")
	public Optional<Users> getAvaiUser(@RequestParam int id) {
		return user.findById(id);
	}
	
	@CrossOrigin
	@DeleteMapping("/delAvai/{delId}")
	public boolean delAvai(@PathVariable int delId)  {
		try {
			item_available.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@PostMapping("/reclaimItem")
	public boolean reclaimItem(@RequestParam int user_id,@RequestParam String item_code,@RequestParam int avai_id)  {
		try {
			val dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			var temp = new Reclaim();
			temp.setUser_id(user_id);
			temp.setItem_code(item_code);
			
			temp.setReclaim_date(dateTimeFormat.format(new Date()));
			temp.setReclaim_status(0);

			reclaim.save(temp);

			var available = item_available.findById(avai_id).get();
			available.setAvailable_state(2);

			item_available.save(available);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	//For Use All Project
	@CrossOrigin
	@GetMapping("/findAllAvai")
	public Iterable<Available_Items> findAllAvai(){
		return item_available.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findAvai/{id}")
	public Available_Items findFix(@PathVariable(name="id") int id) {
		return item_available.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateAvai/{id}")
	public RedirectView updateAvai(@PathVariable(name="id")int id, @RequestParam Available_Items temp) {
		
		var avai = item_available.findById(id).get();
		
		avai.setAvailable_state(temp.getAvailable_state());
		avai.setItem_code(temp.getItem_code());
		avai.setStart_date(temp.getStart_date());
		avai.setUser_id(temp.getUser_id());
		
		return new RedirectView("/findAvai/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createAvai")
	public RedirectView createAvai(@RequestParam Available_Items temp) {
		
		var avai = new Available_Items();
		avai.setAvailable_state(temp.getAvailable_state());
		avai.setItem_code(temp.getItem_code());
		avai.setStart_date(temp.getStart_date());
		avai.setUser_id(temp.getUser_id());
		item_available.save(avai);

		return new RedirectView("/findAllAvai");
	}
}
