package com.prasadthegreat.timebank;

public class homemodel {

    String worktitle;
    String workdata;
    String name;
    String timecredits;
    String id;
    String mid;
    String profilepic;

    public homemodel() {
    }

    public homemodel(String worktitle, String workdata, String name, String timecredits, String id, String mid, String profilepic) {
        this.worktitle = worktitle;
        this.workdata = workdata;
        this.name = name;
        this.timecredits = timecredits;
        this.id = id;
        this.mid = mid;
        this.profilepic = profilepic;
    }

    public String getWorktitle() {
        return worktitle;
    }

    public void setWorktitle(String worktitle) {
        this.worktitle = worktitle;
    }

    public String getWorkdata() {
        return workdata;
    }

    public void setWorkdata(String workdata) {
        this.workdata = workdata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimecredits() {
        return timecredits;
    }

    public void setTimecredits(String timecredits) {
        this.timecredits = timecredits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}