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
public class ChatItem implements Serializable {

    private String time;
    private String owner;
    private String content;

    public ChatItem() {
    }

    public ChatItem(String time, String owner, String content) {
        this.time = time;
        this.owner = owner;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "[" + time + "] " + owner + ": " + content;
    }

}
