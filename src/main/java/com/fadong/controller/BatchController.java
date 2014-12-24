package com.fadong.controller;

import com.fadong.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@RestController
@RequestMapping("batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @RequestMapping(value = "page/update", method = GET)
    public String pageUpdate() {
        Date updateDate = batchService.updatePage();
        return updateDate.toString();
    }

    @RequestMapping(value = "card/update")
    public String cardUpdate() {
        Date updateDate = batchService.updateCard();
        return updateDate.toString();
    }
}
