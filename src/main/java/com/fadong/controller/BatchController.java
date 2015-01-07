package com.fadong.controller;

import com.fadong.service.BatchService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        batchService.updatePage();
        return DateTime.now(DateTimeZone.forOffsetHours(9)).toString();
    }

    @RequestMapping(value = "card/update")
    public String cardUpdate() {
        batchService.updateCardRecently();
        return DateTime.now(DateTimeZone.forOffsetHours(9)).toString();
    }

    @RequestMapping(value = "card/update/all")
    public String cardUpdateAll() {
        batchService.updateCardAll();
        return DateTime.now(DateTimeZone.forOffsetHours(9)).toString();
    }
}
