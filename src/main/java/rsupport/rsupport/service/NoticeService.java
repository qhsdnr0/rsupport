package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.controller.NoticeForm;
import rsupport.rsupport.domain.File;
import rsupport.rsupport.domain.Notice;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileRepository fileRepository;
    private final FileQueryRepository fileQueryRepository;

    public void createNotice(User user, Notice notice) {
        notice.addUser(user);
        noticeRepository.save(notice);
    }

    @Cacheable(value = "notice", cacheManager = "redisCacheManager")
    public Notice getNotice(Long id) {
        return Hibernate.unproxy(noticeRepository.getById(id), Notice.class);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public void deleteAllFile(Notice notice) {
        fileRepository.deleteAllInBatch(fileQueryRepository.getFiles(notice));
    }

    public void addFiles(Notice notice, List<String> fileUrls) {
        for(String fileUrl : fileUrls) {
            File file = new File();
            file.setFileUrl(fileUrl);
            notice.addFiles(file);
            fileRepository.save(file);
        }
    }
}
