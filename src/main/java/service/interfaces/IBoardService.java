package service.interfaces;

import domain.BoardDTO;
import domain.PointDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IBoardService {
    boolean makeBoard(BoardDTO board);
    boolean deleteBoard(long boardId) throws Exception;
    boolean updateBoard(BoardDTO board, long boardID) throws Exception;
    boolean revisePoint(PointDTO point) throws Exception;
    List<BoardDTO> findBoard(String title, int type, int page) throws Exception;
    BoardDTO readBoard(long boardId, HttpServletResponse response) throws Exception;
    List<BoardDTO> boardList(int page);

}
