package gr.agileadvisors.firedep.firedep.repository;

import gr.agileadvisors.firedep.firedep.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
