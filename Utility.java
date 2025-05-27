import java.io.*;
import java.util.ListIterator;

class Statistics{
    public static int noofNodes1, noofNodes2;
    public static int ExpandedNodes1, ExpandedNodes2;

    public Statistics()
    {
        noofNodes1 = noofNodes2 = ExpandedNodes1 = ExpandedNodes2 = 0;
    }
}

//Class with common functionality used by other classes
public class Utility {
    
    int[] localBoard1, localBoard2;
    int localKalah1, localKalah2;

    //Copy values in local variables
    public void setLocals(Game x){
        this.localKalah1=x.getPlayer1_Kalah();
        this.localKalah2=x.getPlayer2_Kalah();
        localBoard1=new int[x.getBoardSize()];
        localBoard2=new int[x.getBoardSize()];
        System.arraycopy(x.getPlayer1_Board(), 0, localBoard1, 0, x.getBoardSize());
        System.arraycopy(x.getPlayer2_Board(), 0, localBoard2, 0, x.getBoardSize());
    }
    
    /* The following function comprises of the moves made
     * game argument is current state of game
     * player indicates current player who is playing
     * index indicate the hole from which stones needs to be moved
     * 
     * This function returns the new state of game after moving stones 
     * for given player from given hole index
     */
    public Game moveStones(Game game, int player, int index){
        //System.out.println("MoveStones function is called");
        Game state=new Game(game);
        state.setGetAnotherTurn(false);
        setLocals(state);
        if(player==1){
            if(localBoard1[index]>0){
                int stones=localBoard1[index];
                localBoard1[index]=0;
                int j=index+1;
                boolean reverse=false;
                while(stones>0){
                    if(j==state.getBoardSize()){
                        if(stones==1)
                            state.setGetAnotherTurn(true);
                        localKalah1++;
                        stones--;
                        reverse=true;
                        j=state.getBoardSize()-1;
                    }
                    else if(j<0){
                        j=0;
                        reverse=false;
                    }
                    else if(reverse){
                        localBoard2[j]++;
                        stones--;
                        j--;
                    }
                    else{
                        if(stones==1 && localBoard1[j]==0){
                            localKalah1+=localBoard2[j]+1;
                            localBoard2[j]=0;
                            stones--;
                        }
                        else{
                            localBoard1[j]++;
                            stones--;
                            j++;
                        }
                    }
                }
            }
        }
        else{
            if(localBoard2[index]>0){
                int stones=localBoard2[index];
                localBoard2[index]=0;
                int j=index-1;
                boolean reverse=true;
                while(stones>0){
                    if(j<0){
                        if(stones==1)
                            state.setGetAnotherTurn(true);
                        localKalah2++;
                        stones--;
                        reverse=false;
                        j=0;
                    }
                    else if(j==state.getBoardSize()){
                        j=state.getBoardSize()-1;
                        reverse=true;
                    }
                    else if(!reverse){
                        localBoard1[j]++;
                        stones--;
                        j++;
                    }
                    else{
                        if(stones==1 && localBoard2[j]==0){
                            localKalah2+=localBoard1[j]+1;
                            localBoard1[j]=0;
                            stones--;
                        }
                        else{
                            localBoard2[j]++;
                            stones--;
                            j--;
                        }
                    }
                }
            }
        }
        if(Player1_SideEmpty(localBoard1)){
            for(int i=0;i<localBoard2.length;i++){
                localKalah2+=localBoard2[i];
                localBoard2[i]=0;
            }
            state.setGetAnotherTurn(false);
        }
        if(Player2_SideEmpty(localBoard2)){
            for(int i=0;i<localBoard1.length;i++){
                localKalah1+=localBoard1[i];
                localBoard1[i]=0;
            }
            state.setGetAnotherTurn(false);
        }
                
        state.setPlayer1_Board(localBoard1);
        state.setPlayer2_Board(localBoard2);
        state.setPlayer1_Kalah(localKalah1);
        state.setPlayer2_Kalah(localKalah2);
        return  state;
    }
    
    /*
     * x is current state of game
     * 
     * The function returns if the game is over or not on current state of game
     */
    public boolean GameOver(Game x){
        //System.out.println("GameOver function is called");
        int[] temp1=new int[x.getBoardSize()];
        int[] temp2=new int[x.getBoardSize()];
        System.arraycopy(x.getPlayer1_Board(), 0, temp1, 0, x.getBoardSize());
        System.arraycopy(x.getPlayer2_Board(), 0, temp2, 0, x.getBoardSize());
        
        boolean side1Empty=true;
        boolean side2Empty=true;
        for(int i=0;i<x.getBoardSize();i++){
            if(temp1[i]>0){
                side1Empty=false;
                break;
            }
        }
        for(int i=0;i<x.getBoardSize();i++){
            if(temp2[i]>0){
                side2Empty=false;
                break;
            }
        }   
        return (side1Empty || side2Empty);
    }
    
