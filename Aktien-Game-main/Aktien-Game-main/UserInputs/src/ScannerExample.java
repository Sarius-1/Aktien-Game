import java.util.Scanner;
import java.util.Random;

public class ScannerExample {

    static Random rnd;
    private static int zahlC;
    static boolean erraten;
    static int durchgang;


    public static void main(String[] args){

        durchgang = 1;
        erraten = false;
        Random rnd = new Random();
        zahlC = rnd.nextInt(10000);

        while(!erraten && durchgang <= 10) {
        Scanner sc = new Scanner(System.in);
        ScannerExample scex = new ScannerExample();
        int input = sc.nextInt();

        scex.zahlErraten(input);
        }
    }

    void zahlErraten(int x){
        if(x == zahlC) {
            System.out.println("Du hast die Zahl " + x + " erraten!!");
            erraten = true;
        }
        else if(x<zahlC){
            System.out.println("Die Zahl " + x + " ist zu klein!");
        }
        else if(x>zahlC){
            System.out.println("Die Zahl " + x + " ist zu groß");
        }
        System.out.println("Versuch "+ durchgang + " von 10");
        durchgang++;
        if(durchgang == 11 && !erraten){
            System.out.println("Die maximale Anzahl an Versuchen ist erreicht, du hast verloren!");
            System.out.println("Die gesuchte Zahl war: " + zahlC);

        }
    }
}
