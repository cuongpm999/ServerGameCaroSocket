/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import vn.ptit.model.Match;

/**
 *
 * @author Cuong Pham
 */
public class DAOMatch extends IDAO<Match> {

    public DAOMatch(Connection conn) {
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Match> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Match match) {
        int isSuccess = 1;
        String sqlPlayInMatch = "INSERT INTO playerinmatch (Result,MatchId,PlayerId) values (?,?,?)";
        String sqlMatch = "INSERT INTO caro_game.match (StartAt,EndAt) values (?,?)";
        Timestamp timestamp;
        try {
            conn.setAutoCommit(false);
            // insert match
            this.preStatement = this.conn.prepareStatement(sqlMatch, statement.RETURN_GENERATED_KEYS);
            timestamp = new Timestamp(match.getStartAt().getTime());
            this.preStatement.setTimestamp(1, timestamp);
            timestamp = new Timestamp(match.getEndAt().getTime());
            this.preStatement.setTimestamp(2, timestamp);
            this.preStatement.executeUpdate();
            rs = this.preStatement.getGeneratedKeys();
            if (rs.next()) {
                match.setId(rs.getInt(1));
            }

            // insert tbl_play_in_match
            this.preStatement = this.conn.prepareStatement(sqlPlayInMatch);
            this.preStatement.setString(1, match.getPlayerInMatchs().get(0).getResult());
            this.preStatement.setInt(2, match.getId());
            this.preStatement.setInt(3, match.getPlayerInMatchs().get(0).getPlayer().getId());
            this.preStatement.executeUpdate();

            this.preStatement = this.conn.prepareStatement(sqlPlayInMatch);
            this.preStatement.setString(1, match.getPlayerInMatchs().get(1).getResult());
            this.preStatement.setInt(2, match.getId());
            this.preStatement.setInt(3, match.getPlayerInMatchs().get(1).getPlayer().getId());
            this.preStatement.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            isSuccess = 0;
            try {
                conn.rollback();
                isSuccess = 0;
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                isSuccess = 0;
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    @Override
    public int update(Match object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Match findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
