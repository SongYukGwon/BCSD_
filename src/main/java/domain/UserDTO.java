package domain;

import java.sql.Timestamp;

public class UserDTO {
    private String account;
    private String password;
    private String name;
    private String email;
    private long id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int is_deleted;

    public String toString(){
        String tmp = "account : " + account + "\nname : " + name;
        return tmp;
    }

    public long getId(){return id;}

    public void setId(long id){this.id = id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getUpdated_at(){return updated_at;}

    public void setUpdated_at(Timestamp updated_at){this.updated_at = updated_at;}

    public Timestamp getCreated_at(){return created_at;}

    public void setCreated_at(Timestamp created_at){this.created_at = created_at;}

    public int getDeleted_at(){return is_deleted;}

    public void setDeleted_at(int is_deleted){this.is_deleted = is_deleted;}
}