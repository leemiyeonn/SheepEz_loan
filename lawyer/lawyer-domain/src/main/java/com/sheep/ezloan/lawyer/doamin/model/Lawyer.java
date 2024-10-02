package com.sheep.ezloan.lawyer.doamin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Lawyer {

    private Long id;

    private String nickname;

    private String name;

    private String email;

    private String introduction;

    private String certificationInfo;

    private String career;

    private Boolean isAccepted;

    private Boolean isPublic;

}
