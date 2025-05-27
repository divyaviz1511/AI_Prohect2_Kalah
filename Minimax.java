import java.io.*;
import java.util.ListIterator;

import javax.lang.model.util.ElementScanner6;

/* Get next best move based on Minimax technique
 * And traverse log shows the exploration of game 
 * tree in order to select best next move.
 */
public class Minimax {
    Utility util=new Utility();
    private final int depth;
    private static int count=0;
    
    public Minimax(int depth){
        this.depth=depth;
    }
    
    //Game tree exploration
    public Game minimax(Game g, int choice){
        Game nextstate=null;
        try{
            FileOutputStream fileOutputStreamTraverseLog=new FileOutputStream("traverse_log.txt");
            OutputStreamWriter outputStreamWriterTraverseLog=new OutputStreamWriter(fileOutputStreamTraverseLog);
            BufferedWriter bufferedWriterTraverseLog=new BufferedWriter(outputStreamWriterTraverseLog);

            bufferedWriterTraverseLog.write("Node,Depth,Value");
            bufferedWriterTraverseLog.newLine();
            
            Node root=new Node("root", g, Double.NEGATIVE_INFINITY, 0, true, null);
            
            getAllMoves(root, bufferedWriterTraverseLog,choice);
            //setValuesAndTraverse(root, bufferedWriterTraverseLog);
            nextstate = util.nextState(root);
            
            bufferedWriterTraverseLog.close();
            outputStreamWriterTraverseLog.close();
            fileOutputStreamTraverseLog.close();  
        }
        catch(Exception ex){
            System.out.println("Exception in Minimax : "+ex);
            ex.printStackTrace();
        }
        return nextstate;
    }
    
    /* Get all the valid moves based on current state
     * and select next best move
    */
    public void getAllMoves(Node n, BufferedWriter bw, int choice) throws IOException{
        count=n.depth;
        if(count>=depth && !n.game.getGetAnotherTurn()){
            if(choice==1)
                n.eval=util.eval(n.game.getPlayer(), n.game.getPlayer1_Kalah(), n.game.getPlayer2_Kalah());
            else if(choice==2)
                n.eval=util.eval3(n.game);
            else    
                n.eval=util.eval(n.game.getPlayer(), n.game.getPlayer1_Kalah(), n.game.getPlayer2_Kalah());
            //System.out.println("The evaluated value is: " + n.eval);
            bw.write(n.name+","+n.depth+","+util.evalToString(n.eval));
            bw.newLine();            
            return;
        }
        boolean valid=false;
        if(count==depth && n.game.getGetAnotherTurn())
            valid=true;
        while((count<depth || valid) && !util.GameOver(n.game)){
            bw.write(n.name+","+n.depth+","+util.evalToString(n.eval));
            bw.newLine();
            //System.out.println("Expand " + n.name);
            util.expansion(n);
            valid=false;
            ListIterator<Node> listIterator=n.children.listIterator();
            while(listIterator.hasNext()){
                Node temp=listIterator.next();
                getAllMoves(temp,bw,choice);
                if(n.max){  
                    if(n.eval < temp.eval)
                        n.eval=temp.eval;
                }
                else{
                    if(n.eval > temp.eval)
                        n.eval=temp.eval;
                }
                bw.write(n.name+","+n.depth+","+util.evalToString(n.eval));
                bw.newLine();
            }
            count++;
        }
    }    
}
