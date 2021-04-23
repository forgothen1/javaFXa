package printMode;

import javafx.print.PrinterJob;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

public class PrintRun {

  PrinterJob printerJob = PrinterJob.createPrinterJob();
  String b = "leti leti izi";
  StringNode node = new StringNode();


    public void setPrinterJob(PrinterJob printerJob) {
      //  printerJob.printPage(b);
        this.printerJob = printerJob;
    }
}
