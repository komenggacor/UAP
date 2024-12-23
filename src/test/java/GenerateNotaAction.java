import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.List;

class GenerateNotaAction implements ActionListener {
    private final List<Order> orders;

    public GenerateNotaAction(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String fileName = "CoffeeShop_Nota.docx";
            XWPFDocument document = new XWPFDocument();

            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Coffee Shop Nota");
            titleRun.setBold(true);
            titleRun.setFontSize(20);
            title.setAlignment(ParagraphAlignment.CENTER);

            for (Order order : orders) {
                XWPFParagraph orderParagraph = document.createParagraph();
                XWPFRun orderRun = orderParagraph.createRun();
                orderRun.setText(order.toString());
            }

            try (FileOutputStream out = new FileOutputStream(fileName)) {
                document.write(out);
            }

            JOptionPane.showMessageDialog(null, "Nota saved as " + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error generating nota: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}