/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piotrek
 */
public class Page {
    private int modB;
    private int num;
    private Proces container;
    
    public Page(int n, Proces p){
        num = n;
        modB = 0;
        container = p;
    }
    public void setModB(int b){
        modB = b;
    }
    public int getModB(){
        return modB;
    }
    public Proces getProces(){
        return container;
    }
}
