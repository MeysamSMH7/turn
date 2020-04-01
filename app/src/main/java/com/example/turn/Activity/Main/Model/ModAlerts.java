package com.example.turn.Activity.Main.Model;


public class ModAlerts {
    private String titleName;
    private String id;


    public ModAlerts(String titleName,String id) {
        this.titleName = titleName;
        this.id = id;
    }

    public String getTitle() {
        return this.titleName;
    }
    public String getId() {
        return this.id;
    }

}