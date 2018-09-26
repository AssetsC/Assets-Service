package com.example.project.controller;

import java.util.ArrayList;
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

import com.example.project.models.Fix;
import com.example.project.models.History;
import com.example.project.models.Reclaim;
import com.example.project.models.Users;
import com.example.project.models.Widens;
import com.example.project.models.crud.FixCrud;
import com.example.project.models.crud.HistoryCrud;
import com.example.project.models.crud.ReclaimCrud;
import com.example.project.models.crud.Users_Crud;
import com.example.project.models.crud.WidenCrud;

@RestController
public class Main_Controller {
	
	@Autowired private Users_Crud users;
	@Autowired private HistoryCrud history;
	@Autowired private ReclaimCrud reclaim;
	@Autowired private FixCrud fix;
	@Autowired private WidenCrud widen;
	
	//For Use CI
	@CrossOrigin
	@GetMapping("/getAdminId")
	public List<Integer> getAdminId() {
		
		var user_id = new ArrayList<Integer>();
		var admin = users.findAdminId();
		
		admin.forEach(element ->{
			user_id.add(element.getId());
		});
		
		return user_id;
	}
	
	@CrossOrigin
	@GetMapping("/findAllProcessUser/{user_id}")
	public List<Integer> findAllProcessUser(@PathVariable int user_id){
		var tempRecliam = (List<Reclaim>)reclaim.findAllUserDesc(user_id);
		var tempWiden = (List<Widens>)widen.findWidenByUserId(user_id);
		var	tempFix = (List<Fix>)fix.findAllDescUser(user_id);
		
		var data = new ArrayList<Integer>();

		data.add(tempWiden.size());
		data.add(tempRecliam.size());
		data.add(tempFix.size());
		
		return data;
	}
	
	@CrossOrigin()
	@DeleteMapping("/delHistory/{histId}")
	public boolean delHistory(@PathVariable int histId)  {
		try {
			history.deleteById(histId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@PutMapping("/updateHistory")
	public int updateHistory(@RequestParam int id, 
			  @RequestParam(defaultValue="") String name,
			  @RequestParam(defaultValue="") String content,
			  @RequestParam(defaultValue="") String path,
			  @RequestParam boolean readed
			)  {
		try {
			 History hist = history.findById(id).get();
			
			if(!name.equals("")) 
				hist.setName(name);
			if(!content.equals(""))
				hist.setContent(content);
			if(!path.equals(""))
				hist.setPath(path);
			
			hist.setReaded(readed);
		
			history.save(hist);
			
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@CrossOrigin
	@PostMapping("/saveHistry")
	public int saveHistry(@RequestParam int user_id, 
						  @RequestParam String name,
						  @RequestParam String content,
						  @RequestParam String path,
						  @RequestParam boolean readed)  {
		try {
			var hist = new History();
			hist.setUser_id(user_id);
			hist.setName(name);
			hist.setContent(content);
			hist.setPath(path);
			hist.setReaded(readed);
		
			history.save(hist);
			
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@CrossOrigin
	@DeleteMapping("/delHistryByUser/{userId}")
	public boolean delHistryByUser(@PathVariable int userId) {
		try {
			var user_history = history.findHistory(userId);
			history.deleteAll(user_history);
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/selectHistry")
	public List<History> selectHistry(@RequestParam int user_id) {
		return history.findHistory(user_id);
	}
	
	//For Use All Project
	
	@CrossOrigin
	@GetMapping("/findAllUsers")
	public Iterable<Users> findAllUsers(){		
		return users.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/findUsers/{id}")
	public Users findUsers(@PathVariable(name="id") int id) {
		return users.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/updateUsers/{id}")
	public RedirectView updateUsers(@PathVariable(name="id")int id,@RequestParam Users temp) {
		
		var user = users.findById(id).get();
		
		user.setDepart_id(temp.getDepart_id());
		user.setDisplay_name(temp.getDisplay_name());
		user.setEmail(temp.getEmail());
		user.setFirstname(temp.getFirstname());
		user.setLastname(temp.getLastname());
		user.setUsername(temp.getUsername());
		user.setRole_id(temp.getRole_id());
		
		users.save(user);
		return new RedirectView("/findUsers/" + id);
	}
	
	@CrossOrigin
	@PostMapping("/createUsers/{id}")
	public RedirectView createWidenDetail(@PathVariable(name="id")int id,@RequestParam Users temp) {
		
		var user = new Users();
		
		user.setDepart_id(temp.getDepart_id());
		user.setDisplay_name(temp.getDisplay_name());
		user.setEmail(temp.getEmail());
		user.setFirstname(temp.getFirstname());
		user.setLastname(temp.getLastname());
		user.setUsername(temp.getUsername());
		user.setRole_id(temp.getRole_id());
		
		users.save(user);
		return new RedirectView("/findAllUsers");
	}
	
	@CrossOrigin
	@DeleteMapping("/delUsers/{delId}")
	public boolean delWidenDetail(@PathVariable int delId)  {
		try {
			users.deleteById(delId);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
