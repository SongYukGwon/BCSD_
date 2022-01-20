package domain;

public class PointDTO {
    private int point;
    private Long id;
    private Long user_id;
    private Long board_id;
    private int is_deleted;
    private Long comment_id;

    public Long getCommentId(){return comment_id;}

    public void setCommentId(Long comment_id){this.comment_id = comment_id;}

    public int getPoint() {
        return point;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public Long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Long board_id){
        this.board_id = board_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getIs_deleted(){return is_deleted;}

    public void setIs_deleted(int is_deleted){this.is_deleted = is_deleted;}
}
