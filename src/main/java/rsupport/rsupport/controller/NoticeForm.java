package rsupport.rsupport.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class NoticeForm {

    @NotNull
    private Long userId;

    private String title;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;

    private List<String> fileUrls;
}
