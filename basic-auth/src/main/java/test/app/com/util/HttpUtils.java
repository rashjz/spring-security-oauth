package test.app.com.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;

public class HttpUtils {

    private HttpUtils() {
    }

    public static HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = String.format("%s:%s", username, password);
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
} 
