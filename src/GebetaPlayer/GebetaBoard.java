package GebetaPlayer;

import javax.swing.JFrame;

public class GebetaBoard extends JFrame{    
    public GebetaBoard(Hole gbd){
        super("GEBETA");
        add(gbd.HoleFactory(10, 10,"5"));
        add(gbd.HoleFactory(10, 120,"4"));
        add(gbd.HoleFactory(10, 230,"3"));
        add(gbd.HoleFactory(10, 340,"2"));
        add(gbd.HoleFactory(10, 450,"1"));
        add(gbd.HoleFactory(10, 560,"0"));
        add(gbd.HoleFactory(150, 10,"6"));
        add(gbd.HoleFactory(150, 120,"7"));
        add(gbd.HoleFactory(150, 230,"8"));
        add(gbd.HoleFactory(150, 340,"9"));
        add(gbd.HoleFactory(150, 450,"10"));
        add(gbd.HoleFactory(150, 560,"11"));
        add(gbd.Button());
        setSize(372, 710);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	setResizable(false);  
	setLocationRelativeTo(null);
        add(gbd);
	setVisible(true);
        setComponentZOrder(gbd,0);
     
    }
}    



	

