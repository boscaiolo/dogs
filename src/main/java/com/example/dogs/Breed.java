package com.example.dogs;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Breed {

        @Id
        @NotBlank(message = "Name is mandatory")
        private String name;

        @ElementCollection
        private Set<String> subBreeds = new HashSet<>();

        public Set<String> getSubBreeds() {
                return subBreeds;
        }

        public void setSubBreeds(Set<String> subBreeds) {
                this.subBreeds = subBreeds;
        }

        public void setSubBreed(String subBreed) {
                this.subBreeds.add(subBreed.toUpperCase());
        }

        public String getSubBreed() {
                return this.subBreeds.toString();
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name.toUpperCase();
        }
}
