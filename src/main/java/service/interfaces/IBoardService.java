package service.interfaces;

import domain.BoardDTO;

public interface IBoardService {
    boolean makeBoard(BoardDTO board);
    boolean deleteBoard(long boardId);
    boolean updateBoard(BoardDTO board);
    boolean reivsePoint(int point);
    boolean findBoard(String title);
    BoardDTO readBoard(long boardId);

}
