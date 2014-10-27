import java.util.Scanner;

public class Sudoku {
    private int[][] board;
    public static final int SIZE_ROW = 9;
    public static final int SIZE_COLUMN = 9;
    
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    public Sudoku(int[][] board) {
        this.board = board;
    }
    
    public int[][] board() {
        return this.board;
    }
    
    public boolean[] candidates(int row, int column) {
        boolean[] box = new boolean[10];
        boolean[] row = new boolean[10];
        boolean[] col = new boolean[10];
        boolean[] total = new boolean[10];
        box[0] = false;
        row[0] = false;
        col[0] = false;
        
        int rowNum;
        int colNum;
        
        // Check box
        /*
        int rowNum = (row / 3) * 3;
        int colNum = (col / 3) * 3;
        */
        if (row + 1 % 3 == 2) {
            rowNum = row - 1;
        } else if (row + 1 % 3 == 0) {
            rowNum = row - 2;
        }
        
        if (column + 1 % 3 == 2) {
            colNum = column - 1;
        } else if (column + 1 % 3 == 0) {
            colNum = column - 2;
        }

        for (int i = rowNum; i < (rowNum + 3); i++) {
            for (int j = colNum; j < (colNum + 3); j++) {
                if (board[i][j] != null) {
                    if (board[i][j] != 0) {
                        box[board[i][j]] = false;
                    } else {
                        box[board[i][j]] = true;
                    }
                }
            }
        }
        
        //Check row
        for (int i = 0; i < SIZE_ROW; i++) {
            if (board[i][column] != null) {
                if (board[i][column] != 0) {
                    row[board[i][column]] = false;
                } else {
                    row[board[i][column]] = true;
                }
            }
        }
        
        //Check column
        for (int i = 0; i < SIZE_COLUMN; i++) {
            if (board[row][i] != null) {
                if (board[row][i] != 0) {
                    row[board[row][i]] = false;
                } else {
                    row[board[row][i]] = true;
                }
            }
        }
        
        // Compare total
        for (int i = 0; i < 10; i++) {
            if (box[i] && row[i] && colummn[i]) {
                total[i] = true;
            } else {
                total[i] = false;
            }
        }
        
        return total;
    }
    
    public boolean isSolved() {
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void solve() {
        while (!isSolved() && (nakedSingles() || hiddenSingles())) {
            
        }
    }
    
    public boolean nakedSingles() {
        boolean[] test;
        int count = 0;
        int number;
        boolean status = false;
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                test = candidates(i, j);
                for (int k = 0; k < test.length; k++) {
                    if (test[k] == true) {
                        count++;
                        number = k + 1;
                    }
                }
                if (count == 1) {
                    board[i][j] = number;
                    status = true;
                }
            }
        }
        return status;
    }
    
    public boolean hiddenSingles() {
        
    }
    
    /*
    public void initializeFromCLI(String[] args) {
        int k = 0;
        int sudoku[][];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sudoku[i][j] = args.charAt(k);
                k++;
            }
        }
        Sudoku(sudoku);
    }
    */
    
    public static void main(String[] args) {
        int[][] board = parseString(args[0]);
        Sudoku s = new Sudoku(board);
    }
}
