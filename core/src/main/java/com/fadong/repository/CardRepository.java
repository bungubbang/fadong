package com.fadong.repository;

import com.fadong.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import static com.fadong.domain.Card.STATUS;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 14. 12. 23.
 */
public interface CardRepository extends JpaRepository<Card, String>,
                                        JpaSpecificationExecutor<Card> {

    List<Card> findByStatus(STATUS status);
    List<Card> findByCreatedTimeLikeAndCategory(String createTime, String category);
    List<Card> findByCreatedTimeLike(String createTime);

}
