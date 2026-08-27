package com.fragma.oauth;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class OAuth2AuthorizationRequest {

    private String authorizationUri;
    private String redirectUri;
    private String clientId;
    private String responseType;
    private Set<String> scopes;
    private String state;
    
	public OAuth2AuthorizationRequest(String authorizationUri, String redirectUri, String clientId,
			Set<String> scopes, String state, String responseType) {
		super();
		this.authorizationUri = authorizationUri;
		this.redirectUri = redirectUri;
		this.clientId = clientId;
		this.responseType = responseType;
		this.scopes = scopes;
		this.state = state;
	}
    
    

}
