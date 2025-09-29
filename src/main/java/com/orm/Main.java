package com.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Students.class);
        config.addAnnotatedClass(StudentAddress.class);
        config.addAnnotatedClass(Answer.class);
        config.addAnnotatedClass(Question.class);
        config.addAnnotatedClass(Employee.class);
        config.addAnnotatedClass(Project.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        //crudOps(session);

        //imageDemo(session);

        //embedDemo(session);

        //oneToOneMapping(session);

        //oneToManyMapping(session);

        manyToManyMapping(session);

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

    private static void oneToOneMapping(Session session) {

        Question question = new Question();
        question.setQuestion("What is JAVA?");

        Answer answer = new Answer();
        answer.setAnswer("Java is a programming language");

        //question.setAnswer(answer);

        session.persist(answer);
        session.persist(question);
    }

    private static void oneToManyMapping(Session session) {

        Question question = new Question();
        question.setQuestion("What is JAVA?");

        Answer answer = new Answer();
        answer.setAnswer("Java is a programming language");
        answer.setQuestion(question);

        Answer answer2 = new Answer();
        answer2.setAnswer("OOPs bases language");
        answer2.setQuestion(question);

        Answer answer3 = new Answer();
        answer3.setAnswer("Java is a secured language");
        answer3.setQuestion(question);

        List<Answer> answers = new ArrayList<Answer>();
        answers.add(answer);
        answers.add(answer2);
        answers.add(answer3);

        question.setAnswers(answers);

        session.persist(answer);
        session.persist(answer2);
        session.persist(answer3);
        session.persist(question);
    }

    private static void manyToManyMapping(Session session) {

        Employee employee = new Employee();
        employee.setEmployeeName("Aditya");

        Employee employee1 = new Employee();
        employee1.setEmployeeName("Abhi");

        Project project = new Project();
        project.setProjectName("Java");

        Project project1 = new Project();
        project1.setProjectName("React");

        Project project2 = new Project();
        project2.setProjectName("ML");

        Project project3 = new Project();
        project3.setProjectName("AI");

        Project project4 = new Project();
        project4.setProjectName("RUST");

        List<Project> e1Projects = new ArrayList<>();
        e1Projects.add(project1);
        e1Projects.add(project2);
        e1Projects.add(project3);

        employee.setProjects(e1Projects);

        List<Project> e2Projects = new ArrayList<>();
        e2Projects.add(project3);
        e2Projects.add(project4);
        e2Projects.add(project);

        employee1.setProjects(e2Projects);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);

        project.setEmployees(employeeList);

        session.persist(project);
        session.persist(project1);
        session.persist(project2);
        session.persist(project3);
        session.persist(project4);

        session.persist(employee);
        session.persist(employee1);



    }
}