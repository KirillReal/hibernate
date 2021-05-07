package ru.job4j.lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "mark")
    private List<Model> modelCarsList = new ArrayList<>();


    public static Mark of(String name) {
        Mark mark = new Mark();
        mark.name = name;
        return mark;
    }

    public Mark() {
    }

    public Mark(int id) {
        this.id = id;
    }

    public Mark(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCar(Model modelCars) {
        this.modelCarsList.add(modelCars);
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

    public List<Model> getModelCarsList() {
        return modelCarsList;
    }

    public void setModelCarsList(List<Model> modelCarsList) {
        this.modelCarsList = modelCarsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mark mark = (Mark) o;
        return id == mark.id
                && Objects.equals(name, mark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Mark{"
                + "id=" + id
                + ", name='" + name
                + '\'' + '}';
    }
}
