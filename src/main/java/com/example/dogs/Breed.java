package com.example.dogs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "breeds")
public class Breed {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Name is mandatory")
        @Size(max = 100)
        @Column(unique = true)
        private String name;

        @ElementCollection
        @CollectionTable(name = "sub_breeds", joinColumns = @JoinColumn(name = "breed_id"))
        @Column(name = "sub_breed")
        private Set<String> subBreeds = new HashSet<>();

        public Set<String> getSubBreeds() {
                return subBreeds;
        }

        public void setSubBreeds(Set<String> subBreeds) {
                this.subBreeds = subBreeds;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name.toUpperCase();
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }
}
