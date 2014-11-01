import java.util.Arrays;

public class Sudoku {
    private int[][] board;
    private int[][] boardCopy;
    public static final int SIZE_ROW = 9;
    public static final int SIZE_COLUMN = 9;
    
    //Status: working
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    //Status: working
    public Sudoku(int[][] board) {
        int[][] copy = board();
        this.boardCopy = copy;
    }
    
    //Status: working
    public int[][] board() {
        return this.board.clone();
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
        
        //Only start checking if the checked value is zero (i.e. empty)
        if (copy[row][column] == 0) {
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
        } 
        
        /*
        //--->For diagosis purpose
        // Check box diagnosis
        System.out.println("boxArr: ");
        for (int i = 0; i < boxArr.length; i++) {
            System.out.println(i + " " + boxArr[i]);
        }
        System.out.println();
        
        // Check row diagnosis
        System.out.println("rowArr: ");
        for (int i = 0; i < rowArr.length; i++) {
            System.out.println(i + " " + rowArr[i]);
        }
        System.out.println();
        
        // Check column diagnosis
        System.out.println("colArr: ");
        for (int i = 0; i < colArr.length; i++) {
            System.out.println(i + " " + colArr[i]);
        }
        System.out.println();
        
        //Check total diagnosis
        System.out.println("Result/total: ");
        for (int i = 0; i < total.length; i++) {
            System.out.println(i + " " + total[i]);
        }
        */
        
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
        while (!isSolved() && (nakedSingles() || hiddenSingles()));
    }
    
