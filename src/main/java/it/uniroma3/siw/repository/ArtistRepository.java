package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    public boolean existsByNameAndSurnameAndNationality(String name, String surname, String nationality);
    public List<Artist> getArtistByMovieActedInNotContains(Movie movie);
}
