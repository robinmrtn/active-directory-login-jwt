package com.romart.ad_login_jwt.domain.ismnet;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EventPlanSubscription {

    @Id
    private int id;
    private  String pOid;
    private  String dfOid;

    public EventPlanSubscription() {}

    public EventPlanSubscription(String pOid, String dfOid) {
        this.pOid = pOid;
        this.dfOid = dfOid;
    }

    public String getpOid() {
        return pOid;
    }

    public String getDfOid() {
        return dfOid;
    }

    public void setpOid(String pOid) {
        this.pOid = pOid;
    }

    public void setDfOid(String dfOid) {
        this.dfOid = dfOid;
    }

    @Override
    public String toString() {
        return "EventPlanSubscription{" +
                "id=" + id +
                ", pOid='" + pOid + '\'' +
                ", dfOid='" + dfOid + '\'' +
                '}';
    }
}

