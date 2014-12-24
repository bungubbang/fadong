package com.fadong.repository;

import com.fadong.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 14. 12. 23.
 */
public interface CardRepository extends JpaRepository<Card, String> {
}
