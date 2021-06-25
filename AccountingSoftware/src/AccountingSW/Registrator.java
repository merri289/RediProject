package AccountingSW;

import java.io.*;
import java.util.Scanner;

public class Registrator {

    public void registerNewUser()
    { // registers new user.

        // 1. get user input
        System.out.println("Enter UserName");
        Scanner InputScanner = new Scanner(System.in);
        String userNameInput = InputScanner.nextLine();
        System.out.println("Enter password");
        String userPasswordInput = InputScanner.next();
        System.out.println("Enter Age");
        int userAgeInput = InputScanner.nextInt();

        long calcdNewUsedId = 0;

        //2. open work file and get the next valid user ID to assign to this new user.
        try {
            BufferedReader workFileReader = new BufferedReader(new FileReader("C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\_workFile.txt"));
            String fileContent;
            while((fileContent = workFileReader.readLine()) != null)
            {
                if(fileContent.startsWith("lastUsedId"))
                {
                    calcdNewUsedId = Long.parseLong(fileContent.substring(fileContent.lastIndexOf('=') + 1).trim()) + 1;
                    System.out.println(calcdNewUsedId);
                }
            }

            workFileReader.close();
            System.out.println("\n***** Registration Successful ! *****\n");
            System.out.println("Your user Id is \" " + calcdNewUsedId + "\"" + ". Keep this number as you will need it when logging in");
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //3. create new file with new available id. should register only 1000 users max. Can be extended as needed.
        if (calcdNewUsedId >0 && calcdNewUsedId<1000)
        {
            String newUserFileName = "C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\" + userNameInput + '_' + calcdNewUsedId+ ".txt";
            try
            {
                // initiate the new user file with user details.
                BufferedWriter newUserFileWriter = new BufferedWriter(new FileWriter(newUserFileName));
                newUserFileWriter.write(userNameInput);
                newUserFileWriter.newLine();
                newUserFileWriter.write(userPasswordInput);
                newUserFileWriter.newLine();
                newUserFileWriter.write(String.valueOf(userAgeInput));
                newUserFileWriter.newLine();
                newUserFileWriter.write("------------------------------");

                newUserFileWriter.close();

                // read workfile in to buffered string and write it back with updated lastUsedId, to be used for next user.
                BufferedReader workFileReader = new BufferedReader(new FileReader("C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\_workFile.txt"));
                StringBuffer fileContentBuffer = new StringBuffer();
                String workFileContent;
                while((workFileContent = workFileReader.readLine()) != null)
                {
                    // copy existing content of work file in to string buffer and write back with updated lastUsedId. That is the onlyway we can update lastUsedId
                    if(workFileContent.startsWith("lastUsedId"))
                    {
                        long lastUsedId = Long.parseLong(workFileContent.substring(workFileContent.indexOf("=")+1, workFileContent.indexOf("=")+2));
                        // update the lastUsedId entry in the work file.
                        long thisUserId = lastUsedId + 1;
                        workFileContent = "lastUsedId="+ thisUserId;
                        fileContentBuffer.append(workFileContent);
                        fileContentBuffer.append("\n");
                    }
                    else
                    {
                        fileContentBuffer.append(workFileContent);
                        fileContentBuffer.append("\n");
                    }
                }
                workFileReader.close();

                String workFileContentString = fileContentBuffer.toString();

                // Update lasUsedId. Write user name and password to work file, will be used by authenticator during login.
                BufferedWriter workFileWriter = new BufferedWriter(new FileWriter("C:\\Users\\Henok\\IdeaProjects\\AccountingSoftware\\src\\Files\\_workFile.txt"));
                workFileWriter.write(workFileContentString); // rewriting the file with updated lastUsedId.
                workFileWriter.newLine();
                workFileWriter.write(userNameInput + ',' + userPasswordInput);
                workFileWriter.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Number of users is either invalid or already reached 1000...");
        }
    }

}
