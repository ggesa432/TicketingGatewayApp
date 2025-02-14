package TicketingGateway.controller;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/13/2024
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        return "home";
    }

}
