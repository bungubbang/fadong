package com.fadong.controller;

import com.fadong.controller.dto.PageUpdateDto;
import com.fadong.domain.Card;
import com.fadong.repository.CardRepository;
import com.fadong.repository.PageRepository;
import com.fadong.service.BatchService;
import com.fadong.service.FeviBatchService;
import com.google.common.base.Strings;
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

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 12/24/14
 */
@RestController
@RequestMapping("api")
public class ApiController {

    Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired private CardRepository cardRepository;
    @Autowired private PageRepository pageRepository;
    @Autowired private BatchService batchService;

    @RequestMapping(value = "card")
    public Page<Card> card(Pageable pageable, @RequestParam(required = false) final String category) {
        return cardRepository.findAll(new Specification<Card>() {
            @Override
            public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (!query.getResultType().equals(Long.class)) {
                    query.orderBy(cb.desc(root.get("updated_time")));
                }


                if(!Strings.isNullOrEmpty(category)) {
                    return cb.and(
                            cb.equal(root.get("status"), Card.STATUS.ENABLE),
                            cb.equal(root.get("category"), category)
                    );
                }

                return cb.and(
                        cb.equal(root.get("status"), Card.STATUS.ENABLE)
                );
            }
        }, pageable);
    }

    @RequestMapping(value = "page/update", method = RequestMethod.POST)
    public com.fadong.domain.Page pageUpdate(PageUpdateDto pageUpdateDto) {
        com.fadong.domain.Page page = new com.fadong.domain.Page();
        page.setId(pageUpdateDto.getId());
        page.setCategory(pageUpdateDto.getCategory());
        pageRepository.save(page);

        batchService.updatePage();

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
