package com.example.minilibrary;

public class MenuItem {
    private String actionName;
    private int actionIcon;
    private int actionColour;
    private Class activity;

    public MenuItem(String actionName, int actionIcon, int actionColour, Class activity) {
        this.actionName = actionName;
        this.actionIcon = actionIcon;
        this.actionColour = actionColour;
        this.activity = activity;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getActionIcon() {
        return actionIcon;
    }

    public void setActionIcon(int actionIcon) {
        this.actionIcon = actionIcon;
    }

    public int getActionColour() {
        return actionColour;
    }

    public void setActionColour(int actionColour) {
        this.actionColour = actionColour;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
