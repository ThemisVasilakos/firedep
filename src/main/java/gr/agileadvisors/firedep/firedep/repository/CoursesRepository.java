package gr.agileadvisors.firedep.firedep.repository;

import gr.agileadvisors.firedep.firedep.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Courses,String> {
}
