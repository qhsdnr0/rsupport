package rsupport.rsupport.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rsupport.rsupport.domain.File;
import rsupport.rsupport.domain.Notice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    public List<File> getFiles(Notice notice) {
        return em.createQuery("select f from File f where notice= :notice", File.class)
                .setParameter("notice", notice)
                .getResultList();
    }

}
