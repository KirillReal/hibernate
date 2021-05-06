package ru.job4j.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            ModelCar one = ModelCar.of("x1");
            ModelCar two = ModelCar.of("x3");
            ModelCar three = ModelCar.of("x5");
            ModelCar four = ModelCar.of("x6");
            ModelCar five = ModelCar.of("m1");
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);
            Car car = Car.of("bmw");

            car.addModel(session.load(ModelCar.class, one.getId()));
            car.addModel(session.load(ModelCar.class, two.getId()));
            car.addModel(session.load(ModelCar.class, three.getId()));
            car.addModel(session.load(ModelCar.class, four.getId()));
            car.addModel(session.load(ModelCar.class, five.getId()));

            session.save(car);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
