package pw.dimax.client;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws InterruptedException {


        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream()) ) {

            Thread.sleep(2000);
            String t = in.readUTF();
            System.out.println("Client connected to socket.");

            while(!socket.isOutputShutdown()){

                if(br.ready()){

                    String clientCommand = br.readLine();

                    out.writeUTF(clientCommand);
                    out.flush();

                    System.out.println("Clien sent message " + clientCommand + " to server.");

                    if(clientCommand.equalsIgnoreCase("quit")){

                        if(in.read() > -1)     {
                            System.out.println("reading...");
                            String input = in.readUTF();
                            System.out.println(input);
                        }

                        break;
                    }

                    System.out.println(in.readUTF());

                    if(in.read() > 1)     {
                        String input = in.readUTF();
                        System.out.println(input);
                    }

                }
            }

            System.out.println("Closing connections & channels on clentSide - DONE.");



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("h");
            e.printStackTrace();
        }
    }

}

