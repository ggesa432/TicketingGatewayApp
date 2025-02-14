package TicketingGateway.service;

import TicketingGateway.domain.Ticket;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;



/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/26/2024
 */
@Service
public class PdfGeneratorService {

    public String generatePdfSummary(Ticket ticket) {
        String filePath = "Ticket_Summary_" + ticket.getId() + ".pdf";

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Ticket Summary")
                    .setBold()
                    .setFontSize(20)
                    .setMarginBottom(20));

            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 7}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addCell("Field");
            table.addCell("Value");

            table.addCell("Ticket ID");
            table.addCell(String.valueOf(ticket.getId()));

            table.addCell("Department");
            table.addCell(ticket.getDepartment());

            table.addCell("Status");
            table.addCell(ticket.getStatus());

            table.addCell("Progress");
            table.addCell(ticket.getProgress());

            table.addCell("Priority");
            table.addCell(ticket.getPriority());

            table.addCell("Type");
            table.addCell(ticket.getType());

            table.addCell("Due Date");
            table.addCell(ticket.getDueDate().toString());

            table.addCell("Project");
            table.addCell(ticket.getProject());

            table.addCell("Description");
            table.addCell(ticket.getDescription());

            table.addCell("Amount");
            table.addCell(String.valueOf(ticket.getAmount()));

            document.add(table);

            document.close();
            pdf.close();

            System.out.println("PDF Summary generated at: " + filePath);
        } catch (Exception e) {
            System.err.println("Failed to generate PDF summary: " + e.getMessage());
        }

        return filePath;
    }
}

