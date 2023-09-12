import java.util.Random;
import java.util.Scanner;

public class Blackjack {


    static Scanner sc;
    static String input;
    static Random rnd = new Random();
    static int gesamtZahlKarten;
    static int mng2 = 4;
    static int mng3 = 4;
    static int mng4 = 4;
    static int mng5 = 4;
    static int mng6 = 4;
    static int mng7 = 4;
    static int mng8 = 4;
    static int mng9 = 4;
    static int mng10 = 4;
    static int mngJ = 4;
    static int mngQ = 4;
    static int mngK = 4;
    static int mngA = 4;
    static int nummer = 1;

    static String[] kartenWerteS;
    static String[] kartenWerteC;

    static int wert;
    static int zahl;

    public static void main(String[] args){
        start();
    }


    static void belegen(){
        sc = new Scanner(System.in);
        input = sc.nextLine();
    }
    static void start(){
        mng2 = 4;
        mng3 = 4;
        mng4 = 4;
        mng5 = 4;
        mng6 = 4;
        mng7 = 4;
        mng8 = 4;
        mng9 = 4;
        mng10 = 4;
        mngJ = 4;
        mngQ = 4;
        mngK = 4;
        mngA = 4;
        gesamtZahlKarten = mng2+mng3+mng4+mng5+mng6+mng7+mng8+mng10+mngJ+mngQ+mngK+mngA;
        kartenWerteC = new String[5];
        kartenWerteS = new String[5];
        System.out.println("Blackjack:");
        System.out.println();
        System.out.println();
        ersteRunde();
    }

    static void ersteRunde(){
        System.out.println("Deine Karten:");
        kartenWerteS[0]=karteZiehen();
        kartenWerteS[1]=karteZiehen();
        System.out.println(kartenWerteS[0]+" "+kartenWerteS[1]);
        System.out.println("Wert: "+wertBerechnen(1));


        System.out.println();
        System.out.println("Karten Dealer:");
        kartenWerteC[0] = karteZiehen();
        System.out.println(kartenWerteC[0]+" X");
        System.out.println("Wert: "+kartenWerteC[0]);
        System.out.println();
        if(wertBerechnen(1)==21){
            gewonnen();
        } else {
            weitereRunde();
        }
    }

    static void weitereRunde(){
        System.out.println("Möchtest du noch eine Karte ziehen oder halten?");
        belegen();
        if(input.equals("ziehen")){
            System.out.println();
            for(int i = 0; i<5; i++){
                if(kartenWerteS[i]==null){
                    kartenWerteS[i]=karteZiehen();
                    break;
                }
            }
            weitereRundeDrucken();
        } else if(input.equals("halten")){
            System.out.println();
            dealerZug();
        } else {
            System.out.println("Fehlerhafte Eingabe!");
            weitereRunde();
        }
    }

    static void weitereRundeDrucken(){
        System.out.println("Deine Karten:");
        System.out.print(kartenWerteS[0]);
        for(int i = 1; i<5;i++){
            if(kartenWerteS[i]!=null){
                System.out.print(" + ");
                System.out.print(kartenWerteS[i]);
            }
        }
        System.out.println();
        System.out.println("Wert: "+wertBerechnen(1));
        System.out.println();
        System.out.println();
        System.out.println("Karten Dealer:");
        System.out.println(kartenWerteC[0]+" X");
        if(wertBerechnen(1)==21){
            gewonnen();
        } else if(wertBerechnen(1)>21){
            verloren();
        } else {
            weitereRunde();
        }
    }

    static void dealerZug(){
        kartenWerteC[nummer]=karteZiehen();
        nummer++;
        System.out.println();
        System.out.println("Karten Dealer:");
        System.out.println(kartenWerteC[0]+" "+kartenWerteC[1] +" "+kartenWerteC[2]+" "+kartenWerteC[3]);
        System.out.println("Wert: "+wertBerechnen(0));
        if(wertBerechnen(0)==21){
            verloren();
        } else if(wertBerechnen(0)>21){
            gewonnen();
        } else {
            if (wertBerechnen(0 )< 17) {
                dealerZug();
            } else {
                if (wertBerechnen(0) > wertBerechnen(1)) {
                    verloren();
                } else if(wertBerechnen(0)== wertBerechnen(1)){
                    System.out.println("Stand of");
                    start();
                }
                else {
                    gewonnen();
                }
            }
        }
    }

