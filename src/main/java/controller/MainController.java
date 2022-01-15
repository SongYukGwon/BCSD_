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
import service.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    //회원가입 프런트
    @RequestMapping(value="/abc", method = RequestMethod.GET)
    public String signUpPage(){
        return "signUp";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ApiOperation(value = "프로필", notes="프로필 창을 위한 api입니다.")
    public ResponseEntity<UserDTO> profile() throws Exception {
        return new ResponseEntity<>(userService.readUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "로그아웃", notes="로그아웃을 위한 api입니다.")
    public ResponseEntity<String> logout(){
        if(userService.logout())
            return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
        else
            return new ResponseEntity<>("로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/quit", method=RequestMethod.DELETE)
    @ApiOperation(value = "회원탈퇴", notes="회원탈퇴를 위한 api입니다.")
    public ResponseEntity<String> quit(@RequestBody String password){
        if(userService.quit(password))
            return new ResponseEntity<>("회원탈퇴 성공", HttpStatus.OK);
        else
            return new ResponseEntity<>("회원탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ApiOperation(value = "회원가입", notes="회원가입을 위한 api입니다.")
    public ResponseEntity<String> signUps(@RequestBody UserDTO user) {
        //매개변수를 db에 등록하기위해 Service의 signUp호출
        if(userService.signUp(user))
            //ResponseEntity HttpRequest에 대한 응답 데이터를 포함하는 클래스
            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        else
            return new ResponseEntity<>("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "로그인", notes="로그인을 위한 api입니다.")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        if(userService.login(user))
            return new ResponseEntity<>("Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

