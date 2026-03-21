import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket client = serverSocket.accept();

                // Handle each client in a new thread
                new Thread(new ClientHandler(client)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}