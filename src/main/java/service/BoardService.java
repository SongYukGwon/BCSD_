package service;


import domain.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;

import javax.servlet.http.HttpServletRequest;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    public Long CheckUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long CUserId = (Long)request.getSession().getAttribute("userId");
        return CUserId;
    }

    public boolean makeBoard(BoardDTO board){
        Long userId = CheckUserId();
        if(userId==null)
            return false;
        else{
            board.setUser_Id(userId);
            boardMapper.makeBoard(board);
            return true;
        }
    }


}
