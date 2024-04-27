package com.springboot.MiniProject.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class PDFUtil {

        private static final int BOUNDARY_LENGTH = 16;

    public static String createBoundary() {
        String boundary = "-------------------------" + System.currentTimeMillis();
        // Ajouter un journal pour afficher la valeur de la séquence générée
        System.out.println("Boundary generated: " + boundary);
        return boundary;
    }


    public static byte[] createBlankPage() throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage blankPage = new PDPage(PDRectangle.A4);
            document.addPage(blankPage);

            // Create a content stream for the blank page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, blankPage)) {
                // You can add any content to the page if needed
                // For example, you can add text
                contentStream.beginText();
                //contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("This is a blank page");
                contentStream.endText();
            }

            // Convert the document to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}
