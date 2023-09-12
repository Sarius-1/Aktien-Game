public class Arbeitszeitrechner {

    static Account acc = new Account();
    static String ankunft;
    static int hh;
    static int mm;

    public static void main(String[] args){
        start();
    }

    static void start(){
        hh= 0;
        mm= 0;
        System.out.println("Wann hast du heute eingestempelt?");
        acc.belegen();
        ankunft = Account.input;
        try {
            System.out.println("Ankunft: " + ankunft + " Uhr");

            if (ankunft.length() == 4) {
                hh = Integer.parseInt(ankunft.substring(0, 1));
                mm = Integer.parseInt(ankunft.substring(2, 4));
            } else {
                hh = Integer.parseInt(ankunft.substring(0, 2));
                mm = Integer.parseInt(ankunft.substring(3, 5));
            }

            mm += 15;
            if (mm > 59) {
                hh++;
                mm -= 60;
            }
            hh += 8;
            if (mm < 10) {
                System.out.println("Ohne minus zu machen dürftest du ab " + hh + ":0" + mm + " Uhr gehen");
            } else {
                System.out.println("Ohne minus zu machen dürftest du ab " + hh + ":" + mm + " Uhr gehen");
            }
        }
        catch (Exception e){
            System.out.println("Bitte tätige deine Eingabe im Format hh:mm");
            start();
        }
    }
}
