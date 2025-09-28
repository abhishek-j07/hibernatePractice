package com.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Students.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        //create new
        /*Students students = new Students();
        students.setId(105);
        students.setFirstName("Yogender");
        students.setLastName("M");
        students.setAge(25);
        students.setTech("ML");
        session.persist(students);*/

        //fetch
        //Students s = session.find(Students.class, 101);
        //System.out.println(s);

        //update/create
        /*Students students1 =  new Students();
        students1.setId(101);
        students1.setFirstName("Abhi");
        students1.setLastName("J");
        students1.setAge(26);
        students1.setTech("IS");
        session.merge(students1);*/

        //remove/delete
        //Students s2 = session.find(Students.class, 103);
        //session.remove(s2);

        transaction.commit();

        session.close();
        factory.close();


    }
}