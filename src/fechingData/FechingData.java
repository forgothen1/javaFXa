package fechingData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FechingData {
    public String fechingDB()
    { String sub = null;
        try {
            File myObj = new File("E://dev stuff/fileName.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String line = myReader.nextLine();
                if (line.contains("database@")) {
                    int index = line.indexOf("@")+1;
                     sub= line.substring(index);

                    System.out.println(sub.trim());

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return sub;
    }
    public String fechingPassowrd()
    {   String sub = null;

        try {
            File myObj = new File("E://dev stuff/fileName.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String line = myReader.nextLine();
                if (line.contains("password@")) {
                    int index = line.indexOf("@")+1;
                     sub= line.substring(index);

                    System.out.println(sub.trim());

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return sub;
    }
    public String fechingUser()
    {
        String sub = null;
        try {
            File myObj = new File("E://dev stuff/fileName.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String line = myReader.nextLine();
                if (line.contains("username@")) {
                    int index = line.indexOf("@")+1;
                     sub= line.substring(index).trim();

                    System.out.println(sub);

                }
            }
           
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return sub;
    }
}
