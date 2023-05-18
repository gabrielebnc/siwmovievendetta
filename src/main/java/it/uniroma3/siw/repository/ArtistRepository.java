package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist,Long> {
    boolean existsByNameAndSurnameAndBirthDate(String name, String surname, LocalDate birthDate);
    List<Artist> getArtistByActedMoviesNotContains(Movie movie);
}
