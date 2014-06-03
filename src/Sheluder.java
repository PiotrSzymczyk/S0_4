
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
    RAM ramSphere;
    int [][]pomocniczaProp;
    int [][]pomocniczaEqual;
    int [][]pomocniczaControl;
    int [][]pomocniczaSphere;
    LinkedList<Proces> listaProp;
    LinkedList<Proces> listaEqual;
    LinkedList<Proces> listaControl;
    LinkedList<Proces> listaSphere;
    public Sheluder(int rozmiarRAMu,LinkedList<Proces> prezent){
        
        ramProp = new RAM(rozmiarRAMu);
        ramEqual = new RAM(rozmiarRAMu);
        ramControl = new RAM(rozmiarRAMu);
        ramSphere = new RAM(rozmiarRAMu);
        listaProp = new LinkedList<Proces>();
        listaEqual = new LinkedList<Proces>();
        listaControl = new LinkedList<Proces>();
        listaSphere = new LinkedList<Proces>();
        pomocniczaProp = new int[prezent.size()][4];
        pomocniczaEqual = new int[prezent.size()][4]; // tablice 1sza linia zawiera numer procesu, 2ga liczbę zapewnianych ramek
        pomocniczaControl = new int[prezent.size()][4]; // 3cia liczbę stron w ramie, 4-ta liczbę błędów 
        pomocniczaSphere = new int[prezent.size()][4];
        for(int i = 0; i < prezent.size(); i++){
            listaProp.add(prezent.get(i).clone());
            listaEqual.add(prezent.get(i).clone());
            listaControl.add(prezent.get(i).clone());
            listaSphere.add(prezent.get(i).clone());
        }
        for(int i = 0; i < prezent.size(); i++){
            pomocniczaProp[i][0] = i;
            pomocniczaEqual[i][0] = i;
            pomocniczaControl[i][0] = i;
            pomocniczaSphere[i][0] = i;
            pomocniczaProp[i][3] = 0;
            pomocniczaEqual[i][3] = 0;
            pomocniczaControl[i][3] = 0;
            pomocniczaSphere[i][3] = 0;
            pomocniczaProp[i][2] = 0;
            pomocniczaEqual[i][2] = 0;
            pomocniczaControl[i][2] = 0;
            pomocniczaSphere[i][2] = 0;
            pomocniczaProp[i][1] = 0;
            pomocniczaEqual[i][1] = 0;
            pomocniczaControl[i][1] = 0;
            pomocniczaSphere[i][1] = 0;
        }
        /*      Tu jak byś mi nie ufał, gamoniu. No żeby tak Procesu skopiować nie umieć... Zawiodłeś mnie po raz ostatni, admirale. 
        for(Proces p : prezent){
                System.out.print(p.getNumOfRef() + " ");
        } System.out.println();
        for(Proces p : listaProp){
                System.out.print(p.getNumOfRef() + " ");
        } System.out.println();
        for(Proces p : listaEqual){
                System.out.print(p.getNumOfRef() + " ");
        } System.out.println();
        for(Proces p : listaSphere){
                System.out.print(p.getNumOfRef() + " ");
        } System.out.println();
        for(Proces p : listaControl){
                System.out.print(p.getNumOfRef() + " ");
        } System.out.println();
        // A tu ciąg odwołań (pacz, działa, eureka)
        listaProp.getFirst().print();
        prezent.getFirst().print();
        */
    }
    public void setMinSizeEqual(){
        for(int i = 0; i < pomocniczaEqual.length; i++){
            pomocniczaEqual[i][1] = (int)(ramEqual.getSize()/110);
        }
    }
    public void setMinSizeProp(){
         for(int i = 0; i < pomocniczaProp.length; i++){
            pomocniczaProp[i][1] = listaProp.get(i).getSize()/3;
        }
    }
    public void setMinSizeStrafe(){
        for(int i = 0; i < pomocniczaSphere.length; i++){
            pomocniczaSphere[i][1] = listaSphere.get(i).getWSetSize();
        }
    }
   public void setMinSizeControl(){
        for(int i = 0; i < pomocniczaControl.length; i++){
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
                    if(!ramProp.contains(page) && !ramProp.add(page)){
                        LRU.errorHandle(ramProp, page, pomocniczaProp, listaProp);
                        pomocniczaProp[i][2]++;
                        pomocniczaProp[i][3]++;
                    }else if(!ramProp.contains(page)){
                        pomocniczaProp[i][2]++;
                    }
                    isDone = false;
                }
                
            }
        }
        isDone =false;
        while(!isDone){
            isDone = true;
            for(int i = 0; i < listaEqual.size(); i++){
                if(!listaEqual.get(i).isDone()){
                    page = listaEqual.get(i).getPage();
                    if(!ramEqual.contains(page) && !ramEqual.add(page)){
                        LRU.errorHandle(ramEqual, page, pomocniczaEqual, listaEqual);
                        pomocniczaEqual[i][2]++;
                        pomocniczaEqual[i][3]++;
                    }else if(!ramEqual.contains(page)){
                        pomocniczaEqual[i][2]++;
                    }
                    isDone = false;
                }
            }
        }
        isDone = false;
        while(!isDone){
            isDone = true;
            licznik++;
            if(licznik%5 == 0){
                setMinSizeControl();
            }        
            for(int i = 0; i < listaControl.size(); i++){
                if(!listaControl.get(i).isDone()){
                    page = listaControl.get(i).getPage();
                    if(!ramControl.contains(page) && !ramControl.add(page)){
                        LRU.errorHandle(ramControl, page, pomocniczaControl, listaControl);
                        pomocniczaControl[i][3]++;
                        pomocniczaControl[i][2]++;
                    }else if(!ramControl.contains(page)){
                        pomocniczaControl[i][2]++;
                    }
                    isDone = false;
                    
                }
            }
        }
        licznik = 0;
        isDone = false;
        while(!isDone){
            licznik++;
            if(licznik % interval == 0){
                setMinSizeStrafe();
            }
            isDone = true;
            for(int i = 0; i < listaSphere.size(); i++){
                if(!listaSphere.get(i).isDone()){
                    page = listaSphere.get(i).getPage();
                    if(!ramSphere.contains(page) && !ramSphere.add(page)){
                        LRU.errorHandle(ramSphere, page, pomocniczaSphere, listaSphere);
                        pomocniczaSphere[i][2]++;
                        pomocniczaSphere[i][3]++;
                    }else if(!ramSphere.contains(page)){
                        pomocniczaSphere[i][2]++;
                    }
                
                isDone = false;
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
        for(int i = 0; i < pomocniczaSphere.length; i++){
            suma += pomocniczaSphere[i][3];
        }
        System.out.println("Ilość błędów dla strefowego: "  +   suma);
    }
    

}
