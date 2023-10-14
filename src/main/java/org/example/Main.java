package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dto.CountedEnrollmentForStudent;
import org.example.dto.EnrolledStudent;
import org.example.entities.Course;
import org.example.entities.Enrollment;
import org.example.entities.Student;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String puName = "pu-name";
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "none"); // create ,update, none

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

//            Student s = new Student();
//            s.setName("Yash");
//
//
//            Course c = new Course();
//            c.setTitle("Clg");
//
//
//            Enrollment e = new Enrollment();
//            e.setEnrollmentDate(LocalDate.now());
//            e.setStudent(s);
//            e.setCourse(c);
//
//            em.persist(s);
//            em.persist(c);
//            em.persist(e);


//            String jpql = """
//                    SELECT s,e FROM Student s INNER JOIN s.enrollments e
//                    """;

//            String jpql = """
//                    SELECT s,e FROM Student s  JOIN s.enrollments e
//                    """;

//            String jpql = """
//                    SELECT s,e FROM Student s,Enrollment e WHERE s.id= e.student.id
//                    """;

//            String jpql = """
//                    SELECT s,e FROM Student s,Enrollment e WHERE s= e.student
//                    """;

//            String jpql = """
//
//                      SELECT s,e FROM Student s left JOIN s.enrollments e
//                     """;
//            String jpql = """
//
//                      SELECT s,e FROM Student s right JOIN s.enrollments e
//                     """;

//            String jpql = """
//
//                      SELECT NEW org.example.dto.EnrolledStudent(s,e)  FROM Student s right JOIN s.enrollments e
//                     """;
//
//            String jpql = """
//
//                      SELECT s FROM Student  s  WHERE (SELECT COUNT (e) FROM Enrollment  e WHERE e.student.id =s.id) >0
//
//                     """;
            String jpql = """

                      SELECT new org.example.dto.CountedEnrollmentForStudent(s,(SELECT COUNT (e) FROM Enrollment e where e.student = s))
                      FROM Student s
                  
                     """;


            TypedQuery<CountedEnrollmentForStudent> oq = em.createQuery(jpql,CountedEnrollmentForStudent.class);
            oq.getResultList().forEach(o -> System.out.println(o.s()+" "+o.count()));



//            TypedQuery<EnrolledStudent> oq = em.createQuery(jpql,EnrolledStudent.class);
//            oq.getResultList().forEach(o -> System.out.println(o.student() + " " + o.enrollment()));



//            TypedQuery<Object[]> oq = em.createQuery(jpql,Object[].class);
//           oq.getResultList().forEach(o -> System.out.println(o[0] + " " + o[1]));


            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}