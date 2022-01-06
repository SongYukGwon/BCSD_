package repository;

import domain.UserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    void quit(UserDTO user);
    void signUp(UserDTO user);
    UserDTO login(UserDTO user);
    UserDTO findWithUserId(long userID);
}
