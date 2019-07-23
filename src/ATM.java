import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    public int atmMoney;
    public ATM (){
        atmMoney=20000;
    }
    public int getAtmMoney() {
        ;   return atmMoney;
    }

    public void setAtmMoney(int atmMoney) {
        this.atmMoney = atmMoney;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Card card = new Card(login());
        card.checkPinCode(card.getCardNumber());
        Account account = new Account(card.getCardNumber());
        account.setBalance(account.changeBalance(account.getBalance()));
        account.saveAndExit(account.getAccNumber(), account.getBalance(), account.getOldBalance());
    }

    public static String login() throws IOException {
        String card ="";
        boolean check = false;

        while (check != true) {
            card = inputCard();
            check = checkCard(card);
            if (check == false) System.out.println("Карта не найдена.");
        }
        return card;
    }

    public static String inputCard() throws IOException {
        System.out.println("Введите номер карты вида: XXXX-XXXX-XXXX-XXXX");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cardInput = reader.readLine();
        return cardInput;
    }

    public static boolean checkCard(String card) throws FileNotFoundException {
        boolean valid = false;
        Scanner scanner = new Scanner(new FileReader("account.txt"));
        HashMap<String, String> map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] columns = scanner.nextLine().split(" ");
            map.put(columns[0], columns[1]);
        }
        if (map.containsKey(card))  valid = true;
        else valid = false;
        return valid;
    }
}
