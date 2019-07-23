import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
    private String accNumber;
    private int balance;
    private int oldBalance;

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(int oldBalance) {
        this.oldBalance = oldBalance;
    }

    Account() {
    }

    Account(String accNumber) throws FileNotFoundException {
        this.accNumber = accNumber;
        this.balance = checkBalance(accNumber);
        this.oldBalance = balance;
    }

    Account(String accNumber, int balance) {
        this.accNumber = accNumber;
        this.balance = balance;
        this.oldBalance = balance;
    }

    public int checkBalance(String accNumber) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("balance.txt"));
        HashMap<String, String> map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] columns = scanner.nextLine().split(" ");
            map.put(columns[0], columns[1]);
        }

        String amount = map.get(accNumber);
        int amountValue = Integer.parseInt(amount);
        System.out.println("Карта номер " + accNumber + ", баланс " + amountValue);
        System.out.println("************");
        return amountValue;
    }

    public int changeBalance(int balance) throws IOException {
        ATM atm = new ATM();
        while (true) {
            System.out.println("Хотите пополнить счёт или снять наличные? \nВведите + если хотите пополнить баланс, либо - если хотите снять средства");
            System.out.println("Либо введите end, чтобы завершить работу.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String change = reader.readLine();

            if (change.equals("+")) {
                while (true) {
                    System.out.println("Пополнение счета. Введите количество либо введите end, чтобы вернуться назад.");
                    String plus = reader.readLine();
                    if (plus.equals("end")) break;
                    else {
                        try {
                            int plusInt = Integer.parseInt(plus);
                            if ((plusInt > 0)&&(plusInt<1000000)) {
                                balance += plusInt;
                                atm.setAtmMoney(atm.getAtmMoney()+plusInt);
                                System.out.println("Баланс счёта равен: " + balance);
                                break;
                            } else {
                                System.out.println("Значение должно быть положительным, но не более 1000000");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Что-то пошло не так. попробуйте заново.");
                        }

                    }


                }

            }


            if (change.equals("-")) {
                while (true) {
                    System.out.println("Сколько хотите снять? Ведите end для отмены.");
                    String minus = reader.readLine();
                    if (minus.equals("end")) break;
                    else {
                        try {

                            int withdraw = Integer.parseInt(minus);
                            if (withdraw < 0) withdraw = (withdraw * (-1));
                            if (((balance - withdraw) >= 0)&&(withdraw <= atm.getAtmMoney())){
                                balance = balance - withdraw;
                                atm.setAtmMoney(atm.getAtmMoney()-withdraw);
                                System.out.println("Выполнено. Новый баланс счёта равен: " + balance);
                                break;
                            }
                            if ((balance - withdraw) < 0) {
                                System.out.println("Недостаточно средств на счете. Текущий баланс равен: " + balance);
                            } else {
                                System.out.println("В банкомате не достаточно средств");

                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода. Попробуйте ещё раз.");
                        }

                    }
                }
            }

            if (change.equals("end")) {
                System.out.println("Работа завершена.");
                break;
            }

        }
        return balance;
    }

    public void saveAndExit(String accNumber, int newBalance, int oldBalance) throws IOException {
        File balance = new File("balance.txt");
        String oldContent = "";
        BufferedReader reader = new BufferedReader(new FileReader("balance.txt"));
        String line = reader.readLine();
        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator();
            line = reader.readLine();
        }
        String newContent = oldContent.replaceAll(accNumber + " " + oldBalance, accNumber + " " + newBalance);
        FileWriter writer = new FileWriter("balance.txt");
        writer.write(newContent);
        reader.close();
        writer.close();
    }
}


