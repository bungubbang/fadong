package com.fadong.controller;

import com.fadong.domain.Card;
import com.fadong.repository.CardRepository;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

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

    @RequestMapping(value = "card")
    public Page<Card> card(Pageable pageable, @RequestParam(required = false) final String category) {
        if(Strings.isNullOrEmpty(category)) {
            return cardRepository.findAll(pageable);
        }

        return cardRepository.findAll(new Specification<Card>() {
            @Override
            public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("category"), category);
            }
        }, pageable);
    }
}
