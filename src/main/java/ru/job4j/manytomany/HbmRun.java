package ru.job4j.manytomany;

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

            Author author1 = Author.of("Griboyedov");
            Author author2 = Author.of("Tolstoy");

            Book book1 = Book.of("War and peace");
            book1.getAuthors().add(author1);
            book1.getAuthors().add(author2);

            Book book2 = Book.of("Idiot");
            book2.getAuthors().add(author1);

            session.persist(book1);
            session.persist(book2);

            Book book = session.get(Book.class, book1.getId());
            session.remove(book);
            Author author = session.get(Author.class, author2.getId());
            session.remove(author);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
