package com.fragma.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fragma.entity.OAuth2UserInfo;

@Service
public class OAuth2UserInfoService {
	
	 public OAuth2UserInfo getUserInfo(String provider,Map<String, Object> attributes) {
	            
	            

	        if (provider.equals("google")) {

	            return new OAuth2UserInfo("google",(String) attributes.get("sub"),(String) attributes.get("name"),(String) attributes.get("email"),(String) attributes.get("picture"));
	        }

	        if (provider.equals("github")) {

	            return new OAuth2UserInfo("github",String.valueOf(attributes.get("id")),(String) attributes.get("name"),(String) attributes.get("email"),(String) attributes.get("avatar_url"));
	        }

	        throw new IllegalArgumentException( "Unsupported provider: " + provider);
	    }
}
