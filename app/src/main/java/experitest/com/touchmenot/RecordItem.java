package experitest.com.touchmenot;

import java.io.Serializable;

public class RecordItem implements Serializable {
    public String getmUsername() {
        return mUsername;
    }

    public String getmScore() {
        return mScore;
    }

    public String getmDate() {
        return mDate;
    }

    private String mUsername;
    private String mScore;
    private String mDate;

    public RecordItem(String theUsername, String theScore, String theDate) {
        this.mUsername = theUsername;
        this.mScore = theScore;
        this.mDate = theDate;
    }

}


