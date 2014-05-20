package com.example.bom;

public class NavigationItem {
    private String title;
    private int icon;
     
    public NavigationItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
     
    public String getTitle(){
        return this.title;      
    }
     
    public int getIcon(){
        return this.icon;
    }
}
