package com.example.project.controller;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.models.crud.Items_Crud;
import com.example.project.storage.StorageFileNotFoundException;
import com.example.project.storage.StorageService;


@RestController
public class FileUploadController {
	
	@Autowired Items_Crud items;
	
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @CrossOrigin
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        var file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @CrossOrigin
    @GetMapping("/files/{folder}/{filename:.+}")
    public ResponseEntity<Resource> serveFolderFile(@PathVariable String folder , @PathVariable String filename) {
        var file = storageService.loadAsResource(folder + "/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @CrossOrigin
    @PostMapping("/uploadFile")
    public boolean handleFileUpload(@RequestParam("item_url") MultipartFile file,@RequestParam("item_code") String item_code) {
    	try {
    		if(file.getSize() != 0 && !file.getOriginalFilename().isEmpty()) {
    			var part = file.getOriginalFilename().split("\\.");
    			var getType = part[1];
    			
    			storageService.store(file , item_code);
    			var it = items.findOneItemCode(item_code).get();
    			it.setItem_url(item_code + "." + getType);
    			
    			items.save(it);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
    	    return false;
		}
        return true;
    }
    
    @CrossOrigin
    @PostMapping("/uploadFile/{folder}")
    public boolean handleFolderFileUpload(@PathVariable String folder , @RequestParam("item_url") MultipartFile file,@RequestParam("item_code") String item_code) {
    	try {
    		if(file.getSize() != 0 && !file.getOriginalFilename().isEmpty()) {
    			var part = file.getOriginalFilename().split("\\.");
    			var getType = part[1];
    			
    			storageService.store(file , folder + "/" + item_code);
    			var it = items.findOneItemCode(item_code).get();
    			it.setItem_url(folder + "/" + item_code + "." + getType);
    			
    			items.save(it);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
    	    return false;
		}
        return true;
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
