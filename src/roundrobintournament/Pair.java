package roundrobintournament;

public class Pair {
    private Player a;
    private Player b;
    float scoreDifference=0;
   public Pair(Player a, Player b) {
        this.a = a;
        this.b = b;
        scoreDifference=calculateDifference(a,b);

    }

    // Getters
    public Player a() {
        return a;
    }

    public Player b() {
        return b;
    }
    private static float calculateDifference(Player a, Player b) {
        return Math.abs(a.getScore() - b.getScore());
    }
    public float getScoreDifference(){
       return scoreDifference;
    }
}
