package com.example.dogs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Breed {

        @Id
        @NotBlank(message = "Name is mandatory")
        private String name;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name.toUpperCase();
        }
}
