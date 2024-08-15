package com.example.bigbrotherbe.domain.campusNotice.dto;

import com.example.bigbrotherbe.domain.campusNotice.entity.CampusNotice;
import com.example.bigbrotherbe.domain.campusNotice.entity.CampusNoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CampusNoticeResponse {
    private Long noticeId;
    private String title;
    private String content;
    private CampusNoticeType type;
    private List<String> urlList;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static CampusNoticeResponse fromCampusNoticeResponse(CampusNotice campusNotice, List<String> urlList) {
        return CampusNoticeResponse.builder()
                .noticeId(campusNotice.getId())
                .title(campusNotice.getTitle())
                .content(campusNotice.getContent())
                .type(campusNotice.getType())
                .urlList(urlList)
                .createAt(campusNotice.getCreateAt())
                .updateAt(campusNotice.getUpdateAt())
                .build();
    }
}
