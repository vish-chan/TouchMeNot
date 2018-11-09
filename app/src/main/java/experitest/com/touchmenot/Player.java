package experitest.com.touchmenot;

public class Player {
    private String mUsername;
    private Integer mScore;
    private String mStartTime;
    private String mEndTime;

    public Player(String theUsername, String theStartTime) {
        this.mUsername = theUsername;
        this.mStartTime = theStartTime;
    }

    public void setScore(Integer theScore) {
        this.mScore = theScore;
    }

    public void setEndTime(String theEndTime) {
        this.mEndTime = theEndTime;
    }
}
