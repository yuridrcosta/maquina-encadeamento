
import java.util.Scanner;

public class Utils{

    static Scanner read = new Scanner(System.in);
    static Engine engine = new Engine();

    public static void entry() {
        int option;
        String saux;
        System.out.println("[1] Encadeamento para trás");
        System.out.println("[2] Encadeamento para frente");
        option = read.nextInt();
        saux = read.nextLine();

        switch(option){
            case 1:
                engine.runBackwardsChaining();
                break;
            case 2:
                engine.runForwardsChaining();
                break;
        }
    }




}
