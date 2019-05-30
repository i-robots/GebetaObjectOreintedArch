package GebetaPlayer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Hole extends GebetaDisplay implements MouseListener,ActionListener{
int cnt;
public Hole(){
     super();    
}

public JPanel HoleFactory(int x,int y,String name){
     JPanel o = new JPanel();
     o.setBounds(x, y, 100, 100);
     o.setName(name);
     o.addMouseListener(this);
     return o;
}
public JButton Button(){
    JButton b = new JButton("||");
    b.setBounds(331, 9, 25, 25);
    b.setBackground(Color.red);
    b.addActionListener(this);
    return b;
}
boolean CheckMove(int click){
    if(game.get_last_index()==0){
        if(ListenFromServer.player == 1){
            return click < 6;
        }
        else if(ListenFromServer.player == 2){
            return click > 5;
        }
    }
    return false;   
}   
 void check_move_validity(int click) throws IOException{ 
      Is_valid_move = (game.get_last_index() == click | CheckMove(click))&& Game.myTurn && ListenFromServer.list[click] != 0 ;
      if(Is_valid_move){
          game.game(click);
          if(ListenFromServer.count > 3){
              game.check_if_4(); 
              if(!Game.myTurn){
                   game.sendMove(ListenFromServer.list);
                   game.sendMove(game.stop) ;                  
                   ListenFromServer.toServer.write(game.score);
                   Game.last_index = 0;
                   return; 
               }           
         }
          new ListenFromServer().sendMove(ListenFromServer.list);
          if(ListenFromServer.list[game.get_last_index()] == 1){
                ListenFromServer.myTurn = false;
                Game.last_index = 0;
                new ListenFromServer().sendMove(game.stop);
                ListenFromServer.toServer.write(game.score);
          }
   }
        System.out.println("click: "+click+" last_index: "+game.get_last_index()+" turn: "+Game.myTurn+" score: "+game.score+" count: "+game.count);
        repaint();
      }  
        @Override
        public void mouseClicked(MouseEvent e) { 
            try { 
                check_move_validity(Integer.parseInt(e.getComponent().getName()));
            } catch (IOException ex){
                System.out.println("error"+ex.toString());
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
           // System.out.println("Pressed");
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("released");
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
           // System.out.println("entered");
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println("exit");
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(cnt%2==0){
            System.out.println("game paused");
            ListenFromServer.countinueToPlay = false;
            ListenFromServer.myTurn = false;
            cnt++;
        }else{
            System.out.println("game continued.");
             ListenFromServer.countinueToPlay = true;
             ListenFromServer.myTurn = true;
             cnt++;
        }
    }   
      
 }

