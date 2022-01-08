package service.interfaces;

import domain.BoardDTO;

import java.util.List;

public interface IBoardService {
    boolean makeBoard(BoardDTO board);
    boolean deleteBoard(long boardId) throws Exception;
    boolean updateBoard(BoardDTO board, long boardID) throws Exception;
    boolean revisePoint(int point);
    List<BoardDTO> findBoard(String title);
    BoardDTO readBoard(long boardId) throws Exception;
    List<BoardDTO> boardList();

}
