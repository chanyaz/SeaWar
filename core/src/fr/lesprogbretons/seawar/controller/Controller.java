package fr.lesprogbretons.seawar.controller;

/* Classe Controller qui va gère l'interraction avec l'utilisateur */

import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.cases.Case;

import java.util.ArrayList;

/**
 * Classe Controller
 */
public class Controller {

    //Partie que gère le contrôleur
    private Partie game;

    /**
     * Constructeur
     *
     * @param game
     */
    public Controller(Partie game) {
        this.game = game;
    }


    /**
     * Procédure qui gère la sélection d'une case quelconque à la souris
     *
     * @param x : colonne de la case sélectionnée
     * @param y : ligne de la case sélectionnée
     */
    public void selection(int x, int y) {
        Case c = game.getMap().getCase(x, y);

        boolean actionFaite = false;

        //Les actions que veut faire le joueur avec le bateau qu'il a sélectionné
        if (game.isAnyBateauSelectionne()) {
            ArrayList<Case> casesPorteeTir;
            casesPorteeTir = game.getMap().getCasesPortees(game.getBateauSelectionne());

            //Si la case sélectionnée est à portée de tir
            if (casesPorteeTir.contains(c)) {
                if (game.getMap().casePossedeBateau(c, game.getOtherPlayer())) {
                    game.getBateauSelectionne().shoot(game.getMap().bateauSurCase(c));
                    game.setAnyBateauSelectionne(false);
                    actionFaite = true;
                }
            }

            if (!actionFaite) {
                ArrayList<Case> casesDispo = new ArrayList<>();
                game.getMap().getCasesDisponible(game.getBateauSelectionne().getPosition(), 1, casesDispo);

                //Si la case sélectionnée est à portée de déplacement
                if (casesDispo.contains(c) && game.getBateauSelectionne().getMoveAvailable() > 0) {
                    game.getBateauSelectionne().moveBoat(c);
                    if (c.isPhare()) {
                        game.getMap().prendPhare(c, game.getCurrentPlayer());
                    }
                    game.setAnyBateauSelectionne(false);
                } else {
                    game.setAnyBateauSelectionne(false);
                }
            }


        } else {
            //Si le joueur sélectionne un de ses bateaux
            if (game.getMap().casePossedeBateau(c, game.getCurrentPlayer())) {
                game.setBateauSelectionne(game.getMap().bateauSurCase(c));

            }

        }
    }

    /**
     * Procédure qui finit le tour du joueur quand on appuie sur le bouton fin de tour
     */
    public boolean endTurn() {
        boolean isOver = false;
        game.finPartie();

        if (!game.isFin()) {
            game.setAnyBateauSelectionne(false);
            isOver = game.endTurn();
        }
        return isOver;
    }

    public void changercanon() {
        game.getBateauSelectionne().setCanonSelectionne(3 - game.getBateauSelectionne().getCanonSelectionne());

    }

}
