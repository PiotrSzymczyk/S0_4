/**
 * Instancje tej klasy symulują prace pamięci RAM zapisując w tablicy strony
 * należące do wykonywanych procesów
 * @author Piotrek
 */
public class RAM {
    private Page[] ram;
    public RAM(int size){
        ram = new Page[size];
    }
    public boolean contains(Page p){
        boolean contains = false;
        for(Page tmp : ram){
            if(tmp != null && tmp.equals(p)){
                contains = true;
            }
        }
        return contains;
    }
    public Page get(int i){
        return ram[i];
    }
    public void set(int i, Page p){
        ram[i] = p; 
    }
    public boolean add(Page p){
        if( ! isFull() ){
            int i = 0;
            while(ram[i] != null) i++;
            ram[i] = p;
            p.setModB(1);
            return true;
        } else return false;
    }
    public boolean add(int index, Page p){
        if( ram[index] == null){
            ram[index] = p;
            return true;
        } else return false;
    }
    public boolean isFull(){
        return ram[ram.length-1] != null ? true : false;
    }
    public int getSize(){
        return ram.length;
    }
}
