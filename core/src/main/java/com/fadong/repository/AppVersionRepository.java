package com.fadong.repository;

import com.fadong.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 7. 7.
 */
public interface AppVersionRepository extends JpaRepository<AppVersion, String> {
}
