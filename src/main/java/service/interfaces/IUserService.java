package service.interfaces;

import domain.UserDTO;


public interface IUserService {
    boolean signUp(UserDTO user);
    UserDTO login(UserDTO user);
}
