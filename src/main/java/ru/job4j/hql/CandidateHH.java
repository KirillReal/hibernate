package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidate")
public class CandidateHH {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_of_vacancy_id")
    private BaseVac baseVac;

    public static CandidateHH of(String name) {
        CandidateHH candidateHH = new CandidateHH();
        candidateHH.setName(name);
        return candidateHH;
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

    public BaseVac getVacancyDb() {
        return baseVac;
    }

    public void setVacancyDb(BaseVac vacancyDb) {
        this.baseVac = vacancyDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateHH that = (CandidateHH) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CandidateHH{"
                + "id=" + id
                + ", name='" + name + '\''
                + "}";
    }
}
