
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mateusz
 */
public class Sheluder {
    RAM ramProp;
    RAM ramEqual;
    RAM ramControl;
    RAM ramStrafe;
    int errorNumberProp;
    int errorNumberEqual;
    int errorNumberControl;
    int errorNumberStrafe;
    int [][]pomocniczaProp;
    int [][]pomocniczaEqual;
    int [][]pomocniczaControl;
    int [][]pomocniczaStrafe;
    LinkedList<Proces> listaProp;
    LinkedList<Proces> listaEqual;
    LinkedList<Proces> listaControl;
    LinkedList<Proces> listaStrafe;
    LinkedList<Proces> lista;
    public Sheluder(int rozmiarRAMu,LinkedList<Proces> prezent){
        lista = prezent;
        errorNumberProp = 0;
        errorNumberEqual = 0;
        errorNumberControl = 0;
        errorNumberStrafe = 0;
        ramProp = new RAM(rozmiarRAMu);
        ramEqual = new RAM(rozmiarRAMu);
        ramControl = new RAM(rozmiarRAMu);
        ramStrafe = new RAM(rozmiarRAMu);
        listaProp = (LinkedList<Proces>) prezent.clone();
        listaEqual = (LinkedList<Proces>) prezent.clone();
        listaControl = (LinkedList<Proces>) prezent.clone();
        listaStrafe = (LinkedList<Proces>) prezent.clone();
        pomocniczaProp = new int[prezent.size()][3];
        pomocniczaEqual = new int[prezent.size()][3];
        pomocniczaControl = new int[prezent.size()][3];
        pomocniczaStrafe = new int[prezent.size()][3];
        for(int i = 0; i < prezent.size(); i++){
            pomocniczaProp[i][0] = i;
            pomocniczaEqual[i][0] = i;
            pomocniczaControl[i][0] = i;
            pomocniczaStrafe[i][0] = i;
            
        }
    }
    public void setMinSizeEqual(int min){
        for(int i = 0; i < pomocniczaEqual.length; i++){
            pomocniczaEqual[i][1] = min;
        }
    }
    public void setMinSizeProp(){
         for(int i = 0; i < pomocniczaProp.length-1; i++){
            pomocniczaEqual[i][1] = listaProp.get(i).getSize()/3;
        }
    }
    public void execution(){     //
        while(!listaProp.isEmpty()){
            
        }
        //czy zostały jeszcze jakiekolwiek procesy na którejkolwiek liście
    }
    public void printErrors(){
        System.out.println("Ilość błędów dla LRU: "  +   kolejkaLRU.getTotal());
        System.out.println("Ilość błędów dla ALRU: "  +   kolejkaALRU.getTotal());
        System.out.println("Ilość błędów dla FIFO: "  +   kolejkaFIFO.getTotal());
        System.out.println("Ilość błędów dla OPT: "  +   kolejkaOPT.getTotal());
        System.out.println("Ilość błędów dla losowego: "  +   kolejkaRand.getTotal());
        }
    

}
