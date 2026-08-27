package com.fragma.controller;

import java.io.IOException;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fragma.entity.OAuth2UserInfo;
import com.fragma.entity.OAuthProvider;
import com.fragma.entity.Userdata1;
import com.fragma.oauth.OAuth2AuthorizationRequest;
import com.fragma.oauth.OAuthProperties;
import com.fragma.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class OAuthController {
	
	@Autowired
	 private OAuthProperties oauthProperties;
	@Autowired
	private UserRepository userRepository;
	 
	
	@GetMapping("/")
	public String Home() {
		return "index";
	}
	  @GetMapping("/oauth/login/{provider}")
	    public void login( @PathVariable String provider,HttpServletResponse response) throws IOException {
         
	        response.sendRedirect("/customOAuth2/authorization/" + provider);
	    }
	  @GetMapping("/oauth2/login-success")
	    public String loginSuccess( HttpSession session,Model model) {
	           

	        OAuth2UserInfo userInfo = (OAuth2UserInfo) session.getAttribute("OAUTH2_USER");
	        if (userInfo == null) {

	            return "redirect:/";
	        }

	     String email = userInfo.getEmail();
	     String name = userInfo.getName();
	     String provider=userInfo.getProvider();
	     String picture = null;
		    if (provider.equals("google")) {
               picture = userInfo.getPicture();
		    } 
		    else {	       
		        picture = userInfo.getPicture();  
		    }
		
		    Userdata1 userData = (email != null) ? userRepository.findByEmail(email).orElse(null) : null;
		if (userData != null) {
			model.addAttribute("user", userData);
			return "profile";
		}
		else {
			Userdata1 user1 = new Userdata1();
			System.out.println("email : " + email);
			user1.setName(name);
			user1.setEmail(email);
			user1.setPicture(picture);

			model.addAttribute("user", user1);
			return "registration";
		}
	}
	
	@PostMapping("/register123")
	public String register(@ModelAttribute Userdata1 user, Model model) {

	    try {
	    	userRepository.save(user);
	        return "redirect:/oauth2/login-success";

	    } catch (Exception e) {

	        model.addAttribute("user", user);
	        model.addAttribute("error", "Registration failed. Please try again.");
	        return "registration";
		}
	}

	       
	    }

