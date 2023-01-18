/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ptit.utils;

public class StreamData {

    public enum Type {
        CONNECT_SERVER, // 
        LOGIN, // 
        SIGNUP, // 
        LOGOUT, // 
        LIST_ROOM, // 
        LIST_ONLINE, // 
        CREATE_ROOM, // 
        JOIN_ROOM, // 
        CHECK_ROOM, //
        CHAT_ROOM, // 
        LEAVE_ROOM, // 
        START, //
        MOVE, // 
        PLAYER_STAT, //
        UPDATE_PROFILE, //
        SEND_INVITE_FRIEND,
        ACCEPT_INVITE_FRIEND,
        LIST_FRIEND,
        CHECK_FRIEND,
        SEND_INVITE_ROOM,
        FIND_MATCH,
        ACCEPT_FIND_MATCH,
        TIME_FIND_MATCH,
        UNKNOW_TYPE, // 
    }

    public static Type getType(String typeName) {
        Type result = Type.UNKNOW_TYPE;

        try {
            result = Enum.valueOf(StreamData.Type.class, typeName);
        } catch (Exception e) {
            System.err.println("Unknow type: " + e.getMessage());
        }

        return result;
    }

    public static Type getTypeFromData(String data) {
        String typeStr = data.split(";")[0];
        return getType(typeStr);
    }
}
