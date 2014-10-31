import java.util.Arrays;

public class Sudoku {
    private int[][] board;
    public static final int SIZE_ROW = 9;
    public static final int SIZE_COLUMN = 9;
    
    //Status: working
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    //Status: working
    public Sudoku(int[][] board) {
        this.board = board;
    }
    
    //Status: working
    public int[][] board() {
        int[][] copy = board.clone();
        return copy;
    }
    
    //Status: working
    public boolean[] candidates(int row, int column) {
        int[][] copy = board();
        boolean[] boxArr = new boolean[10];
        boolean[] rowArr = new boolean[10];
        boolean[] colArr = new boolean[10];
        boolean[] total = new boolean[10];
        Arrays.fill(boxArr, true);
        Arrays.fill(rowArr, true);
        Arrays.fill(colArr, true);
        Arrays.fill(total, true);
        boxArr[0] = false;
        rowArr[0] = false;
        colArr[0] = false;
        
        int rowNum = 0;
        int colNum = 0;

        // Check box. Index start from 1
        if ((row + 1) % 3 == 2) {
            rowNum = row - 1;
        } else if ((row + 1) % 3 == 0) {
            rowNum = row - 2;
        } else {
            rowNum = row;
        }
        
        if ((column + 1) % 3 == 2) {
            colNum = column - 1;
        } else if ((column + 1) % 3 == 0) {
            colNum = column - 2;
        } else {
            colNum = column;
        }
          
        for (int i = rowNum; i < (rowNum + 3); i++) {
            for (int j = colNum; j < (colNum + 3); j++) {
                if (copy[i][j] != 0) {
                    boxArr[copy[i][j]] = false;
                } 
            }
        }

        /*
        // Check box. Index start from 0
        rowNum = (row / 3) * 3;
        colNum = (column / 3) * 3;
        for (int i = rowNum; i < (rowNum + 3); i++) {
            for (int j = colNum; j < (colNum + 3); j++) {
                if (board[i][j] != 0) {
                    boxArr[board[i][j]] = false;
                } else {
                    boxArr[board[i][j]] = true;
                }
            }
        }
        */
        
        // Check row
        for (int i = 0; i < SIZE_ROW; i++) {
            if (copy[row][i] != 0) {
                rowArr[copy[row][i]] = false;
            } 
        }
        
        // Check column
        for (int i = 0; i < SIZE_COLUMN; i++) {
            if (copy[i][column] != 0) {
                colArr[copy[i][column]] = false;
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
    
    //Status: Working
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
    
    //Status: working
    public void solve() {
        while (!isSolved() && (nakedSingles() || hiddenSingles())) {
        }
    }
    
    //Status: working
    public boolean nakedSingles() {
        int[][] copy = board();
        boolean[] test;
        int count;
        int number = 0;
        boolean status = false;
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                if (copy[i][j] == 0) {
                    count = 0;
                    test = candidates(i, j);
                    for (int k = 0; k < test.length; k++) {
                        if (test[k] == true) {
                            count++;
                            number = k;
                        }
                    }
                    if (count == 1) {
                        board[i][j] = number;
                        status = true;
                    }
                }
            }
        }
        return status;
    }
    
    public boolean hiddenSingles() {
        
    }
    
    /*
    // Status: Not working
    public boolean hiddenSingles() {
        /* // Version 1
        boolean[] test;
        int[] countRow = new int[10];
        int[] countCol = new int[10];
        int[] countBox = new int[10];
        boolean status = false;
        
        // Test row
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                // Test for one cell
                test = candidates (i, j);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        countRow[k]++;
                    }
                }
            }
            
            // Apply the number 
            for (int j = 0; j < SIZE_COLUMN; j++) {
                test = candidates(i, j);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        if (countRow[k] == 1) {
                            board[i][j] = k;
                            status = true;
                        }
                    }
                }
            }
        } // Test row ends
        
        // Test columns
        for (int i = 0; i < SIZE_COLUMN; i++) {
            for (int j = 0; j < SIZE_ROW; j++) {
                // Test for one cell
                test = candidates (j, i);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        countCol[k]++;
                    }
                }
            }
            
            // Apply the number 
            for (int j = 0; j < SIZE_COLUMN; j++) {
                test = candidates(j, i);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        if (countCol[k] == 1) {
                            board[i][j] = k;
                            status = true;
                        }
                    }
                }
            }
        } // Test column ends
        
        // Test box
        
        // Test hidden singles. Apply the number
        for (int i = 0; i < 10; i++) {
            if (countRow[i] == 1 && countCol[i] == 1 && countBox[i] == 1) {
                
            }
        }
        //System.out.println(status);
        */
        /*
        // Version 2
        boolean[] test;
        int[] countRow = new int[10];
        int[] countCol = new int[10];
        int[] countBox = new int[10];
        boolean status = false;
        int index = 0;
        int[] rowListRow;
        int[] colListRow;
        int[] rowListCol;
        int[] colListCol;
        int[] rowListBox;
        int[] colListBox;
        
        // Test row
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                // Test for one cell
                test = candidates (i, j);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        countRow[k]++;
                        rowList[index]++;
                        colList[index]++;
                        index++;
                    }
                }
            }
        } // Test row ends
        
         
        
        // Test columns
        for (int i = 0; i < SIZE_COLUMN; i++) {
            for (int j = 0; j < SIZE_ROW; j++) {
                // Test for one cell
                test = candidates (j, i);
                for (int k = 1; k < test.length; k++) {
                    if (test[k] == true) {
                        countCol[k]++;
                        rowList[index]++;
                        colList[index]++;
                        index++;
                    }
                }
            }
        } // Test column ends
        
        // Test box
        for (int i = (row / 3) * 3; i < (rowNum + 3); i++) {
            for (int j = (col / 3) * 3; j < (colNum + 3); j++) {
                if (board[i][j] != 0) {
                    boxArr[board[i][j]] = false;
                } else {
                    boxArr[board[i][j]] = true;
                }
            }
        }
        
        // Test hidden singles. Apply the number
        for (int i = 0; i < 10; i++) {
            if (countRow[i] == 1 && countCol[i] == 1 && countBox[i] == 1) {
                if (rowList[i] == 
            }
        }
        return status;
    }
    */
        
    // Status: working
    public static int[][] initializeFromCLI(String args) {
        int k = 0;
        int sudoku[][] = new int[9][9];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                sudoku[i][j] = (int)(args.charAt(k)) - 48;
                k++;
            }
        }
        return sudoku;
    }

    // Status: working
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
    
    //Status: working
    public void printBoard() {
        int[][] result = new int[9][9];
        result = board();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.printf("%s ", result[i][j]);
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
                if (j == 8) {
                    System.out.print("\n");
                }
            }
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        //String s = "000041000060000200000000000320600000000050040700000000000200300480000000501000000"; //Easy
        //String s2 = "000105000140000670080002400063070010900000003010090520007200080026000035000409000"; //Ori
        
        //Test strings for naked singles
        //String s3 = "412736589000000106568010370000850210100000008087090000030070865800000000000908401";
        //String s4 = "010900740000800003070320690004030200000602000008010300081070030300008000069003020";
        
        //Test strings for hidden singles
        //String s5 = "";
        //String s6 = "";
        
        Sudoku s = new Sudoku();
        int[][] board = initializeFromCLI(args[0]);
        s = new Sudoku(board);
        System.out.println("Original board");
        s.printBoard();
        s.solve();
        System.out.println();
        System.out.println("Solved board");
        s.printBoard();
    }
}
