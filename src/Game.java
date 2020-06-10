import java.util.HashSet;

public class Game {
    char _turn;
    Board _currentBoard;
    HashSet<Board> _SuperKo;
    public Game() {
        _turn = 'b';
        _currentBoard = new Board(9);
        _SuperKo = new HashSet<Board>();
    }
    public void Move(char color, int x, int y) {
        //takes current board and copies it
        Board newBoard = new Board(9, _currentBoard.get_board());
        //takes the copy and adds a piece (indicated by the color and coordinates)
        if (newBoard.make_Move(color, x, y) == 'e') {
            return;
        }
        //takes the copy and removes Dead pieces
        if (color == 'b') {
            newBoard.remove_Dead('w');
            newBoard.remove_Dead('b');
        }
        if (color == 'w') {
            newBoard.remove_Dead('b');
            newBoard.remove_Dead('w');
        }
        // checks if board isLegal
        if (isLegal(newBoard)) {
            _SuperKo.add(_currentBoard);
            _currentBoard = newBoard;
            if (_turn == 'b') {
                _turn = 'w';
            }
            if (_turn == 'w') {
                _turn = 'b';
            }
        } else {
            return;
        }
        // if not, throw error?
        // passes the old board to _SuperKo
        // passes new board to _currentBoard
        // changes _turn
    }
    public Boolean isLegal(Board currBoard) {
        for (Board prevBoard : _SuperKo) {
            if (currBoard.equals(prevBoard)) {
                return false;
            }
        }
        return true;
    }
    public void printTurn() {
        System.out.println(_turn);
    }
    public void printSize() {
        System.out.println(_currentBoard.get_size());
    }
    public void printBoard() {
        for (int j = 0; j < _currentBoard.get_size(); j++) {
            String thisLine = "";
            for (int i = 0; i < _currentBoard.get_size(); i++) {
                if (_currentBoard.get_board()[j][i].getColor() == 'e') {
                    thisLine += "+";
                }
                if (_currentBoard.get_board()[j][i].getColor() == 'b') {
                    thisLine += "b";
                }
                if (_currentBoard.get_board()[j][i].getColor() == 'w') {
                    thisLine += "w";
                }
            }
            System.out.println(thisLine);
        }
        System.out.println();
    }
}
