package acikgoz.kaan.UserSecurityAPI.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/homepage")
    public String homepage(@AuthenticationPrincipal UserDetails userDetails) {
        return "Welcome to the homepage, " +userDetails.getUsername()+ "!";
    }

}
