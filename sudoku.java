/*
use this as a reference board

3 0 6 5 0 8 4 0 0
5 2 0 0 0 0 0 0 0
0 8 7 0 0 0 0 3 1
0 0 3 0 1 0 0 8 0
9 0 0 8 6 3 0 0 5
0 5 0 0 9 0 6 0 0
1 3 0 0 0 0 2 5 0
0 0 0 0 0 0 0 7 4
0 0 5 2 0 6 3 0 0

*/


import java.util.*;

class SudokuSolver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] board = new int[9][9];

        System.out.println("Enter a sudoku board");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        if (!solve(board)) {
            System.out.println("The sudoku board is unsolvable");
        }

    }

    // print the board out in the console
    public static void printBoard(int[][] board) {
        System.out.println("- - - - - - - - - -");
        for (int i = 0; i < board.length; i++) {
            if (i%3 == 0 && i != 0) {
                System.out.println("- - - - - - - - - -");
            }

            for (int j = 0; j < board[0].length; j++) {
                if (j%3 == 0 && j != 0) {
                    System.out.print("|");
                }
                if (j == 8) {
                    System.out.println(board[i][j]);
                } else {
                  System.out.print(Integer.toString(board[i][j]) + " ");
                }
            }
        }
    }

    // find the next empty square on the board
    public static void findNextEmpty(int[][] board, int[] nextEmptySquare) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {

                    // fill in the row and column of the empty square
                    nextEmptySquare[0] = i;
                    nextEmptySquare[1] = j;
                    return;
                }
            }
        }
        nextEmptySquare[0] = -1;
        nextEmptySquare[1] = -1;
    }

    public static boolean isValid(int[][] board, int guess, int row, int col) {
        // check row if the guessed number already exists
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == guess) {
                return false;
            }
        }

        // check column if the guessed number already exists
        for (int j = 0; j < 9; j++) {
            if (board[j][col] == guess) {
                return false;
            }
        }

        // check 3x3 grid if the guessed number already exists
        int rowStart = (row/3)*3;
        int colStart = (col/3)*3;

        for (int k = rowStart; k < rowStart+3; k++) {
            for (int m = colStart; m < colStart+3; m++) {
                if (board[k][m] == guess) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solve(int[][] board) {

        // a mutable array whereby the first value is the row of the next empty square
        // and the second value is the column of the next empty square
        int[] nextEmptySquare = new int[2];

        // find the row & column of the next empty square
        findNextEmpty(board, nextEmptySquare);
        int row = nextEmptySquare[0];
        int col = nextEmptySquare[1];

        // if all squares are filled up, sudoku board is solved
        if (nextEmptySquare[0] == -1) {
            printBoard(board);
            return true;
        }

        // make a guess as to the correct number in the empty square
        for (int k = 1; k <= 9; k++) {
            // check if the board is still valid after putting the number in the square
            if (isValid(board, k, row, col) == true) {
                board[row][col] = k;

                // recursively call solve
                if (solve(board)) {
                    return true;
                }
            }
            
            // if invalid or the guess does not solve puzzle, backtrack
            board[row][col] = 0;
        }

        // return false if puzzle is unsolvable for all possible values.
        return false;
    }
}