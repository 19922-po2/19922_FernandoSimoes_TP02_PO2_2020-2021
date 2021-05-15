package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

/**
 * @author Fernando Simões nº 19922
 */

public class GameButton extends Button {
    // https://game-icons.net/
    private static final Image DIAMOND = new Image("/resources/Diamond.png");
    private static final Image FREE_TUNNEL = new Image("/resources/FreeTunnel.png");
    private static final Image GATE = new Image("/resources/Gate.png");
    private static final Image OCCUPIED_TUNNEL = new Image("/resources/OccupiedTunnel.png");
    private static final Image ROCK = new Image("/resources/Rock.png");
    private static final Image ROCKFORD = new Image("/resources/Rockford.png");
    private static final Image WALL = new Image("/resources/Wall.png");
    private static final Image ENEMY = new Image("/resources/Enemy.png");
    private ImageView imageView;


    public GameButton(AbstractPosition abstractPosition) {
        this.imageView = new ImageView(OCCUPIED_TUNNEL);
        setButtonImage(abstractPosition);
        this.setGraphic(this.imageView);
    }

    public void setButtonImage(AbstractPosition position) {
        position.setImage(this);
    }

    public void setDiamond() {
        this.imageView.setImage(DIAMOND);
    }

    public void setFreeTunnel() {
        this.imageView.setImage(FREE_TUNNEL);
    }

    public void setGate() {
        this.imageView.setImage(GATE);
    }

    public void setOccupiedTunnel() {
        this.imageView.setImage(OCCUPIED_TUNNEL);
    }

    public void setRock() {
        this.imageView.setImage(ROCK);
    }

    public void setRockford() {
        this.imageView.setImage(ROCKFORD);
    }

    public void setWall() {
        this.imageView.setImage(WALL);
    }

    public void setEnemy() {
        this.imageView.setImage(ENEMY);
    }
}
