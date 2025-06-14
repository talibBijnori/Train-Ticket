package com.example.helloword.model;

import jakarta.persistence.*;

@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private Integer trno;
    private String trname;
    private String fromstn;
    private String tostn;
    private String seats;
    private String fare;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
         this.id = id;
    }
    public Integer getTr_no(){
        return trno;
    }
    public void setTr_no(Integer tr_no){
         this.trno = tr_no;
    }
    public String getTr_name(){
        return trname;
    }
    public void setTr_name(String tr_name){
         this.trname = tr_name;
    }
    public String getFrom_stn(){
        return fromstn;
    }
    public void setFrom_stn(String from_stn){
         this.fromstn = from_stn;
    }
    public String getTo_stn(){
        return tostn;
    }
    public void setTo_stn(String to_stn){
         this.tostn = to_stn;
    }
    public String getSeats(){
        return seats;
    }
    public void setSeats(String seats){
         this.seats = seats;
    }
    public String getFare(){
        return fare;
    }
    public void setFare(String fare){
         this.fare = fare;
    }
}