    /*
     * player is current player
     * mancala1 is number of stones in player 1's mancala
     * mancala2 is number of stones in player 2's mancala
     * 
     * This function returns the heuristic (Eval value) for current player
     * which helps in deciding the next best move
     */
    public int eval(int player, int kalah1, int kalah2){
        if(player==1){
            return kalah1-kalah2;
        }
        else{
            return kalah2-kalah1;
        }
    }

    //Evaluates total number of stones in each pit + Kalah
    public int eval1(Game g1){
        setLocals(g1);
        int sum1=0, sum2=0;
        for(int i=0; i< localBoard1.length; i++)
            sum1+=localBoard1[i];
        sum1+=localKalah1;
        for(int i=0; i< localBoard2.length; i++)
            sum2+=localBoard2[i];    
        sum2+=localKalah2;
        if(g1.getPlayer()==1){
            return sum1-sum2;
        }
        else{
            return sum2-sum1;
        }
    }

    //Evaluates the number of empty pits on each side
    public int eval2(Game g1){
        setLocals(g1);
        int count1=0, count2=0;
        for(int i=0; i< localBoard1.length; i++)
        {
            if(localBoard1[i]==0)
            count1++;
        }
        for(int i=0; i< localBoard2.length; i++)
        {
            if(localBoard2[i]==0)
            count2++;
        }
        if(g1.getPlayer()==1){
            return count1-count2;
        }
        else{
            return count2-count1;
        }
    }
    public int eval3(Game g1){
        setLocals(g1);
        int sum1=0, sum2=0;
        for(int i=4; i< localBoard1.length; i++)
            sum1+=localBoard1[i];
        for(int i=4; i< localBoard2.length; i++)
            sum1+=localBoard1[i];
        if(g1.getPlayer()==1){
            return sum2-sum1;
        }
        else{
            return sum1-sum2;
        }
    }
    
    /*
     * player is current player
     * index is hole index for current player on the game board
     * 
     * This function returns the name for hole 
     * For below representation
     *  	  	Player 2
		   1   2   3   4   5
		 ---------------------
		A|   |   |   |   |   |
		 |	 -------------
		B|   |   |   |   |   |
		 ---------------------
				Player 1
		This is for hole represenation purpose while priting the traverse log.
		Example For player 1 second hole will be represented as B3
     */
    public String getName(int player, int index){
        String name="";
        if(player==1){
            name="B".concat(Integer.toString(index+2));
        }
        else{
            name="A".concat(Integer.toString(index+2));
        }
        return name;
    }
    
    /*
     * name is String represenation of hole
     * 
     * This function is opposite of getName, 
     * it returns the player and index of hole
     *  
     * For below representation
     *  	  	Player 2
		   1   2   3   4   5
		 ---------------------
		A|   |   |   |   |   |
		 |   -------------
		B|   |   |   |   |   |
		 ---------------------
				Player 1
		This is for hole represenation purpose while priting the traverse log.
		Example : A4 will be player 2's 3rd hole
     */
    public int[] decodeName(String name){
        int[] returnValue=new int[2];// returnValue[0] is player number and returnValue[1] is index in board
        if(name.charAt(0)=='A'){
            returnValue[0]=2;
        }
        else{
            returnValue[0]=1;
        }
        int temp=Integer.parseInt(name.substring(1));
        returnValue[1]=temp-2;
        return returnValue;
    }
    
    /*
     * n is some node which contain soem state of game
     * This function expands the current state into further valid states
     * and create node for add all the valid states and 
     * add them as children of current node 
     */
    public void expansion(Node n){
        //System.out.println("MoveStones function is called");
        if(!(GameOver(n.game))){
            int currentPlayer;
            if(n.game.getPlayer()==1)
                System.out.println("Expanding Node by Player 1 " + Statistics.ExpandedNodes1++);
            else System.out.println("Expanding Node by Player 2 " + Statistics.ExpandedNodes2++);
            if(n.max)
                currentPlayer=n.game.getPlayer();
            else{
                if(n.game.getPlayer()==1)
                    currentPlayer=2;
                else
                    currentPlayer=1;
            }

            int[] checkValidMove=new int[n.game.getBoardSize()];
            if(currentPlayer==1){
                System.arraycopy(n.game.getPlayer1_Board(), 0, checkValidMove, 0, n.game.getBoardSize());
            }
            else{
                System.arraycopy(n.game.getPlayer2_Board(), 0, checkValidMove, 0, n.game.getBoardSize());
            }
            for(int i=0;i<n.game.getBoardSize();i++){
                if(checkValidMove[i]>0){
                    Game temp=new Game(moveStones(n.game, currentPlayer, i));
                    Node xn=new Node();
                    xn.parent=n;
                    xn.game=new Game(temp);
                    xn.name=getName(currentPlayer, i);
                    char startChar=n.name.charAt(0);
                    if (xn.name.charAt(0)==startChar){
                        xn.depth=n.depth;
                    }
                    else{
                        xn.depth=n.depth+1;
                    }
                    if(temp.getGetAnotherTurn()){
                        xn.max=n.max;
                    }
                    else{
                        xn.max=!n.max;
                    }
                    if(xn.max){
                        xn.eval=Double.NEGATIVE_INFINITY;
                    }
                    else{
                        xn.eval=Double.POSITIVE_INFINITY;
                    }
                    xn.alpha=n.alpha;
                    xn.beta=n.beta;
                    n.children.add(xn);
                    if(currentPlayer==1)
                        Statistics.noofNodes1++;
                    else Statistics.noofNodes2++;
                }
            }
        }
    }
    
