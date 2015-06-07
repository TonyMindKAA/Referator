package edu.dashkevich.diploma;

import com.google.common.base.Preconditions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        App app = new App();
        List<String> xmlFileNames = app.getXMLFileNames("C:\\Users\\Mr.Green\\Desktop\\download\\XML");
        for(String name: xmlFileNames){
            System.out.println(name);
        }
    }

    public List<String> getXMLFileNames(String string) {
        Preconditions.checkNotNull(string,"Path can not be null");
        final File folder = new File(string);
        Preconditions.checkArgument(folder.isDirectory(), "It's not a directory!");
        return findXMLFilesInDirectory(folder);
    }

    private List<String> findXMLFilesInDirectory(File folder) {
        List<String> fileNames = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile() && fileEntry.getName().endsWith(".xml")) {
                fileNames.add(fileEntry.getName());
            } else {
                printLog(fileEntry);
            }
        }
        return fileNames;
    }

    private void printLog(File fileEntry) {
        if (fileEntry.isDirectory()) {
            System.out.println("Skip directory: " + fileEntry.getName());
        } else {
            System.out.println("It's not xml-file(skiped): " + fileEntry.getName());
        }
    }
}
