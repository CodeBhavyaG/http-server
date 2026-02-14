import java.io.*;
import java.net.*;

public class test {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Open http://localhost:" + port + " in your browser.");

            while (true) {
                // Wait for a browser to connect
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("\n--- New Request Received ---");

                    // Read the raw input stream
                    InputStream input = clientSocket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    String line;
                    // Read and print headers until an empty line is reached
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        System.out.println(line);
                    }

                    // Send a basic HTTP response so the browser doesn't hang
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/plain");
                    out.println();
                    out.println("Hello! I received your raw request.");
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
