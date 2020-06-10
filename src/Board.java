import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;

public class Board {
    private int _size;
    private Piece[][] _board;

    public Board(int size) {
        _size = size;
        _board = new Piece[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _board[i][j] = new Piece();
            }
        }
    }

    public Board(int size, Piece[][] board) {
        _size = size;
        _board = new Piece[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _board[i][j] = board[i][j];
            }
        }
    }

    public int get_size() {
        return _size;
    }

    public char make_Move(char color, int x, int y) {
        if (_board[x][y].getColor() == 'e') {
            _board[x][y] = new Piece(color);
            return color;
        } else {
            return 'e';
        }
    }

    public void remove_Dead(char color) {
        //checks each stone one by one
        //if the stone is the right color, check the surrounding pieces
        //if there are empty stones next to the stone, move on
        //Flood fill, but stop the flood fill if a stone has empty spaces next to it
        //Keep a boolean array that marks if something has been checked
        // so that as we iterate through every stone, we don't check twice
        boolean[][] checked = new boolean[_size][_size];
        for (int i = 0; i < _size; i ++) {
            for (int j = 0; j < _size; j++) {
                if (!checked[i][j] && _board[i][j].getColor() == color) {
                    HashSet<Pair> thischeck = new HashSet<>();
                    int total_libs = check_libs(i, j);
                    Stack<Pair> stack = new Stack<Pair>();
                    stack.push(new Pair(i,j));
                    while (!stack.empty()) {
                        Pair thispair = stack.pop();
                        int x = (int)thispair.getKey();
                        int y = (int)thispair.getValue();
                        if (_board[x][y].getColor() ==color && !thischeck.contains(thispair)) {
                            total_libs += check_libs(x,y);
                            checked[x][y] = true;
                            thischeck.add(thispair);
                            if (x > 0) {
                                stack.push(new Pair(x-1,y));
                            }
                            if (x < _size-1) {
                                stack.push(new Pair(x+1,y));
                            }
                            if (y > 0) {
                                stack.push(new Pair(x, y-1));
                            }
                            if (y < _size-1) {
                                stack.push(new Pair(x, y+1));
                            }
                        }
                    }
                    if (total_libs == 0) {
                        for (Pair deadpair : thischeck) {
                            _board[(int)deadpair.getKey()][(int)deadpair.getValue()] = new Piece('e');
                        }
                    }
                }
            }
        }
    }

    public int check_libs(int x, int y) {
        int libs = 0;
        char color = _board[x][y].getColor();
        if (x > 0) {
            if (_board[x-1][y].getColor() == 'e') {
                libs++;
            }
        }
        if (x < _size-1) {
            if (_board[x+1][y].getColor() == 'e') {
                libs++;
            }
        }
        if (y > 0 ) {
            if (_board[x][y-1].getColor() == 'e') {
                libs++;
            }
        }
        if (y < _size-1) {
            if (_board[x][y+1].getColor() == 'e') {
                libs++;
            }
        }
        return libs;
    }

    public Piece[][] get_board() {
        return _board;
    }
}
