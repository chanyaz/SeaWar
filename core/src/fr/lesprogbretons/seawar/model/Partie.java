package fr.lesprogbretons.seawar.model;

import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.map.DefaultMap;
import fr.lesprogbretons.seawar.model.map.Grille;


public class Partie {

    //Carte
    private Grille map = new DefaultMap();

    //Joueurs
    private Player joueur1 = map.getJoueur1();
    private Player joueur2 = map.getJoueur2();

    //Joueur dont c'est le tour
    private Player currentPlayer = joueur1;

    //Compteur de tours
    private int turnCounter = 1;
    private boolean isPlayer2 = false;

    //Bateau sélectionné par le joueur
    private Boat bateauSelectionne;
    private boolean isAnyBateauSelectionne = false;

    //Pour savoir si la partie est finie
    private boolean fin = false;

    //Vainqueur
    private Player winner;


    //Getters & Setters
    public Grille getMap() {
        return map;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Boat getBateauSelectionne() {
        return bateauSelectionne;
    }

    public void setBateauSelectionne(Boat bateauSelectionne) {
        this.bateauSelectionne = bateauSelectionne;
    }

    public boolean isAnyBateauSelectionne() {
        return isAnyBateauSelectionne;
    }

    public void setAnyBateauSelectionne(boolean anyBateauSelectionne) {
        isAnyBateauSelectionne = anyBateauSelectionne;
    }

    public boolean isFin() {
        return fin;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    /////////////////////////////////////////////////////////////////////////////:
    public Player getOtherPlayer() {
        if (getCurrentPlayer().getNumber() == 1) {
            return joueur2;
        } else {
            return joueur1;
        }
    }

    public void finPartie() {
        if (getJoueur1().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur1());
        } else if (getJoueur2().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur2());
        } else if (getCurrentPlayer().equals(getJoueur1())) {
            boolean fin = true;

            for (int i = 0; i < map.getBateaux1().size(); i++) {
                if (map.getBateaux1().get(i).isAlive()) {
                    fin = false;
                }
            }

            if (fin) {
                setFin(true);
                setWinner(joueur1);
            }
        } else if (getCurrentPlayer().equals(getJoueur2())) {
            boolean fin = true;

            for (int i = 0; i < map.getBateaux2().size(); i++) {
                if (map.getBateaux2().get(i).isAlive()) {
                    fin = false;
                }
            }

            if (fin) {
                setFin(true);
                setWinner(joueur2);
            }
        }
    }

    public boolean endTurn() {
        boolean bateauxDeplaces = true;

        if (getCurrentPlayer().equals(joueur1)) {
            for (int i = 0; i < map.getBateaux1().size(); i++) {
                if (map.getBateaux1().get(i).getMoveAvailable() == map.getBateaux1().get(i).getMove()) {
                    bateauxDeplaces = false;
                }
            }
        } else {
            for (int i = 0; i < map.getBateaux2().size(); i++) {
                if (map.getBateaux2().get(i).getMoveAvailable() == map.getBateaux2().get(i).getMove()) {
                    bateauxDeplaces = false;
                }
            }
        }

        if (bateauxDeplaces) {
            if (getCurrentPlayer().equals(joueur1)) {
                for (int i = 0; i < map.getBateaux1().size(); i++) {
                    map.getBateaux1().get(i).endTurn();
                    map.getBateaux2().get(i).setShootTaken(0);
                }
            } else {
                for (int i = 0; i < map.getBateaux2().size(); i++) {
                    map.getBateaux2().get(i).endTurn();
                    map.getBateaux1().get(i).setShootTaken(0);

                }
            }

            setCurrentPlayer(getOtherPlayer());
            if (isPlayer2) {
                turnCounter++;
                isPlayer2 = false;
            } else {
                isPlayer2 = true;
            }

            return true;
        }

        //Si le joueur n'a pas déplacé tous ses bateaux
        else {
            return false;
        }
    }
}
