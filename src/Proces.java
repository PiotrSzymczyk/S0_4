
import java.util.LinkedList;

 /**
 * Instancje tej klasy symulują procesy zawierające lilstę odwołań do stron
 * oraz moment ich pojawienia się.
 * @author Piotrek
 */
public class Proces implements Cloneable {
    private Page[] pageList;
    private LinkedList<Page> pages; // Odwołania do kolejnych stron
    private LinkedList<Page> used; 
    private int interval;
    private int wSetLen;
    private int pageErrors;
    
     /**
     * Tworzy proces o zadanej ilości stron i losowym, (2-5)-strefowym ciągu odwołań
     */
    public Proces(int in, int num){
        pageErrors = 0;
        interval = in;
        pageList = new Page[(int) (Math.random() * num + 1)];
        wSetLen = (int) (pageList.length * 0.4);
        pages = new LinkedList();
        used = new LinkedList();
        for(int i = 0; i < pageList.length; i++){
            pageList[i] = new Page(i,this);
        }
        for(int i = (int) Math.random()*4 + 2; i > 0; i--){         // i = ilość stref (1-5)
            int j = (int) Math.random()*pageList.length;            // j = wielkość strefy
            int l = (int) (Math.random() * (pageList.length - j));  // l = strona od której się strefa zaczyna
            for(int k = (int) (Math.random()*4 + 1); k > 0; k--){   // powtórzenie strefy (1-4) razy
                for(int n = 0; n < j; n++){
                    pages.add(pageList[l+n]);
                }
            }
        }
        for(int i = (int) (Math.random()*pages.size()/8); i > 0; i--){ // Na każde 8 odwołań jedna strona nieporządku
            pages.add((int) (Math.random()*pages.size()), pageList[(int) (Math.random()*pageList.length)]);
            pages.add((int) (Math.random()*pages.size()), pageList[(int) (Math.random()*pageList.length)]);
        }
    }
    
    /**
     * Tworzy proces o 3 - 20 stronach i losowym, (2-5)-strefowym ciągu odwołań
     */
     
     public Proces(int in){
        pageErrors = 0;
        interval = in;
        pageList = new Page[(int) (Math.random() * 18 + 3)];
        wSetLen = (int) (pageList.length * 0.4);
        pages = new LinkedList();
        used = new LinkedList();
        for(int i = 0; i < pageList.length; i++){
            pageList[i] = new Page(i,this);
        }
        for(int i = (int) (Math.random()*4 + 2); i > 0; i--){         // i = ilość stref (2-10)
            int j = (int) (Math.random()*pageList.length)*4/5;            // j = wielkość strefy
            int l = (int) (Math.random() * (pageList.length - j));  // l = strona od której się strefa zaczyna
            for(int k = (int) (Math.random()*4 + 1); k > 0; k--){   // powtórzenie strefy (1-4) razy
                for(int n = 0; n < j; n++){
                    pages.add(pageList[l+n]);
                }
            }
        } 
        // Dodanie 2 stron dla zakłócenia lokalnosci odwaolan
        pages.add((int) (Math.random()*pages.size()), pageList[(int) (Math.random()*pageList.length)]);
        pages.add((int) (Math.random()*pages.size()), pageList[(int) (Math.random()*pageList.length)]);
    }
     /**
     * Tworzy kopię procesu
     */
    public Proces(int inter, Page[] pageL, int wSetLength, LinkedList<Page> pag, LinkedList<Page> use){
        pageErrors = 0;
        interval = inter;
        pageList = new Page[pageL.length];
        wSetLen = wSetLength;
        pages = new LinkedList<>();
        
        for(int i = 0; i < pageList.length; i++){   // skopiuj pafeList (ma swje strony)
            pageList[i] = new Page(i,this);
        }
        
        int i;
        for(Page p : pag){                          // dodaj referencje do odp stron w odp kolejności
            i = 0;
            while(!pageL[i].equals(p)) i++;
            pages.add(pageList[i]);
        }
        used = new LinkedList<>();
        
         
     }
    public Proces clone(){
        return new Proces(interval, pageList, wSetLen, pages, used);
    }
    public void  print(){
        for(Page p : pages)
            System.out.print(p.num + " ");
        System.out.println();
    }
    private void setWSetSize(){
        wSetLen = 0;
        for(Page p : pageList){
            if(p.getModB() == 1){
                p.setModB(0);
                wSetLen++;
            }
        }
    }
    public int getWSetSize(){
        return wSetLen;
    }
    
    public int getErrors(){
        return pageErrors;
    }
    
    public void incrErrors(){
        pageErrors++;
    }
    
    public Page getPage(){
        Page tmp = pages.remove(0);
        used.add(0,tmp);
        tmp.setModB(1);
        if(used.size() != 1 && used.size() % interval == 0 ) setWSetSize();
        return tmp;
    }

    public int getSize(){
        return pageList.length;
    }
    public int getNumOfRef(){
        return pages.size();
    }
    public int lastTimeUsed(Page p){
        int tmp = Integer.MAX_VALUE;
        for(int i = 0; i < used.size();i++){
            if(used.get(i) == p){
                tmp = i+1;
                break;
            }
        }
        return tmp;
    }
    public int timeToGo(Page p){
        int time = -1;
        for(int i = 0; i< pages.size(); i++){
            if(pages.get(i).equals(p)){
                 time = i;
                 break;
            }
        }
        return time;
    }
    public boolean contains(Page p){
        boolean contains = false;
        for(Page tmp : pageList){
            if(tmp.equals(p)){
                contains = true;
            }
        }
        return contains;
    }
    public boolean isDone(){
        return pages.isEmpty();
    }
    
}