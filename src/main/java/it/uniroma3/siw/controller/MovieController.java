package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Controller
public class MovieController {
    @Autowired MovieRepository movieRepository;
    @Autowired ArtistRepository artistRepository;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/formNewMovie")
    public String formNewMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "formNewMovie.html";
    }

    @PostMapping("/movies")
    public String newMovie(@ModelAttribute("movie") Movie movie, Model model) {
        if (!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())) {
            this.movieRepository.save(movie);
            model.addAttribute("movie", movie);
            return "movie.html";
        } else {
            model.addAttribute("messaggioErrore", "Questo film esiste già");
            return "formNewMovie.html";
        }
    }

    @GetMapping("/movies/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.movieRepository.findById(id).get());
        return "movie.html";
    }

    @GetMapping("/movies")
    public String showMovies(Model model) {
        model.addAttribute("movies", this.movieRepository.findAll());
        return "movies.html";
    }

    @GetMapping("/formSearchMovies")
    public String formSearchMovies() {
        return "formSearchMovies.html";
    }

    @PostMapping("/searchMovies")
    public String searchMovies(Model model, @RequestParam Integer year) {
        model.addAttribute("movies", this.movieRepository.findByYear(year));
        return "foundMovies.html";
    }

    @GetMapping("/indexMovies")
    public String indexMovies(){
        return "indexMovie.html";
    }

    @GetMapping("/manageMovies")
    public String manageMovies(Model model){
        model.addAttribute("movies", this.movieRepository.findAll());
        return "manageMovies.html";
    }

    @GetMapping("/formUpdateMovie/{id}")
    public String formUpdateMovie (@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.movieRepository.findById(id).get());
        return "formUpdateMovie.html";
    }

    @GetMapping("/addDirector/{id}")
    public String addDirector(@PathVariable("id") Long id, Model model){
        model.addAttribute("artists", this.artistRepository.findAll());
        model.addAttribute("movie", this.movieRepository.findById(id).get());
        return "addDirector.html"; 
    }
    
    @GetMapping("/setDirectorToMovie/{artistId}/{movieId}")
    public String setDirectorToMovie(@PathVariable("artistId") Long artistId, @PathVariable("movieId") Long movieId, Model model){
        Movie movie = this.movieRepository.findById(movieId).get();
        movie.setRegist(this.artistRepository.findById(artistId).get());
        model.addAttribute("movie", movie);
        return "formUpdateMovie.html";
    }

    @GetMapping("/removeDirectorToMovie/{movieId}")
    public String removeDirectorToMovie(@PathVariable("movieId") Long movieId, Model model){
        Movie movie = this.movieRepository.findById(movieId).get();
        movie.setRegist(null);
        model.addAttribute("movie", movie);
        return "formUpdateMovie.html";
    }

    @GetMapping("/updateActorsOnMovie/{movieId}")
    public String updateActorsOnMovie(@PathVariable("movieId") Long movieId, Model model){
        Movie currentMovie = this.movieRepository.findById(movieId).get();
        List<Artist> notActing = this.artistRepository.getArtistByMovieActedInNotContains(currentMovie);

        model.addAttribute("movie", currentMovie);
        model.addAttribute("notActing", notActing);

        return "updateActorsOnMovie.html";
    }

    @GetMapping("/addActorToMovie/{movieId}/{artistId}")
    public String addActorToMovie(@PathVariable("movieId") Long movieId, @PathVariable("artistId") Long artistId, Model model){
        Movie movie = this.movieRepository.findById(movieId).get();
        Artist artist = this.artistRepository.findById(artistId).get();

        List<Artist> movieActors = movie.getActors();
        movieActors.add(artist);
        movie.setActors(movieActors);
        this.movieRepository.save(movie);
        List<Movie> movies = artist.getMovieActedIn();
        movies.add(movie);
        artist.setMovieActedIn(movies);
        this.artistRepository.save(artist);
        model.addAttribute("movie", movie);
        List<Artist> notActing = this.artistRepository.getArtistByMovieActedInNotContains(movie);
        
        model.addAttribute("notActing", notActing);
        return "updateActorsOnMovie.html";
    }

}
