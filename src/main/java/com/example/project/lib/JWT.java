package com.example.project.lib;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.NonNull;
import lombok.var;

public class JWT {
	
	public static String createToken(@NonNull Map<String,String> secret, @NonNull String skey) {
		try {
			var algorithm = Algorithm.HMAC256(skey);
			var build = com.auth0.jwt.JWT.create();
		    var token = "";
		    
		    secret.forEach((k,v)->{
		    	build.withClaim(k, v);
		    });
		   
		    token = build.sign(algorithm);
		    return token;
		    
		} catch (JWTCreationException exception){
			exception.printStackTrace();
		}
		return "";
	}	
	
	public static Map<String,String> decodeToken(@NonNull String token) {
		try {
		    var jwt = com.auth0.jwt.JWT.decode(token);
		    var claim = jwt.getClaims();
		    var data = new HashMap<String,String>();
		    
		    claim.forEach((k,v)->{
		    	data.put(k, v.asString());
		    });
		    
		    return data;
		    
		} catch (JWTDecodeException exception){
		    exception.printStackTrace();
		}
		return null;
	}
}
