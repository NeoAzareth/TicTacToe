package com.sichengzhu.tictactoe;

public class GameLogic {

    private char[][] board;
    private char marker;

    public GameLogic() {
        board = new char[3][3];
        initBoard();
    }

    public void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '*';
    }

    public boolean checkFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '*')
                    isFull = false;

        return isFull;
    }

    public char[][] getBoard(){
        return board;
    }

    public void setBoard(char [][] board){
        this.board = board;
    }

    public boolean addMarker(char marker, int row, int column) {
        if (board[row][column] == '*') {
            board[row][column] = marker;
            return true;
        }

        return false;
    }

    public boolean hasWon() {
        return (checkRows() || checkCols() || checkDiagonals());
    }

    public char getWinningMarker(){
        return marker;
    }

    private boolean check(char c1, char c2, char c3) {
        if((c1 != '*') && (c1 == c2) && (c2 == c3))
            marker = c1;

        return ((c1 != '*') && (c1 == c2) && (c2 == c3));
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++)
            if (check(board[i][0], board[i][1], board[i][2]) == true)
                return true;

        return false;
    }

    private boolean checkCols() {
        for (int i = 0; i < 3; i++)
            if (check(board[0][i], board[1][i], board[2][i]) == true)
                return true;

        return false;
    }

    private boolean checkDiagonals() {
        return ((check(board[0][0], board[1][1], board[2][2]) == true) || (check(board[0][2], board[1][1], board[2][0]) == true));
    }
}
