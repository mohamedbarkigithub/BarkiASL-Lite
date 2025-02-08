package com.mohamed.barki.asl.lite.Model;
public class Message {
    private final String key;
    private final String time;
    private String name;
    private final String message;
    public Message(String key, String time, String name, String message){
        this.key = key;
        this.time = time;
        this.name = name;
        this.message = message;
    }
    public String getKey() {return key;}
    public String getTime() {return time;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getMessage() {return message;}
}
