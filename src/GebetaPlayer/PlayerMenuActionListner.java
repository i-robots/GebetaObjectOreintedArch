/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GebetaPlayer;

import static GebetaPlayer.PlayerMenu.name;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerMenuActionListner extends PlayerMenu implements ActionListener{
    
    PlayerMenuActionListner(){
        super();
    }
    @Override
    public void actionPerformed(ActionEvent b) {
        if(b.getSource() == jb1){
            try {
                socket = new Socket(server.getText(),Integer.parseInt(port.getText())); 
                jb1.setEnabled(false);
                new DataOutputStream(socket.getOutputStream()).writeUTF(name.getText()); 
                P = new DataInputStream(socket.getInputStream()).read();
                lb4.setText("connection established at port "+socket.getPort()+
                        "\nand you are player "+P+".\nYou can know start the game.\n");
            } catch (IOException ex) {
               lb4.setText("error in connecting to the server!"+ex.toString());
            }
        }
        if(b.getSource() == jb2){
            try{
                Other_player =  new DataInputStream(socket.getInputStream()).readUTF();
            }catch(IOException e){}
            if(socket == null ){
                lb4.setText("You are not connected to any server!");
            }
            else{
                 this.setVisible(false);
                 new ListenFromServer(socket).start();
                 new GebetaBoard(new Hole());
            }
        }
	
    }
}
