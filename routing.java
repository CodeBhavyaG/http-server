import java.net.*;
import java.io.*;
import java.util.*;


class routing{
	public static void main(String[] args){
		try(ServerSocket ss = new ServerSocket(8080)){

			String m;
			List<String> list = new ArrayList<>();
			String[] header;
			while(true){
				try(Socket s = ss.accept()){
					
					DataInputStream input = new DataInputStream(s.getInputStream());
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					m = reader.readLine();
					header = m.split(" ");
					while((m = reader.readLine()) != null && !m.isEmpty()){
						list.add(m); 
					}
					System.out.println(header[0] + "on path" + header[1]);
				
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				writer.println("HTTP/1.2 200 OK");
				writer.println("Content-Type: text/html");
				writer.println();
				writer.println("<h1>Connected</h1>");
				writer.flush();

				}
				catch(IOException i ){System.out.println(i);}
			}	
			
		}
		catch(IOException i){ System.out.println(i);}		
	}
}
