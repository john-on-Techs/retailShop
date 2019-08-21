package com.jotech.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
    public static String readKeyboard(String prompt) {
        String text = "";
        System.out.print("Enter "+prompt+":");

        try  {
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            text = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

}
