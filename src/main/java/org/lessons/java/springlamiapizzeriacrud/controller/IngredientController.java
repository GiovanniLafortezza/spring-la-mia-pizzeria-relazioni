package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.repository.IngredientRepository;
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

     @GetMapping("/create")
     public String create(Model model) {
         Ingredient newIngredient = new Ingredient();
         model.addAttribute("ingredient", newIngredient);
         return "ingredients/create";
     }

     @GetMapping("/edit/{id}")
     public String edit(@PathVariable Integer id, Model model) {
         Optional<Ingredient> result = ingredientRepository.findById(id);
         if (result.isPresent()) {
             model.addAttribute("ingredient", result.get());
             return "ingredients/edit";
         }
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente con id : " + id + " non trovato");
     }

     @PostMapping("/edit/{id}")
     public String update(@PathVariable Integer id, @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ingredients/edit";
        } else {
            Ingredient updateIngredient =  ingredientRepository.save(formIngredient);
            return "redirect:/ingredient";
        }
     }


     @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
         Optional<Ingredient> result = ingredientRepository.findById(id);
         if (result.isPresent()) {
             ingredientRepository.deleteById(id);
             redirectAttributes.addFlashAttribute("redirectMessage", "Ingrediente " + result.get().getName() + " è stato eliminato");
             return "redirect:/ingredient";
         } else
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente con id : " + id + " non trovato");

     }
}
