package com.springboot.major_project.service;

import com.springboot.major_project.model.request.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    PdfService pdfService;

    @Test
    void testPdfGenerator() throws FileNotFoundException {
        List<OrderDto> mockOrderList = new ArrayList<>();
        mockOrderList.add(new OrderDto(1, "123 Test St.", new Date(), 100));
        mockOrderList.add(new OrderDto(2, "456 Test St.", new Date(), 150));
        when(orderService.viewAll()).thenReturn(mockOrderList);
        String pdfPathStr = pdfService.pdfGenerator();
        Path pdfPath = Path.of(pdfPathStr);
        assertTrue(Files.exists(pdfPath), "PDF file not exist");
    }
}