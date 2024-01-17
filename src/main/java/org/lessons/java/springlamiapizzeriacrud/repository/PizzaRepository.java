package org.lessons.java.springlamiapizzeriacrud.repository;

import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// interfaccia che eredita da JpaRepository tutti i metodi che permettono di fare le CRUD
// i generics chiedono: che tipo di dato è l'entità (Pizza) e che tipo di dato è la chiave primaria (Integer)
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByNameContaining(String search);

}
