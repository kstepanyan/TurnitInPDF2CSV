package com.kstepanyan.TurnitInPDFReportProcessor;

import java.io.File;
import java.io.IOException;


public class ProcessorTest {

    public static void main(String args[]) throws IOException {
        TurnitInPDFReportProcessor pdfProcessor = new TurnitInPDFReportProcessor("input", "output/TurnitInFeedback.csv");

        File dir = new File("input");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String[] paperData = pdfProcessor.processFile(dir.getName() + "/" + child.getName());
                pdfProcessor.getAllSumbissionData().add(paperData);
            }
            pdfProcessor.getCsvBuilder().writeDataToCSV(pdfProcessor.getAllSumbissionData());
        } else {
            if(!dir.isDirectory()){
                System.out.println("Not a directory");
            }
        }

    }
}
