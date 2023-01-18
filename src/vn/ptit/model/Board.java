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
public class Board implements Serializable {

    private String[][] pieces;

    public Board() {
    }

    public Board(String[][] pieces) {
        this.pieces = pieces;
    }

    public String[][] getPieces() {
        return pieces;
    }

    public void setPieces(String[][] pieces) {
        this.pieces = pieces;
    }

}
