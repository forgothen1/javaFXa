package recordInfo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*idealogy for log stuff here shud be all stuff  determent for logging and all that*/

/**
 * record to log file  for connection , imput  just make it to look on difrent resourse file
 */
public class  RecordInfo {
    Logger logger = Logger.getLogger(RecordInfo.class);

    public Logger forConnection()
    {
        PropertyConfigurator.configure("resources/connection4j.properties");
        return logger;
    }
    public Logger imputOfStuff()
    {
        PropertyConfigurator.configure("resources/imputofstuff.properties");
        return logger;
    }
}
