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
        boolean[] boxArr = new boolean[10];
        boolean[] rowArr = new boolean[10];
        boolean[] colArr = new boolean[10];
        boolean[] total = new boolean[10];
        boxArr[0] = false;
        rowArr[0] = false;
        colArr[0] = false;
        
        int rowNum = 0;
        int colNum = 0;
        
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
                if (board[i][j] != 0) {
                    boxArr[board[i][j]] = false;
                } else {
                    boxArr[board[i][j]] = true;
                }
            }
        }
        
        //Check row
        for (int i = 0; i < SIZE_ROW; i++) {
            if (board[i][column] != 0) {
                rowArr[board[i][column]] = false;
            } else {
                rowArr[board[i][column]] = true;
            }
        }
        
        //Check column
        for (int i = 0; i < SIZE_COLUMN; i++) {
            if (board[row][i] != 0) {
                rowArr[board[row][i]] = false;
            } else {
                rowArr[board[row][i]] = true;
            }
        }
        
        // Compare total
        for (int i = 0; i < 10; i++) {
            if (boxArr[i] && rowArr[i] && colArr[i]) {
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
        int number = 0;
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
        boolean[] test;
        //int counter;
        int[] count = new int[10];
        boolean status = false;
        
        // Test row
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                // Test for one cell
                test = candidates (i, j);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        count[k]++;
                    }
                }
            }
            
            // Apply the number 
            for (int j = 0; j < SIZE_COLUMN; j++) {
                test = candidates(i, j);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        if (count[k] == 1) {
                            board[i][j] = k;
                            status = true;
                        }
                    }
                }
            }
        }
        
        // Test columns
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                // Test for one cell
                test = candidates (j, i);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        count[k]++;
                    }
                }
            }
            
            // Apply the number 
            for (int j = 0; j < SIZE_COLUMN; j++) {
                test = candidates(j, i);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        if (count[k] == 1) {
                            board[i][j] = k;
                            status = true;
                        }
                    }
                }
            }
        }
        
        // Test box
        
        return status;
    }
    
    
    public static int[][] initializeFromCLI(String args) {
        int k = 0;
        int sudoku[][] = new int[9][9];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                sudoku[i][j] = args.charAt(k);
                k++;
            }
        }
        return sudoku;
    }

    
    public String getBoard() {
        String s = "";
        int[][] result = new int[9][9];
        result = board();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                s += result[i][j];
            }
        }
        return s;
    }
    
    public static void main(String[] args) {
        //String s = "000041000060000200000000000320600000000050040700000000000200300480000000501000000";
        //int[][] board = parseString(s);
        int[][] board = initializeFromCLI(args[0]);
        Sudoku s = new Sudoku(board);
        s.solve();
        System.out.printf("%s\n", s.getBoard());
    }
}
