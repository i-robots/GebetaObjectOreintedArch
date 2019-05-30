package GebetaPlayer;

public class Game extends ListenFromServer{

    int[] stop = {0,0};
    static int last_index;
    void game(int I){
        last_index = I + list[I] + 1;
        list[I] = 0;
        
        for(int i = I+1; i < last_index; i++){
            list[make_circular_array(i)]++;
        }
        
        for(int i = 0;i < 12; i++){
            System.out.print(list[i]+",");
        }
        System.out.println("");
        
    }
    int make_circular_array(int i){
        if(i > 11){
            i = i - 12;
            return make_circular_array(i);
        }else{
            return i;
         }       
    }  
    int get_last_index(){
        if(last_index > 0){
            return make_circular_array(last_index - 1);
        }else{
            return 0;
        }
    }
    void check_if_4(){
        int sum1 = 0;
        int sum2 = 0;
        for(int i = 0;i < 12; i++){
            if(list[i] == 4){
                list[i] = 0;
                score += 4;
                System.out.println("4 is found at: "+i);
                if(i == get_last_index()){
                    myTurn = false;
               }
            } 
            if(i < 6){
                sum1 += list[i];
            }else{sum2 += list[i];}
        }
        if(sum1==0|sum2==0){
            GebetaDisplay.won = true;
        } 
        System.out.println("s1  "+sum1+" s2  "+sum2);
    }
}
