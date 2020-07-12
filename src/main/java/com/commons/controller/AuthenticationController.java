package com.commons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.commons.model.Authentication;
import com.commons.service.ConferanceService;
import com.commons.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	UserService userService;
	
	@Autowired
	ConferanceService conferanceService;
	
	@GetMapping(value = "/")
	public ModelAndView loginView() {
		return new ModelAndView("login", "authentication", new Authentication());
	}

	@PostMapping(value = "/auth")
	public ModelAndView authentication(@ModelAttribute("authentication") Authentication auth) {
		ModelAndView mv ;
		if(userService.isAuthenticate(auth)) {
			mv = new ModelAndView("dashboard");
			mv.addObject("duration", conferanceService.fetchAllDuration());
		}else {
			mv = new ModelAndView("login");
		}
		return mv;
	}
	
	@PostMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        System.out.println("logout()");
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }
}
