package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        Credentials credentials = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            userDetails = (UserDetails)authentication.getPrincipal();
            credentials = credentialsService.getCredentials(userDetails.getUsername());
        }
        if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "admin/indexAdmin.html";

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("movies", this.movieRepository.findAll());
        return "index.html";
    }
    @GetMapping("/index")
    public String index2(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        Credentials credentials = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            userDetails = (UserDetails)authentication.getPrincipal();
            credentials = credentialsService.getCredentials(userDetails.getUsername());
        }
        if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "admin/indexAdmin.html";

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("movies", this.movieRepository.findAll());
        return "index.html";
    }

    @GetMapping(value = "/login")
    public String showLoginForm (Model model) {
        return "formLogin.html";
    }

    @GetMapping(value = "/register")
    public String showRegisterForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "formRegister.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult, @Valid
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "formLogin.html";
        }
        return "formRegister.html";
    }

    @GetMapping("/artist/{id}")
    public String artist(@PathVariable("id") Long id, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            userDetails = (UserDetails)authentication.getPrincipal();
        }
        model.addAttribute("userDetails", userDetails);


        Artist artist = this.artistRepository.findById(id).get();
        Image profilePic = artist.getProfilePicture(); //è una string rappresentante l'immagine in base64
        model.addAttribute("artist", this.artistRepository.findById(id).get());
        model.addAttribute("profilePic", profilePic);
        
        return "artist.html";
    }

    @GetMapping("/movie/{id}")
    public String movie(@PathVariable("id") Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            userDetails = (UserDetails)authentication.getPrincipal();
        }
        model.addAttribute("userDetails", userDetails);

        Movie movie = this.movieRepository.findById(id).get();
        Image image = movie.getImage();

        model.addAttribute("movie", movie);
        model.addAttribute("image", image);
        return "movie.html";
    }
    

}
