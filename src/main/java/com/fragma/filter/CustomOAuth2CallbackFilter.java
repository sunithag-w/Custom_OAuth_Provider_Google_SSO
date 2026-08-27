package com.fragma.filter;


import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fragma.entity.OAuth2UserInfo;
import com.fragma.entity.OAuthProvider;
import com.fragma.oauth.OAuth2AuthorizationRequest;
import com.fragma.oauth.OAuthProperties;
import com.fragma.service.OAuth2UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomOAuth2CallbackFilter extends OncePerRequestFilter {
	@Autowired
	  private  OAuthProperties oauthProperties;
	@Autowired
	    private RestTemplate restTemplate;
	@Autowired
	private  OAuth2UserInfoService userInfoService;

    @Override
    protected void doFilterInternal( HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)  throws ServletException, IOException {
      
        String path = request.getRequestURI();

        String prefix = "/login/oauth2/code/";

        
        if (!path.startsWith(prefix)) {

            filterChain.doFilter(request, response);
            return;
        }

       
        String provider = path.substring(prefix.length());


        String code = request.getParameter("code");

      
        String returnedState = request.getParameter("state");

       

        
        OAuth2AuthorizationRequest savedRequest = (OAuth2AuthorizationRequest) request.getSession().getAttribute( "OAUTH2_AUTHORIZATION_REQUEST");
              
        if (savedRequest == null) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"OAuth2 authorization request not found in session");

            return;
        }
        String originalState = savedRequest.getState();

 
        if (!originalState.equals(returnedState)) {

            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid OAuth2 state");

            return;
        }

        
        OAuthProvider providerProperties =oauthProperties .getProviders().get(provider);
        
        MultiValueMap<String, String> body =new LinkedMultiValueMap<>();
                

        body.add("grant_type", "authorization_code");
        body.add("code", code);

        body.add("client_id",providerProperties.getClientId());

        body.add("client_secret", providerProperties.getClientSecret());

        body.add( "redirect_uri",providerProperties.getRedirectUri());


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType( MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<MultiValueMap<String, String>> tokenRequest =new HttpEntity<>(body, headers);
                
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(providerProperties.getTokenUri(),tokenRequest, Map.class);

        if (!tokenResponse.getStatusCode().is2xxSuccessful()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token request failed");
            return;
        }
        

        Map<String, Object> tokens = tokenResponse.getBody();
               
        if (tokens == null) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"No token response received");
            return;
        }
        String accessToken =(String) tokens.get("access_token");
        String userInfoUri =providerProperties.getUserInfoUri(); 
 

        HttpHeaders userInfoHeaders =new HttpHeaders();
                

        userInfoHeaders.setBearerAuth(accessToken);

        HttpEntity<Void> userInfoRequest =new HttpEntity<>(userInfoHeaders);
                
        ResponseEntity<Map> userInfoResponse =restTemplate.exchange( userInfoUri, HttpMethod.GET, userInfoRequest, Map.class);
                
        
        if (!userInfoResponse.getStatusCode().is2xxSuccessful()) {
                
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"UserInfo request failed");

            return;
        }

        Map<String, Object> userInfo = userInfoResponse.getBody();
               

        if (userInfo == null) {

            response.sendError( HttpServletResponse.SC_BAD_REQUEST, "No user information received");

            return;
        }
        
        OAuth2UserInfo oauth2User =userInfoService.getUserInfo(provider,userInfo);
             
        request.getSession().setAttribute("OAUTH2_USER",oauth2User);
   
        response.sendRedirect("/oauth2/login-success");

        return;
      
                

    }
}
