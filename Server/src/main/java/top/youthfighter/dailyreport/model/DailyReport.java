package top.youthfighter.dailyreport.model;

import java.util.Calendar;
import java.util.Date;

public class DailyReport {
    private String id;
    private Long reportDate;
    private String author;
    private Long createDateTime;
    private String tagId; // 0 1 2 3 4 5
    private String url;
    private String images;
    private String describition;
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getReportDate() {
        return reportDate;
    }
    public void setReportDate(Long reportDate) {
        this.reportDate = reportDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Long createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescribition() {
        return describition;
    }

    public void setDescribition(String describition) {
        this.describition = describition;
    }
}
