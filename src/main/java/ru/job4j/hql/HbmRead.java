package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbmRead {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRead.class.getName());

    public static void main(String[] args) {
        CandidateHH candidateHH = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            candidateHH = session.createQuery(
                    "select distinct can from CandidateHH can "
                            + "join fetch can.baseVac vacDb "
                            + "join fetch vacDb.vacancies "
                            + "where can.id = :id", CandidateHH.class
            )
                    .setParameter("id", 1)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        assert candidateHH != null;
        LOG.debug(candidateHH.getVacancyDb().getVacancies().toString());
    }
}
