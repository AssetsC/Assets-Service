package com.example.project.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.project.models.Item_Detail;
import com.example.project.models.Item_Part;
import com.example.project.models.Items;
import com.example.project.models.collection.Admin_Item;
import com.example.project.models.collection.User_Item;
import com.example.project.models.crud.Available_Items_Crud;
import com.example.project.models.crud.ItemPartCrud;
import com.example.project.models.crud.Item_Detail_Crud;
import com.example.project.models.crud.Items_Crud;

@RestController
public class Material_Controller {
	
	@Autowired private Item_Detail_Crud item_detail;
	@Autowired private Items_Crud items;
	@Autowired private Available_Items_Crud item_available;
	@Autowired private ItemPartCrud item_part;
	
	
	//For Use CI
	@CrossOrigin
	@GetMapping("/changeStatusItem")
	public Iterable<Items> changeStatusItem(
		   @RequestParam String id
			) {
		byte[] decodedBytes = Base64.decodeBase64(id);
		String decodedString = new String(decodedBytes);
		
		return items.findByCode(decodedString);
	}
	
	@CrossOrigin
	@GetMapping("/item_admin_search")
	public List<Admin_Item> item_admin_search(
		   @RequestParam String status) {
		List<Admin_Item> admin_item = new ArrayList<>();
		Iterable<Items> item_query;
		
		int parse_state = Integer.parseInt(status);
		
		if(parse_state == 0) 
			item_query = items.findByFDByCode(3);
		else if(parse_state == 1)
			item_query = items.findByStatusByCode(0);
		else if(parse_state == 2) 
			item_query = items.findByStatusByCode(1);
		else 
			item_query = items.findAll();
		
		item_query.forEach(data->{
			Admin_Item admin = new Admin_Item();
			String item_code = data.getItem_code();
			
			List<Available_Items> user_id = item_available.findAvailableByCode(item_code);
			
			if(user_id.size() != 0) {
				int temp =  user_id.get(0).getUser_id();
				admin.setUser_id(temp);
			}else 
				admin.setUser_id(-1);
			
			admin.setItem(data);
			admin_item.add(admin);
		});
		
		return admin_item;
	}
	
	@CrossOrigin
	@GetMapping("/item_admin")
	public  List<Admin_Item> item_admin(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value,
			@RequestParam(defaultValue = "item_code") String select) {
		
		List<Admin_Item> admin_item = new ArrayList<>();
		Iterable<Items> item_query;
		
		if(!name.isEmpty() && !name.equals("")) {
			switch(select) {
				case "item_code"  : item_query = items.findByCode(name);  break;
				case "item_name"  : item_query = items.findByName(name);  break;
				case "item_model" : item_query = items.findByModel(name); break;
				case "item_detail": item_query = items.findByDetail(name,value);break;
				default : item_query = items.findAllDesc(); break;
			}
		}else {
			  item_query = items.findAllDesc();
		}
		
		item_query.forEach(data->{
			Admin_Item admin = new Admin_Item();
			String item_code = data.getItem_code();
			
			List<Available_Items> user_id = item_available.findAvailableByCode(item_code);
			
			if(user_id.size() != 0) {
				int temp =  user_id.get(0).getUser_id();
				admin.setUser_id(temp);
			}else 
				admin.setUser_id(-1);
			
			admin.setItem(data);
			admin_item.add(admin);
		});
		
		return admin_item;
	}

	@CrossOrigin
	@GetMapping("/items_user")
	public  List<User_Item> items_user(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String value,
			@RequestParam(defaultValue = "item_code") String select,
			@RequestParam(defaultValue = "") String type) {
		
		List<User_Item> user_item = new ArrayList<>();
		
		Iterable<Items> item_query;
		
		if(!name.isEmpty() && !name.equals("")) {

			if(type.isEmpty()) {
				switch(select) {
					case "item_code"  : item_query = items.findByCode(name);  break;
					case "item_name"  : item_query = items.findByName(name);  break;
					case "item_model" : item_query = items.findByModel(name); break;
					case "item_detail": item_query = items.findByDetail(name,value);break;
					default : item_query = items.findAllDesc(); break;
				}
			}else {
				switch(select) {
					case "item_code"  : item_query = items.findByCodeAndType(name,Integer.parseInt(type)); break;
					case "item_name"  : item_query = items.findByNameAndType(name,Integer.parseInt(type)); break;
					case "item_model" : item_query = items.findByModelAndType(name,Integer.parseInt(type)); break;
					case "item_detail": item_query = items.findByDetailAndType(name, value, Integer.parseInt(type)); break;
					default : item_query = items.findAllDesc(); break;
				}
			}
			
		}else {
			if(!type.isEmpty()) 
			  item_query = items.findAllAndType(Integer.parseInt(type));
			else 
			  item_query = items.findAllDesc();
		}
		
		item_query.forEach(data->{
			User_Item user = new User_Item();
			String item_code = data.getItem_code();
			
			List<Item_Detail> ip = item_detail.findByIP(item_code);
			if(ip.size() != 0) {
				String temp =  ip.get(0).getDetail_value();
				user.setIp(temp);
			}else {
				user.setIp("");
			}
			
			user.setItem(data);
			user_item.add(user);
		});
		
		return user_item;
	}
	
