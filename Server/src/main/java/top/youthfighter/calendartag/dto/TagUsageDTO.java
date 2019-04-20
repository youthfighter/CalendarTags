package top.youthfighter.calendartag.dto;

public class TagUsageDTO {
    private String id;
    private String name;
    private String color;
    private int status;
    private int usageCount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
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
}
