package service;

import domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.UserMapper;
import service.interfaces.IUserService;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptImpl bCrypt;

    @Override
    public boolean signUp(UserDTO user){
        //이미 가입된 아이디가 없는지 확인
        UserDTO tmp = userMapper.login(user);

        if(tmp == null){
            //이미 가입된 아이디가 없다면 db등록
            user.setPassword(bCrypt.encrypt(user.getPassword()));
            System.out.println(user.getPassword());
            userMapper.signUp(user);
            return true;
        }
        //이미 가입된 아이디가 있다면 false 리턴
        else {
            return false;
        }

    }

    @Override
    public boolean login(UserDTO user){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");
        //로그인된 정보가 없다면 mapper에서 user정보 받아오기
        if(CUserId == null) {
            UserDTO fuser = userMapper.login(user);
            //받아온 유저정보가 유효한지 확인
            if(fuser != null&&fuser.getDeleted_at()==0&& bCrypt.isMatch(user.getPassword(), fuser.getPassword())) {
                //정보가 유효하면 로그인정보 세션에 저장
                request.getSession().setAttribute("userId", fuser.getId());
                return true;
            }
        }
        //로그인된 정보가 있으면 오류
        return false;
    }

    @Override
    public boolean logout(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");

        //유저 정보가 없는데 로그아웃실행이면 에러
        if(CUserId == null)
            return false;
        else{
            //로그인 정보 삭제
            request.getSession().removeAttribute("userId");
            return true;
        }
    }

    @Override
    public boolean quit(String password){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");

        UserDTO fuser = userMapper.findWithUserId(CUserId);
        if(fuser!=null && bCrypt.isMatch(password, fuser.getPassword())){
            userMapper.quit(fuser);
            request.getSession().removeAttribute("userId");
            return true;
        }
        return false;
    }
}

