package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.domain.Notice;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.repository.NoticeQueryRepository;
import rsupport.rsupport.repository.NoticeRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    public void createNotice(Notice notice, User user) {
        noticeRepository.save(notice);
        notice.addUser(user);
    }

    public Notice getNotice(Long id) {
        return noticeQueryRepository.findNoticeAndUser(id);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public void updateNotice(Long id, Notice newNotice) {
        Notice notice = noticeRepository.getById(id);
        notice = newNotice;
    }
}
