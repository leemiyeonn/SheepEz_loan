package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.repository.PostRepository;
import com.sheep.ezloan.contact.storage.custom.PostRepositoryCustom;
import com.sheep.ezloan.contact.storage.entity.PostEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryAdapter implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    private final PostRepositoryCustom postRepositoryCustom;

    @Override
    public PostResult save(Post post) {
        PostEntity entity = new PostEntity(post.getUserId(), post.getUsername(), post.getTitle(), post.getContent(),
                post.getLoanType());
        return postJpaRepository.save(entity).toDomain();
    }

    @Override
    public Page<PostResult> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable).map(PostEntity::toDomain);
    }

    @Override
    public Page<PostResult> searchByKeyword(String keyword, Pageable pageable) {
        return postRepositoryCustom.searchByKeyword(keyword, pageable).map(PostEntity::toDomain);
    }

    @Override
    public PostResult findByUuid(UUID postUuid) {
        return postJpaRepository.findById(postUuid).map(PostEntity::toDomain).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PostResult update(UUID postUuid, String title, String content, LoanType loanType) {
        PostEntity entity = postJpaRepository.findById(postUuid).orElseThrow(EntityNotFoundException::new);

        if(title == null) title = entity.getTitle();
        if(content == null) content = entity.getContent();
        if(loanType == null) loanType = entity.getLoanType();

        entity.update(title, content, loanType);

        log.info(entity.getTitle());

        return postJpaRepository.save(entity).toDomain();
    }

    @Override
    public void delete(UUID postUuid) {
        postJpaRepository.deleteById(postUuid);
    }

}
