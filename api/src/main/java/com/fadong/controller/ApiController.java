package com.fadong.controller;

import com.fadong.controller.dto.PageUpdateDto;
import com.fadong.domain.AccessToken;
import com.fadong.repository.AccessTokenRepository;
import com.fadong.repository.CardRepository;
import com.fadong.domain.Card;
import com.fadong.repository.PageRepository;
import com.fadong.repository.PageTokenRepository;
import com.fadong.service.AccessTokenService;
import com.fadong.service.ApiService;
import com.fadong.service.dto.CardSearch;
import org.apache.commons.logging.Log;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.apache.commons.logging.LogFactory.getLog;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 12/24/14
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private static final Log log = getLog(ApiController.class);

    @Autowired private CardRepository cardRepository;
    @Autowired private PageRepository pageRepository;
    @Autowired private ApiService apiService;
    @Autowired private PageTokenRepository pageTokenRepository;
    @Autowired private AccessTokenService accessTokenService;

    @Autowired private RestTemplate restTemplate;

    @RequestMapping(value = "card")
    public Page<Card> card(CardSearch cardSearch, Pageable pageable) {
        log.info("Call card api : " + cardSearch.toString() +  ", Page : " + pageable);
        return apiService.getCard(cardSearch, pageable);
    }

    @RequestMapping(value = "page/update", method = RequestMethod.POST)
    public com.fadong.domain.Page pageUpdate(PageUpdateDto pageUpdateDto) {
        com.fadong.domain.Page page = new com.fadong.domain.Page();
        page.setId(pageUpdateDto.getId());
        page.setCategory(pageUpdateDto.getCategory());
        pageRepository.save(page);

        return page;
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public List<com.fadong.domain.Page> pageAll() {
        return pageRepository.findAll();
    }

    @RequestMapping(value = "card/disable", method = RequestMethod.GET)
    public List<Card> disableCardList() {
        return cardRepository.findByStatus(Card.STATUS.DISABLE);
    }

    @RequestMapping(value = "card/disable", method = RequestMethod.POST)
    public Card disableCard(String id) {
        System.out.println("id = [" + id + "]");
        Card card = cardRepository.findOne(id);
        card.setStatus(Card.STATUS.DISABLE);
        return cardRepository.save(card);
    }

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public String postRecommend(@RequestParam(required = false, defaultValue = "6") Integer size) {
        String accessToken = pageTokenRepository.findAll().get(0).getAccessToken();
        String postUrl = "https://graph.facebook.com/v2.4/ggoolzam/feed";

        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        List<Card> cards = cardRepository.findByCreatedTimeLike(yesterday.toString("yyyy-MM-dd") + "%", new PageRequest(0, size)).getContent();

        StringBuilder sb = new StringBuilder();
        sb.append(now.toString("yyyy-MM-dd")).append(" 오늘의 업데이트 동영상").append("\n");

        for (int i = 0; i < cards.size(); i++) {
            sb.append((i+1) + ". ");
            String description = cards.get(i).getDescription();
            if(description.length() < 15) {
                sb.append(description.replace("\n", "") + "\n");
            } else {
                sb.append(description.substring(0, 15).replace("\n", "") + "\n");
            }
        }


        String[] ids = cards.stream().map(Card::getId).toArray(String[]::new);

        String vids = StringUtils.arrayToDelimitedString(ids, ",");

        String shortenUrl = restTemplate.getForObject("http://moonsang.kr/s.php?s=" + "http://appinkorea.co.kr/fevi/summary.php?vid=" + vids, String.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("message", sb.toString());
        params.put("link", shortenUrl);
        params.put("picture", cards.get(0).getPicture());
        params.put("access_token", accessToken);



        return restTemplate.postForObject(postUrl, params, String.class);
    }

    @RequestMapping(value = "publish", method = RequestMethod.POST)
    public void pagePublish(@RequestParam String message) {
        String pageAccessToken = accessTokenService.getPageAccessToken(accessTokenService.getAccessToken());
        log.info("[PAGE TOKEN] : " + pageAccessToken);
        log.info("[Publish - START]");
        apiService.publishPage(pageAccessToken, message);
        log.info("[Publish - END]");
    }
}
