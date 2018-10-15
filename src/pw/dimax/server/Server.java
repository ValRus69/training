package pw.dimax.server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements Runnable{

    private static final int BUFFER_SIZE = 1024;
    private int port;
    private InetAddress host;
    private Selector selector;

    public static void main(String[] args) {


    }

    @Override
    public void run() {
        while(true) {

        }
    }

    public Server() throws IOException{
        this(InetAddress.getLocalHost(), 7272);
    }

    public Server(InetAddress host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    private Selector initSelector() throws IOException {

        // Create a new selector
        Selector socketSelector = Selector.open();

        // Create a new non-blocking server socket channel
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);

        // Bind the server socket to the specified address and port
        socketChannel.socket().bind( new InetSocketAddress(this.host, this.port) );





        return null;
    }
}
