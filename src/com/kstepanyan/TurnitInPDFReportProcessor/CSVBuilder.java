package com.kstepanyan.TurnitInPDFReportProcessor;


import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVBuilder {

    private File file;
    private  FileWriter outputfile;
    public CSVBuilder(String filePath){
        try {
            File file = new File(filePath);
            outputfile = new FileWriter(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void writeDataToCSV(List<String[]> data) {

        try {
            // create FileWriter object with file as parameter

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            // create a List which contains String array
//            data.add(new String[] { "Name", "Class", "Marks" });
//            data.add(new String[] { "Aman", "10", "620" });
//            data.add(new String[] { "Suraj", "10", "630" });
            for(String[] paperEntry : data ){
                if(paperEntry != null) {

                    for (String field : paperEntry) {
                        System.out.print(field + " , ");
                    }
                    System.out.println();
                }
            }
            writer.writeAll(data);


            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
