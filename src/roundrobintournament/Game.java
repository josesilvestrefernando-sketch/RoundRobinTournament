package roundrobintournament;

import java.util.ArrayList;

public class Game {
    Match match;
    Standing standing;
    public Game(Match match1, Standing standing1){
        match=match1;
        standing=standing1;
    }
    Match getMatch(){
        return match;
    }
}
