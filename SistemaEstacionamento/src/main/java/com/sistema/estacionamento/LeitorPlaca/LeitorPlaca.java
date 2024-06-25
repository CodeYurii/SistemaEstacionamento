package com.sistema.estacionamento.LeitorPlaca;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class LeitorPlaca {

    public static void main(String[] args) {
        String imagePath = "C:\\m2\\SistemaEstacionamento\\SistemaEstacionamento\\src\\main\\resources\\Placas\\1.png";
        String placa = lerPlaca(imagePath);
    }

    public static String lerPlaca(String imagePath) {
        File imageFile = new File(imagePath);
        ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        //instance.setLanguage("por");
        instance.setTessVariable("user_defined_dpi", "300");
        try {
            String result = instance.doOCR(imageFile);
            return result.trim();
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
