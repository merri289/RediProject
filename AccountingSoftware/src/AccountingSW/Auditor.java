package AccountingSW;

import java.io.*;
import java.util.Scanner;

public class Auditor {

    String userFileName;

    public Auditor(long userId, String userName)
    {

        this.userFileName = "C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\" + userName + '_' + userId + ".txt";

    }

// 1. display all previous entries to user.
    public void displayExistingEntry ()
    {
        String result;
        float balance = 0;

        try
        {
            BufferedReader auditorFileReader = new BufferedReader(new FileReader(userFileName));
            System.out.println("=========================================");
            System.out.println("Displaying entry for " + auditorFileReader.readLine());
            System.out.println("=========================================");
            for(int i = 0; i<3; i++) // ignore the first 4 lines in the file. they contain username, password, age and separator line.
            {
                auditorFileReader.readLine();
            }
            while((result = auditorFileReader.readLine()) != null)
            {
                if (!result.equals(""))  // do not print empty lines in the file else parseFloat will throw exception
                {
                    System.out.println(result);
                    balance = balance + Float.parseFloat(result);
                }
            }
            System.out.println("-----------------------------");
            System.out.println("Current Balance is "+ balance);
            System.out.println("-----------------------------");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Can not open file. Please try again ...");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // allow user to input new entries
public void addNewEntry()
{
    Scanner getUserInput = new Scanner(System.in);
    float userNewEntryInput;
    try
    {
        BufferedWriter newEntryFile = new BufferedWriter(new FileWriter(userFileName, true));
        while(true)
        {
            System.out.println("Enter new entry. Press -0 to go to Auditor menu");

            userNewEntryInput = getUserInput.nextFloat();
            if(userNewEntryInput == -0)
            {
                break;
            }
            newEntryFile.newLine();
            newEntryFile.write(String.valueOf(userNewEntryInput));
            newEntryFile.flush();
        }
        newEntryFile.close();
    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }

}

}
