package com.example.project.lib;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.var;

import org.apache.commons.codec.binary.Base64;

public class SoundSetting {
	
	public static String getBase64Sound(@NonNull String urlCon) throws IOException {
		var url = new URL(urlCon);

        var urlConnection = url.openConnection();
        @Cleanup var buffer = new BufferedInputStream(urlConnection.getInputStream());

        var t = new byte[urlConnection.getContentLength()];
        buffer.read(t , 0 , t.length);
        
        var encodedAudio = Base64.encodeBase64(t);

        return new String(encodedAudio);
	}
}
