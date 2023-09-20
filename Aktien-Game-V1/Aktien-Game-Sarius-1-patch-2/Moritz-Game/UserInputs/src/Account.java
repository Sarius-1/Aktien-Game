import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {

    public static final String ANSI_RESET = "\u001B[0m";
    //public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //public static final String ANSI_YELLOW = "\u001B[33m";
    //public static final String ANSI_BLUE = "\u001B[34m";
    //public static final String ANSI_PURPLE = "\u001B[35m";
    //public static final String ANSI_CYAN = "\u001B[36m";
    //public static final String ANSI_WHITE = "\u001B[37m";
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    static String uhrzeitAlt = dtf.format(LocalDateTime.now());

    static Random rnd = new Random();
    static Scanner sc;
    static Account acc;
    static String input;
    static Krypto kry = new Krypto();
    static Blackjack blck = new Blackjack();
    static Lotto6aus49 lotto = new Lotto6aus49();
    static Arbeitszeitrechner azr = new Arbeitszeitrechner();
    static float aktie1 = 100;
    static float aktie2 = 150;
    static float aktie3 = 50;
    static float aktie1alt = 100;
    static float aktie2alt = 150;
    static float aktie3alt = 50;

    static float aktieFirma = 5;  //normaler Wert: 50 (ebenfalls für aktieFirmaAlt)
    static float aktieFirmaAlt = 5;
    static float firmenwert = 200; //normaler Wert: 2000 (ebenfalls für firmenwertAlt)
    static float firmenwertAlt = 200;
    static String firmenname;
    static float firmenkosten = 20;
    static boolean gegründet = false;
    static float einnahmen;
    static int mitarbeiter;
    static int mitarbeiterzuf = 3; // 1: sehr unzufrieden, 2: unzufrieden, 3: zufrieden, 4: glücklich, 5: sehr glücklich
    static int gehalt = 15; //standart für mitarbeitergehalt
    static boolean aktie1ins = false;
    static boolean aktie2ins = false;
    static boolean aktie3ins = false;

    static int aktien[][] = new int[10][4];
    static int merkerAktie;

    static String b, d;
    static float a, c, e;
    static String loggedBen;
    static String loggedPass;
    static int merker;

    static int zufallszahl;
    static int merker2;
    static String temp;
    static boolean loggedIn = false;
    static String[] benutzername = new String[10];
    static String[] passwort = new String[10];

    static String[][] postfach = new String[10][10];

    static float[] saldo = new float[11]; //Konto mit index 10 gehört zur firma

    public static void main(String[] args) {

        //Test
        benutzername[0] = "Admin";
        passwort[0] = "Passwort";
        saldo[0] = 10000;

        loggedout();

    }

    static void belegen() {
        sc = new Scanner(System.in);
        acc = new Account();
        input = sc.nextLine().trim().replace(",", ".");
    }

    static void loggedout() {
        if (loggedIn) {
            logged();
        } else {
            login();
        }
    }


    static void login() {
        System.out.println("Bitte einloggen!");
        System.out.println("Benutzername:");
        wait(500);
        belegen();
        if (input.equals("vergessen")) {
            if (temp != null) {
                schreibePostfach(0, ": Der Benutzer " + temp + " hat sein Passwort vergessen");
                System.out.println("Nachricht an Admin gesendet!");
                System.out.println("Bitte schicke nun keine weiteren Nachrichten an den Admin, sondern versuche es in der nächsten Zeit mit dem Passwort 'NULL'");
                System.out.println();

                login();
            } else {
                System.out.println("Versuche erst, dich einzuloggen und setzte ggf. dann dein Passwort zurück!");
            }
        } else {
            sucheB(input);
            if (!benVorhanden(input)) {
                System.out.println("Der Nutzer " + input + " ist nicht gelistet!");
                login();
            }
            temp = input;
            System.out.println("Passwort:");
            wait(500);
            belegen();
            if (sucheP(input)) {
                loggedIn = true;
                loggedBen = benutzername[merker];
                loggedPass = passwort[merker];
                System.out.println("Erfolgreich eingeloggt!");
                System.out.println();
                logged();
            } else {
                System.out.println("Fehler bei Benutzername oder Passwort!");
                System.out.println("Falls du dein Passwort vergessen hast, schreibe 'vergessen' bei Benutzername hin und der Admin setzt dein Passwort auf 'NULL'");
                System.out.println();
                login();
            }
        }
    }


    static void hauptmenue() {
        belegen();
        if (input.equals("create") && loggedBen.equals("Admin")) {
            System.out.println();
            System.out.println("Neuen Nutzer erstellen...");
            System.out.println("Benutzernamen eingeben");
            wait(500);
            belegen();
            for (int i = 0; i < 10; i++) {
                if (benutzername[i] == null) {
                    benutzername[i] = input;
                    saldo[i] = 10000;
                    break;
                }
            }
            System.out.println("Passwort eingeben");
            wait(500);
            belegen();
            for (int i = 0; i < 10; i++) {
                if (passwort[i] == null) {
                    passwort[i] = input;
                    break;
                }
            }
            System.out.println("Nutzer erfolgreich erstellt!");
            System.out.println();
            logged();
        } else if (input.equals("logout")) {
            loggedIn = false;
            loggedBen = null;
            loggedPass = null;
            System.out.println();
            loggedout();
        } else if (input.equals("show")) {
            System.out.println("Benutzername: " + loggedBen);
            System.out.println("Passwort: " + loggedPass);
            System.out.println();
            logged();
        } else if (input.equals("list") && loggedBen.equals("Admin")) {
            System.out.println();
            druckeBP();
            System.out.println();

            logged();
        } else if (input.equals("msgservice")) {
            post();
        } else if (input.equals("change")) {
            change();
            logged();
        } else if (input.equals("calc")) {
            calculator();
            logged();
        } else if (input.equals("money")) {
            geld();
            logged();
        } else if (input.equals("stonks")) {
            aktienmarkt();
            logged();
        } else if (input.equals("blackjack")) {
            blck.start();
            logged();
        } else if (input.equals("krypto")) {
            kry.start();
            logged();
        } else if (input.equals("lotto")) {
            System.out.println("Wie viel Geld möchtest du wetten?");
            belegen();
            if (saldo[merker] >= Integer.parseInt(input)) {
                saldo[merker] -= Integer.parseInt(input);
                saldo[merker] += (Integer.parseInt(input) * lotto.start());
            } else {
                System.out.println("Dein Saldo ist zu gering für diesen Einsatz!");
            }
            logged();
        } else if (input.equals("azr")) {
            azr.start();
            logged();
        } else if (input.equals("firma") && loggedBen.equals("Admin")) {
            firma();
            logged();
        } else {
            System.out.println("Fehler bei Eingabe, bitte erneut versuchen!");
            System.out.println();
            logged();
        }
    }

    static void logged() {
        uhrzeitAlt = dtf.format(LocalDateTime.now());
        drucken();
        hauptmenue();
    }

    static void drucken() {
        System.out.println();
        if (loggedBen.equals("Admin")) {
            System.out.println("Nutzer erstellen: create");
            System.out.println("Alle Benutzer und Passwörter auflisten: list");
        }
        System.out.println("Abmelden von " + loggedBen + ": logout");
        System.out.println("Nutzerdaten anzeigen: show");
        System.out.println("Nutzerdaten ändern: change");
        System.out.println("Nachrichtendienst aufrufen: msgservice");
        System.out.println("Taschenrechner: calc");
        System.out.println("Saldo und Transaktionen: money");
        System.out.println("Aktienmarkt aufrufen: stonks");
        System.out.println("Firmenmenü aufrufen: firma");
        System.out.println("Verschlüsselungsprogramm öffnen: krypto");
        System.out.println("Blackjack spielen: blackjack");
        System.out.println("Lotto 6aus49 spielen: lotto");
        System.out.println("Arbeitszeitrechner öffnen: azr");
        System.out.println();
    }

    static void firma() {
        System.out.print("Firmenmenü:");
        System.out.println();
        if (!gegründet) {
            System.out.println("Du hast bisher keine Firma gegründet! Einmalige Kosten zum Gründen betragen 3000€!");
            System.out.println("Des weiteren belaufen sich die Kosten bei jedem Relaunch auf 20€");
            System.out.println("Die Firma wird direkt als AG gemeldet, jede Aktie ist zu Beginn 50€ wert.");
            System.out.println("Du bekommst nach dem Gründen 50% Firmenanteile, also 20 Aktien");
            System.out.println();
            System.out.println();
            System.out.println("Gründen der Firma: found");
            System.out.println("Zurück zum Hauptmenü: menu");
            belegen();
            if (input.equals("found")) {
                gründen();
            } else if (input.equals("menu")) {
                logged();
            }
        } else {
            firmaVerwaltung();
        }
    }

    static void gründen() {
        aktieFirma = 50; //normaler Wert: 50 (ebenfalls für aktieFirmaAlt)
        aktieFirmaAlt = 50;
        firmenwert = 2000; //normaler Wert: 2000 (ebenfalls für firmenwertAlt)
        firmenkosten = 20;
        firmenwertAlt = 2000;
        saldo[10] = 120;
        System.out.println();
        if (saldo[merker] < 3000) {
            System.out.println("Du verfügst nicht über genügend Geldmittel, um eine Firma zu gründen!");
            logged();
        } else {
            saldo[merker] -= 3000;
            System.out.println("3000€ wurden von deinem Konto abgebucht!");
            System.out.println("Gib nun den Namen deiner neuen Firma ein");
            belegen();
            firmenname = input;
            aktien[merker][3] = 20;
            gegründet = true;
            einnahmenAusgaben();
            firmaVerwaltung();
        }
    }

    static void firmaVerwaltung() {
        System.out.println();
        System.out.println("Verwaltung Firma: " + firmenname);
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.println("Aktueller Firmenwert: " + firmenwert + " €");
        System.out.println("Alter Firmenwert: " + firmenwertAlt + " €");
        if (firmenwert - firmenwertAlt > 10) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (firmenwert - firmenwertAlt) + " €" + ANSI_RESET);
        } else if ((firmenwert - firmenwertAlt < -10)) {
            System.out.println(ANSI_RED + "Veränderung: " + (firmenwert - firmenwertAlt) + " €" + ANSI_RESET);
        } else {
            System.out.println("Veränderung: " + (firmenwert - firmenwertAlt) + " €");
        }
        System.out.println();
        System.out.println("Aktie " + firmenname + ", aktueller Wert: " + aktieFirma + " €");
        System.out.println("Alter Wert: " + aktieFirmaAlt + " €");
        if (aktieFirma - aktieFirmaAlt > 10) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
        } else if ((aktieFirma - aktieFirmaAlt < -10)) {
            System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
        } else {
            System.out.println("Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €");
        }
        System.out.println();
        System.out.println("Einnahmen: " + (firmenwert - firmenwertAlt) / 10 + " €");
        System.out.println("Ausgaben: -" + firmenkosten + " €");
        if ((einnahmen - firmenkosten >= 0)) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (einnahmen - firmenkosten) + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Veränderung: " + (einnahmen - firmenkosten) + ANSI_RESET);
        }
        if (saldo[10] < 0) {
            System.out.println(ANSI_RED + "Firmenkonto: " + saldo[10] + " €" + ANSI_RESET);
        } else {
            System.out.println("Firmenkonto: " + saldo[10] + " €");
        }
        System.out.println();
        System.out.println("Aktien neu berechnen: relaunch");
        System.out.println("Mitarbeiter verwalten: employ");
        System.out.println("Geld auf das Firmenkonto überweisen: send");
        System.out.println("Geld von dem Firmenkonto abheben: withdraw");
        System.out.println("Firma verkaufen: sell");
        System.out.println("Zurück zum Hauptmenü: menu");
        belegen();
        if (input.equals("menu")) {
            logged();
        } else if (input.equals("re") || input.equals("relaunch")) {
            relaunch();
            einnahmenAusgaben();
            firmaVerwaltung();
        } else if (input.equals("send")) {
            sucheB(loggedBen);
            System.out.println("Saldo: " + saldo[merker] + "€");
            System.out.println();
            System.out.println("Wie viel Geld möchtest du auf das Firmenkonto überweisen?");
            belegen();
            if (saldo[merker] >= Float.parseFloat(input)) {
                saldo[merker] -= Float.parseFloat(input);
                saldo[10] += Float.parseFloat(input);
                firmaVerwaltung();
            } else {
                System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                firmaVerwaltung();
            }
        } else if (input.equals("withdraw")) {
            System.out.println("Wie viel Geld möchtest du von dem Firmenkonto abheben?");
            belegen();
            if (saldo[10] >= Float.parseFloat(input)) {
                saldo[10] -= Float.parseFloat(input);
                saldo[merker] += Float.parseFloat(input);
                firmaVerwaltung();
            } else {
                System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                firmaVerwaltung();
            }
        } else if (input.equals("sell")) {
            System.out.println();
            System.out.println("Firma " + firmenname + " aufgelöst!");
            saldo[merker] += aktien[merker][3] * aktieFirma;
            gegründet = false;

        } else if (input.equals("employ")) {
            mitarbeiter();
        } else {
            System.out.println("Eingabefehler!");
            firmaVerwaltung();
        }
    }

   
    static void mitarbeiter(){
        System.out.println("Mitarbeiterverwaltung:");
        System.out.println();
        System.out.println("Aktuelle Mitarbeiterzahl: "+mitarbeiter);
        if(mitarbeiter>=1){
            System.out.print("Aktuelle Mitarbeiterzufriedenheit: ");
            switch (mitarbeiterzuf){
                case 1: System.out.println("sehr unglücklich"); break;
                case 2: System.out.println("unglücklich"); break;
                case 3: System.out.println("zufrieden"); break;
                case 4: System.out.println("glücklich"); break;
                case 5: System.out.println("sehr glücklich"); break;
            }
        } else {
            System.out.println("Aktuelle Mitarbeiterzufriedenheit: -");
        }
        System.out.println("Mitarbeitergehalt: "+gehalt+ " €");
        System.out.println();
        System.out.println("Mitarbeiterzahl ändern: change");
        System.out.println("Mitarbeitergehalt ändern: payroll");
        System.out.println("Zurück zur Firmenübersicht: return");
        System.out.println("Zurück zum Hauptmenü: menu");
        belegen();
        if(input.equals("change")){
            firmenkosten -= (gehalt*mitarbeiter);
            System.out.println("Mit deiner aktuellen Bürofläche kannst du bis zu 3 Mitarbeiter anstellen");
            System.out.println("Wie viele Mitarbeiter möchtest du beschäftigen?");
            belegen();
            if(Integer.parseInt(input)+mitarbeiter>3){
                System.out.println("Mit deiner aktuellen Bürofläche kannst du nur maximal 3 Mitarbeiter beschäftigen");
                firmenkosten += (gehalt*mitarbeiter);
                mitarbeiter();
            } else {
                if(mitarbeiter!=Integer.parseInt(input)){
                    mitarbeiter = Integer.parseInt(input);
                    mitarbeiterZufriedenheit(1);
                } else {
                    mitarbeiter = Integer.parseInt(input);
                }
                firmenkosten += (gehalt * mitarbeiter);
                System.out.println("Du zahlst jetzt " + gehalt * mitarbeiter + " € Lohn!");
                System.out.println();
                mitarbeiter();
            }
        } else if(input.equals("payroll")) {
            firmenkosten -= (gehalt*mitarbeiter);
            System.out.println("Der Durchschnittslohn beträgt 15€ die Stunde");
            System.out.println("Auf welchen Betrag möchtest du das Mitarbeitergehalt setzen?");
            belegen();
            if(Integer.parseInt(input) <= 0){
                System.out.println("Du müsst deinen Mitarbeitern mind. 1€ Gehalt zahlen!");
                firmenkosten += gehalt*mitarbeiter;
                mitarbeiter();
            } else {
                if(gehalt!=Integer.parseInt(input)){
                    gehalt = Integer.parseInt(input);
                    mitarbeiterZufriedenheit(1);
                } else {
                    gehalt = Integer.parseInt(input);
                }
                System.out.println("Du zahlst jetzt " + gehalt * mitarbeiter + " € Lohn!");
                System.out.println();
                firmenkosten += gehalt * mitarbeiter;
                mitarbeiter();
            }
        } else if(input.equals("return")){
            firmaVerwaltung();
        } else if(input.equals("menu")){
            logged();
        } else {
            System.out.println("Eingabefehler!");
            mitarbeiter();
        }
    }


    static void mitarbeiterZufriedenheit(int veränderung){ //veränderung ist dafür nützlich, zu erfassen, ob eine gehaltsänderung durchgeführt wurde oder ob es der regelmäßige methodenaufruf aus der methode "relaunch" ist
        //bei durchschnittslohn von 15€ automatisch zufrieden, unter 10€ unzufrieden, unter 5€ sehr unzufrieden, über 20€ glücklich, über 25€ sehr glücklich
        if (mitarbeiter >= 1) {
            if (veränderung == 1) {
                if (gehalt <= 5) {
                    mitarbeiterzuf = 1;
                } else if (gehalt <= 10) {
                    mitarbeiterzuf = 2;
                } else if (gehalt <= 20) {
                    mitarbeiterzuf = 3;
                } else if (gehalt < 20 && gehalt <= 25) {
                    mitarbeiterzuf = 4;
                } else if (gehalt > 25) {
                    mitarbeiterzuf = 5;
                } else {
                    mitarbeiterzuf = 0; //Fehlerindikator
                }
            } else if (veränderung == 0) {
                zufallszahl = rnd.nextInt(10);
                switch(zufallszahl){
                    case 0: mitarbeiterzuf++; break;
                    case 1: mitarbeiterzuf--; break;
                    default: break;
                }
            }
        }
        if(mitarbeiterzuf<1){
            mitarbeiterzuf=1;
        }
        if(mitarbeiterzuf>5){
            mitarbeiterzuf=5;
        }
    }




    static void einnahmenAusgaben() {
        firmenwertAlt = firmenwert;
        firmenwert = aktieFirma * 40;
        saldo[10] -= firmenkosten; //Ausgaben
        einnahmen = (firmenwert - firmenwertAlt) / 10; //einnahmen
        saldo[10] += einnahmen;
    }

    static void geld() {
        sucheB(loggedBen);
        System.out.println("Saldo: " + saldo[merker] + "€");
        System.out.println();
        System.out.println("Geld überweisen: send");
        if (loggedBen.equals("Admin")) {
            System.out.println("Saldo aller Nutzer abfragen: check");
        }
        System.out.println("Zurück zum Hauptmenü: menu");
        wait(500);
        belegen();

        if (input.equals("send")) {
            System.out.println("An welchen Nutzer soll Geld überwiesen werden?");
            System.out.println("Bitte gib hierzu die Nummer des jeweiligen Benutzers an!");
            System.out.println();
            druckeB();
            System.out.println();
            wait(500);
            belegen();
            try {
                merker2 = Integer.parseInt(input);
            }
            catch (Exception x){
                System.out.println("Bitte gib die Nummer des gewünschten Nutzers an");
                geld();
            }
            try {
                if (benutzername[merker2] == null) {
                    System.out.println("Der Nutzer zu dieser Nummer existiert nicht!");
                    System.out.println();
                } else {
                    System.out.println("Wie viel Geld möchtest du " + benutzername[merker2] + " senden?");
                    wait(500);
                    belegen();
                    if (Float.parseFloat(input) < 0) {
                        System.out.println("Die Eingabe muss positiv sein");
                        geld();
                    } else if (saldo[merker] >= Float.parseFloat(input)) {
                        saldo[merker] -= Float.parseFloat(input);
                        saldo[merker2] += Float.parseFloat(input);
                    } else {
                        System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                        geld();
                    }
                }
            } catch (Exception e) {
                System.out.println("Ihre Eingabe ist ungültig");
                geld();
            }
        } else if (input.equals("menu")) {
            logged();
        } else if (input.equals("check") && loggedBen.equals("Admin")) {
            System.out.println();
            druckeSaldo();
            System.out.println();
            geld();
        } else {
            System.out.println("Fehlerhafte Eingabe!");
            logged();
        }
    }

    static void aktienmarkt() {
        System.out.println("Aktienmarkt:");
        System.out.println();
        if (!aktie1ins) {
            System.out.println("Aktie 1, Siemens AG, aktueller Wert: " + aktie1 + " €");
            System.out.println("Alter Wert: " + aktie1alt + " €");
            if (aktie1 - aktie1alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie1 - aktie1alt) + " €" + ANSI_RESET);
            } else if ((aktie1 - aktie1alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie1 - aktie1alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie1 - aktie1alt) + " €");
            }
            System.out.println();
        }
        if (!aktie2ins) {
            System.out.println("Aktie 2, Finanz Informatik GmbH & Co. KG, aktueller Wert: " + aktie2 + " €");
            System.out.println("Alter Wert: " + aktie2alt + " €");
            if (aktie2 - aktie2alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie2 - aktie2alt) + " €" + ANSI_RESET);
            } else if ((aktie2 - aktie2alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie2 - aktie2alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie2 - aktie2alt) + " €");
            }
            System.out.println();
        }
        if (!aktie3ins) {
            System.out.println("Aktie 3, Continental AG, aktueller Wert: " + aktie3 + " €");
            System.out.println("Alter Wert: " + aktie3alt + " €");
            if (aktie3 - aktie3alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie3 - aktie3alt) + " €" + ANSI_RESET);
            } else if ((aktie3 - aktie3alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie3 - aktie3alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie3 - aktie3alt) + " €");
            }
            System.out.println();
        }
        if (gegründet) {
            System.out.println("Aktie 4, " + firmenname + ", aktueller Wert: " + aktieFirma + " €");
            System.out.println("Alter Wert: " + aktieFirmaAlt + " €");
            if (aktieFirma - aktieFirmaAlt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
            } else if ((aktieFirma - aktieFirmaAlt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €");
            }
            System.out.println();
        }

        System.out.println("Mein Portfolio:");
        System.out.println("Aktie 1: " + aktien[merker][0] + " Stück");
        System.out.println("Aktie 2: " + aktien[merker][1] + " Stück");
        System.out.println("Aktie 3: " + aktien[merker][2] + " Stück");
        System.out.println("Aktie 4: " + aktien[merker][3] + " Stück");
        System.out.println();
        System.out.println("Aktien neu berechnen: relaunch");
        System.out.println("Aktie(n) kaufen und danach neu berechnen: buy");
        System.out.println("Aktie(n) verkaufen und danach neu berechnen: sell");
        System.out.println("Hauptmenü aufrufen: menu");
        wait(500);
        belegen();

        try {

            if (input.equals("relaunch") || input.equals("re")) {
                relaunch();
                aktienmarkt();
            } else if (input.equals("buy")) {
                System.out.println();
                System.out.println("Saldo: " + saldo[merker] + "€");
                System.out.println();
                System.out.println("Welche Aktie(n) möchtest du kaufen?");
                System.out.println("Gib hierzu die Nummer ein!");
                wait(500);
                belegen();
                merkerAktie = Integer.parseInt(input) - 1;
                //try {
                if (input.equals("1")) {
                    System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                    wait(500);
                    belegen();
                    if (saldo[merker] >= (Integer.parseInt(input) * aktie1)) {
                        saldo[merker] -= Integer.parseInt(input) * aktie1;
                        aktien[merker][merkerAktie] += Integer.parseInt(input);
                        System.out.println("Kauf erfolgreich!");
                        System.out.println();
                    } else {
                        System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                    }

                } else if (input.equals("2")) {
                    System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                    wait(500);
                    belegen();
                    if (saldo[merker] >= (Integer.parseInt(input) * aktie2)) {
                        saldo[merker] -= Integer.parseInt(input) * aktie2;
                        aktien[merker][merkerAktie] += Integer.parseInt(input);
                        System.out.println("Kauf erfolgreich!");
                        System.out.println();
                    } else {
                        System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                    }
                } else if (input.equals("3")) {
                    System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                    wait(500);
                    belegen();
                    if (saldo[merker] >= (Integer.parseInt(input) * aktie3)) {
                        saldo[merker] -= Integer.parseInt(input) * aktie3;
                        aktien[merker][merkerAktie] += Integer.parseInt(input);
                        System.out.println("Kauf erfolgreich!");
                        System.out.println();
                    } else {
                        System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                    }
                } else if (input.equals("4") && gegründet) {
                    System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                    wait(500);
                    belegen();
                    if (saldo[merker] >= (Integer.parseInt(input) * aktieFirma)) {
                        if (aktien[merker][merkerAktie] + Integer.parseInt(input) <= 40) {
                            saldo[merker] -= Integer.parseInt(input) * aktieFirma;
                            aktien[merker][merkerAktie] += Integer.parseInt(input);
                            System.out.println("Kauf erfolgreich!");
                            System.out.println();
                        } else {
                            System.out.println("Du kannst nicht mehr als 40 Aktien besitzen");
                            System.out.println();
                        }
                    } else {
                        System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                    }
                } else {
                    System.out.println("Eingabefehler!");
                    aktienmarkt();
                }
            } else if (input.equals("sell")) {
                System.out.println("Welche Aktie(n) möchtest du verkaufen?");
                System.out.println("Gib hierzu die Nummer ein!");
                wait(500);
                belegen();
                merkerAktie = Integer.parseInt(input) - 1;
                if (input.equals("1")) {
                    System.out.println("Wie viele Aktien möchtest du verkaufen?");
                    wait(500);
                    belegen();
                    if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                        aktien[merker][merkerAktie] -= Integer.parseInt(input);
                        saldo[merker] += Integer.parseInt(input) * aktie1;
                    } else {
                        System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                    }
                } else if (input.equals("2")) {
                    System.out.println("Wie viele Aktien möchtest du verkaufen?");
                    wait(500);
                    belegen();
                    if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                        aktien[merker][merkerAktie] -= Integer.parseInt(input);
                        saldo[merker] += Integer.parseInt(input) * aktie2;
                    } else {
                        System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                    }
                } else if (input.equals("3")) {
                    System.out.println("Wie viele Aktien möchtest du verkaufen?");
                    wait(500);
                    belegen();
                    if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                        aktien[merker][merkerAktie] -= Integer.parseInt(input);
                        saldo[merker] += Integer.parseInt(input) * aktie3;
                    } else {
                        System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                    }
                } else if (input.equals("4") && gegründet) {
                    System.out.println("Wie viele Aktien möchtest du verkaufen?");
                    wait(500);
                    belegen();
                    if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                        aktien[merker][merkerAktie] -= Integer.parseInt(input);
                        saldo[merker] += Integer.parseInt(input) * aktieFirma;
                    } else {
                        System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                    }
                } else {
                    System.out.println("Eingabefehler!");
                    aktienmarkt();
                }
            } else if (input.equals("menu")) {
                logged();
            } else {
                System.out.println("Fehlerhafte Eingabe, zurück zum Hauptmenü");
                logged();
            }
        } catch (Exception ec) {
            System.out.println("Ihre Eingabe ist ungültig, bitte geben sie nur (natürliche) Zahlen ein.");
            aktienmarkt();
        }
        aktienmarkt();

    }

    static void relaunch() {
        mitarbeiterZufriedenheit(0);
        if (uhrzeitAlt != dtf.format(LocalDateTime.now())) {
            aktienÄndern();
        }
        if (aktie1 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Siemens AG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][0] = 0;
            aktie1ins = true;
        }
        if (aktie2 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Finanz Informatik GmbH & Co. KG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][1] = 0;
            aktie2ins = true;
        }
        if (aktie3 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Continental AG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][2] = 0;
            aktie3ins = true;
        }
        if (aktieFirma <= 0) {
            firmenwertAlt = firmenwert;
            firmenwert = aktieFirma * 40;

            System.out.println("Aktueller Firmenwert: " + firmenwert + " €");
            System.out.println("Alter Firmenwert: " + firmenwertAlt + " €");
            if (firmenwert - firmenwertAlt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (firmenwert - firmenwertAlt) + " €" + ANSI_RESET);
            } else if ((firmenwert - firmenwertAlt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (firmenwert - firmenwertAlt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (firmenwert - firmenwertAlt) + " €");
            }

            System.out.println("Aktie " + firmenname + ", aktueller Wert: " + aktieFirma + " €");
            System.out.println("Alter Wert: " + aktieFirmaAlt + " €");
            if (aktieFirma - aktieFirmaAlt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
            } else if ((aktieFirma - aktieFirmaAlt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktieFirma - aktieFirmaAlt) + " €");
            }
            System.out.println();
            insolvenz();
        }


        if (saldo[10] < 0) {
            insolvenz();
        }
    }

    static void insolvenz() {
        System.out.println();
        System.out.println(ANSI_RED + firmenname + " steht vor der Insolvenz!" + ANSI_RESET);
        System.out.println();
        System.out.println("Möchtest du die Firma mit Eigenkapital vor der Insolvenz retten? y/n");
        belegen();
        try {
            if (input.equals("y")) {
                sucheB(loggedBen);
                System.out.println("Saldo: " + saldo[merker] + "€");
                System.out.println();
                System.out.println("Wie viel Geld möchtest du in die Firma investieren?");
                if (aktieFirma <= 0) {
                    System.out.println("Der Firmenwert steigt auf ein Viertel der Einzahlung, der Aktienwert auf ein Hundertsechzigstel!");
                    belegen();


                    if (saldo[merker] >= Float.parseFloat(input)) {
                        saldo[merker] -= Float.parseFloat(input);
                        aktieFirmaAlt = aktieFirma;
                        aktieFirma += (Float.parseFloat(input) / 160);
                        System.out.println(ANSI_GREEN + "Insolvenz vorerst abgewendet!" + ANSI_RESET);
                    }


                 else {
                    System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                    insolvenz();
                }
            }
             else{
                    System.out.println("Dein Firmenkonto hat ein negatives Saldo");
                    System.out.println("Eine einfache Begleichung der Schulden genügt, um die Insolvenz abzuwenden");
                    belegen();
                }
                if (saldo[merker] >= Float.parseFloat(input)) {
                    saldo[merker] -= Float.parseFloat(input);
                    saldo[10] += Float.parseFloat(input);
                    System.out.println(ANSI_GREEN + "Insolvenz vorerst abgewendet!" + ANSI_RESET);
                } else {
                    System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                    insolvenz();
                }
            }


        else{
                System.out.println("Möchtest du die Firma wiklich auflösen? y/n");
                belegen();
                if (input.equals("y")) {
                    aktien[merker][3] = 0;
                    gegründet = false;
                    saldo[merker] += saldo[10];
                } else {
                    insolvenz();
                }
            }
        }

        catch(Exception eR){
            System.out.println("Bitte geben sie eine Zahl ein");
            insolvenz();
        }
    }


    static void aktienÄndern() {
        aktie1alt = aktie1;
        aktie2alt = aktie2;
        aktie3alt = aktie3;
        int anzahlAkt = 3;
        if (gegründet) {
            aktieFirmaAlt = aktieFirma;
            anzahlAkt = 4;
        }

        for (int i = 0; i < anzahlAkt; i++) {
            zufallszahl = rnd.nextInt(20);
            switch (zufallszahl) {
                case 0:
                case 2:
                case 4:
                case 6:
                case 8:
                    setPlusAktie(i, 2 * rnd.nextFloat());
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                    setMinusAktie(i, 2 * rnd.nextFloat());
                    break;
                case 10:
                case 12:
                    setPlusAktie(i, 4 * rnd.nextFloat());
                    break;
                case 11:
                case 13:
                    setMinusAktie(i, 4 * rnd.nextFloat());
                    break;
                case 14:
                    setPlusAktie(i, 5 * rnd.nextFloat());
                    break;
                case 15:
                    setMinusAktie(i, 5 * rnd.nextFloat());
                    break;
                case 16:
                    setPlusAktie(i, 10 * rnd.nextFloat());
                    break;
                case 17:
                    setMinusAktie(i, 10 * rnd.nextFloat());
                    break;
                case 18:
                    setPlusAktie(i, 20 * rnd.nextFloat());
                    break;
                case 19:
                    setMinusAktie(i, 20 * rnd.nextFloat());
                    break;

            }
        }


    }

    static void setPlusAktie(int aktie, float operation) {
        switch (aktie) {
            case 0:
                aktie1 += operation;
                break;
            case 1:
                aktie2 += operation;
                break;
            case 2:
                aktie3 += operation;
                break;
            case 3:
                aktieFirma += operation;
                break;
        }
    }

    static void setMinusAktie(int aktie, float operation) {
        switch (aktie) {
            case 0:
                aktie1 -= operation;
                break;
            case 1:
                aktie2 -= operation;
                break;
            case 2:
                aktie3 -= operation;
                break;
            case 3:
                aktieFirma -= operation;
                break;
        }
    }

    static void post() {
        System.out.println("Posteingang aufrufen: post");
        System.out.println("Posteingang leeren: clear");
        System.out.println("Nachricht schreiben: message");
        System.out.println("Hauptmenü aufrufen: menu");
        wait(500);
        belegen();
        if (input.equals("post")) {
            druckePostfach(merker);
            post();
        } else if (input.equals("message")) {
            nachricht();
            post();
        } else if (input.equals("clear")) {
            leerePostfach();
            post();
        } else if (input.equals("menu")) {
            logged();
        } else {
            System.out.println("");
            System.out.println("Ihre Eingabe ist ungültig.");
            post();
        }
    }

    static void schreibePostfach(int benutzer, String nachricht) {
        for (int i = 0; i < 10; i++) {
            if (postfach[benutzer][i] == null) {
                postfach[benutzer][i] = nachricht;
                break;
            }
        }
    }

    static void druckePostfach(int benutzer) {
        for (int i = 0; i < 10; i++) {
            if (postfach[benutzer][i] != null) {
                System.out.println("Nachricht " + (i + 1) + " " + postfach[benutzer][i]);
                System.out.println();
            } else {
                System.out.println("Es liegen keine (weiteren) Nachrichten vor!");
                System.out.println();
                break;
            }
        }
    }

    static void leerePostfach() {
        for (int i = 0; i < 10; i++) {
            postfach[merker][i] = null;
        }
        System.out.println();
    }

    static void nachricht() {
        System.out.println("An welchen Nutzer soll die Nachricht gesendet werden?");
        wait(500);
        belegen();
        if (benVorhanden(input)) {
            sucheB(input);
            System.out.println("Wie lautet deine Nachricht an " + input + "?");
            wait(500);
            belegen();
            schreibePostfach(merker, "(von " + loggedBen + "): " + input);
            System.out.println("Nachricht erfolgreich gesendet!");
            System.out.println();
        } else {
            System.out.println("Der Benutzer " + input + " ist nicht gelistet!");
            System.out.println();
        }
    }

    static void calculator() {
        boolean richtigeEingabe = false;
        System.out.println("Rechenoperationen mit bis zu 3 Zahlen möglich!");

        while (richtigeEingabe == false) {
            System.out.print("Erste Zahl:");
            System.out.println();
            wait(500);
            belegen();
            try {
                a = umrechnen(input);
                richtigeEingabe = true;
            } catch (Exception ex) {
                System.out.println("Bitte nur Zahlen eingeben");
            }
        }
        richtigeEingabe = false;
        wait(500);
        while (richtigeEingabe == false) {
            System.out.print("Operator:");
            System.out.println();
            belegen();
            if (Objects.equals(input, "+") || Objects.equals(input, "*") || Objects.equals(input, "-") || Objects.equals(input, "/")) {
                b = input;
                richtigeEingabe = true;
            } else {
                System.out.println("Bitte nur einen der folgenden Operatoren nutzen: + - * /");
            }

        }
        richtigeEingabe = false;
        while (richtigeEingabe == false) {
            System.out.print("Zweite Zahl:");
            System.out.println();
            wait(500);
            belegen();
            try {
                c = umrechnen(input);
                richtigeEingabe = true;
            } catch (Exception ex) {
                System.out.println("Bitte nur Zahlen eingeben");
            }
        }
        richtigeEingabe = false;
        System.out.println("Operator oder '=':");
        while (richtigeEingabe == false) {
            System.out.print("Operator:");
            System.out.println();
            belegen();
            if (Objects.equals(input, "+") || Objects.equals(input, "*") || Objects.equals(input, "-") || Objects.equals(input, "/") || Objects.equals(input, "=")) {
                d = input;
                richtigeEingabe = true;
            } else {
                System.out.println("Bitte nur einen der folgenden Operatoren nutzen: + - * / =");
            }

        }
        richtigeEingabe = false;
        if (input.equals("=")) {
            System.out.println();
            System.out.println("Ergebnis: " + Math.round(berechnen() * 1000) / 1000.0);
            System.out.println();
        } else {
            while (richtigeEingabe == false) {
                System.out.print("Dritte Zahl:");
                System.out.println();
                wait(500);
                belegen();
                try {
                    e = umrechnen(input);
                    richtigeEingabe = true;
                } catch (Exception ex) {
                    System.out.println("Bitte nur Zahlen eingeben");
                }
            }
            System.out.println();
            System.out.println("Ergebnis: " + Math.round(berechnen() * 1000) / 1000.0);
            System.out.println();
        }
    }

    static float berechnen() {
        if (e == 0) {
            switch (b) {
                case "+":
                    return a + c;
                case "-":
                    return a - c;
                case "*":
                    return a * c;
                case "/":
                    return a / c;
                default:
                    System.out.println("Fehlerhafte Eingabe");
                    System.out.println();
                    return 0;

            }
        } else {
            if (d.equals("*")) {
                switch (b) {
                    case "+":
                        return a + (c * e);
                    case "-":
                        return a - (c * e);
                    case "*":
                        return a * (c * e);
                    case "/":
                        return a / (c * e);
                    default:
                        System.out.println("Fehlerhafte Eingabe");
                        System.out.println();
                        return 0;
                }
            } else if (d.equals("/")) {
                switch (b) {
                    case "+":
                        return a + (c / e);
                    case "-":
                        return a - (c / e);
                    case "*":
                        return a * (c / e);
                    case "/":
                        return a / (c / e);
                    default:
                        System.out.println("Fehlerhafte Eingabe");
                        System.out.println();
                        return 0;
                }
            } else if (d.equals("+")) {
                switch (b) {
                    case "+":
                        return a + c + e;
                    case "-":
                        return a - c + e;
                    case "*":
                        return a * c + e;
                    case "/":
                        return a / c + e;
                    default:
                        System.out.println("Fehlerhafte Eingabe");
                        System.out.println();
                        return 0;
                }
            } else if (d.equals("-")) {
                switch (b) {
                    case "+":
                        return a + c - e;
                    case "-":
                        return a - c - e;
                    case "*":
                        return a * c - e;
                    case "/":
                        return a / c - e;
                    default:
                        System.out.println("Fehlerhafte Eingabe");
                        System.out.println();
                        return 0;
                }
            } else {
                System.out.println("Fehlerhafte Eingabe");
                System.out.println();
                return 0;

            }
        }

    }

    static float umrechnen(String zahl) {
        if (zahl.contains(",")) {
            StringBuilder str = new StringBuilder(zahl);
            str.setCharAt(1, '.');
            zahl = str.toString();
            return Float.parseFloat(zahl);
        }
        return Float.parseFloat(zahl);
    }


    static void change() {
        if (loggedBen.equals("Admin")) {
            System.out.println("Welchen Nutzer möchtest du bearbeiten?");
            System.out.println();
            druckeBP();
            System.out.println();
            System.out.println("Bitte gib hierzu die Nummer des jeweiligen Benutzers an!");
            wait(500);
            belegen();
            merker = Integer.parseInt(input);
            if (benutzername[merker] == null) {
                System.out.println("Der Nutzer zu dieser Nummer existiert nicht!");
                System.out.println();
            } else {
                System.out.println("Gib jetzt den neuen Namen für den Nutzer " + benutzername[merker] + " ein!");
                wait(500);
                belegen();
                benutzername[merker] = input;
                if (merker == 0) {
                    loggedBen = input;
                }
                System.out.println("Gib jetzt das neue Passwort ein!");
                wait(500);
                belegen();
                passwort[merker] = input;
                if (merker == 0) {
                    loggedPass = input;
                }
                System.out.println();
                System.out.println("Nutzerdaten erfolgreich geändert");
                System.out.println();
            }
        } else {
            System.out.println("Gib jetzt den neuen Namen für dich ein!");
            wait(500);
            belegen();
            sucheB(loggedBen);
            benutzername[merker] = input;
            loggedBen = input;
            System.out.println("Gib jetzt das neue Passwort ein!");
            wait(500);
            belegen();
            passwort[merker] = input;
            loggedPass = input;
            System.out.println();
            System.out.println("Nutzerdaten erfolgreich geändert");
            System.out.println();
        }
    }

    static void sucheB(String benutzern) {
        for (int i = 0; i < 10; i++) {
            if (benutzername[i] != null) {
                if (benutzername[i].equals(benutzern)) {
                    merker = i;
                    break;
                }
            }
        }

    }

    static boolean benVorhanden(String benutzern) {
        for (int i = 0; i < 10; i++) {
            if (benutzername[i] != null) {
                if (benutzername[i].equals(benutzern)) {
                    return true;
                }
            }
        }
        return false;
    }

    static void druckeBP() {
        for (int i = 0; i < 10; i++) {
            if (benutzername[i] != null) {
                System.out.println(i + " " + benutzername[i]);
                System.out.println(i + " " + passwort[i]);
            }
        }

    }

    static void druckeB() {
        for (int i = 0; i < 10; i++) {
            if (benutzername[i] != null) {
                System.out.println(i + " " + benutzername[i]);
            }
        }

    }

    static void druckeSaldo() {
        for (int i = 0; i < 10; i++) {
            if (benutzername[i] != null) {
                System.out.println(i + " " + benutzername[i] + " " + saldo[i]);
            }
        }
    }

    static boolean sucheP(String passw) {
        return passwort[merker].equals(passw);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
