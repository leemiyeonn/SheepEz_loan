package com.sheep.ezloan.contact.api.controller.dto;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.model.PostStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Data
    @Builder
    class Response {

        private final UUID postUuid;

        private final Long userId;

        private final String username;

        private final String title;

        private final String content;

        private final PostStatus status;

        private final LoanType loanType;

        private final LocalDateTime createdAt;

        public static Response of(PostResult postResult) {
            return Response.builder()
                .postUuid(postResult.getPostUuid())
                .userId(postResult.getUserId())
                .username(postResult.getUsername())
                .title(postResult.getTitle())
                .content(postResult.getContent())
                .status(postResult.getStatus())
                .loanType(postResult.getLoanType())
                .createdAt(postResult.getCreatedAt())
                .build();
        }

        public static Collection<Response> of(Collection<PostResult> posts) {
            return posts.stream().map(Response::of).collect(Collectors.toList());
        }

    }

}
