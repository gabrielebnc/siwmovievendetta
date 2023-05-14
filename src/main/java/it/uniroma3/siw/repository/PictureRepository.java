package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Image,Long> {
}
