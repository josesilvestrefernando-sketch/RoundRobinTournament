package roundrobintournament;


import processing.data.Table;
import processing.data.TableRow;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RoundRobinTournament {
    private ArrayList<Player> players;
    ArrayList<Player> rankedplayers;
    private Map<String, Player> playersMap = new HashMap<>();

    /*
       a  b  c  d
    a  E  1  1 .5
    b  0  E  1  1
    c  0  0  E  U
    d .5  0  U  E
     */
    Standing[][] record;


    public RoundRobinTournament() {
        this.players = new ArrayList<>();
        record = new Standing[players.size()][players.size()];
    }

    public void addPlayer(String name) {
        if (!playersMap.containsKey(name)) {
            Player player = new Player(players.size(), name, this);
            playersMap.put(name, player);
            players.add(player);
            record = new Standing[players.size()][players.size()];

        } else {
            System.out.println("Player already exists.");
        }
    }

    public void setPlayers(ArrayList<Player> players) {

        this.players = new ArrayList<>(players);
        record = new Standing[players.size()][players.size()];
    }

    public void updateGame(Game game) {
        Match match = game.getMatch();
        Player winner = match.getWinner();
        Player losser = match.getLosser();
        Result result1 = match.getResult();
        if (winner != null) {

            record[winner.getId()][losser.getId()] = Standing.WON;
            record[losser.getId()][winner.getId()] = Standing.LOST;
        } else if (result1 == Result.DRAW) {
            Player whiteplayer = match.getWhitePlayer();
            Player blackplayer = match.getBlackPlayer();
            record[whiteplayer.getId()][blackplayer.getId()] = Standing.DRAW;
            record[blackplayer.getId()][whiteplayer.getId()] = Standing.DRAW;
        }


    }

    public void updateRecord(Match match) {
        Player winner = match.getWinner();
        Player losser = match.getLosser();
        Result result1 = match.getResult();
        if (winner != null) {

            record[winner.getId()][losser.getId()] = Standing.WON;
            record[losser.getId()][winner.getId()] = Standing.LOST;
        } else if (result1 == Result.DRAW) {
            Player whiteplayer = match.getWhitePlayer();
            Player blackplayer = match.getBlackPlayer();
            record[whiteplayer.getId()][blackplayer.getId()] = Standing.DRAW;
            record[blackplayer.getId()][whiteplayer.getId()] = Standing.DRAW;
        }
    }

    ArrayList<Game> getGamesOfPlayer(Player player1) {
        int playerid = player1.getId();

        ArrayList<Game> games = new ArrayList<>();
        for (int i = 0; i < record.length; i++) {
            if (i != playerid) {
                Player player2 = players.get(i);
                int player2id = player2.getId();
                Match match1 = new Match(player1, player2);
                Standing standing = record[playerid][player2id];

                Game game = new Game(match1, standing);
                games.add(game);

            }
        }
        return games;
    }

    public void addPlayer(Player player1) {
        players.add(player1);
    }

    public void removePlayer(int index1) {
        players.remove(index1);
    }

    //Set the score with the given players and the result
    public void setScore(Match match1) {
        updateRecord(match1);


        Player whitePlayer = match1.getWhitePlayer();
        whitePlayer.updateGame(match1);
        Player blackPlayer = match1.getBlackPlayer();
        blackPlayer.updateGame(match1);
    }

    public void showGames(String playerName){
        Player player = playersMap.get(playerName);

        if (player != null) {

            System.out.println(player.showGames());
        } else {
            System.out.println("Player not found.");
        }
    }

    void loadScoreAndRecord(Table table, Standing[][] result) {
        // Get the number of rows in the table
        int numRows = table.getRowCount();
        //first column was not used in record mulitiple array because it only record the standing and not the players name
        int numColumns = table.getColumnCount() - 1;

        // Create a 2D array to store the results
        result = new Standing[numRows][numColumns];

        // Iterate over each row and column

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                // Retrieve the value from the table
                TableRow value = table.getRow(i);

                // Store it in the result array
                //first column was not used in record mulitiple array because it only record the standing and not the players name
                String recordvalue = value.getString(j + 1);

                char firstletter = recordvalue.charAt(0);
                Standing standing1 = charToStanding(firstletter);
                Player whiteplayer = players.get(i);
                Player blackplayer = players.get(j);
                Match match = new Match(whiteplayer, blackplayer);

                switch (standing1) {
                    case WON:
                        match.setResult(Result.WHITEWON);
                        setScore(match);
                        break;
                    case LOST:
                        match.setResult(Result.BLACKWON);
                        setScore(match);
                        break;
                    case DRAW:
                        match.setResult(Result.DRAW);
                        setScore(match);
                        break;
                    default:
                        result[i][j] = standing1;
                        break;
                }

            }
        }


    }

    public void setScore(String playerName, String opponentName, String result) {
        Player player = playersMap.get(playerName);
        Player opponent = playersMap.get(opponentName);

        if (player != null && opponent != null) {
            if ("draw".equalsIgnoreCase(result)) {


                Match match = new Match(player, opponent);
                match.setResult(Result.DRAW);
                setScore(match);
            } else if ("white wins".equalsIgnoreCase(result)) {
                Match match = new Match(player, opponent);
                match.setResult(Result.WHITEWON);
                setScore(match);
            } else if ("black wins".equalsIgnoreCase(result)) {
                Match match = new Match(player, opponent);
                match.setResult(Result.BLACKWON);
                setScore(match);
            } else {
                System.out.println("Invalid result. Use 'draw', 'white wins', or 'black wins'.");
                return;
            }

            System.out.println("Score set successfully.");
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    //clear the score by clearing the score to the given players. Player has only
    //one game per player and a rematch was only out in this project because it only happen
    //if there is a similar score on the final result of the tournament. Therefore it is out
    //of the calculation of this roundrobintournament
    public void clearScore(Player player1, Player player2) {
        int index1 = player1.getId();
        int index2 = player2.getId();

        record[index1][index2] = Standing.UNFINISHED;
        record[index2][index1] = Standing.UNFINISHED;

        Match match1 = new Match(player1, player2);
        player1.updateGame(match1);
        player2.updateGame(match1);
    }

    public String showRecord() {
        String ans = "";
        for (int i = 0; i < record.length; i++) {
            ans = ans + '\n';
            for (int j = 0; j < record[0].length; j++) {
                ans = ans + " " + record[i][j];
            }
        }
        return ans;
    }

    void setRankedplayers() {
        rankedplayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            rankedplayers.add(players.get(i));
        }

        Collections.sort(rankedplayers, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return Float.compare(player1.getScore(), player2.getScore());
            }
        });

        Collections.reverse(rankedplayers);
    }

    public String showRanking() {
        setRankedplayers();
        String ans = "";
        for (int i = 0; i < players.size(); i++) {
            Player player = rankedplayers.get(i);
            String name = player.getName();
            String score = String.valueOf(player.getScore());
            String round = String.valueOf(player.getRound());
            String rank = String.valueOf(i + 1);
            ans = ans + rank + ". " + name + " - " + score + " Points - " + round + " Round\n";
        }
        return ans;
    }

    public void saveRecord() throws IOException {
        Table table = createTable();
        table.save(new File("record.csv"), null);

    }

    // Function to create the table
    public Table createTable() {

        Table table = new Table();

        createTableColumn(table);

        //creating the rows
        //loop to all players
        for (int i = 0; i < players.size(); i++) {

            //at first column is the name of the players
            TableRow newRow = table.addRow();


            int totalcolumn = players.size() + 1;
            //loop the name of all player for the selected column
            for (int j = 0; j < totalcolumn; j++) {
                //k is the index in record without a playername column
                int k = j - 1;
                if (j == 0) {
                    setNameForFirstColumn(newRow, i);
                } else {
                    writeOutcome(newRow, i, k, j);
                }


            }


        }


        return table;
    }

    void writeOutcome(TableRow newRow, int i, int k, int j) {
        Standing outcome = record[i][k];
        //the column and rows was on same player name that does not represent a result
        if (k == i) {
            newRow.setString(j, "X");
        } else {
            if (outcome != null) {
                switch (outcome) {
                    case WON:
                        newRow.setString(j, "W");
                        break;
                    case LOST:
                        newRow.setString(j, "L");
                        break;
                    case DRAW:
                        newRow.setString(j, "D");
                        break;
                    default:
                        newRow.setString(j, "E");
                        break;
                }
            } else {
                newRow.setString(j, "E");
            }
        }


    }

    void createTableColumn(Table table) {
        int numPlayers = players.size();
        //totalcolumn inlcluding the corner side at the top left as empty or the columns for all players
        int totalcolumn = numPlayers + 1;
        //create the header of the table
        for (int i = 0; i < totalcolumn; i++) {
            //set first the empty header or players list header
            if (i == 0) {
                table.addColumn("Players");
            }
            //loop by setting all the players at header
            else {
                Player player = players.get(i - 1);
                String playername = player.getName();
                table.addColumn(playername);
            }
        }
    }

    void setNameForFirstColumn(TableRow newRow, int playerindex) {

        Player player = players.get(playerindex);
        String playername = player.getName();
        newRow.setString(0, playername);
    }

    public void loadRecord(String filename) throws IOException {
        Table table = new Table(new File(filename), "header");
        loadPlayers(table);
        loadScoreAndRecord(table, record);
        System.out.println(showRecord());
    }

    void loadPlayers(Table table) {
        int numRows = table.getRowCount();
        for (int i = 0; i < numRows; i++) {
            TableRow value = table.getRow(i);
            String playername = value.getString(0);
            addPlayer(playername);
        }
    }

    public static Standing[][] convertToMultiArray(Table table) {
        // Get the number of rows in the table
        int numRows = table.getRowCount();
        //first column was not used in record mulitiple array because it only record the standing and not the players name
        int numColumns = table.getColumnCount() - 1;

        // Create a 2D array to store the results
        Standing[][] result = new Standing[numRows][numColumns];

        // Iterate over each row and column

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                // Retrieve the value from the table
                TableRow value = table.getRow(i);

                // Store it in the result array
                //first column was not used in record mulitiple array because it only record the standing and not the players name
                String recordvalue = value.getString(j + 1);

                char firstletter = recordvalue.charAt(0);
                result[i][j] = charToStanding(firstletter);
            }
        }

        return result;
    }

    public static Standing charToStanding(char value1) {
        Standing ans = null;
        switch (value1) {
            case 'E':
                ans = Standing.UNFINISHED;
                break;
            case 'W':
                ans = Standing.WON;
                break;
            case 'L':
                ans = Standing.LOST;
                break;
            case 'D':
                ans = Standing.DRAW;
                break;
            case 'X':
                ans = Standing.EMPTY;
                break;
        }
        return ans;
    }

    public void showNextOpponent(String playerName) {
        Player player = playersMap.get(playerName);

        if (player != null) {

            System.out.println("Next opponent for " + playerName + " - score: " +player.getScore()
                    +" - Round "+ player.getRound()+" :");
            System.out.println(player.showNextOpponents());
        } else {
            System.out.println("Player not found.");
        }
    }

    public void deletePlayer(String name) {
        if (playersMap.containsKey(name)) {
            players.remove(name);
            players.remove(playersMap.get(name));
            record = new Standing[players.size()][players.size()];
            System.out.println("Player deleted successfully.");
        } else {
            System.out.println("Player not found.");
        }
    }
}
