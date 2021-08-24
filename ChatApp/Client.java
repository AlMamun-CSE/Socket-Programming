import java.net.*;
import java.io.*;

public class Client {

    //create client
    Socket client;
    ServerSocket server;

    //reader and writer reference
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending request to server");
            client = new Socket("127.0.01",7777);
            System.out.println("Connection done.");



            //then data read and write
            //toh client jeh input stream tah ase seta read korbo br diye
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());

            startingRead();
            startingWrite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void startingRead(){

        // ai thread tih read korte thakbe data
        Runnable r1 = ()->{
            System.out.println("reader started.......");
            try {
                while(true){
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        client.close();
                        break;
                    }
                    
                    System.out.println("Server: "+msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connections loss");
            }

           

        };
        //thread start
        new Thread(r1).start();

    }

    public void startingWrite(){
        //ai thread tih user theke data nibe and send korbe client porjontoh
        Runnable r2 =()->{
            System.out.println("writer started.");
            try {
                while(!client.isClosed()){
                   
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        client.close();
                        break;
                    }

                }
            } catch (Exception e) {
               // e.printStackTrace();
               System.out.println("Connections close");
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
