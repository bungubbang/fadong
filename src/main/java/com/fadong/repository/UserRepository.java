package com.fadong.repository;

import com.fadong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 14. 12. 23.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
