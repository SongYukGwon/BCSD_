package service.interfaces;

import domain.BoardDTO;

import java.util.List;

public interface IBoardService {
    boolean makeBoard(BoardDTO board);
    boolean deleteBoard(long boardId) throws Exception;
    boolean updateBoard(BoardDTO board, long boardID) throws Exception;
    boolean revisePoint(int point, Long boardId);
    boolean cancelPoint(Long boardId) throws Exception;
    List<BoardDTO> findBoard(String title, int type, int page) throws Exception;
    BoardDTO readBoard(long boardId) throws Exception;
    List<BoardDTO> boardList(int page);

}
