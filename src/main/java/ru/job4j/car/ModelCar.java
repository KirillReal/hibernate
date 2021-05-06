package ru.job4j.car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carModel")
public class ModelCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public static ModelCar of(String name) {
        ModelCar model = new ModelCar();
        model.name = name;
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelCar modelCar = (ModelCar) o;
        return id == modelCar.id && Objects.equals(name, modelCar.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ModelCar{"
                + "id=" + id
                + ", name='" + name
                + '\''
                + '}';
    }
}
