package GebetaServer;
import GebetaServer.ServerThread;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

public class Server extends Application implements EventHandler<ActionEvent>{

    private int sessionNo = 1; // Number a session
    TextArea taLog;
    TextField port;
    TextField serverAdd;
    @Override
    public void start(Stage primaryStage) throws Exception {   
        
        BorderPane pane = new BorderPane();
        Label l1 = new Label("Enter Server address & Port No :");
        Button bt = new  Button("Start Server");
        bt.setOnAction(this);
        serverAdd = new TextField("127.0.0.1");
        port = new TextField("8080");
        port.setPrefSize(50, 20);
        HBox hb = new HBox();
        hb.getChildren().addAll(l1,serverAdd,port,bt);
        
        taLog = new TextArea("Welcome to the game server\nSet the server address & port No to start the server.\n");
        taLog.setEditable(false);
        ScrollPane sp = new ScrollPane(taLog);
        
        pane.setTop(hb);
        pane.setBottom(sp);
    
        // Create a scene and place it in the stage 
        Scene scene = new Scene(pane, 450, 200);
        primaryStage.setTitle("Gebeta Server"); // Set the stage title 
        primaryStage.setScene(scene); // Place the scene in the stage 
        primaryStage.show(); // Display the stage 

    }

    @Override
    public void handle(ActionEvent e) {
        new Thread( () -> { 
        try { 
          // Create a server socket

          ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port.getText())); 
          Platform.runLater(() -> taLog.appendText(new Date() + 
           ": Server "+serverAdd.getText()+" started at socket "+port.getText()+"\n")); 
 
            // Ready to create a session for every two players 
           while (true) { 
           Platform.runLater(() -> taLog.appendText(new Date() + 
           ": Wait for players to join session " + sessionNo + '\n')); 

             // Connect to player 1 
             Socket player1 = serverSocket.accept();                           
             String P1name = new DataInputStream(player1.getInputStream()).readUTF();
             
             Platform.runLater(() -> { 
              taLog.appendText(new Date() +" :player 1/"+P1name+" joined session " 
                 + sessionNo + '\n'); 
                taLog.appendText(P1name+"'s IP address" + 
                player1.getInetAddress().getHostAddress() + '\n'); 
              }); 
 
              // Notify that the player is Player 1                             
               new DataOutputStream( 
               player1.getOutputStream()).write(1);
  
                // Connect to player 2 
               Socket player2 = serverSocket.accept();                            
               String P2name = new DataInputStream(player2.getInputStream()).readUTF();
               
               Platform.runLater(() -> { 
                 taLog.appendText(new Date() + 
                   ": player2 /"+P2name+" joined session " + sessionNo + '\n'); 
                 taLog.appendText(P2name+"'s IP address" + 
                   player2.getInetAddress().getHostAddress() + '\n'); 
               }); 
  
               // Notify that the player is Player 2 
               new DataOutputStream(                                             
                 player2.getOutputStream()).write(2);
               
               //send names for each players.
               new DataOutputStream( 
               player1.getOutputStream()).writeUTF(P2name);
               
               new DataOutputStream( 
               player2.getOutputStream()).writeUTF(P1name);
  
               // Display this session and increment session number 
               Platform.runLater(() -> 
			taLog.appendText(new Date() + 
	                ": Start a thread for session " + sessionNo++ + '\n')); 
 
               // Launch a new thread for this session of two players a session for two players 
               new Thread(new ServerThread(player1, player2)).start(); 
           } 
         } 
         catch(IOException ex) {
             System.out.println("error occured."+ex.toString());
         } 
        }).start(); 
        
    }   
    
    public static void  main(String[] args) { 
        Application.launch(args); 
    }
}
   
	

   