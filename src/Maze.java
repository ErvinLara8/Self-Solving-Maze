import java.nio.file.Path;
import java.util.Random;

public class Maze {
    //class variables
    private int size;
    private char[][] grid ;

    private static final char PATH = '.';
    private static final char WALL = '#';

    private int end_row, end_col, start_row, start_col;

    //constructor
    public Maze(int size){
        this.size = size;
        this.grid = new char[size][size];
        createMze();

    }
    //method that creates the maze
    private void createMze(){

        Random random = new Random();

        for (int i = 0; i < this.size ; i++) {

            for (int j = 0; j < this.size; j++) {

                //if the section of the array is a boundary its automatically a wall
                if (i == 0 || i == this.size - 1 || j == 0 || j == this.size -1){
                    this.grid[i][j] = WALL;

                }else{
                    //else a random pattern is created in the insides of the wall
                    char wallOrPath = random.nextInt(this.size * this.size)>
                            ((this.size * this.size) /4 ) ? PATH : WALL;
                    this.grid [i][j] = wallOrPath;
                }
            }
        }

        //picking our starting point
        start_col = 0;
        start_row = random.nextInt(this.size-1);
        if(start_row == 0){
            start_row++;
        }
        //picking our end point
        end_col = this.size -1 ;
        end_row = random.nextInt(this.size-1);
        if(end_row == 0){
           end_row ++;
        }
        //setting our end and starting point
        grid [start_row][start_col] = PATH;
        grid [end_row][end_col] = PATH;
    }
    //method that prints the maze
    public void printMaze(){

        //nested double for loop for print each component of the maze
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.printf("%3c", this.grid[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n----------------------------------------------------------------\n");
    }
    //method that starts the maze solving process
    public void solveMaze(){
        traverseMaze(start_row, start_col);
    }
    //method that controls the movements of the maze
    public void traverseMaze(int x, int y){

        this.grid[x][y] = 'X';
        printMaze();
        pauseDisplay();

        if(x == end_row  && y == end_col ){
            System.out.println("Maze Solved!!!");
            System.exit(0);
        }

        else{

            //controlling movement to the right
            int right = y + 1;
            if(right < this.size && this.grid[x][right] != WALL && this.grid[x][right] != 'X'
                    && this.grid[x][right] != '?'){
                traverseMaze(x, right);
            }

            //controlling movement up
            int up = x - 1 ;
            if(up < this.size && this.grid[up][y] != WALL && this.grid[up][y] != 'X'
                    && this.grid[up][y] != '?'){
                traverseMaze(up, y);
            }

            //controlling movements down
            int down = x + 1;
            if(down < this.size && this.grid[down][y] != WALL && this.grid[down][y] != 'X'
                    && this.grid[down][y] != '?'){
                traverseMaze(down, y);
            }

            //controlling movements to the left
            int left = y - 1;
            if( left > 0 && grid[x][left] != WALL && this.grid[x][left] != 'X'
                    && this.grid[x][left] != '?') {
                traverseMaze(x, left);
            }

            //checking if maze is unsolvable
            if (x == start_row && y == start_col) {

                System.out.println("Maze Not Solvable!");
                System.exit(0);
            }
            //if none of the conditions are met make that path a traveled one
            this.grid[x][y] = '?';
        }

    }
    //threat method that pauses the program for a second so its visible
    private void pauseDisplay(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
