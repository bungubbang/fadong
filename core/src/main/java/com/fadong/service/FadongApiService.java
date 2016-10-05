package com.fadong.service;

import com.fadong.domain.Card;
import com.fadong.repository.CardRepository;
import com.fadong.service.dto.CardSearch;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 23.
 */
@Service
public class FadongApiService implements ApiService {

    @Autowired
    private CardRepository cardRepository;

    @Cacheable("cards")
    @Override
    public Page<Card> getCard(CardSearch search, Pageable pageable) {
        return cardRepository.findAll((root, query, cb) -> {
            if (!query.getResultType().equals(Long.class)) {
                query.orderBy(cb.desc(root.get("updatedTime")));
            }


            if(!Strings.isNullOrEmpty(search.getCategory())) {
                return cb.and(
                        cb.equal(root.get("status"), Card.STATUS.ENABLE),
                        cb.equal(root.get("category"), search.getCategory())
                );
            }

            if(!Strings.isNullOrEmpty(search.getId())) {
                return cb.and(
                        cb.equal(root.get("status"), Card.STATUS.ENABLE),
                        cb.equal(root.get("id"), search.getId())
                );
            }

            if(!Strings.isNullOrEmpty(search.getKeyword())) {
                return cb.and(
                        cb.equal(root.get("status"), Card.STATUS.ENABLE),
                        cb.like(root.get("description"), "%" + search.getKeyword() + "%")
                );
            }

            return cb.and(
                    cb.equal(root.get("status"), Card.STATUS.ENABLE)
            );
        }, pageable);
    }
}
