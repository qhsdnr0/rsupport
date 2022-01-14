package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
