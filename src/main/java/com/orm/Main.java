package com.orm;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

        //manyToManyMapping(session);

        //lazyAndEagerLoading(session);

        //hibernateObjectStates(session);

        //hqlQuery(session);

        //insertMultipleRowsInDB(session);

        //hqlPagination(session);

        //nativeSqlQuery(session);

        //cascading(session);

        //firstLevelCache(session);

        //secondLevelCache(factory);

        criteriaAPI(session);

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

        Certificate certificate = new Certificate("AI",3);

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

    private static void lazyAndEagerLoading(Session session) {

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

        // not fetch answers within
        Question q = (Question) session.find(Question.class, 1);

        System.out.println(q.getQuestion());
        System.out.println(q.getQuestionId());

        // lazy loading here once we call getAnswers
        //System.out.println(q.getAnswers().size());
    }

    private static void hibernateObjectStates(Session session) {


        //transient state
        Students student = new Students();
        student.setFirstName("Aditya");
        student.setLastName("M");
        student.setAge(29);
        student.setTech("AI");

        Certificate certificate = new Certificate("Java",3);
        student.setCertificate(certificate);

        //persistent state - both in session and db
        session.persist(student);
        //student.setFirstName("Abhi");

        //session.close();
        //detached state - wont update this in db
        //student.setLastName("Mahim");
    }

    private static void hqlQuery(Session session) {

        String query = "from Students as s where s.firstName = :x";

        Query q = session.createQuery(query);
        q.setParameter("x", "Abhi");

        //single result

        //multiple result
        List<Students> studentsList = q.getResultList();

        for (Students student : studentsList) {
            System.out.println(student.getFirstName() + " - " + student.getCertificate());
        }

        /*Query deleteQuery = session.createQuery("" +
                "delete Students s where s.firstName = :x");
        deleteQuery.setParameter("x", "Abhi");
        int r =  deleteQuery.executeUpdate();
        System.out.println(r);*/

        Query joinQuery = session.createQuery("select q.questionId, a.answer" +
                " from Question as q INNER JOIN " +
                "q.answers as a");

        List<Object[]> list = joinQuery.getResultList();

        for(Object[] obj : list){
            System.out.println(Arrays.toString(obj));
        }
    }

    private static void hqlPagination(Session session) {

        String query = "from Students";

        Query<Students> q = session.createQuery(query);

        q.setFirstResult(5);
        q.setMaxResults(10);

        List<Students> studentsList = q.list();

        for(Students student : studentsList){
            System.out.println(student.getFirstName() + " - " + student.getCertificate().getCourse());
        }

    }

    private static void insertMultipleRowsInDB(Session session) {

        for(int i = 0 ; i < 50 ; i++){

            Students student = new Students();
            student.setFirstName("StudentFirstName " + String.valueOf(i));
            student.setLastName("StudentLastName " + String.valueOf(i));
            student.setAge(i+10);
            student.setTech("Tech " + String.valueOf(i));
            student.setCertificate(new Certificate("Course" + String.valueOf(i), i+2));

            session.persist(student);
        }
    }

    private static void nativeSqlQuery(Session session) {

        String query = "select * from Students";
        NativeQuery nq = session.createNativeQuery(query);

        List<Object[]> studentsList = nq.list();

        for(Object[] student : studentsList){
            System.out.println(student[4] + " - " + student[5]);
        }
    }

    private static void cascading(Session session) {

        Question q1 = new Question();
        q1.setQuestion("What is Cascading ?");

        Answer answer1 = new Answer();
        answer1.setAnswer("Cascading is a concept");

        Answer answer2 = new Answer();
        answer2.setAnswer("Cascading is a tech concept");

        Answer answer3 = new Answer();
        answer3.setAnswer("Cascading is a important concept");

        List<Answer> answersList = new ArrayList<>();
        answersList.add(answer1);
        answersList.add(answer2);
        answersList.add(answer3);

        q1.setAnswers(answersList);

        session.persist(q1);

    }

    private static void firstLevelCache(Session session) {

        //by default enabled

        Students student1 = session.find(Students.class, 23);

        //this tome query won't be fired again
        Students student2 = session.find(Students.class, 23);
        System.out.println(session.contains(student2));

    }

    private static void secondLevelCache(SessionFactory sf) {

        Session session1 = sf.openSession();
        Students students1 = session1.find(Students.class, 23);
        System.out.println(students1);
        session1.close();

        Session session2 = sf.openSession();
        Students students2 = session2.find(Students.class, 23);
        System.out.println(students2);
        session1.close();
    }

    private static void criteriaAPI(Session session) {

        Criteria c = session.createCriteria(Students.class);
        c.add(Restrictions.eq("tech","Tech 0"));

        List<Students> studentsList = c.list();

        for(Students student : studentsList){
            System.out.println(student.getFirstName() + " - " + student.getCertificate().getCourse());
        }
    }
}