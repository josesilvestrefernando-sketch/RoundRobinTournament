package roundrobintournament;

public class Match {
    Player whitePlayer;
    Player blackPlayer;
    Result result=null;
    public Match(Player whitePlayer, Player blackPlayer){
        this.whitePlayer=whitePlayer;
        this.blackPlayer=blackPlayer;
    }
    public void setResult(Result result1){
        result=result1;
    }
    public Result getResult(){
        return result;
    }
    public Player getWhitePlayer(){
        return whitePlayer;
    }
    public Player getBlackPlayer(){
        return blackPlayer;
    }
    Player getWinner(){
        if (result==Result.WHITEWON){
            return whitePlayer;
        }
        else if(result==Result.BLACKWON){
            return blackPlayer;
        }
        return null;
    }

    Player getLosser(){
        if (result==Result.WHITEWON){
            return blackPlayer;
        }
        else if(result==Result.BLACKWON){
            return whitePlayer;
        }
        return null;
    }
    boolean isPlayerOnTheMatch(Player player1){
        if (player1.equals(whitePlayer) || player1.equals(blackPlayer)){
            return true;
        }
        return false;
    }
}
