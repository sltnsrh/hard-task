package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String address = "127.0.0.1";
    private static final int port = 8090;

    public void runClient(RequestToServer requestToServer) {
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            //the loop is just for multithreading tests
            boolean keepSocket = true;
            while (keepSocket) {
                String jsonClientRequest = requestToServer.getStringRequest();
                output.writeUTF(jsonClientRequest);
                System.out.println("Sent: " + jsonClientRequest);
                String serverMsg = input.readUTF();
                System.out.println("Received: " + serverMsg);
                if (jsonClientRequest.toLowerCase().contains("exit")) {
                    keepSocket = false;
                }
                Thread.sleep(500);
            }
        } catch (IOException e) {
            throw new RuntimeException("Some problem with connection or input/output.", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
