package com.fadong.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 7. 7.
 */
@Entity
public class AppVersion {
    @Id
    private String version;

    private boolean forceUpdate = false;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
