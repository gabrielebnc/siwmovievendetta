package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.PictureRepository;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieValidator movieValidator;
    
    @GetMapping("/admin/formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "/admin/formNewMovie.html";
    }

    @PostMapping("/admin/uploadMovie")
    public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, @RequestParam("file") MultipartFile[] images) throws IOException {
        this.movieValidator.validate(movie,bindingResult);
        if(!bindingResult.hasErrors()){
            List<Image> movieImgs = new ArrayList<>();

            for(MultipartFile image : images){
                Image picture = new Image(image.getBytes());
                this.pictureRepository.save(picture);
                movieImgs.add(picture);
            }

            movie.setImages(movieImgs);
            this.movieRepository.save(movie);

            model.addAttribute("movie",movie);
            model.addAttribute("pictures",movieImgs);
            return "movie.html";
        } else {
            return "/admin/formNewMovie.html";
        }
    }

    @GetMapping("/admin/formNewArtist")
    public String newArtist(Model model){
        model.addAttribute("artist",new Artist());
        return "formNewArtist.html";
    }

    /*TODO ADD POST NEW ARTIST AND MOVIE-ARTISTS LINKING METHODS*/
}