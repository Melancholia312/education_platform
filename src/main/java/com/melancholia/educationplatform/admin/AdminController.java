package com.melancholia.educationplatform.admin;

import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.user.UserDetailsServiceImpl;
import com.melancholia.educationplatform.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-panel")
    public String viewAllUsers(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "admin/panel";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/ban")
    public String banUser(@PathVariable("id") long id,
                          Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (((User) authentication.getPrincipal()).getEmail().equals(user.getEmail())) return "redirect:/admin-panel";
        userDetailsService.banAppUser(user.getEmail());
        return "redirect:/admin-panel";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/unban")
    public String unbanUser(@PathVariable("id") long id,
                            Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (((User) authentication.getPrincipal()).getEmail().equals(user.getEmail())) return "redirect:/admin-panel";
        userDetailsService.enableAppUser(user.getEmail());
        return "redirect:/admin-panel";
    }
}
