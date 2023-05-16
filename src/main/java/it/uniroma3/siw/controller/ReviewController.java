package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {
    @GetMapping("/user/formNewReview")
    public String newReview(Model model){
        model.addAttribute(new Review());
        return "user/formNewReview.html";
    }

    /*TODO ADD POST REVIEW*/

}
