package vn.ptit;


import vn.ptit.controller.dao.ConnectionUtils;
import vn.ptit.controller.sockethandler.ClientHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.ptit.model.Match;
import vn.ptit.model.Room;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Cuong Pham
 */
public class ServerMain {

    public static List<ClientHandler> clientHandlers;
    public static List<Room> rooms;
    public static List<Match> matchs;
    public static ServerSocket serverSocket;
    public static Connection connection;

    public ServerMain() throws IOException, SQLException, ClassNotFoundException {
        clientHandlers = new ArrayList<>();
        rooms = new ArrayList<>();
        matchs = new ArrayList<>();
        
        connection = ConnectionUtils.getMyConnection();
        int port = 6969;
        serverSocket = new ServerSocket(port);
        System.out.println("Created Server at port " + port);
        
        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket, connection);
            clientHandlers.add(client);
            client.start();
            System.out.println("New client request received : " + socket);
        }
    }

    public static void main(String[] args) {
        try {
            ServerMain serverMain = new ServerMain();
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

}
