package com.fadong.controller;

import com.fadong.domain.AppVersion;
import com.fadong.repository.AppVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 7. 7.
 */
@RestController
public class VersionCheckController {

    @Autowired
    private AppVersionRepository appVersionRepository;

    @RequestMapping("version")
    public AppVersion version() {
        List<AppVersion> all = appVersionRepository.findAll();
        return all.get(0);
    }
}
