package com.fadong.service;

import com.fadong.domain.Card;
import com.fadong.repository.CardRepository;
import com.fadong.service.dto.CardSearch;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private Log log = LogFactory.getLog(FadongApiService.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public void publishPage(String accessToken, String message) {
        CardSearch cardSearch = new CardSearch();
        cardSearch.setCategory("TREND");
        PageRequest pageRequest = new PageRequest(0, 1);
        org.springframework.data.domain.Page<Card> cardPage = getCard(cardSearch, pageRequest);
        log.info("Page card : " + cardPage.getContent().get(0));
        Card card = cardPage.getContent().get(0);

        String link = "http://facebook.com/" + card.getId();

        StringBuilder tokenUrl = new StringBuilder();
        tokenUrl.append("https://graph.facebook.com/ggoolzam/feed");
        tokenUrl.append("?published=" + "true");
        tokenUrl.append("&access_token=" + accessToken);
        tokenUrl.append("&message=" + message);
        tokenUrl.append("&link=" + link);
        log.info("Page publish url : " + tokenUrl.toString());
        String response = restTemplate.postForObject(tokenUrl.toString(), null, String.class);
        log.info("Page publish - " + response);
    }
}
