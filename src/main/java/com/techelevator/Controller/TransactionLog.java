package com.techelevator.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class TransactionLog {

    /**
     * write into a file of required information.
     * @param logInformation the login information in its required format
     * @return void
     */
    public void writingLog(String logInformation){
        File logFile = new File("Log.txt");
        try(PrintWriter log = new PrintWriter(new FileOutputStream(logFile, true))){
            log.println(logInformation);
        } catch (FileNotFoundException e){
            System.out.println("Unable to open a log file: " + logFile.getAbsolutePath());
        }
    }

}
