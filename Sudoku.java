/**
 * project4 - Sudoku.java
 * 
 * This program will solve an easy sudoku puzzle using only naked and hidden singles
 * method. The program will receive input either from the Command Line Interface (CLI)
 * or from a string defined within the main method. The print method for this puzzle is optional.
 * 
 * @authors Joshua Saliutama <jsaliuta@purdue.edu>
 *          Kurt Sermersheim <ksermers@purdue.edu>
 * 
 * @date November 3, 2014
 */
import java.util.Arrays;

public class Sudoku {
    private int[][] board;                        //Stores the original state of the board.
    private int[][] boardCopy = new int[9][9];    //Stores the copy of the original board.
    private int[][] copy = new int[9][9];         //Stores the copy of the copied board. Will be modified in final.
    public static final int SIZE_ROW = 9;
    public static final int SIZE_COLUMN = 9;
    
    /**
     * Default constructor for Sudoku board. Returns an empty 9x9 board.
     */
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    /**
     * Sudoku constructor. Copies the sudoku board and put the copy in a new private variable.
     */ 
    public Sudoku(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                this.boardCopy[i][j] = board[i][j];
            }
        }
    }
    
    /**
     * Returns the copy of the board. 
     * 
     * @param void
     * 
     * @return int[][]
     */ 
    public int[][] board() {
        for (int i = 0; i < boardCopy.length; i++) {
            for (int j = 0; j < boardCopy[i].length; j++) {
                copy[i][j] = boardCopy[i][j];
            }
        }
        return copy;
    }
    
    /**
     * This function will determine the number candidates of a specific cell in the sudoku board.
     * 
     * @param int row - stores the row coordinate for the checked cell (A = 0, B = 1, etc.).     
     *        int column - stores the column coordinate for the checked cell.
     */ 
    public boolean[] candidates(int row, int column) {
        int[][] copy = board();                //Stores the copy of the board.
        boolean[] boxArr = new boolean[10];    //Stores the candidates for a specific box.
        boolean[] rowArr = new boolean[10];    //Stores the candidates for a specific row.
        boolean[] colArr = new boolean[10];    //Stores the candidates for a specific column.
        boolean[] total = new boolean[10];     //Stores the final candidate for a specific cell.
        
        //Initializing values.
        Arrays.fill(boxArr, true);
        Arrays.fill(rowArr, true);
        Arrays.fill(colArr, true);
        Arrays.fill(total, false);
        boxArr[0] = false;
        rowArr[0] = false;
        colArr[0] = false;
        
        int rowNum = 0;
        int colNum = 0;
        
        //Only start checking if the checked value is zero (i.e. empty).
        if (copy[row][column] == 0) {
            // Check box. Index start from 1.
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
            
            
            // Check row.
            for (int i = 0; i < SIZE_ROW; i++) {
                if (copy[row][i] != 0) {
                    rowArr[copy[row][i]] = false;
                } 
            }
            
            
            // Check column.
            for (int i = 0; i < SIZE_COLUMN; i++) {
                if (copy[i][column] != 0) {
                    colArr[copy[i][column]] = false;
                } 
            }
            
            
            // Compare total.
            for (int i = 0; i < 10; i++) {
                if (boxArr[i] && rowArr[i] && colArr[i]) {
                    total[i] = true;
                } 
            }
        }         
        return total;
    }
    
    /**
     * This function will determine whether the puzzle is solved or not by determining whether a zero (i.e. an
     * empty cell) is present in the cell.
     * 
     * @param void
     * 
     * @return boolean
     */ 
    public boolean isSolved() {
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                if (boardCopy[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This function is the main solving function for the board.
     * 
     * @param void
     * 
     * @return void
     */ 
    public void solve() {
        while (!isSolved() && (nakedSingles() || hiddenSingles()));
    }
    
    /**
     * This function will determine the number of naked singles present in a board. The function will
     * scan the whole 9x9 board and determine whether the number is a naked single or not based on the
     * number of candidates. The function will return 'true' if a naked single is found and a change is made
     * to the board.
     * 
     * @param void
     * 
     * @return boolean
     */ 
    public boolean nakedSingles() {
        boolean[] test;             //Stores the candidate values of a particular cell.
        boolean status = false;     //Stores the final status of nakedSingles().
        int count;                  //Stores the number of counts of 'true' values to determine naked single(s).
        int number = 0;             //Stores the possible value of naked single.
        int[][] copy = board();     //Stores the copy of the board.
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                //Test naked singles if the designated cell is 0 (i.e. empty)
                if (copy[i][j] == 0) {
                    count = 0;   // Fill/reset counter
                    test = candidates(i, j);
                    
                    //Count how many 'true' are in the test array.
                    for (int k = 0; k < test.length; k++) {
                        if (test[k] == true) {
                            count++;
                            number = k;
                        }
                    }
                    
                    //If only one, implement number.
                    if (count == 1) {
                        boardCopy[i][j] = number;
                        status = true;
                    }
                }
            }
        }
        return status;
    }
    
    /**
     * This function will determine the hidden singles in a board based on the number of unique candidates present
     * within the board. The function will return true if there is a hidden single and a change is made to the board.
     * 
     * @param void
     * 
     * @return boolean
     */ 
    public boolean hiddenSingles() {
        int[][] copy = board();   //Stores the copy of the board.
        boolean[] test;           //Stores the candidate value for the cell.
        int count = 0;            //Stores the number of candidates for determining hidden singles.
        boolean status = false;   //Stores the final status for hiddenSingles().
        
        //For testing hidden singles
        boolean test1; //Hatchet/box
        boolean test2; //Row
        boolean test3; //Column
        
        //For checking box
        int rowNum;               //Stores the modified row coordinates of the cell.
        int colNum;               //Stores the modified column coordinates of the cell.
        
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                //Start test if the designated cell is 0 (i.e. empty).
                if (copy[i][j] == 0) {
                    //Prepare candidates.
                    test = candidates(i, j);
                    //Test for each number candidate.
                    for (int x = 0; x < test.length; x++) {
                        if (test[x] == true) {
                            //Fill/reset test status to false.
                            test1 = false; //Hatchet/box
                            test2 = false; //Row
                            test3 = false; //Column

                            //--->Test #1. Checking box. Alternative of hatchet method
                            count = 0;      // Reset counter.
                            rowNum = i / 3 * 3;
                            colNum = j / 3 * 3;
                            for (int m = 0; m < 3; m++) {
                                for (int n = 0; n < 3; n++) {
                                    if (candidates(rowNum + m, colNum + n)[x] == true) {
                                        count++;
                                    }
                                }
                            }
                            //If only one candidate possible, turn test1 to true.
                            if (count == 1) {
                                test1 = true;
                            }

                            //--->Test #2
                            //Check the candidate of every cells in the same row that has candidate 'x'.
                            count = 0;      // Reset counter.
                            for (int m = 0; m < SIZE_ROW; m++) {
                                if (candidates(i, m)[x] == true) {
                                    count++;
                                }
                            }
                            
                            //If only one candidate possible, turn test2 to true.
                            if (count == 1) {
                                test2 = true;
                            }
                            
                            
                            //--->Test #3
                            //Check the candidate of every cells in the same column that has candidate 'x'.
                            count = 0;      // Reset counter.
                            for (int m = 0; m < SIZE_COLUMN; m++) {
                                if (candidates(m, j)[x] == true) {
                                    count++;
                                }
                            }
                            
                            //If only one candidate possible, turn test3 to true.
                            if (count == 1) {
                                test3 = true;
                            }
                            
                            //Apply the number. If the cell has number already, don't implement.
                            if (test1 || test2 || test3) { 
                                boardCopy[i][j] = x;
                                status = true;
                                break;
                            }
                        } //End of test condition.
                    } // End of test loop.
                }
            } // End of j.
        } // End of i.
        return status;
    }
    
    /**
     * This function will help initializing Sudoku board from user input via CLI.
     * 
     * @param String args - user input of string arguments.
     * 
     * @return int[][]
     */ 
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
    
    /**
     * This function will return the solved board in form of a String.
     * 
     * @param void
     * 
     * @return String
     */ 
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
    
    /**
     * This function will print the board to the CLI so that the user can see the final, solved
     * state of the board.
     * 
     * @param void
     * 
     * @return void
     */ 
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
        //String s7 = "100200300020010040003005006700600500050080070008004001800700400030060020009002007";// Solvable only with hidden singles
        //String s8 = "001000200000304000500060001050102070008000400020907080900070008000509000003000700";
        //String s9 = "100020003040000010005040600000201000708000105000408000007060800080000020400030007";
        //String s10 = "100020003040000050000607000005000800700080002006000900000802000010000060200070001";
        //1..2..3......4..5...6..7..82.....7...5..1..4...7.....98..1..2...6..2......4..6..7
        //...........12.34...235.617..35...74.....3.....76...82..143.825...87.23...........
        //003456789000000000000000000020000000000000000000000000000000000000000000000000000
        //000000000000200000300000000400000000500000000600000000700000000800000000900000000
        //003000000456000000789000000020000000000000000000000000000000000000000000000000000
        
        String[] sudokuPossible = {"040000080900403060078056120015340200000008000000200800109000000400000008030000542",
            "050080600016030907004910002200003170090000200005008000003000018000040093500070420",
            "850000210040300005000000000700268050000400601006000900100002000008794000090530704",
            "009201007000500109040080600704103000030000000052060070000000000260050800008000045",
            "090000050007905060000004200016543908000800130000002000000000007060300000009700386",
            "690025000000879000000100030039700021040200007100068300004000090020400008010630000",
            "000370010640005000900008040000001060760400000000800904501269430072500000000007800",
            "000000012000950030500008007060003000000007006040000709935060208004030000600002900",
            "000006004603000000000000070900000502000700008004265009000040120706052090410603000",
            "100020003000160000700008405200590006005000200090080000000600700302040000800000050",
            "070000890285000070400100650000500000060472005501000700007000018000090420300010000",
            "034700506600090000090080000009002000060900172100500008500000063000800200006300050",
            "100080000098000504000900017001400600007000020000601000000208005002719003900043000",
            "700200609001390040209046058000010000002507080000400003000000000003920500008050060",
            "080150009300090040000002000030060800972004000010007400800000300000000005003200076",
            "001706080096080130000200000560000000009008070304002000070590006900003000010000500",
            "000048006200000800009006530730000108002000074000002300000301000001004900080070002",
            "400285030703961000506040008005078000300020000000000320100030002600000040002004590",
            "050000000000009067080000904003000120098140003200390050307500006000978002000610000",
            "005002030000000065200000870000001780630000902000829000060200000070400500001007090",
            "900000000007080000680000201068010970502006000000000500010003000200500009000094063",
            "000000000100000400000007008240090005600000890008250060006010050020460001890320000",
            "090100500000092400000700060075610900209485006401000050020870600010000300700040000",
            "000080020100400097902051080000500100096200078010630000400006010000100009200305004",
            "680000901019008070730000060001400020200809000540000000000000007106000004000953000",
            "096800270023901000000000105008000000907002050010040000039000801045000000000023000",
            "009000070000800000008200359004700005900062100003050400800000004020537006000040720",
            "000160020007298000000070030600000708070010500059000064780300001010086970590000000",
            "007508000004000700100009000000000460080061000003000075700820906000000000839400050",
            "006010300100207084004300090640008000270400000000009203001000025080000000050081000",
            "700010060004093520069500300030000000010900004005000200007006000000780006800040005",
            "030000605000597000800000000050904031900720080108000000000000000507400390000815062",
            "020004310900001000006270000000080000400006205307000090000490002800010400100007003",
            "080920000000400060000037108500008009009000020070100300005009070000015906496000000",
            "009045020460207008021036490000000002800302019005070000070000000300000000050008041",
            "001000000020003000304076050000091000010300092030852047090010006500287001008000000",
            "021000630000007000060000000807004056050300007009070080900000040576080100000000509",
            "560008700007950000009060001800006000000100400000030026016800905074001080000040300",
            "486300009190000000075819400004005092600001070000000006007200045000073600000500000",
            "100056700620000930000073000001092070800000100060000000000500000000030852203048600",
            "001004005000008000007090260009200071000070000102030000000000000004080623620005090",
            "062008307010005040000000000407006130000100002001790000100002059080000700003000200",
            "301005000240000800000000007900041205780000906000000300500003002000090060000800000",
            "200000000400060095001030200000000062002000140504200030100000000090006700006405000",
            "020004091610007000000000000541000030300000200000008000090320500000000040070600009",
            "027138500000026008000090000764005100002600007030000600000040700300000000940003210",
            "380070900706009050000400000000060500900380006504000000008100600070000300001090720",
            "054020800009001070100350000007030040283045000006000000400000060000500700001003094",
            "720900000000234005000100080000000073000078064503040002900010008000000050600009030",
            "004000009000500800200006000926010754800060000001000030500490000100000320030200000",
            "083205900095030000070961080000108320000000007000090006210000000030000200040007010",
            "005000107006000000370000029000040006200600008004571002087000065600035870500008000",
            "200004000930000000081000000300001000000000560070050800400708600009000002050069030",
            "182706900005010020060000070050200000900050010006000004000300108400095002500682000",
            "003060400000000000806002090030100608002004000760000000000850010605200087078900304",
            "060000400009000003000026000200140096908030050100005008670080200010073080000000000",
            "020000000309082540050430006000070000000000305400300027008060073100000900000005000",
            "200000705000970060000004000060000300030109070000400600080007100004020000100348009",
            "069000030038900104004000000040000003006701000000060002051320648002090007000000000",
            "000210000800709000020040103000360708005002900000507031036000004700650000001000009",
            "000083005200050041840000690400000000001060008032005100020134900060000000000007200",
            "910020000000900508006001070000200004150840007400013006600002000580190030730000000",
            "025009006000000000406002700790000023000800040800001000908200001000080000210507630",
            "403170800076000000000000020005000060080560000900703000300900000052800049000610057",
            "718509000020006000430000000000000710000000060047800009000090503860045920000300008",
            "003014082900003000000802500059000000030601490001000067000080000000000103700300006",
            "000052040000000300512030000408000501060500204007004080090870000005000020080620037",
            "180034500290080106060000000040050300800100009050000000002069070000005060400078000",
            "005006030200003000060754020790800510000600002000079040400000008000060701000030200",
            "342006000060050000510070000020040130400060200100007000034009506000000700900700420",
            "400000000000800000030756400000092000060000000078000910000084090107000530500000006",
            "008000007020000010000700603006000400004800030000140095900670150800500069000320080",
            "000400027400008100000697040860005310017000000300109000000006430005071000009000050",
            "001400009700320105028090000000000048000000200830900000000040012040602000009007030",
            "002000031010308400400060500007800000081026005000050007000000010000004000070500806",
            "000051000000900065000042000000000006280000900900100204058006049702500083604780001",
            "005080060000200108300000000800500700100900432900430006200000800003012040000394200",
            "000800000000600080020490501000010700705000000980036004009200040800000100600000090",
            "010029807002070000000004010000030650004800000260000000009040000020300000405010368",
            "006700902402030000803000610020040007704012850050600409009000506005000000080000000",
            "150048039070900600000270080030100000009036008001004000500300046000009050040080000",
            "490001002050070040007005000000000005600050980034000000000000307002038514000940026",
            "003090060000100007016045002250000048780600309000000000005010704400039000000000050",
            "200000003008090200509703000400000000012500704000000300000000670000800159980607402",
            "000068050001000040300907028000300002900010000400000900000500800023070065809000070",
            "090000050007010460300600100600050000000000010003902070050000830801300790000071020",
            "000000000801000000750102000900000508230000040000040090006500200120803050075904801",
            "410205800000000005290006400307108000050000000860900102070080000500300008000600010",
            "100003002006007401504001060050000206000030050020040870000008000007906000010050009",
            "034000160070000030809002000080070020500000400010080000006104050000800609000000000",
            "000000000001600000090000400009000030000850007200000560070100084006790350034000200",
            "100008000420379008003106507000000000040001002030000049000090000070203006000460910",
            "000200040100000800000300009800703100006008400500026003004000010702000300080907005",
            "000060000090703600032000000800004003009100040005000719000080390050907000087000500",
            "000160008100078020300920000809001040010009350000004000005000470780502036001000000",
            "830050400004008000000000210007005030080600054042039000000700506093580040701000000",
            "070040000090020300524000010800070050005001060007000002000000070080300040003280005",
            "020900000073000918000010036900005040050000000000703000000002070000608005097300020",
            "020401507070930680600080430310700006000020000200009100048000060500000700000005000",
            "000000702401000080050400003000700401006980230000000000032004008004000009500071600"};
        /*
        Sudoku s = new Sudoku();
        int[][] board = initializeFromCLI(args[0]);
        s = new Sudoku(board);
        System.out.println("Original board");
        s.printBoard();
        //s.candidates(3, 0);
        //System.out.println(s.hiddenSingles());
        //System.out.println(s.nakedSingles());
        */
        for (int i = 0; i < sudokuPossible.length; i++) {
            int[][] board = initializeFromCLI(sudokuPossible[i]);
            Sudoku s1 = new Sudoku(board);
            s1.solve();
            System.out.println(s1.isSolved());
        }
        
        /*
        s.solve();
        System.out.println();
        if (s.isSolved()) {
            System.out.println("Board is solved.");
        } else {
            System.out.println("Board is not solved.");
        }
        
        System.out.println();
        s.printBoard();
        */
    }
}
