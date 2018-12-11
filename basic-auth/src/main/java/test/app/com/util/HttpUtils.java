package test.app.com.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;

public class HttpUtils {

    private HttpUtils() {
    }

    public static HttpHeaders createHeaders(String username, String password) {
        String auth = String.format("%s:%s", username, password);
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = String.format("Basic %s", new String(encodedAuth));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", authHeader);
        return httpHeaders;
    }
} 
