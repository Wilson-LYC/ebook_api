package com.ebook.ebook_api.service;

import com.ebook.ebook_api.dto.ResponseDto;

public interface CommentService {
    ResponseDto getCommentListByFid(Integer fid);

    ResponseDto sendComment(String token, Integer fid, String content);
}
