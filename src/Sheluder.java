
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
        pomocniczaProp = new int[prezent.size()][4];
        pomocniczaEqual = new int[prezent.size()][4]; // tablice 1sza linia zawiera numer procesu, 2ga liczbę zapewnianych ramek
        pomocniczaControl = new int[prezent.size()][4]; // 3cia liczbę procesów w ramie, 4-ta liczbę błędów 
        pomocniczaStrafe = new int[prezent.size()][4];
        for(int i = 0; i < prezent.size(); i++){
            pomocniczaProp[i][0] = i;
            pomocniczaEqual[i][0] = i;
            pomocniczaControl[i][0] = i;
            pomocniczaStrafe[i][0] = i;
            
        }
    }
    public void setMinSizeEqual(){
        for(int i = 0; i < pomocniczaEqual.length; i++){
            pomocniczaEqual[i][1] = (int)(ramEqual.getSize()/110);
        }
    }
    public void setMinSizeProp(){
         for(int i = 0; i < pomocniczaProp.length-1; i++){
            pomocniczaProp[i][1] = listaProp.get(i).getSize()/3;
        }
    }
    public void setMinSizeStrafe(){
        for(int i = 0; i < pomocniczaStrafe.length-1; i++){
            pomocniczaStrafe[i][1] = listaStrafe.get(i).getWSetSize();
        }
    }
   public void setMinSizeControl(){
        for(int i = 0; i < pomocniczaControl.length-1; i++){
            if(pomocniczaControl[i][1] == 0){
                pomocniczaControl[i][1] = listaControl.get(i).getWSetSize();
            }else
                if(pomocniczaControl[i][3] <=3){
                    pomocniczaControl[i][1]--;
                }
                if(pomocniczaControl[i][3] >=7){
                    pomocniczaControl[i][1]++;
                }
        }
    }
    public void execution(int interval){     //
        Page page;
        setMinSizeControl();
        setMinSizeEqual();
        setMinSizeProp();
        setMinSizeStrafe();
        int licznik=0;
        boolean isDone = false;
        while(!isDone){
            isDone = true;
            for(int i = 0; i < listaProp.size(); i++){
                if(!listaProp.get(i).isDone()){
                    page = listaProp.get(i).getPage();
                if(!ramProp.contains(page) && ramProp.add(page)){
                    pomocniczaProp[i][2]++;
                }else{
                    LRU.errorHandle(ramProp, page, pomocniczaProp);
                    pomocniczaProp[i][2]++;
                }
                if(isDone == true && !listaProp.get(i).isDone() ){
                        isDone = false;
                       }
                }
            }
        }
        while(!isDone){
            isDone = true;
            for(int i = 0; i < listaEqual.size(); i++){
                if(!listaEqual.get(i).isDone()){
                    page = listaEqual.get(i).getPage();
                    if(!ramEqual.contains(page) && ramEqual.add(page)){
                        pomocniczaEqual[i][2]++;
                    }else{
                        LRU.errorHandle(ramEqual, page, pomocniczaEqual);
                        pomocniczaEqual[i][2]++;
                    }
                    if(isDone == true && !listaEqual.get(i).isDone() ){
                        isDone = false;
                    }
                }
            }
        }
        while(!isDone){
            isDone = true;
            licznik++;
            if(licznik%5 == 0){
                setMinSizeControl();
            }        
            for(int i = 0; i < listaControl.size(); i++){
                if(!listaControl.get(i).isDone()){
                    page = listaControl.get(i).getPage();
                    if(!ramControl.contains(page) && ramControl.add(page)){
                        pomocniczaControl[i][2]++;
                    }else{
                        LRU.errorHandle(ramControl, page, pomocniczaControl);
                        pomocniczaControl[i][2]++;
                    }
                    if(isDone == true && !listaControl.get(i).isDone() ){
                        isDone = false;
                    }
                }
            }
        }
        licznik = 0;
        while(!isDone){
            licznik++;
            if(licznik % interval == 0){
                setMinSizeStrafe();
            }
            isDone = true;
            for(int i = 0; i < listaStrafe.size(); i++){
                if(!listaStrafe.get(i).isDone()){
                    page = listaStrafe.get(i).getPage();
                    if(!ramStrafe.contains(page) && ramStrafe.add(page)){
                        pomocniczaStrafe[i][2]++;
                    }else{
                        LRU.errorHandle(ramStrafe, page, pomocniczaStrafe);
                        pomocniczaStrafe[i][2]++;
                    }
                    if(isDone == true && !listaStrafe.get(i).isDone() ){
                        isDone = false;
                    }
                }
            }
        }
        //czy zostały jeszcze jakiekolwiek procesy na którejkolwiek liście
    }
    public void printErrors(){
        int suma = 0;
        for(int i = 0; i < pomocniczaProp.length; i++){
            suma += pomocniczaProp[i][3];
        }
        System.out.println("Ilość błędów dla przydziału proporcjonalnego: "  +   suma);
        suma = 0;
        for(int i = 0; i < pomocniczaControl.length; i++){
            suma += pomocniczaControl[i][3];
        }
        System.out.println("Ilość błędów dla sterowania częstością błędów: "  +   suma);
        suma = 0;
        for(int i = 0; i < pomocniczaEqual.length; i++){
            suma += pomocniczaEqual[i][3];
        }
        System.out.println("Ilość błędów dla równego przydziału: "  +   suma);
        suma = 0;
        for(int i = 0; i < pomocniczaStrafe.length; i++){
            suma += pomocniczaStrafe[i][3];
        }
        System.out.println("Ilość błędów dla strefowego: "  +   suma);
    }
    

}
