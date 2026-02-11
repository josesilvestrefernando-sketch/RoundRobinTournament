Round Robin Tournament API (Java)

📌 Overview

This project is a Java-based API that implements a Round Robin Tournament calculation system with a custom rule set:

Players are matched based on Winner vs Loser logic

The tournament progresses until the remaining contestants advance to Winner vs Winner matches

Rankings are updated dynamically based on match results


This project is designed for learning purposes, algorithm practice, and as a portfolio-ready backend logic project.


---

🎯 Features

The API provides the following core functionalities:

✅ Add players to the tournament

🕹️ Generate and track player matches

🏆 Calculate and display player rankings

🔁 Determine the next opponent based on Winner vs Loser rules

📊 View player match history



---

🧠 Tournament Rules Logic

1. All players start in the tournament pool


2. Matches are generated using a Round Robin approach


3. After each match:

Winners are matched against losers in the next round

Rankings are recalculated after every result



4. As the tournament progresses:

Remaining top players eventually face Winner vs Winner matches



5. The tournament continues until final rankings are determined



This rule set ensures fair progression while rewarding consistent winners.


---

🛠️ API Capabilities

Add Player

Adds a new player to the tournament pool.

Show Rankings

Displays the current ranking of all players based on wins/losses.

Show Player Games

Returns the match history of a specific player.

Get Next Opponent

Determines the next opponent for a player according to the Winner vs Loser rule logic.


---

🧩 Example Use Cases

Tournament management systems

Game competition logic

Sports or e-sports ranking simulations

Algorithm and data-structure practice



---

---

🧑‍💻 Technologies Used

Java

Object-Oriented Programming (OOP)

Collections Framework



---

---

📜 License

This project is open-source and available under the MIT License.


---

🙌 Author

Jose Silvestre Fernando
Self-taught Java Developer
GitHub Portfolio Project


---

Feel free to fork, study, or improve this project!
