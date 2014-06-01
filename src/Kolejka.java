
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piotrek
 */

public abstract class Kolejka {
    private int liczbaBledow;
    public Kolejka(){
        liczbaBledow = 0;
    }
    public void errorHandle(Page page){}
    public void increaseError(){
        liczbaBledow++;
    }
    public int getTotal(){
        return liczbaBledow;
    }
    
}
