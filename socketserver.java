import java.io.*;
import java.net.*;

public class socketserver{
       public static void main(String args[]){
		try(ServerSocket ss = new ServerSocket(8080)){
		
		DataInputStream in = null;
		while(true){
		try(Socket s = ss.accept()){
			in = new DataInputStream(s.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String m;

			while((m = reader.readLine()) != null && !m.isEmpty()){
				System.out.println(m);
			}

			PrintWriter write = new PrintWriter(s.getOutputStream());
			write.println("HTTP/1.1 200 OK");
			write.println("Content-Type: text/html");
			write.println();
			write.println("<h1>Hii we made a connection</h1>");
			write.flush();
		
			}
		}
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
       }

}
