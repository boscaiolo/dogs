package com.example.dogs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class DogsController {


    private BreedRepository breedRepository;

    @Autowired
    public DogsController(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @PostConstruct
    public void init(){
        ObjectMapper jsonMapper = new ObjectMapper();

        InputStream jsonFile = null;
        List<Breed> breeds = null;
        try {
            jsonFile = new ClassPathResource("dogs.json").getInputStream();
            JsonNode jsonNode = jsonMapper.readTree(jsonFile);
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while(fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode field = jsonNode.get(fieldName);

                Breed breed = new Breed();
                breed.setName(fieldName);

                Set<String> subBreeds = new HashSet<>();
                for(JsonNode subBreed : field) {
                    subBreeds.add(subBreed.asText());
                }
                breed.setSubBreeds(subBreeds);
                try {
                    breedRepository.save(breed);
                }catch (DataIntegrityViolationException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            breedRepository.save(breed);
        } catch (DataIntegrityViolationException e){
            model.addAttribute("error", "Breed: '" + breed.getName() + "' already exists.");
            return "add-breed";
        }

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
