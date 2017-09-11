package com.example.anmol.democrazy.opinion;

import java.io.Serializable;

public class OpinionPoll implements Serializable {
    String ID;
    String Question;
    String stateCentralID;
    String startDate;
    String endData;

    public OpinionPoll(String ID, String question, String stateCentralID, String startDate, String endData) {
        this.ID = ID;
        Question = question;
        this.stateCentralID = stateCentralID;
        this.startDate = startDate;
        this.endData = endData;
    }

    public String getStateCentralID() {
        return stateCentralID;
    }

    public String getQuestion() {
        return Question;
    }

    public String getID() {
        return ID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndData() {
        return endData;
    }

}
