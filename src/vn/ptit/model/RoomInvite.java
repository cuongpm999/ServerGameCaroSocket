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
public class RoomInvite implements Serializable{
    private String id;
    private String username;
    private boolean status;

    public RoomInvite(String id, String username, boolean status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }

    public RoomInvite() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
