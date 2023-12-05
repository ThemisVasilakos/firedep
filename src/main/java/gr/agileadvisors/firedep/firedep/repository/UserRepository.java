package gr.agileadvisors.firedep.firedep.repository;

import gr.agileadvisors.firedep.firedep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
