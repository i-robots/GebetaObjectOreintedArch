package GebetaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread extends Database implements Runnable{
        
       private final Socket player1; 
       private final Socket player2;
        
       private DataInputStream fromPlayer1; 
       private DataOutputStream toPlayer1; 
       private DataInputStream fromPlayer2; 
       private DataOutputStream toPlayer2;
    
       int playerTurn = 1;
       
       int player1_Score;
       int player2_Score;

       ServerThread(Socket player1,Socket player2) {
             super();
             this.player1 = player1; 
             this.player2 = player2;  
       }
        @Override
        /** Implement the run() method for the thread */ 
       public void run() { 
         try { 
           // Create data input and output streams 
           fromPlayer1 = new DataInputStream(player1.getInputStream()); 
           toPlayer1 = new DataOutputStream(player1.getOutputStream()); 
           fromPlayer2 = new DataInputStream(player2.getInputStream()); 
           toPlayer2 = new DataOutputStream(player2.getOutputStream()); 
 
            // Continuously serve the players and determine and status
           System.out.println("notifying player 1 to start.");
           sendMove(toPlayer1,"1");
           while (true) {
               if(playerTurn==1){
                   System.out.println("**********serving p1**********");
                   ServePlayer1();
               } else{
                   System.out.println("**********serving p2**********"); 
                   ServePlayer2();  
               }
             System.out.println("p1score: "+player1_Score+" p2score: "+player2_Score);
           }         
         }
         catch(IOException | SQLException e){System.out.println(e.toString());} 
       }

    private void sendMove(DataOutputStream out, String lst) throws IOException { 
         out.writeUTF(lst); // Send row index 
    }
    private void ServePlayer1() throws IOException, SQLException {
         // Receive status from player 1 
        String msg = fromPlayer1.readUTF();
        if(msg.compareTo("0,0,")!=0){       
            System.out.println("list from player 1 to p2: "+msg);
            //send updates to the database
            UpdateGameStatus(con,0,0,msg,playerTurn);
            // Send player 1's list to player 2
            sendMove(toPlayer2,msg); 
        }else{
            player1_Score = fromPlayer1.read();
            sendMove(toPlayer2, "2");
            toPlayer2.writeUTF(Integer.toString(player1_Score));
            playerTurn = 2;
        }
    }

    private void ServePlayer2() throws IOException, SQLException {
         // Receive status from player 2 
        String msg = fromPlayer2.readUTF();
        if(msg.compareTo("0,0,")!=0){
            System.out.println("list from player 2 to p1: "+msg); 
            //send updates to the database
            UpdateGameStatus(con,0,0,msg,playerTurn);
            // Send player 2's list to player 1 
            sendMove(toPlayer1, msg); 
        }else{
            player2_Score = fromPlayer2.read();
            sendMove(toPlayer1, "1");
            toPlayer1.writeUTF(Integer.toString(player2_Score));
            playerTurn = 1;
            
        }
    }
    
}
