package com.example.dogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String showAddForm(Breed breed) {
        return "add-breed";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid breed Id:" + id));

        model.addAttribute("breed", breed);
        return "update-breed";
    }

    @GetMapping("/delete/{id}")
    public String deleteBreed(@PathVariable("id") long id, Model model) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid breed Id:" + id));
        breedRepository.delete(breed);
        model.addAttribute("breeds", breedRepository.findAll());
        return "index";
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

    @PostMapping("/update/{id}")
    public String updateBreed(@PathVariable("id") long id, @Valid Breed breed,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            breed.setId(id);
            return "update-breed";
        }

        breedRepository.save(breed);
        model.addAttribute("breeds", breedRepository.findAll());
        return "index";
    }
}
