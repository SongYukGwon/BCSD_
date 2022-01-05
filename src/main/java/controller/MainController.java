package controller;

import domain.UserDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import java.sql.*;

@Controller
//@RequestMapping(value = "/user")
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/abc", method = RequestMethod.GET)
    public String signUpPage() throws ClassNotFoundException, SQLException {
        return "signUp";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ApiOperation(value = "회원가입", notes="회원가입을 위한 api입니다.")
    @ResponseBody
    public ResponseEntity<String> signUps(@RequestBody UserDTO user) {
        System.out.println(user.getAccount());
        //매개변수를 db에 등록하기위해 Service의 signUp호출
        if(userService.signUp(user))
            //ResponseEntity HttpRequest에 대한 응답 데이터를 포함하는 클래스
            return new ResponseEntity<>("Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "로그인", notes="로그인을 위한 api입니다.")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        UserDTO fuser = userService.login(user);
        if(fuser != null)
            return new ResponseEntity<>("Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

