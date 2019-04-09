package top.youthfighter.calendartag.dto;

public class DayReportDTO {
    private String id;
    private String name;
    private String color;
    private DiaryDTO data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DiaryDTO getData() {
        return data;
    }

    public void setData(DiaryDTO data) {
        this.data = data;
    }
}
