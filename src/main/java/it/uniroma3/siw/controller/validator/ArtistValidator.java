package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Artist;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ArtistValidator implements Validator {

    @Override
    public boolean supports(Class<?> classname) {
        return Artist.class.equals(classname);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Artist artist = (Artist) target;

    }
}
