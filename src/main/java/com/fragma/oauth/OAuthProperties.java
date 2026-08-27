package com.fragma.oauth;

import java.util.Map;


import java.util.HashMap;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fragma.entity.OAuthProvider;

@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

    private Map<String, OAuthProvider> providers =new HashMap<>();
           
    public void setProviders(Map<String, OAuthProvider> providers) {
        this.providers = providers;
    }
    
    public Map<String, OAuthProvider> getProviders() {
        return providers;
    }
    
   
    
}
