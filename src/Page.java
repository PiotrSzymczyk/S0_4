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
    private Proces container;
    int num;
    public Page(int n,Proces p){
        modB = 0;
        container = p;
        num = n;
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
