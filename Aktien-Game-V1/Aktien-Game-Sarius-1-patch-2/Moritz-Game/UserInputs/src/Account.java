import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.synth.SynthDesktopIconUI;
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

    static int maxNutzer = 10;
    static Random rnd = new Random();
    static Scanner sc;
    static Account acc;
    static String input;
    static Krypto kry = new Krypto();
    static Blackjack blck = new Blackjack();
    static Lotto6aus49 lotto = new Lotto6aus49();
    static Arbeitszeitrechner azr = new Arbeitszeitrechner();
    static float aktie11 = 100;
    static float aktie12 = 150;
    static float aktie13 = 50;
    static float aktie11alt = 100;
    static float aktie12alt = 150;
    static float aktie13alt = 50;

    static float[] aktieFirma = new float[maxNutzer];  //normaler Wert: 50 (ebenfalls für aktieFirmaAlt)
    static float[] aktieFirmaAlt = new float[maxNutzer];
    static float[] firmenwert = new float[maxNutzer]; //normaler Wert: 2000 (ebenfalls für firmenwertAlt)
    static float[] firmenwertAlt = new float[maxNutzer];
    static String[] firmenname = new String[maxNutzer];
    static float[] firmenkosten = new float[maxNutzer]; //normalwert: 20
    static boolean[] gegründet = new boolean[maxNutzer];
    static float[] einnahmen = new float[maxNutzer];
    static int[] mitarbeiter = new int[maxNutzer];
    static int[] mitarbeiterzuf = new int[maxNutzer]; // 1: sehr unzufrieden, 2: unzufrieden, 3: zufrieden, 4: glücklich, 5: sehr glücklich
    static int[] gehalt = new int[maxNutzer]; //standart für mitarbeitergehalt
    static boolean aktie11ins = false;
    static boolean aktie12ins = false;
    static boolean aktie13ins = false;

    static int[][] aktien = new int[maxNutzer][maxNutzer + 3]; //Index 10, 11 und 12 für NPC-Aktien
    static int merkerAktie;

    static boolean[] firmaAufgelöst = new boolean[maxNutzer];
    static boolean übertragungLäuft = false;
    static int maxAktienNutzer = -1; //erfasst den Nutzer mit den meisten Aktien, standartwert -1, um fehler aufzusprüren
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

    static float[] saldo = new float[10];
    static float[] firmensaldo = new float[10];

    public static void main(String[] args) {

        //Test
        benutzername[0] = "Admin";
        passwort[0] = "Passwort";
        saldo[0] = 10000;

        loggedout();

    }

    static void belegen() {
        sc = new Scanner(System.in);
        input = sc.nextLine().trim().replace(",", ".");
        sucheP(loggedBen);
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
        } else if (input.equals("firma")) {
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
        if (maxAktienNutzer != -1) {
            if (loggedBen.equals(benutzername[maxAktienNutzer])) {
                for(int i = 0; i<maxNutzer;i++){
                    if(firmaAufgelöst[i]){
                        firmaÜbertragen(i);
                    }
                }
            }
        }
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
        if (!gegründet[merker]) {
            System.out.println("Du hast bisher keine Firma gegründet! Einmalige Kosten zum Gründen betragen 2500€!");
            System.out.println("Des weiteren belaufen sich die Kosten bei jedem Relaunch auf 20€");
            System.out.println("Die Firma wird direkt als AG gemeldet, jede Aktie ist zu Beginn 50€ Wert.");
            System.out.println("Du bekommst nach dem Gründen 50% Firmenanteile, also 20 Aktien.");
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
        aktieFirma[merker] = 50; //normaler Wert: 50 (ebenfalls für aktieFirmaAlt)
        aktieFirmaAlt[merker] = 50;
        firmenwert[merker] = 2000; //normaler Wert: 2000 (ebenfalls für firmenwertAlt)
        firmenkosten[merker] = 20;
        firmenwertAlt[merker] = 2000;
        firmensaldo[merker] = 100;
        System.out.println();
        if (saldo[merker] < 2500) {
            System.out.println("Du verfügst nicht über genügend Geldmittel, um eine Firma zu gründen!");
            logged();
        } else {
            saldo[merker] -= 2500;
            System.out.println("2500€ wurden von deinem Konto abgebucht!");
            System.out.println("Gib nun den Namen deiner neuen Firma ein");
            belegen();
            firmenname[merker] = input;
            aktien[merker][merker] = 20;
            gegründet[merker] = true;
            //einnahmenAusgaben();
            firmaVerwaltung();
        }
    }

    static void firmaVerwaltung() {
        System.out.println();
        System.out.println("Verwaltung Firma: " + firmenname[merker]);
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.println("Aktueller Firmenwert: " + firmenwert[merker] + " €");
        System.out.println("Alter Firmenwert: " + firmenwertAlt[merker] + " €");
        if (firmenwert[merker] - firmenwertAlt[merker] > 10) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €" + ANSI_RESET);
        } else if ((firmenwert[merker] - firmenwertAlt[merker] < -10)) {
            System.out.println(ANSI_RED + "Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €" + ANSI_RESET);
        } else {
            System.out.println("Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €");
        }
        System.out.println();
        System.out.println("Aktie " + firmenname[merker] + ", aktueller Wert: " + aktieFirma[merker] + " €");
        System.out.println("Alter Wert: " + aktieFirmaAlt[merker] + " €");
        if (aktieFirma[merker] - aktieFirmaAlt[merker] > 10) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €" + ANSI_RESET);
        } else if ((aktieFirma[merker] - aktieFirmaAlt[merker] < -10)) {
            System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €" + ANSI_RESET);
        } else {
            System.out.println("Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €");
        }
        System.out.println();
        System.out.println("Einnahmen: " + einnahmen[merker] + " €");
        System.out.println("Ausgaben: -" + firmenkosten[merker] + " €");
        if ((einnahmen[merker] - firmenkosten[merker] >= 0)) {
            System.out.println(ANSI_GREEN + "Veränderung: " + (einnahmen[merker] - firmenkosten[merker]) + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Veränderung: " + (einnahmen[merker] - firmenkosten[merker]) + ANSI_RESET);
        }
        if (firmensaldo[merker] < 0) {
            System.out.println(ANSI_RED + "Firmenkonto: " + firmensaldo[merker] + " €" + ANSI_RESET);
        } else {
            System.out.println("Firmenkonto: " + firmensaldo[merker] + " €");
        }
        System.out.println();
        System.out.println("Aktien neu berechnen: relaunch");
        System.out.println("Mitarbeiter verwalten: employ");
        System.out.println("Geld auf das Firmenkonto überweisen: send");
        System.out.println("Geld von dem Firmenkonto abheben: withdraw");
        System.out.println("Firma verkaufen: sell");
        System.out.println("Aktienmarkt aufrufen: stonks");
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
                firmensaldo[merker] += Float.parseFloat(input);
                firmaVerwaltung();
            } else {
                System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                firmaVerwaltung();
            }
        } else if (input.equals("withdraw")) {
            System.out.println("Wie viel Geld möchtest du von dem Firmenkonto abheben?");
            belegen();
            if (firmensaldo[merker] >= Float.parseFloat(input)) {
                firmensaldo[merker] -= Float.parseFloat(input);
                saldo[merker] += Float.parseFloat(input);
                firmaVerwaltung();
            } else {
                System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                firmaVerwaltung();
            }
        } else if (input.equals("sell")) {
            firmaAuflösen();
        } else if (input.equals("employ")) {
            mitarbeiter();
        } else if(input.equals("stonks")){
            aktienmarkt();
        } else {
            System.out.println("Eingabefehler!");
            firmaVerwaltung();
        }
    }


    static void mitarbeiter() {
        System.out.println("Mitarbeiterverwaltung:");
        System.out.println();
        System.out.println("Aktuelle Mitarbeiterzahl: " + mitarbeiter[merker]);
        if (mitarbeiter[merker] >= 1) {
            System.out.print("Aktuelle Mitarbeiterzufriedenheit: ");
            switch (mitarbeiterzuf[merker]){
                case 1: System.out.println("sehr unglücklich"); break;
                case 2: System.out.println("unglücklich"); break;
                case 3: System.out.println("zufrieden"); break;
                case 4: System.out.println("glücklich"); break;
                case 5: System.out.println("sehr glücklich"); break;
                case 0: System.out.println("FEHLER"); break;
            }
        } else {
            System.out.println("Aktuelle Mitarbeiterzufriedenheit: -");
        }
        System.out.println("Mitarbeitergehalt: " + gehalt[merker] + " €");
        System.out.println();
        System.out.println("Mitarbeiterzahl ändern: change");
        System.out.println("Mitarbeitergehalt ändern: payroll");
        System.out.println("Zurück zur Firmenübersicht: return");
        System.out.println("Zurück zum Hauptmenü: menu");
        belegen();
        if (input.equals("change")) {
            firmenkosten[merker] -= (gehalt[merker] * mitarbeiter[merker]);
            System.out.println("Mit deiner aktuellen Bürofläche kannst du bis zu 3 Mitarbeiter anstellen");
            System.out.println("Wie viele Mitarbeiter möchtest du beschäftigen?");
            belegen();
            if (Integer.parseInt(input) + mitarbeiter[merker] > 3) {
                System.out.println("Mit deiner aktuellen Bürofläche kannst du nur maximal 3 Mitarbeiter beschäftigen");
                firmenkosten[merker] += (gehalt[merker] * mitarbeiter[merker]);
                mitarbeiter();
            } else {
                if (mitarbeiter[merker] != Integer.parseInt(input)) {
                    mitarbeiter[merker] = Integer.parseInt(input);
                    mitarbeiterZufriedenheit(1);
                } else {
                    mitarbeiter[merker] = Integer.parseInt(input);
                }
                firmenkosten[merker] += (gehalt[merker] * mitarbeiter[merker]);
                System.out.println("Du zahlst jetzt " + gehalt[merker] * mitarbeiter[merker] + " € Lohn!");
                System.out.println();
                mitarbeiter();
            }
        } else if (input.equals("payroll")) {
            firmenkosten[merker] -= (gehalt[merker] * mitarbeiter[merker]);
            System.out.println("Der Durchschnittslohn beträgt 15€ die Stunde");
            System.out.println("Auf welchen Betrag möchtest du das Mitarbeitergehalt setzen?");
            belegen();
            if (Integer.parseInt(input) <= 0) {
                System.out.println("Du müsst deinen Mitarbeitern mind. 1€ Gehalt zahlen!");
                firmenkosten[merker] += gehalt[merker] * mitarbeiter[merker];
                mitarbeiter();
            } else {
                if (gehalt[merker] != Integer.parseInt(input)) {
                    gehalt[merker] = Integer.parseInt(input);
                    mitarbeiterZufriedenheit(1);
                } else {
                    gehalt[merker] = Integer.parseInt(input);
                }
                System.out.println("Du zahlst jetzt " + gehalt[merker] * mitarbeiter[merker] + " € Lohn!");
                System.out.println();
                firmenkosten[merker] += gehalt[merker] * mitarbeiter[merker];
                mitarbeiter();
            }
        } else if (input.equals("return")) {
            firmaVerwaltung();
        } else if (input.equals("menu")) {
            logged();
        } else {
            System.out.println("Eingabefehler!");
            mitarbeiter();
        }
    }


    static void mitarbeiterZufriedenheit(int veränderung) { //veränderung ist dafür nützlich, zu erfassen, ob eine gehaltsänderung durchgeführt wurde oder ob es der regelmäßige methodenaufruf aus der methode "relaunch" ist
        //bei durchschnittslohn von 15€ automatisch zufrieden, unter 10€ unzufrieden, unter 5€ sehr unzufrieden, über 20€ glücklich, über 25€ sehr glücklich
        for (int i = 0; i < maxNutzer; i++) {
            if (mitarbeiter[merker] >= 1) {
                if (veränderung == 1) {
                    if (gehalt[i] <= 5) {
                        mitarbeiterzuf[i] = 1;
                    } else if (gehalt[i] <= 10) {
                        mitarbeiterzuf[i] = 2;
                    } else if (gehalt[i] <= 20) {
                        mitarbeiterzuf[i] = 3;
                    } else if (gehalt[i] > 20 && gehalt[i] <= 25) {
                        mitarbeiterzuf[i] = 4;
                    } else if (gehalt[i] > 25) {
                        mitarbeiterzuf[i] = 5;
                    } else {
                        mitarbeiterzuf[i] = 0; //Fehlerindikator
                    }
                } else if (veränderung == 0) {
                    zufallszahl = rnd.nextInt(10);
                    switch (zufallszahl) {
                        case 0:
                            mitarbeiterzuf[i]++;
                            break;
                        case 1:
                            mitarbeiterzuf[i]--;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (mitarbeiterzuf[merker] < 1) {
                mitarbeiterzuf[merker] = 1;
            }
            if (mitarbeiterzuf[merker] > 5) {
                mitarbeiterzuf[merker] = 5;
            }
        }
    }


    static void einnahmenAusgaben() {
        for (int firma = 0; firma < maxNutzer; firma++) {
            if (gegründet[firma]) {
                firmenwertAlt[firma] = firmenwert[firma];
                firmenwert[firma] = aktieFirma[firma] * 40;
                firmensaldo[firma] -= firmenkosten[firma]; //Ausgaben
                einnahmenFirma(firma);
                firmensaldo[firma] += einnahmen[firma];
            }
        }
    }

    static void einnahmenFirma(int firma) {
        einnahmen[firma] = (firmenwert[firma] - firmenwertAlt[firma]) / 10; //Fixe Einnahmen basierend auf Firmenwertänderung (kann auch negativ sein)
        if (mitarbeiter[firma] > 0) {
            einnahmen[firma] += rnd.nextFloat() * rnd.nextInt(10) * mitarbeiterzuf[firma];
            if (mitarbeiterzuf[firma] - 2 > 0) {
                einnahmen[firma] += mitarbeiter[firma] * (mitarbeiterzuf[firma] - 2) * (aktieFirma[firma] / 5);
            }
            aktieFirma[firma] += (mitarbeiterzuf[firma]) * (mitarbeiter[firma] / 10);
        }
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
            merker2 = Integer.parseInt(input);
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

        for (int firma = 0; firma < maxNutzer; firma++) {
            if (gegründet[firma]) {
                System.out.println("Aktie " + (firma + 1) + ", " + firmenname[firma] + ", aktueller Wert: " + aktieFirma[firma] + " €");
                System.out.println("Alter Wert: " + aktieFirmaAlt[firma] + " €");
                if (aktieFirma[firma] - aktieFirmaAlt[firma] > 10) {
                    System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma[firma] - aktieFirmaAlt[firma]) + " €" + ANSI_RESET);
                } else if ((aktieFirma[firma] - aktieFirmaAlt[firma] < -10)) {
                    System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma[firma] - aktieFirmaAlt[firma]) + " €" + ANSI_RESET);
                } else {
                    System.out.println("Veränderung: " + (aktieFirma[firma] - aktieFirmaAlt[firma]) + " €");
                }
                System.out.println();
            }
        }

        if (!aktie11ins) {
            System.out.println("Aktie 11, Siemens AG, aktueller Wert: " + aktie11 + " €");
            System.out.println("Alter Wert: " + aktie11alt + " €");
            if (aktie11 - aktie11alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie11 - aktie11alt) + " €" + ANSI_RESET);
            } else if ((aktie11 - aktie11alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie11 - aktie11alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie11 - aktie11alt) + " €");
            }
            System.out.println();
        }
        if (!aktie12ins) {
            System.out.println("Aktie 12, Finanz Informatik GmbH & Co. KG, aktueller Wert: " + aktie12 + " €");
            System.out.println("Alter Wert: " + aktie12alt + " €");
            if (aktie12 - aktie12alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie12 - aktie12alt) + " €" + ANSI_RESET);
            } else if ((aktie12 - aktie12alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie12 - aktie12alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie12 - aktie12alt) + " €");
            }
            System.out.println();
        }
        if (!aktie13ins) {
            System.out.println("Aktie 13, Continental AG, aktueller Wert: " + aktie13 + " €");
            System.out.println("Alter Wert: " + aktie13alt + " €");
            if (aktie13 - aktie13alt > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktie13 - aktie13alt) + " €" + ANSI_RESET);
            } else if ((aktie13 - aktie13alt < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktie13 - aktie13alt) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktie13 - aktie13alt) + " €");
            }
            System.out.println();
        }

        System.out.println("Mein Portfolio:");
        for (int firma = 0; firma < maxNutzer; firma++) {
            if (gegründet[firma]) {
                System.out.println("Aktie " + (firma + 1) + ": " + aktien[merker][firma] + " Stück; noch erhältlich: " + verkAktienBerechnen(firma) + " Stück");
            }
        }
        System.out.println("Aktie 11: " + aktien[merker][10] + " Stück");
        System.out.println("Aktie 12: " + aktien[merker][11] + " Stück");
        System.out.println("Aktie 13: " + aktien[merker][12] + " Stück");
        System.out.println();
        System.out.println("Aktien neu berechnen: relaunch");
        System.out.println("Firmenmenü aufrufen: firma");
        System.out.println("Aktie(n) kaufen: buy");
        System.out.println("Aktie(n) verkaufen: sell");
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
                switch (merkerAktie) {
                    case 10:
                        System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                        wait(500);
                        belegen();
                        if (saldo[merker] >= (Integer.parseInt(input) * aktie11)) {
                            saldo[merker] -= Integer.parseInt(input) * aktie11;
                            aktien[merker][merkerAktie] += Integer.parseInt(input);
                            System.out.println("Kauf erfolgreich!");
                            System.out.println();
                        } else {
                            System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                        }
                        break;

                    case 11:
                        System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                        wait(500);
                        belegen();
                        if (saldo[merker] >= (Integer.parseInt(input) * aktie12)) {
                            saldo[merker] -= Integer.parseInt(input) * aktie12;
                            aktien[merker][merkerAktie] += Integer.parseInt(input);
                            System.out.println("Kauf erfolgreich!");
                            System.out.println();
                        } else {
                            System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                        }
                        break;
                    case 12:
                        System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                        wait(500);
                        belegen();
                        if (saldo[merker] >= (Integer.parseInt(input) * aktie13)) {
                            saldo[merker] -= Integer.parseInt(input) * aktie13;
                            aktien[merker][merkerAktie] += Integer.parseInt(input);
                            System.out.println("Kauf erfolgreich!");
                            System.out.println();
                        } else {
                            System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                        }
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 0:
                        if (!(gegründet[merkerAktie])) {
                            System.out.println("Die Firma mit dieser Nummer existiert nicht!");
                            aktienmarkt();
                            break;
                        } else {
                            System.out.println("Wie viele Aktien möchtest du davon kaufen?");
                            wait(500);
                            belegen();
                            if (saldo[merker] >= (Integer.parseInt(input) * aktieFirma[merker])) {
                                if (Integer.parseInt(input) <= verkAktienBerechnen(merkerAktie)) {
                                    saldo[merker] -= Integer.parseInt(input) * aktieFirma[merker];
                                    aktien[merker][merkerAktie] += Integer.parseInt(input);
                                    System.out.println("Kauf erfolgreich!");
                                    System.out.println();
                                } else {
                                    System.out.println("Es sind aktuell nur " + verkAktienBerechnen(merkerAktie) + " Aktie(n) von dieser Firma verfügbar!");
                                    System.out.println();
                                    break;
                                }
                            } else {
                                System.out.println(ANSI_RED + "Du verfügst nicht über die nötigen Geldmittel!" + ANSI_RESET);
                                break;
                            }
                            break;
                        }
                    default:
                        System.out.println("Eingabefehler!");
                        aktienmarkt();
                        break;

                }
            } else if (input.equals("sell")) {
                System.out.println("Welche Aktie(n) möchtest du verkaufen?");
                System.out.println("Gib hierzu die Nummer ein!");
                wait(500);
                belegen();
                merkerAktie = Integer.parseInt(input) - 1;
                switch (merkerAktie) {
                    case 10:
                        System.out.println("Wie viele Aktien möchtest du verkaufen?");
                        wait(500);
                        belegen();
                        if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                            aktien[merker][merkerAktie] -= Integer.parseInt(input);
                            saldo[merker] += Integer.parseInt(input) * aktie11;
                        } else {
                            System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                        }
                        break;

                    case 11:
                        System.out.println("Wie viele Aktien möchtest du verkaufen?");
                        wait(500);
                        belegen();
                        if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                            aktien[merker][merkerAktie] -= Integer.parseInt(input);
                            saldo[merker] += Integer.parseInt(input) * aktie12;
                        } else {
                            System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                        }
                        break;
                    case 12:
                        System.out.println("Wie viele Aktien möchtest du verkaufen?");
                        wait(500);
                        belegen();
                        if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                            aktien[merker][merkerAktie] -= Integer.parseInt(input);
                            saldo[merker] += Integer.parseInt(input) * aktie13;
                        } else {
                            System.out.println(ANSI_RED + "Du hast nicht so viele Aktien von dieser Firma!" + ANSI_RESET);
                        }
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 0:
                        if (!(gegründet[merkerAktie])) {
                            System.out.println("Die Firma mit dieser Nummer existiert nicht!");
                            aktienmarkt();
                        } else {
                            System.out.println("Wie viele Aktien möchtest du davon verkaufen?");
                            wait(500);
                            belegen();
                            if (aktien[merker][merkerAktie] >= Integer.parseInt(input)) {
                                saldo[merker] += Integer.parseInt(input) * aktieFirma[merker];
                                aktien[merker][merkerAktie] -= Integer.parseInt(input);
                                System.out.println("Verkauf erfolgreich!");
                                System.out.println();
                                if (aktien[merker][merker] == 0) {
                                    firmaAuflösen();
                                }
                            } else {
                                System.out.println(ANSI_RED + "Du kannst nicht mehr Aktien verkaufen als du besitzt!" + ANSI_RESET);
                                break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Eingabefehler!");
                        aktienmarkt();
                        break;

                }
            } else if (input.equals("menu")) {
                logged();
            } else if(input.equals("firma")){
                firma();
            } else {
                System.out.println("Fehlerhafte Eingabe, zurück zum Hauptmenü");
                logged();
            }
        } catch (Exception ec) {
            System.out.println("Exception aufgefangen!");
            System.out.println("Bitte Eingabe prüfen oder Entwickler kontaktieren!");
            aktienmarkt();
        }
        aktienmarkt();

    }

    static void relaunch() {
        mitarbeiterZufriedenheit(0);
        for (int firma = 0; firma < maxNutzer; firma++) {
            if (firmaAufgelöst[firma]) {
                System.out.println();
                System.out.println(ANSI_RED + "Relaunch nicht möglich, da eine Firmenübertragung läuft!" + ANSI_RESET);
                System.out.println();
                if (!übertragungLäuft) {
                    firmaÜbertragen(firma);
                } else {
                    aktienmarkt();
                }
            }
        }
        if (uhrzeitAlt != dtf.format(LocalDateTime.now())) {
            aktienÄndern();
        }
        if (aktie11 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Siemens AG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][10] = 0;
            aktie11ins = true;
        }
        if (aktie12 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Finanz Informatik GmbH & Co. KG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][11] = 0;
            aktie12ins = true;
        }
        if (aktie13 <= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Continental AG insolvent!" + ANSI_RESET);
            System.out.println();
            aktien[merker][12] = 0;
            aktie13ins = true;
        }
        if (aktieFirma[merker] <= 0) {

            System.out.println("Aktueller Firmenwert: " + firmenwert[merker] + " €");
            System.out.println("Alter Firmenwert: " + firmenwertAlt[merker] + " €");
            if (firmenwert[merker] - firmenwertAlt[merker] > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €" + ANSI_RESET);
            } else if ((firmenwert[merker] - firmenwertAlt[merker] < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (firmenwert[merker] - firmenwertAlt[merker]) + " €");
            }

            System.out.println("Aktie " + firmenname[merker] + ", aktueller Wert: " + aktieFirma[merker] + " €");
            System.out.println("Alter Wert: " + aktieFirmaAlt[merker] + " €");
            if (aktieFirma[merker] - aktieFirmaAlt[merker] > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €" + ANSI_RESET);
            } else if ((aktieFirma[merker] - aktieFirmaAlt[merker] < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (aktieFirma[merker] - aktieFirmaAlt[merker]) + " €");
            }
            System.out.println();
            insolvenz();
        }


        if (firmensaldo[merker] < 0) {
            insolvenz();
        }
    }


    static int verkAktienBerechnen(int firma) {
        int anzahl = 0;
        for (int nutzer = 0; nutzer < maxNutzer; nutzer++) {
            if (aktien[nutzer][firma] != 0) {
                anzahl += aktien[nutzer][firma];
            }
        }
        return 40 - anzahl;
    }

    static void firmaAuflösen() {
        System.out.println();
        System.out.println("Firma " + firmenname[merker] + " wurde abgetreten!");
        System.out.println();
        if (verkAktienBerechnen(merker) == 40) { //wenn 40 Aktien übrig sind, hat kein weiterer nutzer Anteile und die Firma wird normal aufgelöst
            if (firmensaldo[merker] > 0) {
                saldo[merker] += firmensaldo[merker];
            }
            if (aktien[merker][merker] * aktieFirma[merker] > 0) {
                saldo[merker] += aktien[merker][merker] * aktieFirma[merker];
            }
            aktien[merker][merker] = 0;
            firmensaldo[merker] = 0;
            gegründet[merker] = false;
        } else {
            if (aktien[merker][merker] * aktieFirma[merker] > 0) {
                saldo[merker] += aktien[merker][merker] * aktieFirma[merker];
            }
            aktien[merker][merker] = 0;
            firmaAufgelöst[merker] = true;
            firmaÜbertragen(merker);
        }
    }

    static void firmaÜbertragen(int firma) {
        übertragungLäuft = true;
        //Überprüfung, welcher Nutzer die meisten Anteile/Aktien hat
        maxAktienNutzer = -1; //erfasst den Nutzer mit den meisten Aktien
        int maxAktien = aktien[0][firma]; //erfasst die Anzahl Aktien, vorbelegt mit Nutzer 0
        for (int i = 1; i < maxNutzer; i++) {
            if (aktien[i][firma] > maxAktien) {
                maxAktien = aktien[i][firma];
                maxAktienNutzer = i;
            } else if (aktien[i][firma] == maxAktien && maxAktien != 0) {
                //Zwei Personen haben gleiche Anzahl an Aktien
                System.out.println("Gleiche Anzahl an Aktien zwischen Nutzern");
                System.out.println("Noch in Arbeit");
            }
        }
        if (!loggedBen.equals(benutzername[maxAktienNutzer])) {
            System.out.println("Die Firmenauflösung ist noch im Gange, bitte warten");
            aktienmarkt();
        } else if (loggedBen.equals(benutzername[maxAktienNutzer])) {
            System.out.println();
            System.out.println("Der Inhaber der Firma " + firmenname[firma] + " hat seine Anteile verkauft.");
            System.out.println("Da du der Nutzer mit den meisten Aktien bist, hast du die Möglichkeit, die Firma zu übernehmen oder sie aufzulösen");
            System.out.println("Aktueller Firmenwert: " + firmenwert[firma] + " €");
            System.out.println("Alter Firmenwert: " + firmenwertAlt[merker] + " €");
            if (firmenwert[firma] - firmenwertAlt[firma] > 10) {
                System.out.println(ANSI_GREEN + "Veränderung: " + (firmenwert[firma] - firmenwertAlt[firma]) + " €" + ANSI_RESET);
            } else if ((firmenwert[merker] - firmenwertAlt[merker] < -10)) {
                System.out.println(ANSI_RED + "Veränderung: " + (firmenwert[firma] - firmenwertAlt[firma]) + " €" + ANSI_RESET);
            } else {
                System.out.println("Veränderung: " + (firmenwert[firma] - firmenwertAlt[firma]) + " €");
            }
            System.out.println("Möchtest du die Firma übernehmen? y/n");
            belegen();
            if (input.equals("y")) {
                firmaÜbernehmen(firma);
            } else {
                System.out.println("Möchtest du die Firma wirklich auflösen? y/n");
                belegen();
                if (input.equals("y")) {
                    System.out.println("Firma aufgelöst!");
                    übertragungLäuft = false;
                    firmaAufgelöst[firma] = false;
                    firmensaldo[firma] = 0;
                    gegründet[firma] = false;
                    if (aktien[merker][firma] * aktieFirma[firma] > 0) { //Nutzer soll Aktienerlös nur bekommen, wenn dieser positiv ist
                        saldo[merker] += aktien[merker][firma] * aktieFirma[firma];
                    }
                    aktien[merker][firma] = 0;
                } else {
                    firmaÜbernehmen(firma);
                }
            }
        }
    }

    static void firmaÜbernehmen(int firma) {
        firmenname[merker] = firmenname[firma];
        firmenname[firma] = null;
        firmensaldo[merker] = firmensaldo[firma];
        firmensaldo[firma] = 0;
        firmenwert[merker] = firmenwert[firma];
        firmenwert[firma] = 0;
        firmenwertAlt[merker] = firmenwertAlt[firma];
        firmenwertAlt[firma] = 0;
        firmenkosten[merker] = firmenkosten[firma];
        firmenkosten[firma] = 0;
        gegründet[firma] = false;
        gegründet[merker] = true;
        aktieFirma[merker] = aktieFirma[firma];
        aktieFirma[firma] = 0;
        aktieFirmaAlt[merker] = aktieFirmaAlt[firma];
        aktieFirmaAlt[firma] = 0;

        //Aktien der Nutzer auf neuen Eigentümer übertragen
        for (int nutzer = 0; nutzer < maxNutzer; nutzer++) {
            if (aktien[nutzer][firma] > 0) {
                aktien[nutzer][merker] = aktien[nutzer][firma];
                aktien[nutzer][firma] = 0;
            }
        }
        System.out.println("Du bist nun der Inhaber der Firma " + firmenname[merker] + "!");
        übertragungLäuft = false;
        firmaAufgelöst[firma] = false;
    }

    static void insolvenz() {
        System.out.println();
        System.out.println(ANSI_RED + firmenname[merker] + " steht vor der Insolvenz!" + ANSI_RESET);
        System.out.println();
        System.out.println("Möchtest du die Firma mit Eigenkapital vor der Insolvenz retten? y/n");
        belegen();
        if (input.equals("y")) {
            sucheB(loggedBen);
            System.out.println("Saldo: " + saldo[merker] + "€");
            System.out.println();
            System.out.println("Wie viel Geld möchtest du in die Firma investieren?");
            if (aktieFirma[merker] <= 0) {
                System.out.println("Der Firmenwert steigt auf ein Viertel der Einzahlung, der Aktienwert auf ein Hundertsechzigstel!");
                belegen();
                if (saldo[merker] >= Float.parseFloat(input)) {
                    saldo[merker] -= Float.parseFloat(input);
                    aktieFirmaAlt[merker] = aktieFirma[merker];
                    aktieFirma[merker] += (Float.parseFloat(input) / 160);
                    System.out.println(ANSI_GREEN + "Insolvenz vorerst abgewendet!" + ANSI_RESET);

                } else {
                    System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                    insolvenz();
                }
            } else {
                System.out.println("Dein Firmenkonto hat ein negatives Saldo");
                System.out.println("Eine einfache Begleichung der Schulden genügt, um die Insolvenz abzuwenden");
                belegen();
                if (saldo[merker] >= Float.parseFloat(input)) {
                    saldo[merker] -= Float.parseFloat(input);
                    firmensaldo[merker] += Float.parseFloat(input);
                    System.out.println(ANSI_GREEN + "Insolvenz vorerst abgewendet!" + ANSI_RESET);
                } else {
                    System.out.println("Dein Saldo ist zu gering für diese Transaktion!");
                    insolvenz();
                }
            }
        } else {
            System.out.println("Möchtest du die Firma wiklich auflösen? y/n");
            belegen();
            if (input.equals("y")) {
                aktien[merker][3] = 0;
                gegründet[merker] = false;
                saldo[merker] += firmensaldo[merker];
                logged();
            } else {
                insolvenz();
            }
        }
    }

    static void aktienÄndern() {
        aktie11alt = aktie11;
        aktie12alt = aktie12;
        aktie13alt = aktie13;
        int anzahlAkt = 3;
        for (int i = 0; i < maxNutzer; i++) {
            if (gegründet[i]) {
                aktieFirmaAlt[i] = aktieFirma[i];
                anzahlAkt++;
            }
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
                aktie11 += operation;
                break;
            case 1:
                aktie12 += operation;
                break;
            case 2:
                aktie13 += operation;
                break;
            default:
                aktieFirma[aktie - 3] += operation;
                break;
        }
    }

    static void setMinusAktie(int aktie, float operation) {
        switch (aktie) {
            case 0:
                aktie11 -= operation;
                break;
            case 1:
                aktie12 -= operation;
                break;
            case 2:
                aktie13 -= operation;
                break;
            default:
                aktieFirma[aktie - 3] -= operation;
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
