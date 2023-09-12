import java.util.Random;

public class Lotto6aus49 {

    static Account acc = new Account();
    static Random rnd = new Random();
    static char[] arr;
    static String[] tipps;
    static int[] tippsZahlen;
    static int[] gewinnZahlen = new int[6];
    static int aktZahl;
    static String zahlen;
    static int eintraege;

    static int gewinne;

    public static void main(String[] args){
        start();
    }

    static int start(){
        eintraege=0;
        gewinne=0;
        System.out.println();
        System.out.println("Lotto 6 aus 49:");
        System.out.println();
        System.out.println("Tippe auf 6 verschiedene Zahlen im Bereich von 1 bis 49");
        System.out.println("Trenne die Zahlen bitte durch ein Semikolon");
        acc.belegen();
        zahlen = Account.input;
        arr = zahlen.toCharArray();
        tipps = new String[arr.length];
        arrayFormieren();
        bestimmungZahlen();
        ermittlungGewinner();
        switch (gewinne){
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 6;
            case 5: return 10;
            case 6:
                System.out.println(Account.ANSI_GREEN+"Jackpot"+Account.ANSI_RESET);
                return 100;
            default:
                System.out.println("Fehler, Gewinne außerhalb von 0 und 6!!!");
                return 0;

        }
    }

    static void arrayFormieren(){
        for(int i = 0, j=0; i<arr.length;i++){
            if(arr[i]==';'){
                i++;
                j++;
                eintraege++;
            }
            if(tipps[j]==null){
                tipps[j] = String.valueOf(arr[i]);
            } else {
                tipps[j] += String.valueOf(arr[i]);
            }
        }
        eintraege++;
        tippsZahlen = new int[eintraege];
        for(int i = 0; i<eintraege; i++) {
            tippsZahlen[i] = Integer.parseInt(tipps[i]);
            System.out.print(tippsZahlen[i]+ " ");
            if(tippsZahlen[i]>49 || tippsZahlen[i]<1){
                System.out.println();
                System.out.println("Fehler bei Tippabgabe, die Zahlen müssen zwischen 1 und 49 liegen!");
                start();
            }
        }
        System.out.println();
        ueberpruefungZahlen();
        }

    static void ueberpruefungZahlen(){
        if(tippsZahlen.length!=6) {
            System.out.println("Du hast nicht exakt 6 Zahlen getippt!");
            start();
        }
        for(int i = 0; i<tippsZahlen.length; i++){
            for(int j = 0; j<tippsZahlen.length; j++){
                if(tippsZahlen[i]==tippsZahlen[j] && j!=i){
                    System.out.println("Du hast mehrere identische Zahlen getippt!");
                    start();
                }
            }
        }
    }

    static void bestimmungZahlen(){
        for(int i = 0; i<6; i++){
            aktZahl = rnd.nextInt(49);
            if(aktZahl!=0 && vorhanden(aktZahl)){
                gewinnZahlen[i] = aktZahl;
            } else {
                i--;
            }
        }
    }

    static void ermittlungGewinner(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<6; j++){
                if(gewinnZahlen[i] == tippsZahlen[j]){
                    System.out.println("Richtiger Tipp mit der Zahl: "+tippsZahlen[i]);
                    gewinne++;
                }
            }
        }
        System.out.println();
        System.out.println("Richtige Tipps gesamt: "+gewinne);
        System.out.println();
    }

    static boolean vorhanden(int zahl){
        for(int i = 0; i<6; i++){
            try{
                if(gewinnZahlen[i] == zahl){
                    return false;
                }
            }
            catch(NullPointerException e){

            }
        }
        return true;
    }



}