	@CrossOrigin
	@GetMapping("/checkItemUsed")
	public Iterable<Items> checkItemUsed()  {
		return items.findByStatusByCode(1);
	}
	
	@CrossOrigin
	@GetMapping("/getItemCode/{item_code}")
	public Items getItemCode(@PathVariable String item_code) {
		return items.findOneItemCode(item_code).get();
	}
	
	
	//For Use All Project
	@CrossOrigin
	@GetMapping("/findAllMaterial")
	public Iterable<Items> findAllMaterial(){
		return items.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findMaterial/{id}")
	public Items findMaterial(@PathVariable(name="id") int id) {
		return items.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateMaterial/{id}")
	public RedirectView updateMaterial(@PathVariable(name="id")int id, @RequestParam Items it) {
		
		Items item = items.findById(id).get();
		
		item.setItem_code(it.getItem_code());
		item.setItem_date(it.getItem_date());
		item.setItem_model(it.getItem_model());
		item.setItem_name(it.getItem_name());
		item.setItem_status(it.getItem_status());
		item.setItem_url(it.getItem_url());
		
		items.save(item);
		
		return new RedirectView("/findMaterial/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createMaterial")
	public RedirectView createMaterial(@RequestParam Items it) {
		
		Items item = new Items();
		item.setItem_code(it.getItem_code());
		item.setItem_date(it.getItem_date());
		item.setItem_model(it.getItem_model());
		item.setItem_name(it.getItem_name());
		item.setItem_status(it.getItem_status());
		item.setItem_url(it.getItem_url());
	
		
		items.save(item);
		
		return new RedirectView("/findAllMaterial");
	}
	
	@CrossOrigin
	@DeleteMapping("/delItem/{delId}")
	public boolean delItem(@PathVariable int delId)  {
		try {
			items.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/findAllMaterialDetail")
	public Iterable<Item_Detail> findAllMaterialDetail(){
		return item_detail.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findMaterialDetail/{id}")
	public Item_Detail findMaterialDetail(@PathVariable(name="id") int id) {
		return item_detail.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateMaterialDetail/{id}")
	public RedirectView updateMaterialDetail(@PathVariable(name="id")int id, @RequestParam Item_Detail itd) {
		
		Item_Detail item = item_detail.findById(id).get();
		
		item.setDetail_name(itd.getDetail_name());
		item.setDetail_value(itd.getDetail_value());
		item.setItem_code(itd.getItem_code());
		
		item_detail.save(item);
		
		return new RedirectView("/findMaterialDetail/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createMaterialDetail")
	public RedirectView createMaterialDetail(@RequestParam Item_Detail itd) {
		
		Item_Detail item = new Item_Detail();
		
		item.setDetail_name(itd.getDetail_name());
		item.setDetail_value(itd.getDetail_value());
		item.setItem_code(itd.getItem_code());
		
		item_detail.save(item);
		
		return new RedirectView("/findAllMaterialDetail");
	}
	
	@CrossOrigin
	@DeleteMapping("/delItemDetail/{delId}")
	public boolean delItemDetail(@PathVariable int delId)  {
		try {
			item_detail.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/findAllMaterialPart")
	public Iterable<Item_Part> findAllMaterialPart(){
		return item_part.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findMaterialPart/{id}")
	public Item_Part findMaterialPart(@PathVariable(name="id") int id) {
		return item_part.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateMaterialPart/{id}")
	public RedirectView updateMaterialPart(@PathVariable(name="id")int id, @RequestParam Item_Part itp) {
		
		Item_Part item = item_part.findById(id).get();
		
		item.setItem_code(itp.getItem_code());
		item.setModel(itp.getModel());
		item.setName(itp.getModel());
		item.setUnit(itp.getUnit());
		item.setValue(itp.getValue());
		
		item_part.save(item);
		
		return new RedirectView("/findMaterialPart/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createMaterialPart")
	public RedirectView createMaterialPart(@RequestParam Item_Part itp) {
		
		Item_Part item = new Item_Part();
		
		item.setItem_code(itp.getItem_code());
		item.setModel(itp.getModel());
		item.setName(itp.getModel());
		item.setUnit(itp.getUnit());
		item.setValue(itp.getValue());
		
		item_part.save(item);
		
		return new RedirectView("/findAllMaterialPart");
	}
	
	@CrossOrigin
	@DeleteMapping("/delItemPart/{delId}")
	public boolean delItemPart(@PathVariable int delId)  {
		try {
			item_part.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
}