    /* Convert Double.MAX_VALUE and Double.MIN_VALUE 
     * as Infinity and Negative Infinity to print them
     * into traverse log.
     */
    public String evalToString(double eval){
        if(Double.isInfinite(eval)){
            return Double.toString(eval);
        }
        else{
            return Integer.toString((int)eval);
        }
    }
    
    /*
     * Get next best move from current game state which is represented in root Node. 
     */
    public Game nextState(Node root) throws IOException{
        ListIterator<Node> listIterator=root.children.listIterator();
        while(listIterator.hasNext()){
            Node temp=listIterator.next();
            if(temp.eval==root.eval){
                Game move=null;
                if(temp.game.getGetAnotherTurn()){
                    Node x=getNextState(temp);
                    move=new Game(x.game);
                }
                else{
                    move=new Game(temp.game);
                }
                writeNextState(move);
                return move;
                //break;   
            }
        }
        return null;
    }
    
    /*
     * Helper function to get next State (Best valid move) 
     */
    public Node getNextState(Node n){
        Node nextMove=null;
        ListIterator<Node> listIterator=n.children.listIterator();
        while(listIterator.hasNext()){
            Node temp=listIterator.next();
            if(temp.eval==n.eval){
                if(temp.game.getGetAnotherTurn()){
                    nextMove=getNextState(temp);
                }
                else{
                    nextMove=new Node(temp);
                    break;
                }
            }
        }
        return nextMove;
    }
    
    /*
     * Print next state of game in required format.  
    */
    public void writeNextState(Game move) throws IOException{
        System.out.println("Board after the move after " + move.getPlayer() + " played");
        FileOutputStream fileOutputStreamNextState=new FileOutputStream("next_state.txt");
        OutputStreamWriter outputStreamWriterNextState=new OutputStreamWriter(fileOutputStreamNextState);
        BufferedWriter bufferedWriterNextState=new BufferedWriter(outputStreamWriterNextState);
        
        int[] temp1=new int[move.getBoardSize()];
        int[] temp2=new int[move.getBoardSize()];
        System.arraycopy(move.getPlayer1_Board(), 0, temp1, 0, move.getBoardSize());
        System.arraycopy(move.getPlayer2_Board(), 0, temp2, 0, move.getBoardSize());
        String s1="";
        String s2="";
        System.out.println("-----------------------------------");
        System.out.print("PLAYER-1  ");
        for(int i=0;i<move.getBoardSize();i++){
            System.out.print(temp1[i] + " ");
        }
        System.out.print("   " +move.getPlayer1_Kalah());
        System.out.println();
        System.out.print("PLAYER-2  ");
        for(int i=0;i<move.getBoardSize();i++){
            System.out.print(temp2[i] + " ");
        }
        System.out.print("   " +move.getPlayer2_Kalah());
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
        for(int i=0;i<move.getBoardSize();i++){
            s1+=Integer.toString(temp1[i])+" ";
            s2+=Integer.toString(temp2[i])+" ";
        }
        bufferedWriterNextState.write(s2.trim());
        bufferedWriterNextState.newLine();
        bufferedWriterNextState.write(s1.trim());
        bufferedWriterNextState.newLine();
        
        bufferedWriterNextState.write(Integer.toString(move.getPlayer2_Kalah()).trim());
        bufferedWriterNextState.newLine();
        bufferedWriterNextState.write(Integer.toString(move.getPlayer1_Kalah()).trim());
        
        bufferedWriterNextState.close();
        outputStreamWriterNextState.close();
        fileOutputStreamNextState.close();
    }
    
    /*
     * Check if all the holes on player 1's side are empty 
     * which is one of the condition for end of game. 
    */
    public boolean Player1_SideEmpty(int[] boardPlayer1){
        for(int i=0;i<boardPlayer1.length;i++){
            if(boardPlayer1[i]>0)
                return false;
        }
        return true;
    }
    
    /*
     * Check if all the holes on player 2's side are empty 
     * which is one of the condition for end of game. 
    */
    public boolean Player2_SideEmpty(int[] boardPlayer2){
        for(int i=0;i<boardPlayer2.length;i++){
            if(boardPlayer2[i]>0)
                return false;
        }
        return true;
    }
}
