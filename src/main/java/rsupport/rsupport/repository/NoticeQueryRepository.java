package rsupport.rsupport.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.domain.Notice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    public Notice findNoticeAndUser(Long id) {
        return em.createQuery("select n from Notice n join User u on n.user=u", Notice.class)
                .getSingleResult();
    }
}
