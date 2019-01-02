package com.matcha.kay.engineerreligion;

public class Comment {
    private String comment;
    private String time;
    private long order;

    public Comment() {
        this.comment = "";
        this.time = "";
        this.order = 0;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getOrder() {
        return order;
    }
}
