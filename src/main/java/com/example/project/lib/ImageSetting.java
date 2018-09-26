package com.example.project.lib;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.var;
import org.apache.commons.codec.binary.Base64;

public class ImageSetting {
	
	public static String getBase64Image(@NonNull String url,String fileType) throws MalformedURLException, IOException {
		var connection = new URL(url).openConnection();
		var filestream = connection.getInputStream();
		@Cleanup var array = new ByteArrayOutputStream();

		var buffer =  ImageIO.read(filestream);
		ImageIO.write(buffer, fileType, array);
		
		var imageData = Base64.encodeBase64(array.toByteArray());
		var a = new String(imageData);
		
		return a;
	}
	
	public static String resizeImage(@NonNull  String urlImage,double percen) throws IOException {
				
		var connection = new URL(urlImage).openConnection();
		var filestream = connection.getInputStream();
		var buffer =  ImageIO.read(filestream);
		
		var newWidth  = (int)(buffer.getWidth() * (percen/100));
		var newHeight = (int)(buffer.getHeight() * (percen/100));
		
		var format = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
		format.getGraphics().drawImage(buffer, 0, 0, newWidth , newHeight , null);

		@Cleanup var newbyte = new ByteArrayOutputStream();
		ImageIO.write(format,"png",newbyte);
		
		var imageData = Base64.encodeBase64(newbyte.toByteArray());
		var a = new String(imageData);
		
		return a;
	}
	
	public static String resizeImage(@NonNull String urlImage ,double percenWidth , double percenHeight) throws IOException {
		
		var connection = new URL(urlImage).openConnection();
		var filestream = connection.getInputStream();
		var buffer =  ImageIO.read(filestream);
		
		var newWidth  = (int)(buffer.getWidth() * (percenWidth/100));
		var newHeight = (int)(buffer.getHeight() * (percenHeight/100));
		
		var format = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
		format.getGraphics().drawImage(buffer, 0, 0, newWidth , newHeight , null);

		@Cleanup var newbyte = new ByteArrayOutputStream();
		ImageIO.write(format,"png",newbyte);
		
		var imageData = Base64.encodeBase64(newbyte.toByteArray());
		var a = new String(imageData);
		
		return a;
	}
}
