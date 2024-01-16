package org.lessons.java.springlamiapizzeriacrud.controller;

import org.lessons.java.springlamiapizzeriacrud.model.Offer;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            Pizza pizzaToOffer = result.get();
            model.addAttribute("pizza", pizzaToOffer);

            Offer newOffer = new Offer();
            newOffer.setPizza(pizzaToOffer);
            model.addAttribute("offer", newOffer);

            return "offer/create";

        }  else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con Id: " + pizzaId + " non trovata");
        }
    }

    @PostMapping("/create")
    public String store(Offer formOffer) {
        Offer storedOffer = offerRepository.save(formOffer);
        return "redirect:/pizza/show/" + storedOffer.getPizza().getId();
    }

}
