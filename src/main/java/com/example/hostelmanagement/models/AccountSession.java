package com.example.hostelmanagement.models;

import java.util.Date;
import java.util.Objects;

public class AccountSession
{
    private int userid;
    private int roleid;
    private Date logtime;

    public AccountSession(int userid, int roleid, Date logtime) {
        this.userid = userid;
        this.roleid = roleid;
        this.logtime = logtime;
    }

    public AccountSession() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    @Override
    public String toString() {
        return "AccountSession{" +
                "userid=" + userid +
                ", roleid=" + roleid +
                ", logtime=" + logtime +
                '}';
    }
}
