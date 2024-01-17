package org.lessons.java.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {
    //--------------- ATTRIBUTI -----------------
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Integer id;
    @NotEmpty(message = "Il nome non deve essere vuoto")
    @Column(nullable = false)
    private String name;
    @NotEmpty(message = "Inserire degli ingredienti!")
    @Column(nullable = false)
    @Lob
    private String description;
    private String image;
    @NotNull(message = "Inserire un prezzo minimo di 5.00")
    @DecimalMin(value = "5.00", message ="Inserire un prezzo minimo di 5.00" )
    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza")
    private List<Offer> offers;



    //--------------- GETTER E SETTER -----------------


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
