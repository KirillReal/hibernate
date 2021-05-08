package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbmCandidate {
    private static final Logger LOG = LoggerFactory.getLogger(HbmCandidate.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate candidate1 = Candidate.of("Oleg", 0, 100000);
            Candidate candidate2 = Candidate.of("Igor", 5, 500000);
            Candidate candidate3 = Candidate.of("Olga", 2, 1000);

            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);

            Query query = session.createQuery("from Candidate");
            for (Object candidate : query.list()) {
                System.out.println(candidate);
            }
            LOG.debug("---------------------");

            query = session.createQuery("from Candidate can where can.id = :Cid");
            query.setParameter("Cid", candidate1.getId());
            LOG.debug("------Кандидат по Id------");
            LOG.debug(query.uniqueResult().toString());

            LOG.debug("---------------------");
            query = session.createQuery("from Candidate can where can.name = :cname");
            query.setParameter("cname", candidate2.getName());
            LOG.debug("----------Кандидат по имени----------");
            LOG.debug(query.uniqueResult().toString());
            LOG.debug("---------------------");

            session.createQuery(
                    "update Candidate can set can.salary = :newSalary where can.id = :cid"
            )
                    .setParameter("newSalary", 99999)
                    .setParameter("cid", candidate3.getId())
                    .executeUpdate();
            LOG.debug("----------Изменение кандидата----------");
            LOG.debug(session.get(Candidate.class, candidate3.getId()).toString());
            LOG.debug("---------------------");

            session.createQuery("delete from Candidate can where can.id = :cid")
                    .setParameter("cid", candidate1.getId())
                    .executeUpdate();
            query = session.createQuery("from Candidate");
            LOG.debug("----------Удаление кандидата----------");
            for (Object candidate : query.list()) {
                System.out.println(candidate);
            }

            session.getTransaction().rollback();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    }
