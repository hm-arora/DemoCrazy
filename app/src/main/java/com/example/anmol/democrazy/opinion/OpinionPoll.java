package com.example.anmol.democrazy.opinion;

import java.io.Serializable;

public class OpinionPoll implements Serializable {
    String ID;
    String Question;
    String stateCentralID;
    String startDate;
    String endData;

    public boolean isShowButton() {
        return isShowButton;
    }

    boolean isShowButton;

    public OpinionPoll(String ID, String question, String stateCentralID, String startDate, String endData, boolean isShowButton) {
        this.ID = ID;
        Question = question;
        this.stateCentralID = stateCentralID;
        this.startDate = startDate;
        this.endData = endData;
        this.isShowButton = isShowButton;
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
