package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rsupport.rsupport.config.RedisConfig;
import rsupport.rsupport.domain.Notice;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.service.NoticeService;
import rsupport.rsupport.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("notices")
@Transactional
@CrossOrigin
public class NoticeController {

    @Autowired CacheManager cacheManager;

    private final NoticeService noticeService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> newNotice(@RequestBody NoticeForm noticeForm) {
        Notice notice = new Notice();
        User user = userService.getUser(noticeForm.getUserId());
        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setStartAt(noticeForm.getStartAt());
        notice.setEndAt(noticeForm.getEndAt());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedAt(LocalDateTime.now());

        noticeService.createNotice(user, notice);
        noticeService.addFiles(notice, noticeForm.getFileUrls());
        return ResponseEntity.ok("CREATED");
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<Notice> getNotice(@PathVariable(value = "noticeId") Long id) {
        Notice notice = noticeService.getNotice(id);
        notice.setViewCount(notice.getViewCount()+1);
        return ResponseEntity.ok().body(notice);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable(value = "noticeId") Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("DELETED");
    }

    @PatchMapping("/{noticeId}")
    @CacheEvict(value = "notice", key = "#id", cacheManager = "redisCacheManager")
    public ResponseEntity<String> updateNotice(
            @PathVariable(value = "noticeId") Long id,
            @RequestBody NoticeForm noticeForm) {

        cacheManager.getCache("notice").evict(id);

        Notice notice = noticeService.getNotice(id);
        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setStartAt(noticeForm.getStartAt());
        notice.setEndAt(noticeForm.getEndAt());
        notice.setUpdatedAt(LocalDateTime.now());

        if(noticeForm.getFileUrls() != null) {
            noticeService.deleteAllFile(notice);
            noticeService.addFiles(notice, noticeForm.getFileUrls());
        }

        return ResponseEntity.ok("UPDATED");
    }
}
