package shop;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static shop.LoadingData.initProductInfo;
//import shop.ListProductInfo;

public class Server {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

        initProductInfo("data/products.csv");
        ListProductInfo lpi = new ListProductInfo();

        int budget = 300;
        String request_id = "12345"; // example initialization
        int item_count = lpi.determineItemCount();
        Product[] sendToUser = lpi.infoSendToUser(item_count);
        // RespondToServer rts = new RespondToServer(sendToUser, budget);

        // default port
        int port = 3000;
        // default serverName (host)
        String serverName = "localhost";

        if (args.length > 1) {
            serverName = args[0];
            port = Integer.parseInt(args[1]);

        } else if (args.length > 0) {
            port = Integer.parseInt(args[0]);

        }

        try {
            ServerSocket sendProductInfo = new ServerSocket(port);
            System.out.printf("Listening on port %d\r\n", port);
            System.out.println("running on " + serverName);

            System.out.println("Waiting for connection");

            Socket connectedToClient = sendProductInfo.accept();
            System.out.println("Got a client connection");

            // Getting the output stream
            OutputStream os = connectedToClient.getOutputStream();
            // OutputStreamWriter writer = new OutputStreamWriter(os);
            // BufferedWriter bw = new BufferedWriter(writer);
            ObjectOutputStream objectWriter = new ObjectOutputStream(os);

            // this is how you send multiple lines of text
            objectWriter.writeObject("request_id: " + request_id + "\n");
            objectWriter.writeObject("item_count: " + item_count + "\n");
            objectWriter.writeObject("budget: " + budget + "\n");
            objectWriter.writeObject(sendToUser);
            objectWriter.writeObject("end");

            // for (Product p : sendToUser) {
            // bw.write("prod_start" + "\n");
            // bw.write(p.toString() + "\n");
            // bw.write("prod_end" + "\n");
            // }

            objectWriter.flush();
            // bw.close();

            // // Getting the input stream
            InputStream is = connectedToClient.getInputStream();
            ObjectInputStream objectReader = new ObjectInputStream(is);

            Object receivedObject;
            List<String> selectedProductId = null; // Variable to store received List<String>

            while ((receivedObject = objectReader.readObject()) != null) {

                if (receivedObject instanceof String) {
                    String message = (String) receivedObject;
                    if (message.equals("client_end")) {
                        break;
                    }

                    if (message.startsWith("request_id:")) {
                        request_id = message.substring(11).trim();
                        System.out.println("request_id: " + request_id);
                    }

                    if (message.startsWith("name:")) {
                        String name = message.substring(5).trim();
                        System.out.println("name: " + name);
                    }

                    if (message.startsWith("email:")) {
                        String email = message.substring(6).trim();
                        System.out.println("email: " + email);
                    }

                    if (message.startsWith("spent:")) {
                        String spentValue = message.substring(6).trim();
                        Integer spent = Integer.parseInt(spentValue);
                        System.out.println("spent: " + spent);
                    }

                    if (message.startsWith("remaining:")) {
                        String remainingValue = message.substring(10).trim();
                        Integer remaining = Integer.parseInt(remainingValue);
                        System.out.println("remaining: " + remaining);
                    }


                }
                // check if the object is a List<String>
                else if (receivedObject instanceof List<?>) {
                    // check the content of the list to ensure it's a List<String>
                    List<?> receivedList = (List<?>) receivedObject;
                    // Since Java has type erasure at runtime, we can't directly check instanceof List<String>, 
                    // so we check the list's first element to confirm it's a String before casting.
                    if (!receivedList.isEmpty() && receivedList.get(0) instanceof String) {
                        selectedProductId = (List<String>) receivedObject; // Safe to cast to List<String>
                        for (String productId : selectedProductId) {
                            System.out.println(productId);
                            
                        }
                    } 

                }

            
            }

            // sendProductInfo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
