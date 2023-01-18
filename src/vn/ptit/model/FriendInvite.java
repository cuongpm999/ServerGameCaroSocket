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
public class FriendInvite implements Serializable{
    private Player player;
    private Player playerFriend;
    private boolean status;

    public FriendInvite() {
    }

    public FriendInvite(Player player, Player playerFriend, boolean status) {
        this.player = player;
        this.playerFriend = playerFriend;
        this.status = status;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayerFriend() {
        return playerFriend;
    }

    public void setPlayerFriend(Player playerFriend) {
        this.playerFriend = playerFriend;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
