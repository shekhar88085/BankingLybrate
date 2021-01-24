import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Account {
    private static int index=0;
    private String name;
    private double balance=0;
    protected int accNum;
    private  String mode;
    private String date;
    private  String txn_type;
    private double txn_amt;
    private double bal1=0,bal2=0;


    //Date and time setup
    Date dnow=new Date();
    SimpleDateFormat ft=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    //adding location of csv's files to string file name
    String file1="/home/sherlock/Documents/JB2/BankingLybrate/src/Data/account.csv";
    String file2="/home/sherlock/Documents/JB2/BankingLybrate/src/Data/account_statement.csv";


    //csv file header
    private static final String FILE_HEADER1= "account_number,name,balance";
    private static final String FILE_HEADER2 = "date,txn_type,account_number,txn_amount,balance,mode";


    //create object of Csv.java to use in this class
    Csv csv=new Csv();

    public Account()
            throws IOException {}


    //For the handling of modes i.e. cash or online payments using enum
    public enum ZeroOrOne {
       // ZERO(0),
        ONE("Cash"),
        TWO("Online");
        public  String val;
        private ZeroOrOne(String val) {
            this.val = val;
        }
        public String getValue() {
            return val;
        }
    }


    // for the handling of modes i.e. cash online or atm using enum
    public enum ZeroOneOrTwo {
        ZERO("Cash"),
        ONE("Online"),
        TWO("Atm");
        public final String val;
        private ZeroOneOrTwo(String val) {
            this.val = val;
        }
        public String getValue() {
            return val;
        }
    }


    //user adding
    public void addUser(String name)
            throws IOException {
        this.name=name;
        this.accNum=setAccountNumber();
        //adding balance 0
        this.balance=balance;
        String str[]={String.valueOf(accNum),name,String.valueOf(balance)};

        //CHECKING WHETHER THE USER ALREADY EXIST OR NOT
        if(csv.read(file1).contains(name)){
            System.out.println("ERROR:User is already Exists");
        }else csv.write(file1,
                  Collections.singletonList(str));
        System.out.println("User created in Database \n name: "+this.name+"\n Account number: "+this.accNum+"\nInitial Balance: "+this.balance);
    }


    //Here we use a simple way...with each time account class is called the index++
    private int setAccountNumber() {
        this.index++;
        return index;
    }


        //adding amount
        public void addAmount(double amount,ZeroOrOne arg)
            throws IOException {
        this.balance+=amount;
        //throws exception when input is invalid
        if(arg.equals("Cash")  || arg.equals("Online")) {
            this.mode=arg.getValue();
            //or set default value instead of throwing an exception
        }else{
            throw new InvalidParameterException();
      }

        //Adding data to account.csv
        String str[]={String.valueOf(this.accNum),name,String.valueOf(balance)};
        csv.write(file1,
                  Collections.singletonList(str));
        //Adding data to account_statement.csv
        String str2[]={this.ft.format(dnow),"Cr",String
        .valueOf(this.accNum),String.valueOf(amount),String.valueOf(balance),mode};
        csv.write(file2,
                  Collections.singletonList(str2));
        System.out.println("Deposited= "+amount+"Through "+mode+" mode");
    }


    //withdraw amount
    public  void withdrawAmount(double amount,ZeroOneOrTwo arg)
            throws IOException {
       this.balance+=amount;
        //throws exception when input is invalid
        if(arg.equals("Cash")  || arg.equals("Online")||arg.equals("Atm")) {
            this.mode=arg.getValue();
            //or set default value instead of throwing an exception
        }else{
            throw new InvalidParameterException();
        }

        //Adding data to account.csv
        String str[]={String.valueOf(this.accNum),this.name,String.valueOf(balance)};
        csv.write(file1,
                  Collections.singletonList(str));

        //Adding data to account_statement.csv
        String str2[]={this.ft.format(dnow),"Dr",String.valueOf(this.accNum),String.valueOf(amount),String.valueOf(balance),mode};
        csv.write(file2,
                  Collections.singletonList(str2));
        System.out.println("Withdrawn = "+amount+"Through "+mode+" mode");
    }


    //printing balance
    private void checkBalance() {
        System.out.println("Your balance is+"+this.balance);
    }
    public  void showInfo(){
        System.out.println("Name "+this.name+"\nAccount Number "+this.accNum+"\n Balance "+balance);
    }
}
