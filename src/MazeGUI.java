import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MazeGUI extends JFrame {
    private JPanel panelMain;
    private JPanel secondPanel;
    private static final int GAP = 2;
    private JLabel[][] gridSquares;
    private static final Color PATH = Color.BLUE;
    private static final Color WALL = Color.BLACK;
    private static final Color START = Color.GREEN;
    private static final Color END = Color.RED;
    private static final Color VALID = Color.GREEN;
    private static final Color INVALID = Color.LIGHT_GRAY;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private int size = 24;

    public MazeGUI(){
        this.setTitle("Maze");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.secondPanel.setLayout(new GridLayout(size, size, GAP, GAP));
        this.initializeGrid();
        this.createMaze();
        this.add(this.panelMain);
        this.setVisible(true);
        this.setExtendedState(this.getExtendedState() | 6);

    }

    private void initializeGrid() {
        this.gridSquares = new JLabel[size][size];

        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; j ++) {
                this.gridSquares[i][j] = new JLabel("");
                this.gridSquares[i][j].setOpaque(true);
                this.gridSquares[i][j].setBackground(PATH);
                this.secondPanel.add(this.gridSquares[i][j]);
            }
        }



    }
    private void createMaze(){
        Random random = new Random();

        for (int i = 0; i < this.size ; i++) {

            for (int j = 0; j < this.size; j++) {

                //if the section of the array is a boundary its automatically a wall
                if (i == 0 || i == this.size - 1 || j == 0 || j == this.size -1){
                    this.gridSquares[i][j].setBackground(WALL);

                }else{
                    //else a random pattern is created in the insides of the wall
                    Color wallOrPath = random.nextInt(this.size * this.size)>
                            ((this.size * this.size) /4 ) ? PATH : WALL;
                    this.gridSquares [i][j].setBackground(wallOrPath);
                }
            }
        }

        //picking our starting point
        startCol= 0;
        startRow= random.nextInt(this.size-1);
        if(startRow== 0){
            startRow++;
        }
        //picking our end point
        endCol= this.size -1;
        endRow= random.nextInt(this.size-1);
        if(endRow== 0){
            endRow ++;
        }
        //setting our end and starting point
        gridSquares [startRow][startCol].setBackground(START);
        gridSquares [endRow][endCol].setBackground(END);

    }

    public void startStartTraveling(){
        traverseMaze(startRow,startCol);
    }

    //method that controls the movements of the maze
    public void traverseMaze(int x, int y){



            this.gridSquares[x][y].setBackground(VALID);
            pauseDisplay();

            if (x == endRow && y == endCol) {
                JOptionPane.showMessageDialog(null, "Maze Solved!!!");
                System.exit(0);
            } else {

                //controlling movement to the right
                int right = y + 1;
                if (right < this.size && this.gridSquares[x][right].getBackground() != WALL &&
                        this.gridSquares[x][right].getBackground() != VALID
                        && this.gridSquares[x][right].getBackground() != INVALID) {
                    traverseMaze(x, right);
                }

                //controlling movement up
                int up = x - 1;
                if (up < this.size && this.gridSquares[up][y].getBackground() != WALL &&
                        this.gridSquares[up][y].getBackground() != VALID
                        && this.gridSquares[up][y].getBackground() != INVALID) {
                    traverseMaze(up, y);
                }

                //controlling movements down
                int down = x + 1;
                if (down < this.size && this.gridSquares[down][y].getBackground() != WALL &&
                        this.gridSquares[down][y].getBackground() != VALID
                        && this.gridSquares[down][y].getBackground() != INVALID) {
                    traverseMaze(down, y);
                }

                //controlling movements to the left
                int left = y - 1;
                if (left > 0 && gridSquares[x][left].getBackground() != WALL &&
                        this.gridSquares[x][left].getBackground() != VALID
                        && this.gridSquares[x][left].getBackground() != INVALID) {

                    traverseMaze(x, left);
                }

                //checking if maze is unsolvable
                if (x == startRow && y == startCol) {

                    JOptionPane.showMessageDialog(null, "Maze Not Solvable!!!!");
                    System.exit(0);
                }
                //if none of the conditions are met make that path a traveled one
                this.gridSquares[x][y].setBackground(INVALID);
            }


    }
    //threat method that pauses the program for a second so its visible
    private void pauseDisplay(){
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        MazeGUI maze2 = new MazeGUI();

        maze2.startStartTraveling();

    }
}