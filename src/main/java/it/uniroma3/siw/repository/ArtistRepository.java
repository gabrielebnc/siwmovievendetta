package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist,Long> {

}
