package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
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
    private final NoticeQueryRepository noticeQueryRepository;
    private final FileRepository fileRepository;
    private final FileQueryRepository fileQueryRepository;

    public void createNotice(User user, Notice notice) {
        notice.addUser(user);
        noticeRepository.save(notice);
    }

    public Notice getNotice(Long id) {
        return noticeRepository.getById(id);
    }

    public Notice getNoticeAndUser(Long id) {
        return noticeQueryRepository.findNoticeAndUser(id);
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
