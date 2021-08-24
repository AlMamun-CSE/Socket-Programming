import java.io.*;
import java.net.*;

public class Server{

    //Server Create Class reference
    ServerSocket server;
    //client reference
    Socket client;
    //reader and writer reference
    BufferedReader br;
    PrintWriter out;

    //Server Constructor
    public Server(){
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to acept connection");
            System.out.println("wating...");

            //server accept connection then return client type
            client = server.accept();// client a type ase client theke asa input connection 

            //then data read and write
            //toh client jeh input stream tah ase seta read korbo br diye
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());

            startingRead();
            startingWrite();


        } catch (IOException e) {
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
                        System.out.println("Client terminated the chat");
                        break;
                    }
                    
                    System.out.println("Client: "+msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

           

        };
        //thread start
        new Thread(r1).start();

    }

    public void startingWrite(){
        //ai thread tih user theke data nibe and send korbe client porjontoh
        Runnable r2 =()->{
            try {
                while(true){
                    System.out.println("writer started.");
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r2).start();
    }


    public static void main(String[]args){
        System.out.println("This is server.....go to server start.");
        new Server();
    }
}