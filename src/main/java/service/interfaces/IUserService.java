package service.interfaces;

import domain.UserDTO;


public interface IUserService {
    boolean signUp(UserDTO user);
    boolean login(UserDTO user);
    boolean logout();
    boolean quit(String password);
    UserDTO readUser() throws Exception;
}
