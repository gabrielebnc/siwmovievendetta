package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ArtistValidator;
import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.repository.MovieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private MovieValidator movieValidator;
    @Autowired
    private ArtistValidator artistValidator;
    
    @GetMapping("/admin/formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "/admin/formNewMovie.html";
    }

    @PostMapping("/admin/uploadMovie")
    public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws IOException {
        this.movieValidator.validate(movie,bindingResult);
        if(!bindingResult.hasErrors()){
            Image movieImg = new Image(image.getBytes());
            this.imageRepository.save(movieImg);
            movie.setImage(movieImg);
            this.movieRepository.save(movie);

            model.addAttribute("movie",movie);
            model.addAttribute("pictures",movieImg);
            return "movie.html";
        } else {
            return "/admin/formNewMovie.html";
        }
    }

    @GetMapping("/admin/formNewArtist")
    public String formNewArtist(Model model){
        model.addAttribute("artist",new Artist());
        return "/admin/formNewArtist.html";
    }

    @PostMapping("/admin/artist")
    public String newArtist(Model model,@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws  IOException{
        this.artistValidator.validate(artist, bindingResult);
        if(!bindingResult.hasErrors()){
            Image pic = new Image(image.getBytes());
            this.imageRepository.save(pic);
            artist.setProfilePicture(pic);
            this.artistRepository.save(artist);

            model.addAttribute("artist", artist);
            model.addAttribute("artistPic", pic );
            return "artist.html";
        }
        else {
            return "/admin/formNewArtist.html";
        }
    }


    /*TODO MOVIE-ARTISTS LINKING METHODS*/
}
