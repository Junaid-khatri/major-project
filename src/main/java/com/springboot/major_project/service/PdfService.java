package com.springboot.major_project.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.major_project.entity.Order;
import com.springboot.major_project.model.request.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final OrderService orderService;

    public String pdfGenerator() throws FileNotFoundException {

        Path pdfPath = Paths.get("C:\\Users\\HP\\Documents\\NetBeansProjects\\spring-projects\\major_project\\reportstore\\report.pdf");
        FileOutputStream outputStream = new FileOutputStream(pdfPath.toFile());

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,outputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(20);
        Paragraph paragraph1 = new Paragraph("Auto generated database Order-details report",font);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new int [] {1,4,3,2});
        table.setSpacingBefore(5);

        font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Address",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("OrderDate",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount",font));
        table.addCell(cell);

        List<OrderDto> orderList = orderService.viewAll();
        for(OrderDto order: orderList){
            table.addCell(String.valueOf(order.getId()));
            table.addCell(order.getAddress());
            table.addCell(String.valueOf(order.getOrderDate()));
            table.addCell(String.valueOf(order.getAmount()));
        }

        document.add(table);
        document.close();
        return pdfPath.toString();
    }

}
