package com.ebook.ebook_api.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.service.AiService;
import com.ebook.ebook_api.util.AiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiServiceImpl implements AiService {
    @Override
    public ResponseDto getAnswer(String question) {
        GenerationResult aiMessage;
        try{
            aiMessage = AiUtil.call(question);
        }catch (Exception e){
            log.error("Failed to get answer from AI service", e);
            return new ResponseDto(500,"系统错误");
        }
        String content = aiMessage.getOutput().getChoices().get(0).getMessage().getContent();
        JSONObject data = new JSONObject();
        data.put("ans", content);
        return new ResponseDto(200, "成功", data);
    }
}
