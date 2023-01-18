/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cuong Pham
 */
public class Room implements Serializable {

    private String id;
    private Date createAt;
    private Player createBy;
    private List<Player> players;
    private String status;

    public Room(String id, Date createAt, Player createBy, List<Player> players, String status) {
        this.id = id;
        this.createAt = createAt;
        this.createBy = createBy;
        this.players = players;
        this.status = status;
    }

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Player getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Player createBy) {
        this.createBy = createBy;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object[] toObject() {
        return new Object[]{id, createBy.getUsername(), status};
    }

}
