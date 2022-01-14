package rsupport.rsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
