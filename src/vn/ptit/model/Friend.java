/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.model;

import java.io.Serializable;

/**
 *
 * @author Cuong Pham
 */
public class Friend implements Serializable{
    private int id;
    private Player player;
    private Player friendPlayer;

    public Friend() {
    }

    public Friend(Player player, Player friendPlayer) {
        this.player = player;
        this.friendPlayer = friendPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getFriendPlayer() {
        return friendPlayer;
    }

    public void setFriendPlayer(Player friendPlayer) {
        this.friendPlayer = friendPlayer;
    }
    
    public Object[] toObject() {
        return new Object[]{friendPlayer.getFullName(), friendPlayer.getUsername(), friendPlayer.getStatus()};
    }
    
    
}
