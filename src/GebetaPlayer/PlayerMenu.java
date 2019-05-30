
package GebetaPlayer;

import java.awt.Font;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlayerMenu extends JFrame{
    
    Socket socket;
    static JButton jb1,jb2;
    static JTextField name;
    JTextField server;
    JTextField port;
    JLabel lb1,lb2,lb3;
    JTextArea lb4;
    JPanel p;
    static int P;
    static String Other_player;
    
    public PlayerMenu(){
                p = new JPanel();
                Font font = new Font(Font.DIALOG, Font.BOLD, 20);
		lb1 = new JLabel("Welcome to Gebeta Game.");
                lb1.setFont(font);
		lb2 = new JLabel("Please Enter Your Name:");
		lb3 = new JLabel("Enter Server & Port No:");
                
                lb4 = new JTextArea("");
                
		name = new JTextField("No-name");
                server = new JTextField("localhost");
                port = new JTextField("8080");
                
		jb1 = new JButton("connect");
		jb2 = new JButton("Start Game");
		
		lb1.setBounds(40,0,270,40);
		lb2.setBounds(10,50,150,40);
		lb3.setBounds(10,100,150,40);
                lb4.setBounds(10,200,300,50);
                
		name.setBounds(160,55,200,30);
                server.setBounds(160,105,100,30);
                port.setBounds(280,105,80,30);

		jb1.setBounds(280,150,80,30);
		jb2.setBounds(130,300,100,30);
                		          
                p.add(lb4);
                p.add(lb3);
                p.add(lb2);
		p.add(lb1);
                p.add(name);
                p.add(server);
                p.add(port);
                p.add(jb2);
                p.add(jb1);
                p.setLayout(null);
                add(p);
                
                setTitle("GEBETA Player Menu");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                setLocationRelativeTo(null);
                setSize(400,400);
                setVisible(true);
                setResizable(false);
    }  
    public static void main(String[] args) {
       PlayerMenuActionListner x = new PlayerMenuActionListner();
       jb1.addActionListener(x);
       jb2.addActionListener(x);       
    }
}
