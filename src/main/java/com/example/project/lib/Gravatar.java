package com.example.project.lib;

import lombok.NonNull;
import lombok.var;
import lombok.val;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

	public static String getGravatar(@NonNull String email,int s,@NonNull String d,@NonNull String r) {
		val path = "https://www.gravatar.com/avatar/";
		return path + md5Hex(email) + "?s=" + s + "&d="+ d + "&r=" + r;
	}
	
	public static String md5Hex (@NonNull String message) {
	      try {
	      	var md = MessageDigest.getInstance("MD5");
	      	return hex (md.digest(message.getBytes("CP1252")));
	      } catch (NoSuchAlgorithmException e) {
	      	e.printStackTrace();
	      } catch (UnsupportedEncodingException e) {
	      	e.printStackTrace();
	      }
	      return null;
	 }
	
	 public static String hex(@NonNull byte[] array) {
	      var sb = new StringBuffer();
	      for (var i = 0; i < array.length; ++i) {
	      sb.append(Integer.toHexString((array[i]
	          & 0xFF) | 0x100).substring(1,3));        
	      }
	      return sb.toString();
	  }
}
