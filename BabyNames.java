//Name: Wyatt Bechtle
//Date: 23 April 2023
//Program: Test Calculator Class

//Algorithm
//---------
//Step 1) Display program explanation.
//Step 2) Get user input.
//Step 3) Validate the input.
//Step 4) Find name ranking.
//Step 4) Prompt user to quit or go again.
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

//Class is used to find baby name rankings in the year span of 2005 to 2010.
public class BabyNames {
    public static void main(String[] args) {

        //Instantiate scanner object on declare variable to hold user input to iterate program.
        Scanner input = new Scanner(System.in);
        String choice;

        //Display program explanation.
        System.out.println("\nGreetings...\n" + 
                            "This programs takes a year from 2005 to 2010 and a\n" +
                            "baby name to determine the name's rank within the\n" +
                            "input year.");

        //Variable to hold user input return value.
        int userInput;

        //Loop is used to iterate program.
        do {
            //Loop is used to validate user input for the year.
            do {
                try{
                    userInput = getUserInput();//Call input method.
                }
                catch (IllegalArgumentException exception) {
                    System.out.println("\n" + exception);
                    userInput = -1;
                }
            } while (userInput == -1); //-1 shows failure.

            //Declare variable to hold name.
            String name;

            //Loop is used to ensure empty string is not entered for name.
            do {
                System.out.print("\nPlease enter a baby name for that year: ");
                name = input.nextLine();

                //Display error if invalid.
                if (name.equalsIgnoreCase("")) {
                    System.out.println("\nERROR: Name cannot be empty string.");
                }
            } while (name.equalsIgnoreCase(""));//Iterate while empty string.

            //Try block used in case files are missing from directory.
            try {
                String rank = getRank(userInput, name);//Call rank method.

                //Not found scenario.
                if (rank.equals("0")) {
                    System.out.printf("\n%s is not ranked for the year %d\n", name, userInput);
                }
                //Found scenario.
                else {
                    System.out.printf("\n%s is ranked %s for the year %d\n", name, rank.trim(), userInput);
                }
            } 
            //Data files missing...
            catch (FileNotFoundException e) {
                System.out.printf("\nNo data found for the year %d\n", userInput);
            } 

            //Loop is used to get user to input "y" or "Y" to continue or "n" or "N" to exit.
            do {

            System.out.print("\nWould you like to continue (Y/N)? ");
            choice = input.nextLine();//Get user input.

            //Display error if input does not meet prompt expectations.
            if (!(choice.equalsIgnoreCase("Y") || 
            choice.equalsIgnoreCase("N"))) {
                System.out.println("\nInvalid input...\nPlease input (Y/N)");
            }
            } while (!(choice.equalsIgnoreCase("Y") || 
            choice.equalsIgnoreCase("N"))); //Iterate while invalid input.

        } while (choice.equalsIgnoreCase("Y"));//Iterate while choice is "Y/y"
    }

    //This method is used to get input from user. Specifically input that needs validation.
    public static int getUserInput() 
    throws IllegalArgumentException {

        //Instantiate scanner object on declare variable to hold user input to iterate program.
        Scanner input = new Scanner(System.in);

        try {
            //Prompt user to input year and assign value to variable.
            System.out.print("\nPlease enter a year from 2005 to 2010: ");
            int year = input.nextInt();
            input.nextLine();//Clear user input.

            //If year is out of range throw exception.
            if (year < 2005 || year > 2010) {
                throw new IllegalArgumentException("The year " + year + " is invalid.");
            }
            return year;
        }
        //Catch is indended for mismatched input.
        catch (InputMismatchException exception) {
            System.out.println("\n" + exception + " : Only enter numerical values for year.");
            input.nextLine(); //Clear user input.
            return -1; //Inication of failure.
        }
    }
    //This method is used to determine the rank of the name in a given year.
    public static String getRank(int year, String name)
            throws FileNotFoundException {

        //Instantiate file and scanner object.
        File file = new File("babynamesranking" + year + ".txt");
        Scanner input = new Scanner(file);

        //If data files missing throw exception.
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        //Declare and set value of rank.
        String rank = "0";

        //Loop used to read through data file.
        while (input.hasNextLine()) {

            //Instantiate string object to hold a lines value form the data file.
            String line = input.nextLine();

            //Split the line into tokens stored inside a String array.
            String [] tokens = line.split("\t");
            
            //If either names from data file's line match given name, assign rank the value
            //of the associated token and break.
            if ((tokens[1].strip().equalsIgnoreCase(name) || tokens[3].strip().equalsIgnoreCase(name))) {
                rank = tokens[0];
                break;
            }
        }
        return rank;
    }
}
