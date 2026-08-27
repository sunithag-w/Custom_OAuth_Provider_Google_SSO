package com.fragma.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fragma.entity.OAuthProvider;
import com.fragma.oauth.OAuth2AuthorizationRequest;
import com.fragma.oauth.OAuthProperties;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomOAuth2AuthorizationFilter extends OncePerRequestFilter{

    @Autowired
    private  OAuthProperties oauthProperties;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		 String path = request.getRequestURI();

		   String prefix = "/customOAuth2/authorization/";
	               

		   if (path.startsWith(prefix)) {

			   String provider =path.substring(prefix.length());
	                    
	        OAuthProvider providerProperties = oauthProperties.getProviders().get(provider);
	               
	        if (providerProperties == null) {

	            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Provider not found");

	            return;
	        }
	            
	            String clientId=providerProperties.getClientId();

	            String redirectUri=providerProperties.getRedirectUri();

	            String authorizationUri=providerProperties.getAuthorizationUri();

	            Set<String> scopes=providerProperties.getScope();
	            
	            String state=UUID.randomUUID().toString();
	        
	            
	            OAuth2AuthorizationRequest authorizationRequest =new OAuth2AuthorizationRequest( authorizationUri, redirectUri,clientId,scopes, state,"code");

	           
	            request.getSession().setAttribute( "OAUTH2_AUTHORIZATION_REQUEST", authorizationRequest);
	                   
	            
	            String authorizationUrl = authorizationRequest.getAuthorizationUri()
	                   
	                    + "?client_id="
	                    + URLEncoder.encode(
	                            authorizationRequest.getClientId(),
	                            StandardCharsets.UTF_8
	                    )
	                    + "&redirect_uri="
	                    + URLEncoder.encode(
	                            authorizationRequest.getRedirectUri(),
	                            StandardCharsets.UTF_8
	                    )
	                    + "&response_type="
	                    + URLEncoder.encode(
	                            authorizationRequest.getResponseType(),
	                            StandardCharsets.UTF_8
	                    )
	                    + "&scope="
	                    + URLEncoder.encode(
	                            String.join(
	                                    " ",
	                                    authorizationRequest.getScopes()
	                            ),
	                            StandardCharsets.UTF_8
	                    )

	                    + "&state="
	                    + URLEncoder.encode(
	                            state,
	                            StandardCharsets.UTF_8
	                    );

	            response.sendRedirect(authorizationUrl);
	           
	            return;
	        }

		   filterChain.doFilter(request, response);
	                   	
	}

}

