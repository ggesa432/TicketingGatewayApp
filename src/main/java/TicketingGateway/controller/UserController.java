package TicketingGateway.controller;

import TicketingGateway.domain.User;
import TicketingGateway.repository.UserRepository;
import TicketingGateway.service.TicketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/13/2024
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TicketNotificationService ticketNotificationService;

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
            return "login";

    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }
        Optional<User> existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            model.addAttribute("error", "Username is already taken.");
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        userRepository.insertUserRole(savedUser.getId(), "ROLE_USER");
        model.addAttribute("success", "Registration successful! Please log in.");
        return "login";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(
            @RequestParam("email") String email,
            Model model) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            // Generate a reset token and set the expiry date
            String resetToken = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);// Token valid for 1 hour

            User userToUpdate = user.get();
            userToUpdate.setResetToken(resetToken);
            userToUpdate.setExpiryDate(expiryDate);
            userRepository.save(userToUpdate);

            String resetLink = "http://localhost:8282/reset-password?token=" + resetToken;
            String subject = "Password Reset Request";
            String body = "Dear User,\n\nClick the link below to reset your password:\n" + resetLink;

            // Use TicketNotificationService to send the email
            ticketNotificationService.sendPasswordResetEmail(email, subject, body,resetToken);

            model.addAttribute("message", "Password reset link has been sent to your email.");
        } else {
            model.addAttribute("error", "No account found with that email address.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> user = userRepository.findByResetToken(token);

        if (user.isPresent() && user.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            model.addAttribute("token", token); // Pass the token to the form
            return "reset-password"; // Show a form where the user can enter a new password
        } else {
            model.addAttribute("error", "Invalid or expired token.");
            return "error";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword, Model model) {
        Optional<User> user = userRepository.findByResetToken(token);

        if (user.isPresent() && user.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            User userToUpdate = user.get();
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userToUpdate.setResetToken(null); // Clear the token
            userToUpdate.setExpiryDate(null); // Clear the expiry date
            userRepository.save(userToUpdate);

            model.addAttribute("message", "Password reset successfully. You can now log in.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid or expired token.");
            return "error";
        }
    }

}
