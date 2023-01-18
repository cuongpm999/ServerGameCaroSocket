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
public class Match implements Serializable {

    private int id;
    private Date startAt;
    private Date endAt;
    private List<PlayerInMatch> playerInMatchs;
    private Room room;
    private Board board;
    private ChatItem chatItem;

    public Match() {
    }

    public Match(Date startAt, Date endAt, List<PlayerInMatch> playerInMatchs, Room room, Board board, ChatItem chatItem) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.playerInMatchs = playerInMatchs;
        this.room = room;
        this.board = board;
        this.chatItem = chatItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public List<PlayerInMatch> getPlayerInMatchs() {
        return playerInMatchs;
    }

    public void setPlayerInMatchs(List<PlayerInMatch> playerInMatchs) {
        this.playerInMatchs = playerInMatchs;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ChatItem getChatItem() {
        return chatItem;
    }

    public void setChatItem(ChatItem chatItem) {
        this.chatItem = chatItem;
    }

}
