public class Role {
    private int rank;
    private String title;
    private String quote;
    private boolean extra;
    private boolean taken;
    private int[] area;

    // constructor
    public Role(int rank, String title, String quote, boolean extra, int[] area) {
        this.rank = rank;
        this.title = title;
        this.quote = quote;
        this.extra = extra;
        this.area = area;
        this.taken = false;
    }

    // returns role rank
    public int getRank() {
        return rank;
    }

    // reutnrs role title
    public String getTitle() {
        return title;
    }

    // returns role quote
    public String getQuote() {
        return quote;
    }

    // returns the area of role
    public int[] getArea() {
        return area;
    }

    // returns true if role is extra
    public boolean isExtra() {
        return extra;
    }

    // returns true if role is taken
    public boolean isTaken() {
        return taken;
    }

    // sets taken status to true
    public void takeRole() {
        taken = true;
    }

    // sets taken status to false
    public void leaveRole() {
        taken = false;
    }
}
