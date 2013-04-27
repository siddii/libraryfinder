package com.boxysystems.libraryfinder.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 5, 2007
 * Time: 11:33:51 PM
 */
public class FileUtil {

    public static void copy(File sourceFile, File destinationFile) throws IOException {
        FileReader reader = null;
        FileWriter writer = null;

        try {
            reader = new FileReader(sourceFile);
            writer = new FileWriter(destinationFile);

            int data = reader.read();
            while (data != -1) {
                writer.write(data);
                data = reader.read();
            }
        }
        finally {
            if (writer != null) {
                writer.close();
            }

            if (reader != null) {
                reader.close();
            }
        }
    }

    public static java.io.File createTempFile(File baseDir, String fileName) {
        File tempFile = new File(baseDir, fileName);
        tempFile.deleteOnExit();
        return tempFile;
    }
}
