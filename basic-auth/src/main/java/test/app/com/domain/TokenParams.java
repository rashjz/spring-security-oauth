package test.app.com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenParams implements Serializable{
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;

} 
