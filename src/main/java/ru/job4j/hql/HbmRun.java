package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbmRun {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRun.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CandidateHH candidateHH = CandidateHH.of("Timur");
            BaseVac baseVac = BaseVac.of("Base");
            candidateHH.setVacancyDb(baseVac);
            Vacancy vacancy1 = Vacancy.of("Vacancy 1");
            Vacancy vacancy2 = Vacancy.of("Vacancy 2");
            baseVac.addVacancy(vacancy1);
            baseVac.addVacancy(vacancy2);

            session.save(candidateHH);
            session.save(baseVac);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
