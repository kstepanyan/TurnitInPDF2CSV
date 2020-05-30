package com.kstepanyan.TurnitInPDFReportProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TurnitInPDFReportProcessor {
    private CSVBuilder csvBuilder;
    private List<String[]> allSumbissionData;
    private String inputDir;
    private String outputDir;

    public TurnitInPDFReportProcessor(String inputDir, String outputDir){
       csvBuilder = new CSVBuilder(outputDir + "/TurnitInFeedback.csv");
       allSumbissionData = new ArrayList<String[]>();
       this.inputDir = inputDir;
       this.outputDir = outputDir;
    }

    public void startProcess(){
        String[] header = new String[] { "Submission ID", "Name", "Mark", "General Comment"};
        allSumbissionData.add(header);

        File dir = new File(inputDir);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                int extentionIndex = child.getName().lastIndexOf(".");
                if(extentionIndex == -1 || !child.getName().substring(extentionIndex).toLowerCase().equals(".pdf")) {
                    continue;
                }
                String[] paperData = processFile(dir.getName() + "/" + child.getName());
                allSumbissionData.add(paperData);
            }
            csvBuilder.writeDataToCSV(allSumbissionData);
        } else {
            if(!dir.isDirectory()){
                System.out.println("Not a directory");
            }
        }
    }


    public String[] processFile(String path){
        File file = new File(path);
        //File file = new File("input/17016238-inst001-exam_1782023_277174760.pdf");
        //File file = new File("input/timed_essay_1782023_1323348897.docx.pdf");
        FileObject fo = new FileObject(file);
        fo.init();
        String[] paperData = new String[4];
        List<String> lines = fo.getLines();
        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).contains("SUBMISSION ID")){
                paperData[0] = getSubmissionId(lines.get(i));
            }else if(lines.get(i).startsWith("by ")){
                paperData[1] = getName(lines.get(i));
            }else if(lines.get(i).contains("FINAL GRADE")){
                paperData[2] = getFinalGrade(lines.get(i+1));
            }else if(lines.get(i).contains("GENERAL COMMENTS")){
                String feedback = "";
                while (!lines.get(i+1).equals("PAGE 1")){
                    feedback += lines.get(i+1) + " ";
                    i++;
                }
                paperData[3] = feedback;
            }
            System.out.println(lines.get(i));
        }
        return paperData;
    }

    public String getSubmissionId(String submissionIdLine){
        return submissionIdLine.substring(14);
    }
    public String getName(String nameLine){
        return nameLine.substring(3);
    }
    public String getFinalGrade(String gradeLine){
        String[] grade = gradeLine.split("/");
        return grade[0];
    }

    public CSVBuilder getCsvBuilder(){
        return this.csvBuilder;
    }

    public List<String[]> getAllSumbissionData(){
        return this.allSumbissionData;
    }
}