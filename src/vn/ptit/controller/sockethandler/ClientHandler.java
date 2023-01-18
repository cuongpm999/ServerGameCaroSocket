/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.controller.sockethandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.MathContext;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.ptit.ServerMain;
import vn.ptit.controller.dao.DAOMatch;
import vn.ptit.controller.dao.DAOPlayer;
import vn.ptit.model.Board;
import vn.ptit.model.ChatItem;
import vn.ptit.model.Friend;
import vn.ptit.model.FriendInvite;
import vn.ptit.model.Match;
import vn.ptit.model.Move;
import vn.ptit.model.ObjectWrapper;
import vn.ptit.model.Player;
import vn.ptit.model.PlayerInMatch;
import vn.ptit.model.PlayerStat;
import vn.ptit.model.Room;
import vn.ptit.model.RoomInvite;
import vn.ptit.utils.StreamData;
import vn.ptit.utils.RandomString;

/**
 *
 * @author Cuong Pham
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Connection conn;
    private DAOPlayer dAOPlayer;
    private DAOMatch dAOMatch;
    private Player player;
    private String idRoom;
    private boolean checkFind = true;

    public ClientHandler(Socket socket, Connection conn) throws IOException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        this.conn = conn;
        dAOPlayer = new DAOPlayer(conn);
        dAOMatch = new DAOMatch(conn);

        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Type.CONNECT_SERVER.name(), null);
        sendData(objectWrapper);

    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectWrapper objectWrapper = new ObjectWrapper();
                objectWrapper = (ObjectWrapper) objectInputStream.readObject();
                String received = objectWrapper.getIdentifier();
                StreamData.Type type = StreamData.getTypeFromData(received);
                switch (type) {
                    case CONNECT_SERVER:
                        onReceiveConnectServer(objectWrapper);
                        break;
                    case SIGNUP:
                        onReceiveSignup(objectWrapper);
                        break;
                    case LOGIN:
                        onReceiveLogin(objectWrapper);
                        break;
                    case LIST_ONLINE:
                        onReceiveListOnline(objectWrapper);
                        break;
                    case CREATE_ROOM:
                        onReceiveCreateRoom(objectWrapper);
                        break;
                    case LIST_ROOM:
                        onReceiveListRoom(objectWrapper);
                        break;
                    case JOIN_ROOM:
                        onReceiveJoinRoom(objectWrapper);
                        break;
                    case SEND_INVITE_FRIEND:
                        onReceiveSendInviteFriend(objectWrapper);
                        break;
                    case ACCEPT_INVITE_FRIEND:
                        onReceiveAcceptInviteFriend(objectWrapper);
                        break;
                    case LIST_FRIEND:
                        onReceiveListFriend(objectWrapper);
                        break;
                    case CHECK_FRIEND:
                        onReceiveCheckFriend(objectWrapper);
                        break;
                    case SEND_INVITE_ROOM:
                        onReceiveSendInviteRoom(objectWrapper);
                        break;
                    case CHAT_ROOM:
                        onReceiveChatRoom(objectWrapper);
                        break;
                    case START:
                        onReceiveGameStart(objectWrapper);
                        break;
                    case MOVE:
                        onReceiveMove(objectWrapper);
                        break;
                    case LEAVE_ROOM:
                        onReceiveLeaveRoom(objectWrapper);
                        break;
                    case PLAYER_STAT:
                        onReceiveStat(objectWrapper);
                        break;
                    case LOGOUT:
                        onReceiveLogout(objectWrapper);
                        break;
                    case UPDATE_PROFILE:
                        onReceiveUpdateProfile(objectWrapper);
                        break;
                    case FIND_MATCH:
                        onReceiveFindMatch(objectWrapper);
                        break;
                    case ACCEPT_FIND_MATCH:
                        onReceiveAcceptFindMatch(objectWrapper);
                        break;
                }
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }

        }
    }

    private void onReceiveConnectServer(ObjectWrapper objectWrapper) {
        sendData(objectWrapper);
    }

    private void onReceiveSignup(ObjectWrapper objectWrapper) {
        Player player = new Player();
        player = (Player) objectWrapper.getObject();
        int flag = dAOPlayer.insert(player);
        if (flag > 0) {
            objectWrapper = new ObjectWrapper(StreamData.Type.SIGNUP.name() + ";" + "success", null);
            sendData(objectWrapper);
        } else {
            objectWrapper = new ObjectWrapper(StreamData.Type.SIGNUP.name() + ";" + "falied", null);
            sendData(objectWrapper);
        }
    }

    private void sendData(ObjectWrapper objectWrapper) {
        try {
            objectOutputStream.writeObject(objectWrapper);
            objectOutputStream.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void onReceiveLogin(ObjectWrapper objectWrapper) {
        Player player = new Player();
        player = (Player) objectWrapper.getObject();
        player = dAOPlayer.checkLogin(player.getUsername(), player.getPassword());
        if (player != null) {
            List<ClientHandler> clients = ServerMain.clientHandlers;
            for (ClientHandler clientHandler : clients) {
                if (clientHandler.player != null && clientHandler.player.getUsername().equalsIgnoreCase(player.getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.LOGIN.name() + ";" + "falied", null);
                    sendData(objectWrapper);
                    return;
                }
            }
            player.setStatus("Trực tuyến");
            this.player = player;
            objectWrapper = new ObjectWrapper(StreamData.Type.LOGIN.name() + ";" + "success", player);
            sendData(objectWrapper);
        } else {
            objectWrapper = new ObjectWrapper(StreamData.Type.LOGIN.name() + ";" + "falied", null);
            sendData(objectWrapper);
        }
    }

    private void onReceiveListOnline(ObjectWrapper objectWrapper) {
        List<ClientHandler> clients = ServerMain.clientHandlers;
        List<Player> players = new ArrayList<>();
        for (ClientHandler client : clients) {
            if (client.player != null) {
                Player player_ = createPlayerToSend(client.player);
                players.add(player_);
            }
        }
        List<Player> listP = getListPlayerToSend();
        for (ClientHandler client : clients) {
            if (client.player != null) {
                for (Player p : listP) {
                    if(client.player.getUsername().equalsIgnoreCase(p.getUsername())) break;
                }
                objectWrapper = new ObjectWrapper(StreamData.Type.LIST_ONLINE.name(), players);
                client.sendData(objectWrapper);
            }
        }

    }

    private void onReceiveCreateRoom(ObjectWrapper objectWrapper) {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.digits);
        String randomId = randomString.nextString();
        List<Player> playersInRoom = new ArrayList<>();
        playersInRoom.add(this.player);
        Room room = new Room(randomId, new Date(), this.player, playersInRoom, "1/2");
        this.player.setStatus("Trong phòng");
        ServerMain.rooms.add(room);
        idRoom = randomId;
        objectWrapper = new ObjectWrapper(StreamData.Type.CREATE_ROOM.name(), room);
        sendData(objectWrapper);

        String msg = this.player.getUsername() + ";đã tạo phòng";
        statusToChat(msg);
    }

    private void onReceiveListRoom(ObjectWrapper objectWrapper) {
        List<ClientHandler> clients = ServerMain.clientHandlers;
        List<Room> rooms = new ArrayList<>();
        for (Room room : ServerMain.rooms) {
            if (!room.getPlayers().isEmpty()) {
                Room r = createRoomToSend(room);
                rooms.add(r);
            }
        }
        
        List<Player> listP = getListPlayerToSend();
        for (ClientHandler client : clients) {
            if (client.player != null) {
                for (Player p : listP) {
                    if(client.player.getUsername().equalsIgnoreCase(p.getUsername())) break;
                }
                objectWrapper = new ObjectWrapper(StreamData.Type.LIST_ROOM.name(), rooms);
                client.sendData(objectWrapper);
            }
        }
    }

    private void onReceiveJoinRoom(ObjectWrapper objectWrapper) {
        List<Room> rooms = ServerMain.rooms;
        List<ClientHandler> clients = ServerMain.clientHandlers;

        for (Room r : rooms) {
            if (r.getId().equalsIgnoreCase(objectWrapper.getObject().toString())) {
                if (r.getPlayers().size() < 2) {
                    player.setStatus("Trong phòng");
                    r.getPlayers().add(player);
                    r.setStatus("2/2");
                    idRoom = objectWrapper.getObject().toString();
                    Room room = createRoomToSend(r);
                    objectWrapper = new ObjectWrapper(StreamData.Type.JOIN_ROOM.name() + ";" + "success", room);
                    this.sendData(objectWrapper);
                    for (ClientHandler client : clients) {
                        if (client.player != null && client.player.getUsername().equalsIgnoreCase(room.getPlayers().get(0).getUsername())) {
                            client.sendData(objectWrapper);
                            break;
                        }
                    }

                    String msg = this.player.getUsername() + ";đã vào phòng";
                    statusToChat(msg);
                } else {
                    objectWrapper = new ObjectWrapper(StreamData.Type.JOIN_ROOM.name() + ";" + "falied", null);
                    sendData(objectWrapper);
                }
                break;
            }
        }

    }

    private void onReceiveSendInviteFriend(ObjectWrapper objectWrapper) {
        String username = (String) objectWrapper.getObject();
        List<ClientHandler> clients = ServerMain.clientHandlers;
        for (ClientHandler client : clients) {
            if (client.player != null && client.player.getUsername().equalsIgnoreCase(username)) {
                objectWrapper = new ObjectWrapper(StreamData.Type.SEND_INVITE_FRIEND.name(), createPlayerToSend(this.player));
                client.sendData(objectWrapper);
                break;
            }
        }
    }

    private Player createPlayerToSend(Player player) {
        Player playerSend = new Player(player.getImg(), player.getUsername(), player.getPassword(), player.getFullName(), player.getStatus());
        playerSend.setId(player.getId());
        return playerSend;
    }

    private Friend createFriendToSend(Friend friend) {
        Friend friendSend = new Friend(createPlayerToSend(friend.getPlayer()), createPlayerToSend(friend.getFriendPlayer()));
        return friendSend;
    }

    private Room createRoomToSend(Room room) {
        List<Player> playerSends = new ArrayList<>();
        for (Player player : room.getPlayers()) {
            Player playerSend = new Player(player.getImg(), player.getUsername(), player.getPassword(), player.getFullName(), player.getStatus());
            playerSend.setId(player.getId());
            playerSends.add(playerSend);
        }
        Room roomSend = new Room(room.getId(), room.getCreateAt(), room.getCreateBy(), playerSends, room.getStatus());

        return roomSend;
    }

    private Board createBoardToSend(Board board) {
        String[][] pieces = new String[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                pieces[i][j] = board.getPieces()[i][j];
            }
        }
        Board boardSend = new Board(pieces);
        return boardSend;
    }

    private Move createMoveToSend(Move move) {
        if (move != null) {
            Move moveSend = new Move(move.getX(), move.getY());
            return moveSend;
        }
        return null;
    }

    private List<PlayerInMatch> createListPlayerInMatchToSend(List<PlayerInMatch> playerInMatchs) {
        List<PlayerInMatch> playerInMatchToSends = new ArrayList<>();
        for (PlayerInMatch playerInMatch : playerInMatchs) {
            PlayerInMatch playerInMatchToSend = new PlayerInMatch(playerInMatch.getResult(), createPlayerToSend(playerInMatch.getPlayer()), createMoveToSend(playerInMatch.getMove()));
            playerInMatchToSends.add(playerInMatchToSend);
        }
        return playerInMatchToSends;

    }

    private Match createMatchToSend(Match match) {
        Match matchSend = new Match(match.getStartAt(), null, createListPlayerInMatchToSend(match.getPlayerInMatchs()), createRoomToSend(match.getRoom()), createBoardToSend(match.getBoard()), null);
        return matchSend;
    }

    private void onReceiveAcceptInviteFriend(ObjectWrapper objectWrapper) {
        FriendInvite friendInvite = (FriendInvite) objectWrapper.getObject();
        if (friendInvite.isStatus()) {
            Friend friend = new Friend(friendInvite.getPlayer(), this.player);
            dAOPlayer.insertFriend(friend);
            friend = new Friend(this.player, friendInvite.getPlayer());
            dAOPlayer.insertFriend(friend);

            List<ClientHandler> clients = ServerMain.clientHandlers;
            for (ClientHandler client : clients) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(friendInvite.getPlayer().getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.ACCEPT_INVITE_FRIEND.name() + ";" + "success", createPlayerToSend(this.player));
                    client.sendData(objectWrapper);
                    break;
                }
            }
        } else {
            List<ClientHandler> clients = ServerMain.clientHandlers;
            for (ClientHandler client : clients) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(friendInvite.getPlayer().getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.ACCEPT_INVITE_FRIEND.name() + ";" + "falied", createPlayerToSend(this.player));
                    client.sendData(objectWrapper);
                    break;
                }
            }
        }

    }

    private void onReceiveListFriend(ObjectWrapper objectWrapper) {
        List<Friend> friends = dAOPlayer.findAllFriendByPlayer(this.player.getId());
        List<ClientHandler> clients = ServerMain.clientHandlers;
        for (Friend friend : friends) {
            int flag = 0;
            for (ClientHandler client : clients) {
                if (client.player != null && friend.getFriendPlayer().getUsername().equalsIgnoreCase(client.player.getUsername())) {
                    friend.getFriendPlayer().setStatus(client.player.getStatus());
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                friend.getFriendPlayer().setStatus("Ngoại tuyến");
            }
        }

        objectWrapper = new ObjectWrapper(StreamData.Type.LIST_FRIEND.name(), friends);
        sendData(objectWrapper);
    }

    private void onReceiveCheckFriend(ObjectWrapper objectWrapper) {
        List<ClientHandler> clients = ServerMain.clientHandlers;
        List<Player> listP = getListPlayerToSend();
        for (ClientHandler client : clients) {
            if (client.player != null) {
                for (Player p : listP) {
                    if(client.player.getUsername().equalsIgnoreCase(p.getUsername())) break;
                }
                client.sendData(new ObjectWrapper(StreamData.Type.CHECK_FRIEND.name(), null));
            }
        }
    }

    private void onReceiveSendInviteRoom(ObjectWrapper objectWrapper) {
        String username = (String) objectWrapper.getObject();
        List<ClientHandler> clients = ServerMain.clientHandlers;
        for (ClientHandler client : clients) {
            if (client.player != null && client.player.getUsername().equalsIgnoreCase(username)) {
                objectWrapper = new ObjectWrapper(StreamData.Type.SEND_INVITE_ROOM.name(), new RoomInvite(idRoom, player.getUsername(), true));
                client.sendData(objectWrapper);
                break;
            }
        }
    }

    private void onReceiveChatRoom(ObjectWrapper objectWrapper) {
        String chatMsg = (String) objectWrapper.getObject();
        System.out.println("Test chat " + chatMsg);
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        ChatItem chatItem = new ChatItem(sdf.format(date), this.player.getUsername(), chatMsg);
        List<ClientHandler> clients = ServerMain.clientHandlers;
        List<Player> players = new ArrayList<>();
        players = findRoomById(idRoom).getPlayers();
        for (ClientHandler client : clients) {
            for (Player player1 : players) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.CHAT_ROOM.name(), chatItem);
                    client.sendData(objectWrapper);
                    break;
                }
            }

        }
    }

    private void statusToChat(String chatMsg) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        ChatItem chatItem = new ChatItem(sdf.format(date), chatMsg.split("\\;")[0], chatMsg.split("\\;")[1]);
        List<ClientHandler> clients = ServerMain.clientHandlers;
        List<Player> players = new ArrayList<>();
        players = findRoomById(idRoom).getPlayers();
        for (ClientHandler client : clients) {
            for (Player player1 : players) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                    ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Type.CHAT_ROOM.name(), chatItem);
                    client.sendData(objectWrapper);
                    break;
                }
            }

        }
    }

    private Room findRoomById(String id) {
        List<Room> rooms = ServerMain.rooms;
        for (Room room : rooms) {
            if (room.getId().equalsIgnoreCase(id)) {
                return room;
            }
        }
        return null;
    }

    private Match findMatchByRoomId(String id) {
        List<Match> matchs = ServerMain.matchs;
        for (Match match : matchs) {
            if (match.getRoom().getId().equalsIgnoreCase(id)) {
                return match;
            }
        }
        return null;
    }

    private void onReceiveGameStart(ObjectWrapper objectWrapper) {
        System.out.println("Size match " + ServerMain.matchs.size());
        Match matchCheck = findMatchByRoomId(idRoom);
        if (matchCheck != null) {
            ServerMain.matchs.remove(matchCheck);
        }
        System.out.println("Size match " + ServerMain.matchs.size());
        List<Player> players = new ArrayList<>();
        players = findRoomById(idRoom).getPlayers();
        List<PlayerInMatch> playerInMatchs = new ArrayList<>();
        for (Player player_ : players) {
            PlayerInMatch playerInMatch = new PlayerInMatch("Tiếp tục", player_, null);
            playerInMatchs.add(playerInMatch);
        }

        String pieces[][] = new String[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                pieces[i][j] = "...";
            }
        }

        Match match = new Match(new Date(), null, playerInMatchs, findRoomById(idRoom), new Board(pieces), null);
        ServerMain.matchs.add(match);
        System.out.println("Test match " + match.getRoom().getPlayers().size());

        List<ClientHandler> clients = ServerMain.clientHandlers;
        for (ClientHandler client : clients) {
            for (Player player1 : players) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.START.name(), createMatchToSend(findMatchByRoomId(idRoom)));
                    client.sendData(objectWrapper);
                    break;
                }
            }

        }

        String msg = this.player.getUsername() + ";game bắt đầu";
        statusToChat(msg);
    }

    private void onReceiveMove(ObjectWrapper objectWrapper) {
        Move move = (Move) objectWrapper.getObject();
        Match match = findMatchByRoomId(idRoom);
        match.getBoard().getPieces()[move.getX()][move.getY()] = this.player.getUsername();
        System.out.println("Test move " + match.getBoard().getPieces()[move.getX()][move.getY()]);

        boolean flagWin = checkWin(move.getX(), move.getY(), match.getBoard().getPieces());
        boolean flagHoa = checkHoa(match.getBoard().getPieces());

        if (flagWin) {
            String msg = this.player.getUsername() + ";THẮNG";
            statusToChat(msg);
            match.setEndAt(new Date());
            for (PlayerInMatch playerInMatch : match.getPlayerInMatchs()) {
                if (playerInMatch.getPlayer().getUsername().equalsIgnoreCase(this.player.getUsername())) {
                    playerInMatch.setMove(move);
                    playerInMatch.setResult("THẮNG");
                } else {
                    playerInMatch.setResult("THUA");
                    playerInMatch.setMove(null);
                }
            }

            List<ClientHandler> clients = ServerMain.clientHandlers;
            List<Player> players = new ArrayList<>();
            players = findRoomById(idRoom).getPlayers();
            for (ClientHandler client : clients) {
                for (Player player1 : players) {
                    if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                        objectWrapper = new ObjectWrapper(StreamData.Type.MOVE.name(), createMatchToSend(match));
                        client.sendData(objectWrapper);
                        break;
                    }
                }

            }
            dAOMatch.insert(match);
            ServerMain.matchs.remove(match);
        } else if (flagHoa) {
            String msg = match.getRoom().getPlayers().get(0).getUsername() + " - " + match.getRoom().getPlayers().get(1).getUsername() + ";HÒA";
            statusToChat(msg);
            match.setEndAt(new Date());
            for (PlayerInMatch playerInMatch : match.getPlayerInMatchs()) {
                if (playerInMatch.getPlayer().getUsername().equalsIgnoreCase(this.player.getUsername())) {
                    playerInMatch.setMove(move);
                    playerInMatch.setResult("HÒA");
                } else {
                    playerInMatch.setResult("HÒA");
                    playerInMatch.setMove(null);
                }
            }

            List<ClientHandler> clients = ServerMain.clientHandlers;
            List<Player> players = new ArrayList<>();
            players = findRoomById(idRoom).getPlayers();
            for (ClientHandler client : clients) {
                for (Player player1 : players) {
                    if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                        objectWrapper = new ObjectWrapper(StreamData.Type.MOVE.name(), createMatchToSend(match));
                        client.sendData(objectWrapper);
                        break;
                    }
                }

            }
            dAOMatch.insert(match);
            ServerMain.matchs.remove(match);

        } else {
            for (PlayerInMatch playerInMatch : match.getPlayerInMatchs()) {
                if (playerInMatch.getPlayer().getUsername().equalsIgnoreCase(this.player.getUsername())) {
                    playerInMatch.setMove(move);
                    playerInMatch.setResult("Tiếp tục");
                } else {
                    playerInMatch.setResult("Tiếp tục");
                    playerInMatch.setMove(null);
                }
            }

            List<ClientHandler> clients = ServerMain.clientHandlers;
            List<Player> players = new ArrayList<>();
            players = findRoomById(idRoom).getPlayers();
            for (ClientHandler client : clients) {
                for (Player player1 : players) {
                    if (client.player != null && client.player.getUsername().equalsIgnoreCase(player1.getUsername())) {
                        objectWrapper = new ObjectWrapper(StreamData.Type.MOVE.name(), createMatchToSend(match));
                        client.sendData(objectWrapper);
                        break;
                    }
                }

            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(match.getBoard().getPieces()[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkWin(int i, int j, String pieces[][]) {
        System.out.println(i + " " + j);
        int d = 0, k = i, h;
        // kiểm tra hàng

        while (pieces[k][j].equalsIgnoreCase(pieces[i][j])) {
            System.out.println(k);
            d++;
            k++;
            if (k > 15) {
                break;
            }
        }

        if (i >= 1) {
            k = i - 1;
            while (pieces[k][j].equalsIgnoreCase(pieces[i][j])) {
                d++;
                k--;
                if (k < 0) {
                    break;
                }
            }
        }
        System.out.println(d + "Đếm");
        if (d > 4) {
            return true;
        }
        d = 0;
        h = j;
        // kiểm tra cột

        while (pieces[i][h].equalsIgnoreCase(pieces[i][j])) {
            d++;
            h++;
            if (h > 15) {
                break;
            }
        }
        if (j >= 1) {
            h = j - 1;
            while (pieces[i][h].equalsIgnoreCase(pieces[i][j])) {
                d++;
                h--;
                if (h < 0) {
                    break;
                }
            }
        }
        System.out.println(d + "Đếm");
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;

        while (pieces[i][j].equalsIgnoreCase(pieces[h][k])) {
            d++;
            h++;
            k++;
            if (h > 15) {
                break;
            }
            if (k > 15) {
                break;
            }
        }
        if (i >= 1 && j >= 1) {
            h = i - 1;
            k = j - 1;
            while (pieces[i][j].equalsIgnoreCase(pieces[h][k]) && h >= 0 && k >= 0) {
                d++;
                h--;
                k--;
                if (h < 0) {
                    break;
                }
                if (k < 0) {
                    break;
                }
            }
        }
        System.out.println(d + "Đếm");
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;

        while (pieces[i][j].equalsIgnoreCase(pieces[h][k])) {
            d++;
            h++;
            k--;
            if (h > 15) {
                break;
            }
            if (k < 0) {
                break;
            }
        }
        if (i >= 1 && j <= 14) {
            h = i - 1;
            k = j + 1;
            System.out.println(h + " " + k);
            while (pieces[i][j].equalsIgnoreCase(pieces[h][k])) {
                d++;
                h--;
                k++;
                if (h < 0) {
                    break;
                }
                if (k > 15) {
                    break;
                }
            }
        }
        System.out.println(d + "Đếm");
        if (d > 4) {
            return true;
        }
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;

    }

    private boolean checkHoa(String pieces[][]) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (pieces[i][j].equalsIgnoreCase("...")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void onReceiveLeaveRoom(ObjectWrapper objectWrapper) {
        this.checkFind = true;
        Room room = findRoomById(idRoom);

        for (int i = 0; i < room.getPlayers().size(); i++) {
            if (room.getPlayers().get(i).getUsername().equalsIgnoreCase(this.player.getUsername())) {
                room.getPlayers().remove(this.player);
            }
        }
        if (room.getPlayers().isEmpty()) {
            ServerMain.rooms.remove(room);
            objectWrapper = new ObjectWrapper(StreamData.Type.LEAVE_ROOM.name(), null);
            this.player.setStatus("Trực tuyến");
            sendData(objectWrapper);
        } else {
            this.player.setStatus("Trực tuyến");
            room.setCreateBy(room.getPlayers().get(0));
            room.setStatus("1/2");
            String msg = this.player.getUsername() + ";đã thoát phòng";
            statusToChat(msg);

            objectWrapper = new ObjectWrapper(StreamData.Type.LEAVE_ROOM.name(), null);
            sendData(objectWrapper);

            List<ClientHandler> clients = ServerMain.clientHandlers;
            List<Player> players = new ArrayList<>();
            players = room.getPlayers();
            for (ClientHandler client : clients) {
                if (client.player != null && client.player.getUsername().equalsIgnoreCase(room.getPlayers().get(0).getUsername())) {
                    objectWrapper = new ObjectWrapper(StreamData.Type.LEAVE_ROOM.name(), room);
                    client.sendData(objectWrapper);
                    break;
                }

            }

        }
        idRoom = null;
    }

    private void onReceiveStat(ObjectWrapper objectWrapper) {
        List<PlayerStat> playerStats = new ArrayList<>();
        playerStats = dAOPlayer.selectAllStat();
        objectWrapper = new ObjectWrapper(StreamData.Type.PLAYER_STAT.name(), playerStats);
        sendData(objectWrapper);
    }

    private void onReceiveLogout(ObjectWrapper objectWrapper) {
        this.player = null;
        objectWrapper = new ObjectWrapper(StreamData.Type.LOGOUT.name(), null);
        sendData(objectWrapper);
    }

    private void onReceiveUpdateProfile(ObjectWrapper objectWrapper) {
        Player player = new Player();
        player = (Player) objectWrapper.getObject();
        dAOPlayer.update(player);
        if (player != null) {
            this.player = player;
            objectWrapper = new ObjectWrapper(StreamData.Type.UPDATE_PROFILE.name() + ";" + "success", player);
            sendData(objectWrapper);
        } else {
            objectWrapper = new ObjectWrapper(StreamData.Type.UPDATE_PROFILE.name() + ";" + "falied", null);
            sendData(objectWrapper);
        }
    }

    private void onReceiveFindMatch(ObjectWrapper objectWrapper) {
        System.out.println("Client --- " + checkFind);
        this.player.setStatus("Đang tìm trận");
        onReceiveListOnline(objectWrapper);
        onReceiveCheckFriend(objectWrapper);

        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.digits);
        String randomId = randomString.nextString();
        List<Player> players = new ArrayList<>();
        Room room = new Room(randomId, null, null, players, null);
        ServerMain.rooms.add(room);
        String username = null;
        int cnt = 15;
        while (username == null) {
            List<Player> list = getListPlayer();
            for (Player player_ : list) {
                objectWrapper = new ObjectWrapper(StreamData.Type.TIME_FIND_MATCH.name(), cnt);
                sendData(objectWrapper);
                cnt--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (cnt == 0) {
                    this.player.setStatus("Trực tuyến");
                    objectWrapper = new ObjectWrapper(StreamData.Type.TIME_FIND_MATCH.name(), cnt);
                    sendData(objectWrapper);

                    onReceiveListOnline(objectWrapper);
                    onReceiveCheckFriend(objectWrapper);
                    return;
                }
                if (!checkFind) {
                    this.checkFind = true; 
                    return;
                }
                System.out.println("Check tìm trận...");
                if (!player_.getUsername().equalsIgnoreCase(this.player.getUsername())) {
                    if (player_.getStatus().equalsIgnoreCase("Đang tìm trận")) {
                        username = player_.getUsername();
                        break;
                    }
                }
            }
        }

        for (ClientHandler client : ServerMain.clientHandlers) {
            if (client.player != null && client.player.getUsername().equalsIgnoreCase(username)) {
                client.checkFind = false;
                System.out.println("Check gửi tìm trận");
                objectWrapper = new ObjectWrapper(StreamData.Type.FIND_MATCH.name(), randomId);
                client.sendData(objectWrapper);
                this.sendData(objectWrapper);
                return;
            }
        }
    }

    private void onReceiveAcceptFindMatch(ObjectWrapper objectWrapper) {
        String randId = (String) objectWrapper.getObject();
        if (randId != null) {
            this.idRoom = randId;
            for (Room room : ServerMain.rooms) {
                if (room.getId().equalsIgnoreCase(randId)) {
                    this.player.setStatus("Trong phòng");
                    onReceiveListOnline(objectWrapper);
                    onReceiveCheckFriend(objectWrapper);
                    
                    room.setStatus("2/2");
                    room.setCreateAt(new Date());
                    room.getPlayers().add(this.player);
                    room.setCreateBy(room.getPlayers().get(0));
                    if (room.getPlayers().size() == 2) {
                        List<ClientHandler> clients = ServerMain.clientHandlers;;
                        for (ClientHandler client : clients) {
                            if (client.player != null && client.player.getUsername().equalsIgnoreCase(room.getCreateBy().getUsername())) {
                                System.out.println("Check create room find match...");
                                objectWrapper = new ObjectWrapper(StreamData.Type.ACCEPT_FIND_MATCH.name(), createRoomToSend(room));
                                client.sendData(objectWrapper);
                                break;
                            }

                        }

                    }
                    break;
                }
            }

        }

    }

    private List<Player> getListPlayer() {
        List<Player> players = new ArrayList<>();
        List<ClientHandler> clients = ServerMain.clientHandlers;
        for (ClientHandler client : clients) {
            if (client.player != null) {
                players.add(client.player);
            }
        }
        return players;
    }
    
    private List<Player> getListPlayerToSend(){
        List<Player> players_ = new ArrayList<>();
        List<Match> matchs_ = ServerMain.matchs;
        for (Match match : matchs_) {
            for(PlayerInMatch p : match.getPlayerInMatchs()){
                players_.add(p.getPlayer());
            } 
        }
        return players_;
    }

}
