package domain;

public class PointDTO {
    private int point;
    private long id;
    private long user_id;
    private long board_id;
    private int is_deleted;


    public int getPoint() {
        return point;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public long getBoard_id() {
        return board_id;
    }
    public void setBoard_id(long board_id){
        this.board_id = board_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getIs_deleted(){return is_deleted;}

    public void setIs_deleted(int is_deleted){this.is_deleted = is_deleted;}
}
