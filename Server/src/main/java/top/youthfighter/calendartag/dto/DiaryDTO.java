package top.youthfighter.calendartag.dto;

import top.youthfighter.calendartag.model.Tag;

public class DiaryDTO {
    private String id;
    private Long reportDate;
    private String author;
    private Long createDateTime;
    private String url;
    private String images;
    private String describition;
    private String tagId;
    private String tagName;
    private String tagColor;

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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        tagColor = tagColor;
    }

    public void setTag(Tag tag) {
        if (tag == null) return;
        this.tagId = tag.getId();
        this.tagName = tag.getName();
        this.tagColor = tag.getColor();
    }
}
