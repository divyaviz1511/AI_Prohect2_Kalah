import java.io.*;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class Kalah {
    public static void main(String[] args) throws IOException{
        FileInputStream fileInputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        
        try{
            fileInputStream=new FileInputStream(args[0]);
            inputStreamReader=new InputStreamReader(fileInputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            Scanner input =new Scanner(System.in);
            
            //Read input file
            //int task=Integer.parseInt(bufferedReader.readLine().trim());
            int player=Integer.parseInt(bufferedReader.readLine().trim());
            int cuttingDepth=Integer.parseInt(bufferedReader.readLine().trim());
            
            String[] boardPlayer2=bufferedReader.readLine().trim().split(" ");
            String[] boardPlayer1=bufferedReader.readLine().trim().split(" ");
            
            int boardSize=boardPlayer2.length;
            int[] board2=new int[boardSize];
            int[] board1=new int[boardSize];
            
            for(int i=0;i<boardSize;i++){
                board2[i]=Integer.parseInt(boardPlayer2[i].trim());
                board1[i]=Integer.parseInt(boardPlayer1[i].trim());
            }
            
            int kalah2=Integer.parseInt(bufferedReader.readLine().trim());
            int kalah1=Integer.parseInt(bufferedReader.readLine().trim());
            
            //Game g=new Game(player, board2, board1, kalah2, kalah1);
            Utility util=new Utility();
            Game next=null;
            Minimax mm;
            //Call Minimax or AlphaBeta method based on value specified in input
            //while(!util.GameOver(g)){  
            System.out.println("-----------------------------------------");
            System.out.println(" Welcome to KALAH Game ");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("System supports the following");
            System.out.println("1. Minimax Vs Alpha-Beta (using same evaluation funtion)");
            System.out.println("2. Minimax Vs Minimax each using a different evaluation function");
            System.out.println("3. Alpha-Beta Vs Alpha-Beta each using a different evaluation function\n");
            
            
            System.out.println("1. Minimax");
            System.out.println("2. AlphaBetaPruning");
            System.out.println("Choose your 1st player : ");
            int choice1 = Integer.parseInt(input.nextLine().trim());
            System.out.println("Choose your 2nd player :");
            int choice2 = Integer.parseInt(input.nextLine().trim());
            int choice3=0, task=0;
            if(choice1==1 && choice2==1)
                {
                    System.out.println("MinMax Vs MinMax");
                    task=1; choice3=1;
                }
            else if(choice1==2 && choice2==2)
                {
                    System.out.println("Alpha-Beta Vs Alpha-Beta");
                    task=2;choice3=1;
                }
            else if(choice1==1 && choice2==2)
                {   System.out.println("1. Eval1");
                    System.out.println("2. Eval2");
                    System.out.println("Choose Player1's Eval : ");
                    choice3 = Integer.parseInt(input.nextLine().trim());
                    if(choice3==1)
                        { task=3;
                            System.out.println("MinMax Vs Alpha-Beta with EVAL1");
                        }
                    else {
                        task=4;
                        System.out.println("MinMax Vs Alpha-Beta with EVAL2"); }
                }
            else if(choice1==1 && choice2==2)
                System.out.println("MinMax Vs Alpha-Beta");

            do{
                Game g=new Game(player, board2, board1, kalah2, kalah1);
                switch(task){
                    case 1:
                        mm=new Minimax(cuttingDepth);
                        next=mm.minimax(g,choice3);
                        task=1;
                        player=next.getPlayer();
                        if(player==1)
                        {
                            player=2;choice3=2;
                        }
                        else 
                        {
                            player=1;choice3=1;
                        }
                        board1=next.getPlayer1_Board();
                        board2=next.getPlayer2_Board();
                        kalah1=next.getPlayer1_Kalah();
                        kalah2=next.getPlayer2_Kalah();
                        //mm.writerNextState();
                        //mm.writeTraverseLog();
                        break;
                    case 2:
                        System.out.println("Calling AlphaBeta");
                        //AlphaBeta ab=new AlphaBeta(cuttingDepth);
                        //ab.alphabeta(g,choice3);

			//if(player==1)
                        //{   
                          //  player=2;choice3=2;
                        /*}
                        else
                        {
                            player=1;choice3=1;
                        }
			board1=next.getPlayer1_Board();
                        board2=next.getPlayer2_Board();
                        kalah1=next.getPlayer1_Kalah();
                        kalah2=next.getPlayer2_Kalah();*/
                        break;
                    case 3:

                    case 4:
                    default: System.out.println("Incorrect Input)");
                }
            }while(!util.GameOver(next));
            
            if(next.getPlayer2_Kalah()>next.getPlayer1_Kalah())
                System.out.println("PLAYER - 2 WON THE GAME");
            else if(next.getPlayer2_Kalah()< next.getPlayer1_Kalah())
                System.out.println("PLAYER - 1 WON THE GAME");
            else 
                System.out.println("Its a Tie");

            System.out.println("\nStatistics");
            System.out.println("-------------------------------------------------");
            System.out.println("PLAYER-1");
            System.out.println("No of Nodes Expanded by Player 1 is "+ Statistics.ExpandedNodes1);
            System.out.println("No of Nodes Generated by Player 1 is "+ Statistics.noofNodes1);
            System.out.println("Memory Used : " + Statistics.noofNodes1*4);
            System.out.println("-------------------------------------------------");
            System.out.println("PLAYER-2");
            System.out.println("No of Nodes Expanded by Player 2 is "+ Statistics.ExpandedNodes2);
            System.out.println("No of Nodes Generated by Player 2 is "+ Statistics.noofNodes2);
            System.out.println("Memory Used : " + Statistics.noofNodes2*4);
        }
        catch(Exception ex){
            System.out.println("Exception occured : " + ex);
            ex.printStackTrace();
        }
        finally{      
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
    }
}
