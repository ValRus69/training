package pw.dimax.client;

import java.io.*;
import java.net.*;

public class ClientSec {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Client side");


        Socket fromserver = null;

        System.out.println("Connecting to localhost");

        fromserver = new Socket(InetAddress.getLocalHost(),7272);
        BufferedReader in  = new
                BufferedReader(new
                InputStreamReader(fromserver.getInputStream()));
        PrintWriter    out = new
                PrintWriter(fromserver.getOutputStream(),true);
        BufferedReader inu = new
                BufferedReader(new InputStreamReader(System.in));

        String fuser,fserver;

        while ((fuser = inu.readLine())!=null) {
            out.println(fuser);
            fserver = in.readLine();
            System.out.println(fserver);
            if (fuser.equalsIgnoreCase("close")) break;
            if (fuser.equalsIgnoreCase("exit")) break;
        }

        out.close();
        in.close();
        inu.close();
        fromserver.close();
    }
}


