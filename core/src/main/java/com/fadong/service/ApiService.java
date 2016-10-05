package com.fadong.service;

import com.fadong.domain.Card;
import com.fadong.service.dto.CardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 23.
 */
public interface ApiService {
    Page<Card> getCard(CardSearch search, Pageable pageable);
}
