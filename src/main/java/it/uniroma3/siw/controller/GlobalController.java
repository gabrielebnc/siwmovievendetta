package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    ArtistRepository artistRepository;

    /* TODO REFACTOR /INDEX AND / ROUTES */
    @GetMapping("/index")
    public String index2(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("usertype", "anonymous");
            return "index.html";
        }
        else {
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if(credentials.getRole().equals(Credentials.ADMIN_ROLE)){
                return "admin/indexAdmin.html";
            } else {
                model.addAttribute("usertype", "default");
                return "index.html";
            }
        }
    }

    @GetMapping(value = "/register")
    public String showRegisterForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "formRegister.html";
    }

    @GetMapping(value = "/login")
    public String showLoginForm (Model model) {
        return "formLogin.html";
    }

/* TODO REFACTOR / AND /INDEX ROUTES */
    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("usertype", "anonymous");
            return "index.html";
        }
        else {
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if(credentials.getRole().equals(Credentials.ADMIN_ROLE)){
                return "admin/indexAdmin.html";
            } else {
                model.addAttribute("usertype", "default");
                return "index.html";
            }
        }
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult, @Valid
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "formLogin.html";
        }
        return "formRegister.html";
    }

}