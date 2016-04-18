package com.love_cookies.vote.Model.Bean;

import java.util.List;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
public class ChangeLogBean {

    /**
     * version : V 1.0
     * date : 2016-01-10
     * description : 投 你所爱
     */

    private List<ChangeLogEntity> change_log;

    public void setChange_log(List<ChangeLogEntity> change_log) {
        this.change_log = change_log;
    }

    public List<ChangeLogEntity> getChange_log() {
        return change_log;
    }

    public static class ChangeLogEntity {
        private String version;
        private String date;
        private String description;

        public void setVersion(String version) {
            this.version = version;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }
    }
}
