
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
    RAM ramOPT;
    RAM ramFIFO;
    RAM ramLRU;
    RAM ramALRU;
    RAM ramRand;
    LinkedList<Proces> lista;
    OPT kolejkaOPT;
    FIFO kolejkaFIFO;
    LRU kolejkaLRU;
    ALRU kolejkaALRU;
    Rand kolejkaRand;
    public Sheluder(int rozmiarRAMu,LinkedList<Proces> prezent){
        lista = prezent;
        ramOPT = new RAM(rozmiarRAMu);
        ramFIFO = new RAM(rozmiarRAMu);
        ramLRU = new RAM(rozmiarRAMu);
        ramALRU = new RAM(rozmiarRAMu);
        ramRand = new RAM(rozmiarRAMu);
        kolejkaOPT = new OPT(ramOPT, lista);
        kolejkaFIFO = new FIFO(ramFIFO);
        kolejkaLRU = new LRU(ramLRU, lista);
        kolejkaALRU = new ALRU(ramALRU);
        kolejkaRand = new Rand(ramRand);

    }
    public void dodanieDoRAMu(Page page){
        if(!ramOPT.contains(page)&&!ramOPT.add(page)){
            kolejkaOPT.errorHandle(page);
        }
        if(!ramFIFO.contains(page)&&!ramFIFO.add(page)){
            kolejkaFIFO.errorHandle(page);
        }
        if(!ramLRU.contains(page)&&!ramLRU.add(page)){
            kolejkaLRU.errorHandle(page);
        }
        if(!ramALRU.contains(page)&&!ramALRU.add(page)){
            kolejkaALRU.errorHandle(page);
        }
        if(!ramRand.contains(page)&&!ramRand.add(page)){
            kolejkaRand.errorHandle(page);
        }
    }
    public void execution(){     //
        while(!lista.isEmpty()){
            for(int i = 0; i < lista.size(); i++){
                dodanieDoRAMu(lista.get(i).getPage());
                if(lista.get(i).isDone()){
                    lista.remove(i);
                }
            }
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
