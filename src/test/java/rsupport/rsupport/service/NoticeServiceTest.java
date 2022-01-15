package rsupport.rsupport.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.controller.NoticeForm;
import rsupport.rsupport.domain.File;
import rsupport.rsupport.domain.Notice;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.repository.FileQueryRepository;
import rsupport.rsupport.repository.FileRepository;
import rsupport.rsupport.repository.NoticeRepository;
import rsupport.rsupport.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private NoticeService noticeService;
    @Autowired private UserService userService;
    @Autowired private NoticeRepository noticeRepository;
    @Autowired private FileRepository fileRepository;
    @Autowired private FileQueryRepository fileQueryRepository;

    @BeforeEach
    void setUp() throws Exception{
        User user = new User();
        user.setName("user");
        userService.createUser(user);

        Notice notice = new Notice();
        notice.setTitle("제목");
        notice.setContent("내용");
        notice.setStartAt(LocalDate.of(2020,2,20));
        notice.setEndAt(LocalDate.of(2020,3,20));
        notice.addUser(user);

        noticeRepository.save(notice);

        List<String> fileUrls = new ArrayList<>();
        fileUrls.add("cdhw.tistory.com");
        fileUrls.add("github.com/qhsdnr0");

        for(String fileUrl : fileUrls) {
            File file = new File();
            file.setFileUrl(fileUrl);
            notice.addFiles(file);
            fileRepository.save(file);
        }

    }

    @AfterEach
    void tearDown() throws Exception {
        fileRepository.deleteAll();

        noticeRepository.deleteAll();

        userRepository.deleteAll();

    }

    @Test
    void createNotice() {
        User user = new User();
        user.setName("user1");
        userService.createUser(user);

        Notice notice = new Notice();
        notice.setTitle("제목1");
        notice.setContent("내용1");
        notice.setStartAt(LocalDate.of(2020,3,20));
        notice.setEndAt(LocalDate.of(2020,4,20));
        notice.addUser(user);

        noticeService.createNotice(user, notice);
        assertEquals("제목1", noticeRepository.getById(notice.getId()).getTitle());
        assertEquals("내용1", noticeRepository.getById(notice.getId()).getContent());
    }

    @Test
    void getNotice() {
        Notice notice = noticeRepository.findAll().get(0);
        assertEquals("제목", noticeService.getNotice(notice.getId()).getTitle());
    }

    @Test
    void deleteNotice() {
        Notice notice = noticeRepository.findAll().get(0);
        assertEquals("제목", noticeService.getNotice(notice.getId()).getTitle());
        noticeService.deleteNotice(notice.getId());
        assertFalse(noticeRepository.existsById(notice.getId()));
    }

    @Test
    void deleteAllFile() {
        Notice notice = noticeRepository.findAll().get(0);
        noticeService.deleteAllFile(notice);
        System.out.println(fileRepository.findAll());
        for(File file : notice.getFiles()) {
            assertFalse(fileRepository.existsById(file.getId()));
        }

    }
}