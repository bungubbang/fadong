package com.fadong.controller;

import com.fadong.controller.dto.PageUpdateDto;
import com.fadong.domain.AccessToken;
import com.fadong.repository.AccessTokenRepository;
import com.fadong.repository.CardRepository;
import com.fadong.domain.Card;
import com.fadong.repository.PageRepository;
import com.fadong.service.ApiService;
import com.fadong.service.dto.CardSearch;
import org.apache.commons.logging.Log;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

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
    @Autowired private AccessTokenRepository accessTokenRepository;

    @Autowired private RestTemplate restTemplate;

    @RequestMapping(value = "card")
    public Page<Card> card(Pageable pageable, @RequestParam(required = false) final String category, @RequestParam(required = false) final String id) {
        log.info("Call card api : pageable = [" + pageable + "], category = [" + category + "], id = [" + id + "]");
        return apiService.getCard(new CardSearch(id, category, pageable));
    }

    @RequestMapping(value = "page/update", method = RequestMethod.POST)
    public com.fadong.domain.Page pageUpdate(PageUpdateDto pageUpdateDto) {
        com.fadong.domain.Page page = new com.fadong.domain.Page();
        page.setId(pageUpdateDto.getId());
        page.setCategory(pageUpdateDto.getCategory());
        pageRepository.save(page);

        return page;
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
    public String postRecommend(String category) {
        String accessToken = "CAACEdEose0cBADgdvaIeYMtBPvXypMUyzJS5R6nPjDEybF5BUrYEvonIAPAkjUZBtUGnXHebX4fNIpy9HRY7SimVAsZBJYogwiFK7tggVvFZBEfHSWub7Tul155O0bZCCyZB0tvbyTHpZBu2T5ZAZBeDSGMVKjfwP6vcGGcIYZCfnKPIcL1F7Jg1NtZBaxwoSvAw6N7W3BnfUzqAZDZD";
        String postUrl = "https://graph.facebook.com/v2.4/ggoolzam/feed";

        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        List<Card> cards = cardRepository.findByCreatedTimeLikeAndCategory(yesterday.toString("yyyy-MM-dd") + "%", category);

        StringBuilder sb = new StringBuilder();
        sb.append(now.toString("yyyy-MM-dd") + " 오늘의 업데이트 동영상 #" + category + "\n");

        for (int i = 0; i < cards.size(); i++) {
            sb.append(i + ". ");
            String description = cards.get(i).getDescription();
            if(description.length() < 15) {
                sb.append(description.replace("\n", "") + "\n");
            } else {
                sb.append(description.substring(0, 20).replace("\n", "") + "\n");
            }
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("message", sb.toString());
        params.put("link", "http://appinkorea.co.kr/fevi/s.php?id=facebookPage&vid=" + cards.get(0).getId());
        params.put("picture", cards.get(0).getPicture());
        params.put("access_token", accessToken);

        return restTemplate.postForObject(postUrl, params, String.class);
    }
}
