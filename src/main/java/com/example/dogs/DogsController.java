package com.example.dogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class DogsController {


    private BreedRepository breedRepository;

    @Autowired
    public DogsController(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @GetMapping("/create")
    public String showAddForm(Breed user) {
        return "add-breed";
    }

    @GetMapping("/")
    public String refresh(Model model) {
        model.addAttribute("breeds", breedRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String add(@Valid Breed breed, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-breed";
        }

        breedRepository.save(breed);
        model.addAttribute("breeds", breedRepository.findAll());
        return "index";
    }
}
