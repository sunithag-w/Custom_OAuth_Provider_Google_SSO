package com.fragma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
	@GetMapping("/logout")
    public String logout(HttpSession session) {

        // Remove logged-in user's session
        session.invalidate();

        // Go back to /
        return "redirect:/";
    }
}
