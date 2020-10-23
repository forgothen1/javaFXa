

/*class that geting  db , user and pass from file  class that will be probably to connect to files  import / export*/
package fechingData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FechingData {
    /**
     *
     * @param requarment
     * @param pathFile
     * @return
     */
 public String nesto(String requarment, String pathFile)
 {
     String sub = null;
     try {
         File myObj = new File(pathFile);
         Scanner myReader = new Scanner(myObj);
         while (myReader.hasNext()) {
             String line = myReader.nextLine();
             if (line.contains(requarment+"@")) {
                 int index = line.indexOf("@")+1;
                 sub= line.substring(index);
                 //   System.out.println(sub.trim());
             }
         }
     } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
     }
     return sub;
 }
}
