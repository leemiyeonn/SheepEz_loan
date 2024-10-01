package com.sheep.ezloan.contact.domain.repository;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostRepository {

    PostResult save(Post post);

    Page<PostResult> findAll(Pageable pageable);

    Page<PostResult> searchByKeyword(String keyword, Pageable pageable);

    PostResult findByUuid(UUID postUuid);

    PostResult update(UUID postUuid, String title, String content, LoanType loanType);

    void delete(UUID postUuid);

}
