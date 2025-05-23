package config;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import javax.swing.JPanel;

public class PanelPrinter implements Printable {
    private final JPanel panel;

    public PanelPrinter(JPanel panel) {
        this.panel = panel;
    }

    public void printPanel() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        System.out.println("Calling print dialog...");
        boolean doPrint = job.printDialog();

        System.out.println("Print dialog returned: " + doPrint);

        if (doPrint) {
            try {
                job.print();
                System.out.println("Print job sent.");
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Print cancelled by user or no printers available.");
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
    if (pageIndex > 0) {
        return NO_SUCH_PAGE;
    }

    Graphics2D g2d = (Graphics2D) graphics;
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    double scaleX = pf.getImageableWidth() / panel.getWidth();
    double scaleY = pf.getImageableHeight() / panel.getHeight();
    double scale = Math.min(scaleX, scaleY);

    g2d.scale(scale, scale);
    panel.printAll(g2d);

    return PAGE_EXISTS;
}
}
