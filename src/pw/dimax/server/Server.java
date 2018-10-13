package pw.dimax.server;

import pw.dimax.util.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket server= new ServerSocket(3345)){
            Socket client = server.accept();

            System.out.println("Connection accepted.");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());

            out.writeUTF("rr");
            out.flush();

            //test callback
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while(!client.isClosed()){

                System.out.println("Server reading from channel");
                String entry = in.readUTF();

                System.out.println("READ from client message - " + entry);

                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - "+entry + " - OK");
                    out.flush();
                    break;
                }


                if(br.ready()){
                    String s = br.readLine();
                    out.writeUTF(s);
                    Log.print(s);
                   // out.flush();
                }

                out.writeUTF("555 OK");
                //out.flush();

            }


            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            client.close();


            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
