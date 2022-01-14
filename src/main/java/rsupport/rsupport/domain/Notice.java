package rsupport.rsupport.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notices")
@Getter @Setter
public class Notice {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "integer default 0")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "notice")
    @JsonBackReference
    private List<File> files = new ArrayList<>();

    public void addUser(User user) {
        this.setUser(user);
        user.getNotices().add(this);
    }

    public void addFiles(File file) {
        file.setNotice(this);
        this.getFiles().add(file);
    }
}
