package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.lesprogbretons.seawar.screen.MapOrthoCamController;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;
import fr.lesprogbretons.seawar.screen.manager.EditeurMapManager;

import static fr.lesprogbretons.seawar.SeaWar.editeurController;
import static fr.lesprogbretons.seawar.SeaWar.game;
import static fr.lesprogbretons.seawar.screen.SeaWarMapScreen.selectedTile;

import fr.lesprogbretons.seawar.utils.Utils;

public class UiEditeur extends Ui {

    private Label playerLabel;
    private Label turnLabel;


    private MapOrthoCamController cameraController;


    private EditeurMapManager mapManager;

    public UiEditeur(EditeurMapManager mapManager) {
        super();

        this.mapManager = mapManager;

        TextButton optionsButton = new TextButton("Options", skin, "default");
        TextButton saveButton = new TextButton("Save", skin, "default");
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextField nomCarte = new TextField("", skin);
                Dialog d = new Dialog("Nom de la carte", skin, "dialog")
                        .text("Choisissez le nom de votre carte :");

                TextButton validerButton = new TextButton("Sauvegarder", skin, "default");
                TextButton annulerButton = new TextButton("Annuler", skin, "default");
                validerButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        editeurController.save(nomCarte.getText());
                        d.hide();
                    }
                });
                annulerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        d.hide();
                    }
                });
                d.row();
                d.add(nomCarte);
                d.row();
                d.add(validerButton);
                d.add(annulerButton);
                d.show(s);

            }

        });

        TextButton menuButton = new TextButton("Menu", skin, "default");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMenuScreen());
            }
        });

        TextButton caseEau = new TextButton("Case Eau", skin, "default");
        caseEau.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Utils.getSelectedHexagon(cameraController.touchX, cameraController.touchY, selectedTile);
//                editeurController.creerCaseEau(selectedTile.row,selectedTile.column);
            }
        });

        show.add(optionsButton).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(saveButton).padLeft(10);
        show.add(menuButton).padLeft(10);
        show.add(caseEau).padLeft(10);
        show.row();
        show.left().top();

    }

}