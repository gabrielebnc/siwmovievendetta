package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Controller
public class ArtistController {
    
    @Autowired MovieRepository movieRepository;
    @Autowired ArtistRepository artistRepository;

    @GetMapping("/indexArtists")
    public String indexArtists(){
        return "indexArtists.html";
    }

    @GetMapping("/formNewArtist")
    public String formNewArtist(Model model){
        model.addAttribute("artist", new Artist());
        return "formNewArtist.html";
    }

    @PostMapping("/artists")
    public String newArtist(@ModelAttribute("artist") Artist artist, Model model){ 
        if(!this.artistRepository.existsByNameAndSurnameAndNationality(artist.getName(), artist.getSurname(), artist.getNationality())) {
            this.artistRepository.save(artist);
            model.addAttribute("artist", artist);
            return "artist.html";
        } else {
            model.addAttribute("messaggioErrore", "Questo artista esiste già");
            return "formNewArtist.html";
        }
    }

}
