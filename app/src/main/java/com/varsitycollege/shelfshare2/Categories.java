package com.varsitycollege.shelfshare2;

public class Categories {

    String category;
    String goal;

    public Categories(String category, String goal) {
        this.category = category;
        this.goal = goal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
