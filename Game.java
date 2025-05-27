//Represent Game state
public class Game {
    private int player;
    private int[] board1,board2;
    private int kalah1, kalah2;
    private int boardSize;
    private boolean getAnotherTurn;
    
    //Default Constructor
    public Game(){
        
    }
    
    //Constructor for board creation
    public Game(int player, int[] board2, int[] board1, int kalah2, int kalah1){
        this.player=player;
        this.boardSize=board2.length;
        this.board2=new int[boardSize];
        this.board1=new int[boardSize];
        for(int i=0;i<boardSize;i++){
            this.board2[i]=board2[i];
            this.board1[i]=board1[i];
        }
        this.kalah2=kalah2;
        this.kalah1=kalah1;
    }
    
    //Copy Constructor
    public Game(Game x){
        player=x.getPlayer();
        boardSize=x.getBoardSize();
        this.board2=new int[boardSize];
        this.board1=new int[boardSize];
        //Copies source array from position 0 to destination array from position 0
        System.arraycopy(x.getPlayer2_Board(), 0, this.board2, 0, boardSize);
        System.arraycopy(x.getPlayer1_Board(), 0, this.board1, 0, boardSize);
        kalah2=x.getPlayer2_Kalah();
        kalah1=x.getPlayer1_Kalah();
        getAnotherTurn=x.getGetAnotherTurn();
    }  
    
    //Returns the board size
    public int getBoardSize(){
        return boardSize;
    }
    //Sets the boardSize
    public void setBoardSize(int boardSize){
        this.boardSize=boardSize;
    }
    //Returns the current player
    public int getPlayer(){
        return player;
    }
    //Sets the next player 
    public void setPlayer(int player){
        this.player=player;
    }
    //Returns the player1's board
    public int[] getPlayer1_Board(){
        return board1;
    }
    //Copies the current board of Player1 to this.board1
    public void setPlayer1_Board(int[] board1){
        System.arraycopy(board1, 0, this.board1, 0, boardSize);
    }
    //Gets Player2's board
    public int[] getPlayer2_Board(){
        return board2;
    }
    //Copies the current board of Player2 to this.board2
    public void setPlayer2_Board(int[] board2){
        System.arraycopy(board2, 0, this.board2, 0, boardSize);
    }
    //Gets Player1's Kalah total
    public int getPlayer1_Kalah(){
        return kalah1;
    }
    //Sets the current kalah to Player1's Kalah
    public void setPlayer1_Kalah(int kalah1){
        this.kalah1=kalah1;
    }    
    //Gets Player2's Kalah total
    public int getPlayer2_Kalah(){
        return kalah2; 
    }
    //Sets current kalah total to player2's kalah
    public void setPlayer2_Kalah(int kalah2){
        this.kalah2=kalah2;
    }
    
    public boolean getGetAnotherTurn(){
        return getAnotherTurn;
    }
    
    public void setGetAnotherTurn(boolean getAnotherTurn){
        this.getAnotherTurn=getAnotherTurn;
    }
}