/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.controller.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.ptit.model.Friend;
import vn.ptit.model.Player;
import vn.ptit.model.PlayerStat;

/**
 *
 * @author Cuong Pham
 */
public class DAOPlayer extends IDAO<Player> {

    public DAOPlayer(Connection conn) {
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Player> selectAll() {
        List<Player> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM player";

            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Player player = new Player();
                player.setUsername(rs.getString("Username"));
                player.setPassword(rs.getString("Password"));
                player.setFullName(rs.getString("FullName"));
                player.setImg(rs.getString("Img"));
                player.setId(rs.getInt("Id"));
                result.add(player);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return result;

    }

    public List<Player> selectByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Player player) {
        String sql = "INSERT INTO player (Img, Username, Password, FullName) VALUES (?,?,?,?)";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, player.getImg());
            this.preStatement.setString(2, player.getUsername());
            this.preStatement.setString(3, player.getPassword());
            this.preStatement.setString(4, player.getFullName());

            int rowCount = this.preStatement.executeUpdate();
            return rowCount;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    @Override
    public int update(Player player) {
        String sql = "UPDATE player SET Img = ?, Username = ?, Password = ?, Fullname = ?  WHERE Id = ?";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, player.getImg());
            this.preStatement.setString(2, player.getUsername());
            this.preStatement.setString(3, player.getPassword());
            this.preStatement.setString(4, player.getFullName());
            this.preStatement.setInt(5, player.getId());

            int rowCount = this.preStatement.executeUpdate();
            return rowCount;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Player checkLogin(String username, String password) {
        String sql = "SELECT * FROM player WHERE Username = ? AND Password = ?";
        try {
            Player player = new Player();
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, username);
            this.preStatement.setString(2, password);

            rs = this.preStatement.executeQuery();
            if (rs.next()) {
                player.setId(rs.getInt("Id"));
                player.setImg(rs.getString("Img"));
                player.setUsername(username);
                player.setPassword(password);
                player.setFullName(rs.getString("FullName"));
                return player;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return null;
    }

    public int insertFriend(Friend friend) {
        String sql = "INSERT INTO friend (PlayerId, FriendId) VALUES (?,?)";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, friend.getPlayer().getId());
            this.preStatement.setInt(2, friend.getFriendPlayer().getId());

            int rowCount = this.preStatement.executeUpdate();
            return rowCount;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    public List<Friend> findAllFriendByPlayer(int id) {
        List<Friend> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM friend WHERE PlayerId = " + id;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {               
                Friend friend = new Friend(null, findById(rs.getInt("FriendId")));
                result.add(friend);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    public Player findById(int id) {
        try {
            String sql = "SELECT * FROM player WHERE id = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, id);
            rs = this.preStatement.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setUsername(rs.getString("Username"));
                player.setPassword(rs.getString("Password"));
                player.setFullName(rs.getString("FullName"));
                player.setImg(rs.getString("Img"));
                player.setId(rs.getInt("Id"));
                return player;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public List<PlayerStat> selectAllStat() {
        List<PlayerStat> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM player";

            ResultSet rs1 = statement.executeQuery(sql);
            int i = 1;
            while (rs1.next()) {
                PlayerStat playerStat = new PlayerStat();
                playerStat.setUsername(rs1.getString("username"));
                playerStat.setId(rs1.getInt("id"));
                sql = "SELECT COUNT(PlayerId), Result FROM playerinmatch WHERE Result = 'THẮNG' AND PlayerId = ? GROUP BY Result";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, playerStat.getId());
                rs = this.preStatement.executeQuery();
                if (rs.next()) {
                    playerStat.setWin(rs.getInt(1));
                }

                sql = "SELECT COUNT(PlayerId), Result FROM playerinmatch WHERE Result = 'HÒA' AND PlayerId = ? GROUP BY Result";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, playerStat.getId());
                rs = this.preStatement.executeQuery();
                if (rs.next()) {
                    playerStat.setDraw(rs.getInt(1));
                }

                sql = "SELECT COUNT(PlayerId), Result FROM playerinmatch WHERE Result = 'THUA' AND PlayerId = ? GROUP BY Result";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, playerStat.getId());
                rs = this.preStatement.executeQuery();
                if (rs.next()) {
                    playerStat.setLose(rs.getInt(1));
                }

                playerStat.setPoint(playerStat.getWin() * 3 + playerStat.getDraw());
                playerStat.setStt(i);
                i++;
                result.add(playerStat);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return result;
    }

}
