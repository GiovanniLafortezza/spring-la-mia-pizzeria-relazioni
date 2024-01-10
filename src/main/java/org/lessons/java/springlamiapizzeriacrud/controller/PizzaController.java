package org.lessons.java.springlamiapizzeriacrud.controller;

import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    // il Controller ha bisogno delle funzionalità del Repository
    @Autowired
    PizzaRepository pizzaRepository;
    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizzaList", pizzaList);

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
    public String create() {
        return "pizza/create";
    }

}
