package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    // il Controller ha bisogno delle funzionalità del Repository
    @Autowired
    PizzaRepository pizzaRepository;
    // metodo index che mostra la lista di tute le pizze
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String searchKeyword, Model model) {
        List<Pizza> pizzaList;
        if (searchKeyword != null) {
            // se searchKeyword è presente faccio la ricerca per nome
            pizzaList = pizzaRepository.findByNameContaining(searchKeyword);
        } else {
            // altrimenti recupero la lista di tutte le pizze dal database
            pizzaList = pizzaRepository.findAll();
        }
        // aggiungo la lista di pizze agli attributi del Model
        model.addAttribute("pizzaList", pizzaList);
        // precarico il value dell'input di ricerca con la stringa searchKeyword
        model.addAttribute("preloadSearch", searchKeyword);

        return "pizza/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            return "pizza/show";
        } else {
            // gestisco il caso in cui nel database un Book con quell'id non c'è
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        Pizza pizza = new Pizza();
        model.addAttribute("pizza", pizza);
        return "pizza/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizza/create";
        } else {
            Pizza createPizza = pizzaRepository.save(formPizza);
            return "redirect:/pizza/show/" + createPizza.getId();
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "pizza/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza " + id + " non trovata");
        }

    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizza/edit";
        } else {
            Pizza updatePizza = pizzaRepository.save(formPizza);
            return "redirect:/pizza/show/" + updatePizza.getId();
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            pizzaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "La pizza " + result.get().getName() + " è stata eliminata");
            return "redirect:/pizza";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pizza con id: " + id + " non è stata trobvata");
        }
    }

}
