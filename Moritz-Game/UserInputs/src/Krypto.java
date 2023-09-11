

import java.util.Scanner;

public class Krypto {

    static String input;
    static Scanner sc;
    static int schluesselzahl;
    static String schluesselwort;
    static String text = "";

    public static void main(String[] args){
        start();
    }

    static void belegen(){
        sc = new Scanner(System.in);
        Krypto kry = new Krypto();
        input = sc.nextLine();
    }

    static void start(){
        text = "";
        System.out.println("Welche Verschlüsselungsmethode möchtest du verwenden?");
        belegen();
        switch (input){
            case "Caesar":
            case "caesar":
                caesar();
                break;

            case "vigenere":
            case "Vigenere":
                vigenere();
                break;

            default:
                System.out.println("Falsche Eingabe");
                start();
                break;
        }
    }

    static void caesar(){
        System.out.println("Möchtest du ver- oder entschlüsseln?");
        belegen();
        switch (input){
            case "ver":
            case "Ver":
            case "verschlüsseln":
            case "Verschlüsseln":
                System.out.println("Welchen Schlüssel möchtest du verwenden, Zahl von 1 bis 25?");
                belegen();
                schluesselzahl = Integer.parseInt(input);
                System.out.println("Welche Nachricht willst du verschlüsseln?");
                belegen();
                System.out.println("Verschlüsselte Nachricht: " +caesarVer(schluesselzahl, input));
                start();
                break;

            case "ent":
            case "Ent":
            case "entschlüsseln":
            case "Entschlüsseln":
                System.out.println("Welchen Schlüssel möchtest du verwenden, Zahl von 1 bis 25?");
                belegen();
                schluesselzahl = Integer.parseInt(input);
                System.out.println("Welche Nachricht willst du entschlüsseln?");
                belegen();
                System.out.println("Entschlüsselte Nachricht: " +caesarEnt(schluesselzahl, input));
                start();
                break;
        }
    }

    static void vigenere(){
        System.out.println("Möchtest du ver- oder entschlüsseln?");
        belegen();
        switch (input) {
            case "ver":
            case "Ver":
            case "verschlüsseln":
            case "Verschlüsseln":
                System.out.println("Welches Schlüsselwort willst du verwenden?");
                belegen();
                schluesselwort = input;
                System.out.println("Welche Nachricht willst du verschlüsseln?");
                belegen();
                System.out.println("Verschlüsselte Nachricht: " + vigenereVer(schluesselwort, input));
                start();
                break;

            case "ent":
            case "Ent":
            case "entschlüsseln":
            case "Entschlüsseln":
                System.out.println("Welches Schlüsselwort willst du verwenden?");
                belegen();
                schluesselwort = input;
                System.out.println("Welche Nachricht willst du entschlüsseln?");
                belegen();
                System.out.println("Entschlüsselte Nachricht: " + vigenereEnt(schluesselwort, input));
                start();
                break;
        }
    }

    static String caesarVer(int schluessel, String nachricht){
        nachricht = nachricht.toUpperCase();
        for(int i = 0; i<nachricht.length(); i++){
            if(nachricht.charAt(i)+ schluessel > 90){
                text += (char) ((int) nachricht.charAt(i) + schluessel - 26);
            } else {
                text += (char) ((int) nachricht.charAt(i) + schluessel);
            }
        }
        return text;
    }

    static String caesarEnt(int schluessel, String nachricht){
        nachricht = nachricht.toUpperCase();
        for(int i = 0; i<nachricht.length(); i++){
            if(nachricht.charAt(i)-schluessel < 65){
                text += (char) ((int) nachricht.charAt(i) - schluessel + 26);
            } else {
                text += (char) ((int) nachricht.charAt(i) - schluessel);
            }
        }
        return text;
    }

    static String vigenereVer(String schluessel, String nachricht){
        schluessel = schluessel.toUpperCase();
        nachricht = nachricht.toUpperCase();
        for(int i = 0, j = 0; i<nachricht.length(); i++, j++){
            if(j>=schluessel.length()){
                j = 0;
            }
            if(((int)nachricht.charAt(i) + (int)schluessel.charAt(j) -65)>90){
                text += (char)((int)nachricht.charAt(i) + (int)schluessel.charAt(j) -65-26);
            } else {
                text += (char) ((int) nachricht.charAt(i) + (int) schluessel.charAt(j) - 65);
            }
        }
        return text;
    }

    static String vigenereEnt(String schluessel, String nachricht){
        schluessel = schluessel.toUpperCase();
        nachricht = nachricht.toUpperCase();
        for(int i = 0, j = 0; i<nachricht.length(); i++, j++){
            if(j>=schluessel.length()){
                j = 0;
            }
            if(((int)nachricht.charAt(i) - (int)schluessel.charAt(j) +65)<65){
                text += (char)((int)nachricht.charAt(i) - (int)schluessel.charAt(j) +65+26);
            } else {
                text += (char) ((int) nachricht.charAt(i) - (int) schluessel.charAt(j) + 65);
            }
        }
        return text;
    }

}
