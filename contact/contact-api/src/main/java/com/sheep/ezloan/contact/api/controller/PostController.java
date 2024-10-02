package com.sheep.ezloan.contact.api.controller;

import com.sheep.ezloan.contact.api.controller.dto.PostDto;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.service.PostService;
import com.sheep.ezloan.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostDto.Response> createPost(@RequestBody PostDto.Request postDto) {
        PostResult post = postService.createPost(postDto.getTitle(), postDto.getContent(), postDto.getLoanType());

        return ApiResponse.success(PostDto.Response.of(post));
    }

    @GetMapping
    public ApiResponse<Collection<PostDto.Response>> getAllPosts(@RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("sort") String sort) {
        Collection<PostResult> posts = postService.getAllPosts(page, size, sort);

        return ApiResponse.success(PostDto.Response.of(posts));
    }

    @GetMapping("/search")
    public ApiResponse<Collection<PostDto.Response>> searchPosts(@RequestParam("keyword") String keyword,
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort) {
        Collection<PostResult> posts = postService.searchPosts(keyword, page, size, sort);

        return ApiResponse.success(PostDto.Response.of(posts));
    }

    @GetMapping("/{postUuid}")
    public ApiResponse<PostDto.Response> getPost(@PathVariable(value = "postUuid") UUID postUuid) {
        PostResult post = postService.getPost(postUuid);

        return ApiResponse.success(PostDto.Response.of(post));
    }

    @PutMapping("/{postUuid}")
    public ApiResponse<PostDto.Response> updatePost(@PathVariable(value = "postUuid") UUID postUuid,
            @RequestBody PostDto.Request postDto) {
        PostResult post = postService.updatePost(postUuid, postDto.getTitle(), postDto.getContent(),
                postDto.getLoanType());

        return ApiResponse.success(PostDto.Response.of(post));
    }

    @DeleteMapping("/{postUuid}")
    public ApiResponse<PostDto.DeleteResponse> deletePost(@PathVariable(value = "postUuid") UUID postUuid) {
        PostDto.DeleteResponse deletedPost = PostDto.DeleteResponse.of(postService.deletePost(postUuid));

        return ApiResponse.success(deletedPost);
    }

}
