import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Card {
    private String cardNumber;
    private String pinCode;

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public Card(){
    }
    public Card(String number){
        this.cardNumber=number;
    }


    public boolean checkPinCode(String cardNumber) throws IOException, InterruptedException {
        int count = 0;
        boolean pinValid = false;

        Scanner scanner = new Scanner(new FileReader("account.txt"));
        HashMap<String, String> map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] columns = scanner.nextLine().split(" ");
            map.put(columns[0], columns[1]);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите PIN");
        do {
            for (int i = 0; i < 3; i++) {
                String pin = reader.readLine();
                if (pin.equals(map.get(cardNumber))) {
                    System.out.println("OK!");
                    pinValid = true;
                    break;
                } else {
                    System.out.println("Wrong pin");
                    pinValid = false;
                    count++;
                    System.out.println("Осталось попыток " + (3 - count));
                }
            }
            if (pinValid==false) {
                System.out.println("Карта заблокирована на 24 часа.");
                TimeUnit.HOURS.sleep(24);
                count = 0;
                System.out.println("Карта разблокирована.");
            }
        }
        while (pinValid==false);

        return pinValid;
    }


}
