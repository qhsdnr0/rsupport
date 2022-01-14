package rsupport.rsupport.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter @Setter
public class File {

    @Id @GeneratedValue
    private Long id;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @JsonManagedReference
    private Notice notice;

    public void addNotice(Notice notice) {
        notice.getFiles().add(this);
        this.setNotice(notice);
    }
}
