package com.e201.kidswallet.notice.controller;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.notice.dto.NoticeDto;
//import com.e201.kidswallet.notice.service.NoticeService;
import com.e201.kidswallet.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {

    NoticeService service;

    @Autowired
    public NoticeController(NoticeService service) {
        this.service = service;
    }

    @GetMapping("/list/{userId}")
    public List<NoticeDto> noticeDtoListList(@PathVariable(name="userId") long userId){
        List<NoticeDto> result = service.noticeDtoList(userId);
        log.info("list:"+ Arrays.toString(result.toArray()));
        return result;
    }
    @DeleteMapping("/list/{userId}/{noticeNum}")
    public StatusCode noticeDelete(@PathVariable(name="userId") long userId, @PathVariable(name="noticeNum") int noticeNum){
        return service.deleteNotice(userId,noticeNum);
    }
}
