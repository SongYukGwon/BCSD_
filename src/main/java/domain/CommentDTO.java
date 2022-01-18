package domain;

import java.sql.Timestamp;

public class CommentDTO {

    private long user_Id;
    private long board_Id;
    private String content;
    private long id;
    private int point;
    private Timestamp updated_at;
    private int is_deleted;
    private long parent_comment_id;

    public long getParent_comment_id(){return parent_comment_id;}

    public void setParent_comment_id(long parent_comment_id){this.parent_comment_id = parent_comment_id;}

    public int getIs_deleted(){return is_deleted;}

    public void setIs_deleted(int deleted){this.is_deleted = deleted;}

    public long getBoard_id(){return board_Id;}

    public void setBoard_id(long board_Id){this.board_Id = board_Id;}

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

    public long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }

    public Timestamp getUpdated_at(){return updated_at;}

    public void setUpdated_at(Timestamp updated_at){this.updated_at = updated_at;}
}
