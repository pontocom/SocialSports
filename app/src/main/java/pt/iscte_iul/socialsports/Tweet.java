package pt.iscte_iul.socialsports;

/**
 * Created by cserrao on 17/07/15.
 */
public class Tweet {
    private Long tweetid;
    private String timestamp;
    private String tweetUsername;
    private String tweetContent;
    private String tweetUserPictureURL;
    private String tweetOptionalContentURL;

    public Tweet(Long tweetid, String timestamp, String tweetUserPictureURL,String tweetUsername, String tweetContent, String tweetOptionalContentURL) {
        super();
        this.tweetid = tweetid;
        this.timestamp = timestamp;
        this.tweetUsername = tweetUsername;
        this.tweetContent = tweetContent;
        this.tweetUserPictureURL = tweetUserPictureURL;
        this.tweetOptionalContentURL = tweetOptionalContentURL;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTweetUsername() {
        return tweetUsername;
    }

    public void setTweetUsername(String tweetUsername) {
        this.tweetUsername = tweetUsername;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public String getTweetUserPictureURL() {
        return tweetUserPictureURL;
    }

    public void setTweetUserPictureURL(String tweetUserPictureURL) {
        this.tweetUserPictureURL = tweetUserPictureURL;
    }

    public String getTweetOptionalContentURL() {
        return tweetOptionalContentURL;
    }

    public void setTweetOptionalContentURL(String tweetOptionalContentURL) {
        this.tweetOptionalContentURL = tweetOptionalContentURL;
    }

    public Long getTweetid() {
        return tweetid;
    }

    public void setTweetid(Long tweetid) {
        this.tweetid = tweetid;
    }
}
