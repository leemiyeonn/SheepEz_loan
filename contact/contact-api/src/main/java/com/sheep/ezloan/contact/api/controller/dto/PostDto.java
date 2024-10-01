package com.sheep.ezloan.contact.api.controller.dto;

import com.sheep.ezloan.contact.domain.model.LoanType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public interface PostDto {

    @Data
    @Builder
    class Request {

        private final String title;

        private final String content;

        private final LoanType loanType;

    }

    @Data
    @Builder
    class DeleteResponse {

        private final UUID postUuid;

        public static DeleteResponse of(UUID postUuid) {
            return DeleteResponse.builder().postUuid(postUuid).build();
        }

    }

}
