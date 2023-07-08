package com.company.service;

import java.io.*;

public class FileServiceImpl implements IFileService{
    File myFile;

    public FileServiceImpl(File myFile) {
        this.myFile = myFile;
    }

    public void writeFile(String text) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.myFile);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println("Xatoli");
            e.printStackTrace();
        }

    }

    public String readFile() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(this.myFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String jsonText;
            while((jsonText = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
