/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

/**
*
* @author Mateusz
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Main {
    public static void main(String[]args) throws FileNotFoundException{
        int iloscStron;
        int wielkoscRamu;
        int interval;
        int liczbaProcesow;
        Scanner sc = new Scanner(new FileReader("Test.txt"));
        
        while(sc.hasNextLine()){
                String linia = sc.nextLine();
                Scanner pom = new Scanner(linia);
                wielkoscRamu = pom.nextInt();
                liczbaProcesow = pom.nextInt();
                
                Sheluder shlud;
                ProcesListGenerator plg = new ProcesListGenerator();
                LinkedList<Proces> procesy = new LinkedList<>();
                switch(pom.nextInt()){
                    case 0:
                        interval = pom.nextInt();
                        procesy = plg._20Generate(liczbaProcesow, interval);
                        shlud = new Sheluder(wielkoscRamu, procesy); 
                        System.out.println("RAM o wielkości: " + wielkoscRamu + "  dla " + liczbaProcesow + " procesów, z których każdy ma 5 stron i 12 odwołań");
                        shlud.execution(interval);
                        shlud.printErrors();
                        break;
                    case 1:
                        iloscStron = pom.nextInt();
                        interval = pom.nextInt();
                        procesy = plg.pseudoRandGenerate(liczbaProcesow, iloscStron, interval);
                        shlud = new Sheluder(wielkoscRamu, procesy); 
                        System.out.println("RAM o wielkości: " + wielkoscRamu + "  dla " + liczbaProcesow + " procesów, z których każdy ma 7 stron i " + (iloscStron/7)*8 + " odwołań" );
                        shlud.execution(interval);
                        shlud.printErrors();
                        break;
                } 
            
        }
    }
}
