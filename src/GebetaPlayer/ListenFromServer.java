package GebetaPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

    class ListenFromServer extends Thread{
        
    static DataOutputStream toServer;
    static DataInputStream fromServer;
    
    static int[] list = {4,4,4,4,4,4,4,4,4,4,4,4};
    static int player;
    static boolean myTurn;
    static boolean countinueToPlay = true;

    int score ;
    static int Ascore;
    
    static int count;
 
    public ListenFromServer(){
        
    }  
    public ListenFromServer(Socket socket){
        try {              
                fromServer = new DataInputStream(socket.getInputStream());
                toServer = new DataOutputStream(socket.getOutputStream());
        }catch(IOException ex){
                System.out.println("error occured."+ex.toString());
         }    
    }
    @Override
    public void run() {
       player = PlayerMenu.P;
       while (countinueToPlay) {
            try {
                receiveInfoFromServer(); // Receive list from the server 
                count++;
            }catch(IOException e){
                System.out.println("error occured "+e.toString());
             }
       }            
    }
    /** Send this player's move to the server */
    void sendMove(int[] list) throws  IOException {
        String msg = Array_to_String(list);
        System.out.println("List to Server: "+msg);
        toServer.writeUTF(msg);
        count++;
    }
    private void receiveInfoFromServer() throws  IOException {
        // Receive game status from server

        String msg = fromServer.readUTF();
        System.out.println("form server :" +msg);
        try{
            int No_from_server = Integer.parseInt(msg);
            if(No_from_server == player){
                myTurn = true;
                Game.last_index = 0;
            }
            if(No_from_server > 3){
                Ascore = No_from_server;
            }
        }catch(NumberFormatException r){
            list = string_to_array(msg);
            System.out.println("list from server: "+msg);
        }        
    }
    private int[] string_to_array(String str){
        int[] lst = {0,0,0,0,0,0,0,0,0,0,0,0};
        String[] s = str.split(",");
        for(int i = 0 ;i<12;i++){
            lst[i] = Integer.parseInt(s[i]);
        }
        return lst;
    }    
    private String Array_to_String(int[] list){
           String str = "";
           for(int i : list){
               str += Integer.toString(i)+",";
           }
           return str;
    }     	 
 }
