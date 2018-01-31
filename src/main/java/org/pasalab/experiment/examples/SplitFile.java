package org.pasalab.experiment.examples;

import java.io.*;

public class SplitFile {
    public static void main(String[] args) {
        File inFile = new File(args[0]);
        String pre = args[1];
        int count = Integer.parseInt(args[2]);
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            int c = 0;
            String line = reader.readLine();
            File outFile = new File(pre + "/part_"+i);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            while (line != null) {
                if (c < count) {
                } else {
                    writer.close();
                    i++;
                    outFile = new File(pre + "/part_"+i);
                    writer = new BufferedWriter(new FileWriter(outFile));
                    c = 0;
                }
                writer.write(line + "\n");
                c++;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
