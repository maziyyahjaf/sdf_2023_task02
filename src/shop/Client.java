package shop;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

    public static void main(String[] args) throws ClassNotFoundException {

        String name = "Fred";
        String email = "fred@gmail.com";

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
            Socket receiveProductInfo = new Socket(serverName, port);
            System.out.println("running on " + serverName);
            System.out.printf("connected to server\r\n");

            // getting the input stream
            InputStream is = receiveProductInfo.getInputStream();
            ObjectInputStream objectReader = new ObjectInputStream(is);

            Object receivedObject;
            int budget = 0; // Variable to store budget value
            String request_id = "";
            Product[] products = null; // Declare Product[] outside the loop

            // i want to receive the messages from server and show up on the client side

            while ((receivedObject = objectReader.readObject()) != null) {

                if (receivedObject instanceof String) {
                    String message = (String) receivedObject;
                    if (message.equals("end")) {
                        break;
                    }

                    if (message.startsWith("request_id:")) {
                        request_id = message.substring(11).trim();
                        System.out.println("request_id: " + request_id);
                    }

                    if (message.startsWith("budget:")) {
                        String budgetValue = message.substring(7).trim();
                        budget = Integer.parseInt(budgetValue);
                        System.out.println("budget: " + budget);
                    }

                }
                // If the object is an array of Product objects, process it
                else if (receivedObject instanceof Product[]) {
                    products = (Product[]) receivedObject; // Assign to products array
                    for (Product p : products) {
                        System.out.println("prod_start");
                        System.out.println(p);
                        System.out.println("prod_end");
                    }

                }
            }

            // Access the products array outside the loop
            // if (products != null) ensures that you only access the products array if it
            // has been assigned inside the loop
            // (i.e., it was received over the socket). This avoids a NullPointerException.
            // if (products != null) {
            //     System.out.println("Final list of products:");
            //     for (Product product : products) {
            //         System.out.println(product);
            //     }
            // }

            RespondToServer rts = new RespondToServer(products, budget);
            Product[] sortedProducts = rts.rankingProducts();
            List<Product> productSelected = rts.selectingProductsFromSorted(sortedProducts, budget);
            List<String> productIdSelected = rts.listingProductId(productSelected);
            Integer spent = rts.spent(productSelected);
            Integer remainingBudget = rts.remaining();

            // getting the output stream
            OutputStream os = receiveProductInfo.getOutputStream();
            ObjectOutputStream objectWriter = new ObjectOutputStream(os);

            objectWriter.writeObject("request_id: " + request_id + "\n");
            objectWriter.writeObject("name:" + name + "\n");
            objectWriter.writeObject("email:" + email + "\n");
            objectWriter.writeObject(productIdSelected);
            objectWriter.writeObject("spent: " + spent);
            objectWriter.writeObject("remaining:" + remainingBudget);
            objectWriter.writeObject("client_end");

            objectWriter.flush();

            // bw.close();
            // receiveProductInfo.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
