package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.controller.NoticeForm;
import rsupport.rsupport.domain.File;
import rsupport.rsupport.domain.Notice;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.repository.FileRepository;
import rsupport.rsupport.repository.NoticeQueryRepository;
import rsupport.rsupport.repository.NoticeRepository;
import rsupport.rsupport.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public void createNotice(NoticeForm noticeForm) {
        Notice notice = new Notice();
        User user = userRepository.getById(noticeForm.getUserId());

        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setStartAt(noticeForm.getStartAt());
        notice.setEndAt(noticeForm.getEndAt());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedAt(LocalDateTime.now());
        notice.addUser(user);

        for(String fileUrl : noticeForm.getFileUrls()) {
            File file = new File();
            file.setFileUrl(fileUrl);
            notice.addFiles(file);
            fileRepository.save(file);
        }

        noticeRepository.save(notice);

    }

    public Notice getNotice(Long id) {
        return noticeQueryRepository.findNoticeAndUser(id);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public void updateNotice(Long id, NoticeForm noticeForm) {
        Notice notice = noticeRepository.getById(id);
        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setStartAt(noticeForm.getStartAt());
        notice.setEndAt(noticeForm.getEndAt());
        notice.setUpdatedAt(LocalDateTime.now());
    }
}
