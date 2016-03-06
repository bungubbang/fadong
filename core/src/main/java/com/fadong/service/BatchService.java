package com.fadong.service;

import com.fadong.domain.Card;
import com.fadong.domain.Page;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
public interface BatchService {
    Page updatePage(Page page);
    Page updateCardRecently(Page page);
    Card updateCardAll(Card card);
    void removeCard(Card card);
}
