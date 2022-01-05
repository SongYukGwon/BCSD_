package service;

import domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserMapper;
import service.interfaces.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean signUp(UserDTO user){
        //이미 가입된 아이디가 없는지 확인
        UserDTO tmp = userMapper.login(user);
        if(tmp == null){
            //이미 가입된 아이디가 없다면 db등록
            userMapper.signUp(user);
            System.out.println("Succ");
            return true;
        }
        //이미 가입된 아이디가 있다면 false 리턴
        else {
            System.out.println("fail");
            return false;
        }

    }

    @Override
    public UserDTO login(UserDTO user){
        UserDTO fuser = userMapper.login(user);
        return fuser;
    }
}

