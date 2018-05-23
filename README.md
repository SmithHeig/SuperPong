# Super Pong

## Description

Le SuperPong est un jeu développé par une équipe de quatres personnes à l'haute école d'ingénierie et de gestion à Yverdon, dans le cadre du module *Génie Logiciel*. Ce jeu reprend les concepts du jeu Pong mais est un jeu multijoueur en réseau avec la possibilité de jouer avec des items.

## Technologies utilisées

- Java
- JavaFX
- Maven
- Github

## Protocole de communication

### Protocole principal 

| Commande         | Processus réalisé par le server          | Réponse                                  |
| ---------------- | ---------------------------------------- | ---------------------------------------- |
| `CONNECT`        | Vérification des identifiants et de la disponniblité du serveur | {"connected": bool}                      |
| `STAT`           | Récupère les statistiques sur le serveur et les renvoies à l'utilisateur | {"username": value, <br/>"nbParties": value,<br/>"nbWin": value} |
| ```ADD_FRIEND``` | Regarde si "l'ami" existe et l'ajoute en tant que amis dans la base de donnée | {"found": bool}                          |
| `SHOW_FRIENDS`   | Retroune les amis de l'utilisateur       | {"friends": [user, ...]}                 |
| `DISCONNECT`     | Destruction de la session                | *None*                                   |

## Protocole de jeu

Le jeu est géré par deux thread séparé du coté client. La réception et l'envoie des données et séparé.

### Envoie des données depuis le client

| Commande | Processus réalisé par le serveur         | Réponse                                  |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `PLAY`   | Ajout du joueur dans la liste d'attente. Création du thread pour le match. | Quand autre joueur trouvé: {"found": bool, "username_opponent": value} |
| `MOVE`   | Gère la position donné par l'utilisateur | *None*                                   |

### Envoie des données depuis le serveur

| CMD     | Description                              | Format                                   |
| ------- | ---------------------------------------- | ---------------------------------------- |
| `MOVE`  | Retour de la position du/des joueur(s) adverse(s) et de la balle | {"balls": [{<br />"position":{<br />"x": value,<br />"y": value,<br />}, <br />"velocity": value],<br />players: ["username", value, "position": {<br />"x": value,<br />"y": value,<br />},<br />"item": {<br />"name": value,<br />"time": value}}]} |
| `RESET` | Repositionne tous les joueurs à leur position initial | *None*                                   |
| `GOAL`  | Incrémentation des points d'un joueur    | {"username": value, "nbPoint": value}    |
| `END`   | Fin du match. Un jouer a gagné. Ferme le socket | {"score": {<br />[{"username": value,<br />"points": value}]}} |