    //Status: working
    public boolean nakedSingles() {
        int[][] copy = board();
        boolean[] test;
        int count;
        int number = 0;
        boolean status = false;  //Goal: find as many naked singles as possible.
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                //Test naked singles if the designated cell is 0 (i.e. empty)
                if (copy[i][j] == 0) {
                    count = 0;   // Fill/reset counter
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
    
    //Status: not working
    public boolean hiddenSingles() {
        boolean[][] boardStatus = new boolean[3][3];
        int[][] copy = board();
        boolean[] test;     //Stores the candidate value for the cell
        int count = 0;
        //boolean status = false;
        //For checking box
        int rowNum = 0;
        int colNum = 0;
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                //Start test if the designated cell is 0 (i.e. empty)
                if (copy[i][j] == 0) {
                    //--->Trying to implement hatchet method to solve hidden singles.
                    //Prepare candidates
                    test = candidates(i, j);
                    //Test for each number candidate
                    for (int x = 0; x < test.length; x++) {
                        if (test[x] == true) {
                            // Fill/reset boardStatus array with 'true' value
                            for (boolean[] value : boardStatus) {
                                Arrays.fill(value, true);
                            }
                            for (int a = 0; a < 9; a++) {
                                // Check row
                                if (i % 3 == 0) {
                                    if (copy[i+1][a] == x) { //|| candidates(i+1 , a)[x] == true) {
                                        boardStatus[1][0] = false;
                                        boardStatus[1][1] = false;
                                        boardStatus[1][2] = false;
                                    }
                                    if (copy[i+2][a] == x) { //|| candidates(i+2 , a)[x] == true) {
                                        boardStatus[2][0] = false;
                                        boardStatus[2][1] = false;
                                        boardStatus[2][2] = false;
                                    }
                                } else if (i % 3 == 1) {
                                    if (copy[i-1][a] == x) { //|| candidates(i-1 , a)[x] == true) {
                                        boardStatus[0][0] = false;
                                        boardStatus[0][1] = false;
                                        boardStatus[0][2] = false;
                                    }
                                    if (copy[i+1][a] == x) { //|| candidates(i+1 , a)[x] == true) {
                                        boardStatus[2][0] = false;
                                        boardStatus[2][1] = false;
                                        boardStatus[2][2] = false;
                                    }
                                } else {
                                    if (copy[i-2][a] == x) { //|| candidates(i-2 , a)[x] == true) {
                                        boardStatus[0][0] = false;
                                        boardStatus[0][1] = false;
                                        boardStatus[0][2] = false;
                                    }
                                    if (copy[i-1][a] == x) { //|| candidates(i-1 , a)[x] == true) {
                                        boardStatus[1][0] = false;
                                        boardStatus[1][1] = false;
                                        boardStatus[1][2] = false;
                                    }
                                }
                                
                                //Check column
                                if (j % 3 == 0) {
                                    if (copy[a][j+1] == x) { //|| candidates(a , j+1)[x] == true) {
                                        boardStatus[0][1] = false;
                                        boardStatus[1][1] = false;
                                        boardStatus[2][1] = false;
                                    }
                                    if (copy[a][j+2] == x) { //|| candidates(a , j+2)[x] == true) {
                                        boardStatus[0][2] = false;
                                        boardStatus[1][2] = false;
                                        boardStatus[2][2] = false;
                                    }
                                } else if (j % 3 == 1) {
                                    if (copy[a][j-1] == x) { //|| candidates(a , j-1)[x] == true) {
                                        boardStatus[0][0] = false;
                                        boardStatus[1][0] = false;
                                        boardStatus[2][0] = false;
                                    }
                                    if (copy[a][j+1] == x) { //|| candidates(a , j+1)[x] == true) {
                                        boardStatus[0][2] = false;
                                        boardStatus[1][2] = false;
                                        boardStatus[2][2] = false;
                                    }
                                } else {
                                    if (copy[a][j-2] == x) { //|| candidates(a , j-2)[x] == true) {
                                        boardStatus[0][0] = false;
                                        boardStatus[1][0] = false;
                                        boardStatus[2][0] = false;
                                    }
                                    if (copy[a][j-1] == x) { //|| candidates(a , j-1)[x] == true) {
                                        boardStatus[0][1] = false;
                                        boardStatus[1][1] = false;
                                        boardStatus[2][1] = false;
                                    }
                                }
                                
                                //Check box
                                rowNum = i / 3 * 3;
                                colNum = j / 3 * 3;
                                for (int m = 0; m < 3; m++) {
                                    for (int n = 0; n < 3; n++) {
                                        if (copy[rowNum + m][colNum + n] != 0) {
                                            boardStatus[m][n] = false;
                                        }
                                    }
                                }
                            }
                            
                            /*
                            //---> For diagnostic purpose
                            System.out.println("x = " + x);
                            System.out.println("[i] = " + i + " [j] = " + j);
                            for (int a = 0; a < 3; a++) {
                                for (int b = 0; b < 3; b++) {
                                    System.out.print(boardStatus[a][b] + " ");
                                }
                                System.out.println();
                            }
                            */
                            
                            //Check the status of statusBoard. Count the # of true
                            count = 0;     // Reset counter.
                            for (int row = 0; row < boardStatus.length; row++) {
                                for (int col = 0; col < boardStatus[row].length; col++) {
                                    if (boardStatus[row][col] == true) {
                                        count++;
                                    }
                                }
                            }
                            //If found, implement number, but if the cell has a number already, don't implement.
                            if (count == 1 && boardStatus[i % 3][j % 3] == true) {
                                board[i][j] = x;
                                return true;
                            }
                        } //End of test condition
                    } // End of test 
                }
            } // End of j
        } // End of i
        return false;
        //return status;
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
        //String s5 = "028007000016083070000020851137290000000730000000046307290070000000860140000300700";
        //String s6 = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
        
        Sudoku s = new Sudoku();
        int[][] board = initializeFromCLI(args[0]);
        s = new Sudoku(board);
        System.out.println("Original board");
        s.printBoard();
        //s.candidates(7, 2);
        //System.out.println(s.hiddenSingles());
        
        s.solve();
        System.out.println();
        if (s.isSolved()) {
            System.out.println("Board is solved.");
        } else {
            System.out.println("Board is not solved.");
        }
        
        System.out.println();
        s.printBoard();
    }
}
