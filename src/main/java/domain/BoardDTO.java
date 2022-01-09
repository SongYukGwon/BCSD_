package domain;

import java.sql.Timestamp;

public class BoardDTO {
    private String title;
    private long user_Id;
    private int view;
    private String content;
    private long id;
    private int point;
    private long category_id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int is_deleted;

    public String toString(){
        String tmp = "title : " + title + "\nview : " + view;
        return tmp;
    }

    public long getCategory_id(){return category_id;}

    public void setCategory_id(long category_id){this.category_id = category_id;}

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public long getId(){return id;}

    public void setId(long id){this.id = id;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Timestamp getUpdated_at(){return updated_at;}

    public void setUpdated_at(Timestamp updated_at){this.updated_at = updated_at;}

    public Timestamp getCreated_at(){return created_at;}

    public void setCreated_at(Timestamp created_at){this.created_at = created_at;}

    public int getDeleted_at(){return is_deleted;}

    public void setDeleted_at(int is_deleted){this.is_deleted = is_deleted;}
}
