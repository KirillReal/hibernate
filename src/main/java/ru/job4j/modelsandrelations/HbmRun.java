package ru.job4j.modelsandrelations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Engine engine = Engine.of("123");
            CarRelation bmw = CarRelation.of("БМВ", engine);
            Engine engine2 = Engine.of("1234");
            CarRelation audi = CarRelation.of("audi", engine2);
            Driver driver1 = Driver.of("Петя");
            Driver driver2 = Driver.of("Костя");
            bmw.addDriver(driver1);
            driver1.addCar(bmw);
            audi.addDriver(driver2);
            driver2.addCar(audi);

            session.persist(bmw);
            session.persist(driver2);

            CarRelation bmwDb = session.get(CarRelation.class, bmw.getId());
            System.out.println("--------------------------");
            System.out.println(bmwDb);
            System.out.println(bmwDb.getDrivers());
            System.out.println("--------------------------");
            CarRelation audiDb = session.get(CarRelation.class, audi.getId());
            System.out.println("--------------------------");
            System.out.println(audiDb);
            System.out.println(audiDb.getDrivers());
            System.out.println("--------------------------");

            Driver driver1Db = session.get(Driver.class, driver1.getId());
            System.out.println("--------------------------");
            System.out.println(driver1Db);
            System.out.println(driver1Db.getCars());
            System.out.println("--------------------------");
            Driver driver2Db = session.get(Driver.class, driver2.getId());
            System.out.println("--------------------------");
            System.out.println(driver2Db);
            System.out.println(driver2Db.getCars());
            System.out.println("--------------------------");

            session.delete(driver1);
            session.delete(bmw);
            audiDb = session.get(CarRelation.class, audi.getId());
            System.out.println("--------------------------");
            System.out.println(audiDb);
            System.out.println(audiDb.getDrivers());
            System.out.println("--------------------------");
            driver2Db = session.get(Driver.class, driver2.getId());
            System.out.println("--------------------------");
            System.out.println(driver2Db);
            System.out.println(driver2Db.getCars());
            System.out.println("--------------------------");
            session.delete(audiDb);
            session.delete(driver2);

            List<CarRelation> cars = session.createQuery("from CarRelation ").list();
            System.out.println("--------------------------");
            System.out.println(cars);
            System.out.println("--------------------------");
            List<Engine> engines = session.createQuery("from Engine").list();
            System.out.println("--------------------------");
            System.out.println(engines);
            System.out.println("--------------------------");
            List<Driver> drivers = session.createQuery("from Driver ").list();
            System.out.println("--------------------------");
            System.out.println(drivers);
            System.out.println("--------------------------");

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