    static void gewonnen(){
        if(wertBerechnen(1)==21) {
            System.out.println("Du hast 21 erreicht und somit gewonnen! Blackjack!");
        } else if(wertBerechnen(0)>21){
            System.out.println("Der Dealer hat mehr als 21 erreicht und du hast somit gewonnen!");
        } else {
            System.out.println("Du hast eine höhere Zahl als der Dealer und somit gewonnen!");
        }
        System.out.println("Erneut spielen? y/n");
        belegen();
        if(input.equals("y")) {
            System.out.println();
            start();
        } else {

        }
    }

    static void verloren(){
        if(wertBerechnen(1)>21) {
            System.out.println("Du hast mehr als 21 erreicht und somit verloren!");
        } else if (wertBerechnen(0) == 21) {
            System.out.println("Der Dealer hat 21 erreicht und du hast somit verloren! Blackjack!");
        } else {
            System.out.println("Der Dealer hat eine höhere Zahl als du und somit hast du verloren!");
        }
        System.out.println("Erneut spielen? y/n");
        belegen();
        if(input.equals("y")) {
            System.out.println();
            start();
        } else {

        }
    }

    static int wertBerechnen(int ziel){
        wert = 0;
        if(ziel==0){
            for(int i = 0; i<kartenWerteC.length; i++) {
                if(!(kartenWerteC[i]==null)) {
                    if (kartenWerteC[i].equals("J") || kartenWerteC[i].equals("Q") || kartenWerteC[i].equals("K") || kartenWerteC[i].equals("Z")) {
                        wert += 10;
                    } else if (kartenWerteC[i].equals("A")) {
                        if (wert + 11 > 21) {
                            wert++;
                        } else {
                            wert += 11;
                        }
                    } else {
                        wert += Integer.parseInt(kartenWerteC[i]);
                    }
                }
            }
        } else if(ziel==1) {
            for (int i = 0; i< kartenWerteS.length; i++) {
                if(!(kartenWerteS[i]==null)) {
                    if (kartenWerteS[i].equals("J") || kartenWerteS[i].equals("Q") || kartenWerteS[i].equals("K") || kartenWerteS[i].equals("Z")) {
                        wert += 10;
                    } else if (kartenWerteS[i].equals("A")) {
                        if ((wert + 11) > 21) {
                            wert++;
                        } else {
                            wert += 11;
                        }
                    } else {
                        wert += Integer.parseInt(kartenWerteS[i]);
                    }
                }
            }
        }
        return wert;
    }

    static String karteZiehen(){
        zahl = rnd.nextInt(gesamtZahlKarten);
        if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8-mng7-mng6-mng5-mng4-mng3)){
            if(mng2>=1) {
                mng2--;
                return "2";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8-mng7-mng6-mng5-mng4)){
            if(mng3>=1) {
                mng3--;
                return "3";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8-mng7-mng6-mng5)){
            if(mng4>=1) {
                mng4--;
                return "4";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8-mng7-mng6)){
            if(mng5>=1) {
                mng5--;
                return "5";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8-mng7)){
            if(mng6>=1) {
                mng6--;
                return "6";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9-mng8)){
            if(mng7>=1) {
                mng7--;
                return "7";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10-mng9)){
            if(mng8>=1) {
                mng8--;
                return "8";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ-mng10)){
            if(mng9>=1) {
                mng9--;
                return "9";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ-mngJ)){
            if(mng10>=1) {
                mng10--;
                return "Z";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK-mngQ)){
            if(mngJ>=1) {
                mngJ--;
                return "J";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA-mngK)){
            if(mngQ>=1) {
                mngQ--;
                return "Q";
            } else {
                karteZiehen();
            }
        } else if(zahl < (gesamtZahlKarten-mngA)) {
            if (mngK >= 1) {
                mngK--;
                return "K";
            } else {
                karteZiehen();
            }
        } else if(zahl < gesamtZahlKarten){
            if(mngA>=1) {
                mngA--;
                return "A";
            } else {
                karteZiehen();
            }
        } else {
            return "F";
        }
        return "F";
    }
}
