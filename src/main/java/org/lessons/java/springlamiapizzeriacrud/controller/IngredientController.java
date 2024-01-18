package org.lessons.java.springlamiapizzeriacrud.controller;

import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
     @Autowired
    IngredientRepository ingredientRepository;

     @GetMapping
    public String index(Model model) {
         List<Ingredient> ingredientList = ingredientRepository.findAll();
         model.addAttribute("ingredientList", ingredientList);
         return "ingredients/index";
     }

     @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
         Optional<Ingredient> result = ingredientRepository.findById(id);
         if (result.isPresent()) {
             ingredientRepository.deleteById(id);
             return "ingredients/index";
         } else
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente con id : " + id + " non trovato");

     }
}
