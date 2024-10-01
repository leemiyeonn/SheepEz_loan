package com.sheep.ezloan.contact.domain.service;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResult createPost(String title, String content, LoanType loanType) {
        Long userId = 0L;
        String username = "temporary"; // 임시 유저 생성
        return postRepository.save(new Post(userId, username, title, content, loanType));
    }

    @Transactional(readOnly = true)
    public Collection<PostResult> getAllPosts(int page, int size, String sortBy) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PostResult> postResultPage = postRepository.findAll(pageable);

        return postResultPage.toList();
    }

    @Transactional(readOnly = true)
    public Collection<PostResult> searchPosts(String keyword, int page, int size, String sortBy) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PostResult> postResultPage = postRepository.searchByKeyword(keyword, pageable);

        return postResultPage.toList();
    }

    @Transactional(readOnly = true)
    public PostResult getPost(UUID postUuid) {
        return postRepository.findByUuid(postUuid);
    }

    @Transactional
    public PostResult updatePost(UUID postUuid, String title, String content, LoanType loanType) {
        return postRepository.update(postUuid, title, content, loanType);
    }

    @Transactional
    public UUID deletePost(UUID postUuid) {
        postRepository.delete(postUuid);
        return postUuid;
    }

}
