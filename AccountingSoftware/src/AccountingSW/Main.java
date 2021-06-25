
/************************************************************************************
 *  *******************   Accounting Software   *************************************
 *  Logs user expenses and incomes in to file and calculates current balance.
 *  have helper classes called Registrator and Auditor.
 *  Registrator registers new user and creates a file for the user.
 *  Auditor handles entering and displaying of expenses incomes and current balances
 *  Contains _workFile.txt which holds details such as user names with their passwords
 *      also lastUsedId is saved to this file so next new registered user can get valid Id.
 *  Creates one file per new user file name format is userName_ID
 ***********************************************************************************/

package AccountingSW;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String args[])
    {
        Scanner InputScanner = new Scanner(System.in);
        int mainMenuInput;
        do {
                // 1. display main menu to user and get user choice
                displayMenu(1);
                mainMenuInput = InputScanner.nextInt();

                // 2. decide what to do based on user choice
                // 2.1 log in
                if (mainMenuInput == 1)
                {
                    displayMenu(5);
                    long id = InputScanner.nextInt();
                    System.out.println("Enter User name");
                    String userName = InputScanner.next();
                    System.out.println("Enter password");
                    String userPassword = InputScanner.next();

                    if (authenticate(userName, userPassword)) // if authentication is successful
                    {
                        Auditor myAuditor = new Auditor(id, userName);  // create object of class Auditor
                        int userAuditorChoice = 0;
                        do {
                            displayMenu(6);
                            userAuditorChoice = InputScanner.nextInt();
                            if (userAuditorChoice == 1)
                            {
                                myAuditor.displayExistingEntry();
                            }
                            else if (userAuditorChoice == 2)
                            {
                                myAuditor.addNewEntry();
                            }
                        } while (userAuditorChoice != -0);
                    }
                    else // if authentication failed
                    {
                        System.out.println("please try again...");
                    }

                }
                // 2.2 Register new user
                else if (mainMenuInput == 2)
                {
                    Registrator myRegistrator = new Registrator();
                    myRegistrator.registerNewUser();
                }
                // 2.3 Exit program
                else if (mainMenuInput == -0)
                {
                    System.out.println("Exiting...");
                }
                else
                {
                    System.out.println("Unacceptable input... please try again.");
                }
            } while(mainMenuInput != -0);
    }

    /* display menu to user */
    public static void displayMenu(int x)
    {
        switch(x) {
            case 1: // display (login/register) menu
            {
                System.out.println("======================================");
                System.out.println(" ***** Welcome to Accounting SW ***** ");
                System.out.println("======================================");
                System.out.println(" 1. LogIn");
                System.out.println(" 2. Register");
                System.out.println("-0. Quit");
            }
            break;
            case 2: // display enter user name
            {
                System.out.println("Enter User name");
            }
            break;
            case 3: // display enter password
            {
                System.out.println("Enter password");
            }
            break;
            case 4: // display enter age
            {
                System.out.println("Enter Age");
            }
            break;
            case 5:
            {
                System.out.println("Enter ID");
            }
            break;
            case 6:
            { // Auditor menu
                System.out.println("Press 1 to display Existing Entry");
                System.out.println("Press 2 to add new Entry");
                System.out.println("Press -0 to log out");
            }
            break;
            default:
                // do nothing
        }
    }

    /* Authenticates user who want to log in. */
    public static boolean authenticate(String userName, String password)
    {
        // return 1 if authentication pass, else return 0

        String storedUserPassword = null;
        try
        {
            BufferedReader workFileReader = new BufferedReader(new FileReader("C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\_workFile.txt"));
            String fileContent;
            while((fileContent = workFileReader.readLine()) != null)
            {
                if(fileContent.startsWith(userName))
                {
                    storedUserPassword = fileContent.substring(fileContent.lastIndexOf(',') + 1).trim();
                }
            }

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (password.equals(storedUserPassword))
        {
            System.out.println("\n***** Authentication Successful ! *****\n");
            return true;
        }
        else
        {
            System.out.println("\nxxxxx Authentication Failed... xxxxx \n");
            return false;
        }
    }


}

