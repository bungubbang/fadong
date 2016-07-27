package com.fadong.controller;

import com.fadong.repository.AccessTokenRepository;
import com.fadong.repository.CardRepository;
import com.fadong.repository.PageRepository;
import com.fadong.service.AccessTokenService;
import com.fadong.service.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 5/22/16
 */
@Slf4j
@RestController
@RequestMapping("batch")
public class BatchConfig {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private BatchService batchService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Scheduled(cron="0 0 0 * * *")
    @RequestMapping(value = "page")
    public String allPageUpdate() {
        pageRepository.findAll().stream().forEach((page) -> {
            log.info("[Update Page] : " + page.getName());
            batchService.updatePage(page);

        });
        return "OK";
    }

    @Scheduled(cron="0 0 * * * *")
    @RequestMapping(value = "card")
    private String recentCardUpdate() {
        accessTokenService.refreshAccessToken();
        log.info("[Update Token] : " + accessTokenService.getAccessToken());
        pageRepository.findAll().stream().forEach((page) -> {
            try {
                log.info("[Update Card Recent] : " + page.getName());
                batchService.updateCardRecently(page);
            } catch(Exception e) {
                e.printStackTrace();
            }

        });
        return "OK";
    }

    @Scheduled(cron="0 0 1 * * *")
    @RequestMapping(value = "cardAll")
    private String cardUpdateAll() {
        cardRepository.findAll().stream().forEach(card -> {
            try {
                log.info("[Update Card All] : " + card.getId());
                batchService.updateCardAll(card);
            } catch (Exception e) {
                log.info("[DELETE CARD] : " + card.getId());
                batchService.removeCard(card);
            }

        });
        return "OK";
    }
}
