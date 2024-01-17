package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Offer;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String store(@Valid Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formOffer.getPizza());
            return "offer/create";
        }
        if (formOffer.getEndDate() != null && formOffer.getEndDate().isBefore(formOffer.getStartDate())) {
            formOffer.setEndDate(formOffer.getStartDate().plusDays(10));
        }
        Offer storedOffer = offerRepository.save(formOffer);
        return "redirect:/pizza/show/" + storedOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Offer> result = offerRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("offer", result.get());
            return "offer/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offerta con id : " + id + " non trovata");
        }

    }

    @PostMapping("edit/{id}")
    public String update( @PathVariable Integer id, @Valid Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formOffer.getPizza());
            return "offer/edit";
        }
        if (formOffer.getEndDate() != null && formOffer.getEndDate().isBefore(formOffer.getStartDate())) {
            formOffer.setEndDate(formOffer.getStartDate().plusDays(10));
        }
            Offer updateOffer = offerRepository.save(formOffer);
            return "redirect:/pizza/show/" + updateOffer.getPizza().getId();

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Offer> result = offerRepository.findById(id);
        if (result.isPresent()) {
            Offer offetToDelete = result.get();
            offerRepository.delete(offetToDelete);
            return "redirect:/pizza/show/" + offetToDelete.getPizza().getId();
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offerta con id : " + id + " non trovata");
    }

}
