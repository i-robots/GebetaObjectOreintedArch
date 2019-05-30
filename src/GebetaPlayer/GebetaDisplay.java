package GebetaPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GebetaDisplay extends JPanel{
  BufferedImage GebetaImg;
  Game game = new Game();
  static boolean won;
  boolean Is_valid_move = true;
  
 
  @Override
  public void paintComponent(Graphics g) {
      init(g);
      show_player_turn(g);
      Display_next_move(g);
      Display_Balls(g);
      display_score(g);
      check_won(g);
      Show_invalid_moves(g);
  }
  
  
  void init(Graphics g){
      try {
	     File BgImage = new File("lib/bg.png");
	     GebetaImg = ImageIO.read(BgImage);
              
      } catch (IOException e) {}
      if (GebetaImg != null) { 
             g.drawImage(GebetaImg, 0, 0, null); 
      }
  }
  
  void Display_Balls(Graphics g){
        int disX = 27;
        int disY = 587;
        int count = 0;
        for(int i:Game.list){
            if(count > 5){
                if(count == 6){disY = 32;disX = 172;}
                Ball(i,g,disX,disY);
                disY += 111;
            }else{
                Ball(i,g,disX,disY);
                disY -= 111;
            }
            count++;
        }
  }
  
  void Ball(int times,Graphics g,int disX,int disY){
      int x = disX;
      g.setColor(Color.GRAY);
      for(int j = 0; j < times; j++){
            if(j==5 | j == 12 ){
                disY += 15;
                disX = x - 10;
            }
            if(j ==25| j == 19){disY += 15;disX = x;}
            g.fillOval(disX, disY, 9, 12);
            disX += 11;
        }
  }
  
  void show_player_turn(Graphics g){
      g.setColor(Color.DARK_GRAY);
      Font font = new Font(Font.MONOSPACED, Font.BOLD, 19);
      g.setFont(font);
      if(ListenFromServer.myTurn){
              g.drawString(PlayerMenu.name.getText()+"'s Turn", 70, 15);
      }else{
              g.drawString("Wait for "+PlayerMenu.Other_player, 55, 15);
      }
      new Thread(() ->{
        try {
              Thread.sleep(3000);
              repaint();
        } catch (InterruptedException e) {e.printStackTrace();}}).start();
  }
 
  void display_score(Graphics g){
      int disX = 285;
      int disY = 120;
      for(int j = 0; j < game.score; j++){
             if(j%4==0 && j>0){disX = 285; disY += 15;}
             g.fillOval(disX, disY, 9, 12);
             disX += 11;
         }
        disX = 285;
        disY = 460;
        for(int j = 0; j < Game.Ascore; j++){
           if(j%4==0 && j>0){disX = 285; disY += 15;}
           g.fillOval(disX, disY, 9, 12);
           disX += 11;
        }
      g.setColor(Color.white);
      g.drawString(PlayerMenu.name.getText(), 250, 80);
      g.drawString("SCORE", 270, 98);
      g.drawString(PlayerMenu.Other_player, 250, 420);
      g.drawString("SCORE ", 270, 438);
  }
  
   void check_won(Graphics g){
       if(won){
            g.setColor(Color.GREEN);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 40);
            g.setFont(font);
            ListenFromServer.countinueToPlay = false;
            Game.myTurn = false;
            if(game.score > Game.Ascore){
                g.drawString("YOU WON!", 70, 300);
            }else{g.drawString("YOU LOSE!", 70, 300);}
       }        
   }
   
    void Display_next_move(Graphics g){
          g.setColor(Color.lightGray);
          int x = game.make_circular_array(Game.last_index-1);
          if(x == 0){g.fillOval(15,568,81, 80);}
          if(x == 1){g.fillOval(15,463,81, 80);}
          if(x == 2){g.fillOval(15,354,81, 80);}
          if(x == 3){g.fillOval(14,244,81, 80);}
          if(x == 4){g.fillOval(14,132,81, 80);}
          if(x == 5){g.fillOval(14,20,81, 80);}
          if(x == 11){g.fillOval(158,570,81, 80);}
          if(x == 10){g.fillOval(158,462,81, 80);}
          if(x == 9){g.fillOval(158,353,81, 80);}
          if(x == 8){g.fillOval(157,243,81, 80);}
          if(x == 7){g.fillOval(157,134,81, 80);}
          if(x == 6){g.fillOval(157,22,81, 80);}
    }
    
    void Show_invalid_moves(Graphics g){
           if(!Is_valid_move){
           g.setColor(Color.red);
           Font font = new Font(Font.MONOSPACED, Font.BOLD, 19);
           g.setFont(font);
           g.drawString("INVALID", 257, 15);
           g.drawString("MOVE!", 270, 30);
          }
    }
 
     
}

