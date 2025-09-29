package com.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Students.class);
        config.addAnnotatedClass(StudentAddress.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        //crudOps(session);

        //imageDemo(session);

        embedDemo(session);

        transaction.commit();

        session.close();
        factory.close();

    }

    private static void imageDemo(Session session) {

        StudentAddress address = new StudentAddress();
        address.setActive(true);
        address.setCity("Delhi");
        address.setPincode(110099);
        address.setStreet("CP");
        address.setDateAdded(new Date());
        address.setX(123.11);

        try {
            FileInputStream fis = new FileInputStream("src/main/resources/img.jpg");
            byte[] img = new byte[fis.available()];
            fis.read(img);
            address.setImage(img);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        session.persist(address);
    }

    private static void crudOps(Session session) {

        //create new
        Students students = new Students();
        students.setId(105);
        students.setFirstName("Yogender");
        students.setLastName("M");
        students.setAge(25);
        students.setTech("ML");
        session.persist(students);

        //fetch
        Students s = session.find(Students.class, 101);
        System.out.println(s);

        //update/create
        Students students1 =  new Students();
        students1.setId(101);
        students1.setFirstName("Abhi");
        students1.setLastName("J");
        students1.setAge(26);
        students1.setTech("IS");
        session.merge(students1);

        //remove/delete
        Students s2 = session.find(Students.class, 103);
        session.remove(s2);
    }

    private static void embedDemo(Session session) {

        Students students = new Students();
        students.setFirstName("Aditya");
        students.setLastName("M");
        students.setAge(29);
        students.setTech("AI");

        Certificate certificate = new Certificate();
        certificate.setCourse("AI/ML");
        certificate.setDuration(3);

        students.setCertificate(certificate);

        session.persist(students);
    }
}