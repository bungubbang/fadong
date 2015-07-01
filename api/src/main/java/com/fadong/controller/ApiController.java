package com.fadong.controller;

import com.fadong.controller.dto.PageUpdateDto;
import com.fadong.repository.CardRepository;
import com.fadong.domain.Card;
import com.fadong.repository.PageRepository;
import com.fadong.service.ApiService;
import com.fadong.service.BatchService;
import com.fadong.service.dto.CardSearch;
import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
}
