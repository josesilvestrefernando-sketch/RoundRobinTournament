package roundrobintournament;

import java.io.IOException;
import java.util.*;

public class RoundRobinTournamentApp {


    RoundRobinTournament roundRobinTournament = new RoundRobinTournament();







    public void showRanking() {
        System.out.println("Tournament Ranking:");
        System.out.println(roundRobinTournament.showRanking());
    }



    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nRound Robin Tournament Menu:");
            System.out.println("1. Load Record");
            System.out.println("2. Add Player");
            System.out.println("3. Delete Player");
            System.out.println("4. Set Score");
            System.out.println("5. Show Player's Record");
            System.out.println("6. Show Next Opponent");
            System.out.println("7. Show Ranking");
            System.out.println("8. Show Record");
            System.out.println("9. Exit");



            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    roundRobinTournament.loadRecord("record.csv");
                    System.out.print("Loaded");
                    System.out.print(roundRobinTournament.showRecord());
                    break;
                case 2:
                    System.out.print("Enter player name: ");
                    roundRobinTournament.addPlayer(scanner.nextLine());
                    roundRobinTournament.saveRecord();
                    break;
                case 3:
                    System.out.print("Enter player name to delete: ");
                    roundRobinTournament.deletePlayer(scanner.nextLine());
                    roundRobinTournament.saveRecord();
                    break;
                case 4:
                    System.out.println("Enter player name and opponent name:");
                    String playerName = scanner.nextLine();
                    String opponentName = scanner.nextLine();
                    System.out.println("Enter result (draw, white wins, black wins):");
                    roundRobinTournament.setScore(playerName, opponentName, scanner.nextLine().trim());
                    roundRobinTournament.saveRecord();
                    break;
                case 5:
                    System.out.print("Enter player name to show the record: ");
                    roundRobinTournament.showGames(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter player name to show next opponent: ");
                    roundRobinTournament.showNextOpponent(scanner.nextLine());
                    break;
                case 7:
                    showRanking();
                    break;
                case 8:
                    System.out.print(roundRobinTournament.showRecord());
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }
}
