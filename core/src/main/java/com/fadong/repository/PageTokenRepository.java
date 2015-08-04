package com.fadong.repository;

import com.fadong.domain.PageToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 8. 4.
 */
public interface PageTokenRepository extends JpaRepository<PageToken, Long> {
}
