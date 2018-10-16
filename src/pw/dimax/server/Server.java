package pw.dimax.server;


import pw.dimax.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{

    private static final int BUFFER_SIZE = 1024 * 4;
    private int port;
    private InetAddress host;
    private Selector selector;

    private boolean quit = false;


    // The buffer into which we'll read data when it's available
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);


    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (Exception e) {
            Log.exception(e);
        }

    }



    public Server() throws IOException{
        this(InetAddress.getLocalHost(), 7272);
    }

    public Server(InetAddress host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.selector = this.initSelector();
    }



    @Override
    public void run() {
        while(!quit) try {

            // Wait for an event one of the registered channels
            //may be need to check return num? and continue loop, if it is == 0
            this.selector.select();


            // Iterate over the set of keys for which events are available
            Iterator selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = (SelectionKey) selectedKeys.next();
                selectedKeys.remove();

                if (!key.isValid()) continue;

                // Check what event is available and deal with it
                if (key.isAcceptable()) {
                    this.accept(key);
                } else if (key.isReadable()) {
                    this.read(key);
                } else if (key.isWritable()) {
                    this.write(key);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void accept(SelectionKey key) throws IOException {
        //For an accept to be pending the channel must be a server socket channel.
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        // Accept the connection and make it non-blocking
        SocketChannel socketChannel = serverSocketChannel.accept();
        Socket socket = socketChannel.socket(); //нахуа?
        socketChannel.configureBlocking(false);

        // Register the new SocketChannel with our Selector, indicating
        // we'd like to be notified when there's data waiting to be read
        socketChannel.register(selector, SelectionKey.OP_READ);

        Log.print("New client successfully connected.");
        //TODO: send some data to client
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        this.readBuffer.clear();

        // Attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);
        } catch (IOException e) {
            // The remote forcibly closed the connection, cancel
            // the selection key and close the channel.
            socketChannel.close();
            key.cancel();
            return;
        }

        if (numRead == -1) {
            // Remote entity shut the socket down cleanly. Do the
            // same from our end and cancel the key.
            socketChannel.close();
            key.cancel();
            return;
        }

        // Hand the data off to our worker thread
        //this.worker.processData(this, socketChannel, this.readBuffer.array(), numRead);
    }

    private void write(SelectionKey key) throws IOException {

    }



    private Selector initSelector() throws IOException {

        // Create a new selector
        Selector socketSelector = Selector.open();

        // Create a new non-blocking server socket channel
        // on which we'll accept connections
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);

        // Bind the server socket to the specified address and port
        socketChannel.socket().bind( new InetSocketAddress(this.host, this.port) );

        // Register the server socket channel, indicating an interest in
        // accepting new connections
        socketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }



    private void quit() {
        //TODO: close all resources
        quit = true;
    }
}
