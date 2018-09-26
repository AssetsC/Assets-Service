package com.example.project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.lib.Gravatar;
import com.example.project.lib.ImageSetting;
import com.example.project.lib.JWT;
import com.example.project.lib.QRCodeGenerator;
import com.example.project.lib.SoundSetting;
import com.example.project.models.TableUpdate;
import com.example.project.models.UserUpdate;
import com.example.project.models.collection.ImageData;
import com.example.project.models.crud.TableUpdateCrud;
import com.example.project.models.crud.UserUpdateCrud;
import com.google.zxing.WriterException;

@Controller
public class ProjectController {
	
	@Autowired private TableUpdateCrud table;
	@Autowired private UserUpdateCrud userupdate;
	
	@CrossOrigin
	@GetMapping("/image/{email}")
	public @ResponseBody String getImage(@PathVariable String email,
						   @RequestParam(defaultValue="80") int s,
						   @RequestParam(defaultValue="mp") String d,
						   @RequestParam(defaultValue="g") String r )  {
		
		
		return Gravatar.getGravatar(email, s, d, r);
	}
	
	@CrossOrigin
	@PutMapping("/Utable/{name}")
	public @ResponseBody boolean getDataTable(@PathVariable String name,@RequestParam String change)  {
		if(!name.isEmpty()) {
			TableUpdate t = table.findByTableName(name).get();
			t.setTable_value(change);
			table.save(t);
			
			return true;
		}
		
		return false;
	}
	
	@CrossOrigin
	@GetMapping("/Gtable/{name}")
	public @ResponseBody String setDataTable(@PathVariable String name)  {
		TableUpdate t = table.findByTableName(name).get();
		if(!name.isEmpty()) {
			
			return t.getTable_value();
		}
		
		return "";
	}
	
	@CrossOrigin
	@PostMapping("/Ctable/{name}")
	public @ResponseBody boolean creatDataTable(@PathVariable String name,@RequestParam String tableValue)  {
		try {
			TableUpdate t = new TableUpdate();
			t.setTable_name(name);
			t.setTable_value(tableValue);
			
			table.save(t);
			
		}catch (Exception e) {

			return false;
		}
		
		return true;
	}
	
	@CrossOrigin
	@DeleteMapping("/Dtable/{name}")
	public @ResponseBody boolean delDataTable(@PathVariable String name)  {
		try {
			TableUpdate t = table.findByTableName(name).get();
			table.delete(t);
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	@CrossOrigin
	@PutMapping("/UUtable/{name}/{user_id}")
	public @ResponseBody boolean getUserDataTable(@PathVariable String name,@PathVariable int user_id,@RequestParam String change)  {
		if(!name.isEmpty()) {
			UserUpdate t = userupdate.findByCodeAndUser(name, user_id).get();
			t.setTable_value(change);
			userupdate.save(t);

			return true;
		}
		
		return false;
	}
	
	@CrossOrigin
	@GetMapping("/UGtable/{name}/{user_id}")
	public @ResponseBody String setUserDataTable(@PathVariable String name,@PathVariable int user_id)  {
		UserUpdate t = userupdate.findByCodeAndUser(name, user_id).get();
		if(!name.isEmpty()) {

			return t.getTable_value();
		}
		
		return "";
	}
	
	@CrossOrigin
	@PostMapping("/UCtable/{name}/{user_id}")
	public @ResponseBody boolean creatUserDataTable(@PathVariable String name , @PathVariable int user_id,@RequestParam String tableValue)  {
		try {
			UserUpdate t = new UserUpdate();
			
			t.setTable_name(name);
			t.setUser_id(user_id);
			t.setTable_value(tableValue);
			
			userupdate.save(t);
			
		}catch (Exception e) {

			return false;
		}
		
		return true;
	}
	
	@CrossOrigin
	@DeleteMapping("/UDtable/{name}/{user_id}")
	public @ResponseBody boolean delUserDataTable(@PathVariable String name,@PathVariable int user_id)  {
		try {
			UserUpdate user = userupdate.findByCodeAndUser(name, user_id).get();
			userupdate.delete(user);
			
		}catch (Exception e) {

			return false;
		}
		
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/getBase64Image")
	public @ResponseBody String getBase64Image(@RequestParam String url) throws MalformedURLException, IOException{

		byte[] decodeByte = Base64.getDecoder().decode(url);
		String urlDecode = new String(decodeByte);
		
		return ImageSetting.getBase64Image(urlDecode, "png");
	}
	
	@CrossOrigin
	@GetMapping("/resizeImage")
	public @ResponseBody ImageData resizeImage(@RequestParam String url,@RequestParam double percen) throws MalformedURLException, IOException{

		byte[] decodeByte = Base64.getDecoder().decode(url);
		String urlDecode = new String(decodeByte);
		
		ImageData data = new ImageData();
		
		data.setType("png");
		data.setData(ImageSetting.resizeImage(urlDecode, percen));
		
		return data;
	}
	
	@CrossOrigin
	@GetMapping("/resizeImageWH")
	public @ResponseBody ImageData resizeImageWH(@RequestParam String url,@RequestParam double percenWidth,@RequestParam double percenHeight) throws MalformedURLException, IOException{

		byte[] decodeByte = Base64.getDecoder().decode(url);
		String urlDecode = new String(decodeByte);
		
		ImageData data = new ImageData();
		
		data.setType("png");
		data.setData(ImageSetting.resizeImage(urlDecode, percenWidth,percenHeight));
		
		return data;
	}
	
	@CrossOrigin
	@GetMapping("/getSoundBase64")
	public @ResponseBody String getSoundBase64(@RequestParam String url) throws IOException {
		byte[] decodeByte = Base64.getDecoder().decode(url);
		String urlDecode = new String(decodeByte);
		
		return SoundSetting.getBase64Sound(urlDecode);
	}
	
	@CrossOrigin
	@GetMapping("/getJWT")
	public @ResponseBody String getJWT(@RequestParam Map<String, String> data,@RequestParam(defaultValue="") String skey) {
		return JWT.createToken(data,skey);
	}
	
	@CrossOrigin
	@GetMapping("/decodeJWT/{token}")
	public @ResponseBody Map<String,String> decodeJWT(@PathVariable String token) {
		return JWT.decodeToken(token);
	}
    
	@CrossOrigin
    @GetMapping("/getQRCode")
    public @ResponseBody String getQRCode(@RequestParam String t,@RequestParam int w,@RequestParam int h) throws WriterException, IOException {
        return QRCodeGenerator.generateQRCodeImage(t, w , h);
    }
}
