
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mateusz
 */
public class LRU{
    public static void errorHandle(RAM memory, Page page){
        int spr = 0;
        int pom = 0;
        for(int i = 0; i < memory.getSize(); i++){
            if(memory.get(i).getProces().lastTimeUsed(memory.get(i)) > spr){
                spr = memory.get(i).getProces().lastTimeUsed(memory.get(i));
                pom = i;
            }
        }
        memory.set(pom, page);
    }
    public static void errorHandle(RAM memory, Page page, int min, int maks){
        int spr = 0;
        int pom = 0;
        for(int i = min; i < maks; i++){
            if(memory.get(i).getProces().lastTimeUsed(memory.get(i)) > spr){
                spr = memory.get(i).getProces().lastTimeUsed(memory.get(i));
                pom = i;
            }
        }
        memory.set(pom, page);
    }
    
}
