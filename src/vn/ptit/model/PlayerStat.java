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
public class PlayerStat extends Player implements Serializable {

    private int stt;
    private int point;
    private int win;
    private int lose;
    private int draw;

    public PlayerStat() {
    }

    public PlayerStat(int point, int win, int lose, int draw, String img, String username, String password, String fullName, String status) {
        super(img, username, password, fullName, status);
        this.point = point;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toObject() {
        return new Object[]{stt, getUsername(), point, win, draw, lose};
    }

}
