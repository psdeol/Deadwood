import java.util.Scanner;

public class InputManager{

    private Scanner scanner = new Scanner(System.in);
    private String input = "";
    
    public InputManager(){}

    public String newStringInput(){
        boolean validInput = true;
        String in = null;
        while (!validInput){
            in = scanner.nextLine();
            if (in != null){
                validInput = true;
            }
        }
        input = in;
        return in;
    }
    
    public int newIntInput(){
        boolean validInput = false;
        int num = -1;
        while (!validInput){
            while (!scanner.hasNextInt()){
                System.out.print("Whoops! Please enter a valid number: ");
                scanner.next();
            }
            num = scanner.nextInt();
            if (num >= 0){
                validInput = true;
            }
            else{
                System.out.print("Enter a positive integer: ");
            }
        }
        input = "" + num;
        return num;
    }
    
    public int newIntInput(int low, int high){
        boolean validInput = false;
        int num = -1;
        while (!validInput){
            while (!scanner.hasNextInt()){
                System.out.print("Whoops! Please enter a valid number: ");
                scanner.next();
            }
            num = scanner.nextInt();
            if (num >= low && num <= high){
                validInput = true;
            }
            else{
                System.out.print("Enter an integer between [" + low + "," + high + "]: ");
            }
        }
        input = "" + num;
        return num;
    }
    
    public String previousInput(){
        return input;
    }

}