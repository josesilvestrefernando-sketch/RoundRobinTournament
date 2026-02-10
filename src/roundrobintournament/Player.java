package roundrobintournament;

import java.util.*;

public class Player {

    private final String name;
    int round = 0;
    float score = 0;
    private ArrayList<Game> games;
    private ArrayList<Pair> opponents;

    int id = 0;
    RoundRobinTournament roundRobinTournament;

    public Player(int id1, String name, RoundRobinTournament roundRobinTournament1) {
        this.games = new ArrayList<>();
        this.name = name;
        id = id1;
        roundRobinTournament = roundRobinTournament1;
        opponents = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }

    public int getRound() {
        return round;
    }

    /**
     * <p>
     * <b>updateGame()</b> - Using the match. it gets the game along its standing to update the players games and
     * the score and rounds.
     * </p>
     *
     * @param match
     */
    public void updateGame(Match match) {
        Standing standing = getStanding(match);
        Game game1 = new Game(match, standing);
        updateGame(game1);
        updateNextOpponents();
        updateRound();
        updateScore();


    }

    void updateGame(Game game1) {
        roundRobinTournament.updateGame(game1);
        games = roundRobinTournament.getGamesOfPlayer(this);
    }

    public void updateScore() {
        score = 0;
        for (int i = 0; i < games.size(); i++) {
            Game game1 = games.get(i);
            Standing standing1 = game1.standing;
            if (standing1 == Standing.WON) {
                score++;
            } else if (standing1 == Standing.DRAW) {
                score = score + .5f;
            }
        }
    }

    public void updateRound() {
        round = games.size()-opponents.size();
    }

    public void update() {

    }

    public void clearMatch(Match match1) {
        Player opponent = getOpponent(match1);
        int gameindex = findGame(opponent);
        games.remove(gameindex);
    }

    public Player getOpponent(Match match1) {
        Player player1 = match1.whitePlayer;
        Player player2 = match1.blackPlayer;
        if (player1.equals(this)) {
            return player2;
        }
        return player1;
    }

    int findGame(Player opponent1) {
        for (int i = 0; i < games.size(); i++) {
            Game game1 = games.get(i);
            Match match1 = game1.getMatch();
            Player player1 = match1.whitePlayer;
            Player player2 = match1.blackPlayer;
            if (opponent1.equals(player1) || opponent1.equals(player2)) {
                return i;
            }
        }
        return -1;
    }

    Standing getStanding(Match match) {
        PlayerColor playerColor = getPlayerColor(match);
        Player winner = match.getWinner();
        Player losser = match.getLosser();
        if (match.isPlayerOnTheMatch(this) == false) {
            return null;
        }
        if (winner == null) {
            return null;
        }
        if (winner.equals(this)) {
            return Standing.WON;
        }
        if (losser.equals(this)) {
            return Standing.LOST;
        }
        return Standing.DRAW;
    }

    PlayerColor getPlayerColor(Match match) {
        Player whitePlayer = match.getWhitePlayer();
        Player blackPlayer = match.getBlackPlayer();
        if (blackPlayer.equals(this)) {
            return PlayerColor.BLACKPLAYER;
        } else if (whitePlayer.equals(this)) {
            return PlayerColor.WHITEPLAYER;
        }
        return null;
    }

    boolean equals(Player player1) {
        return player1.getName().equals(getName());
    }

    public String showGames() {
        String ans = "";

        for (int i = 0; i < games.size(); i++) {

            Game game1 = games.get(i);
            Match match1 = game1.getMatch();
            Player player1 = match1.whitePlayer;
            Player player2 = match1.blackPlayer;
            Standing standing = game1.standing;
            ans = ans + player1.getName() + " vs " + player2.getName() + " - " + standing;
            ans = ans + '\n';
        }
        return ans;
    }

    void updateNextOpponents() {
        opponents.clear();
        for (int i = 0; i < games.size(); i++) {

            Game game1 = games.get(i);
            Match match1 = game1.getMatch();
            Player opponent = getOpponent(match1);

            Standing standing = game1.standing;
            if (standing == Standing.UNFINISHED || standing == null) {
                Pair pair=new Pair(this,opponent);


                opponents.add(pair);
            }

        }
        sortNextOpponents();
        Collections.reverse(opponents);
    }

    void sortNextOpponents() {


        // Sort the arraylist based on the score of each Player
        Collections.sort(opponents, new Comparator<Pair>() {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                return Float.compare(pair1.scoreDifference, pair2.scoreDifference);
            }
        });

    }

    public String showNextOpponents() {
        updateNextOpponents();
        String ans = "";
        for (int i = 0; i < opponents.size(); i++) {

            Pair pair= opponents.get(i);
            Player opponent = pair.b();

            ans = ans + opponent.getName() + " - " + opponent.getScore() + " Points - " + opponent.getRound() + " Round \n";

        }
        return ans;
    }
}
