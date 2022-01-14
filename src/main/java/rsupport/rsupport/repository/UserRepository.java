package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
