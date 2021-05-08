package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;


public class GameButton extends Button {
    public static final Image DIAMOND = new Image("/resources/Diamond.png");
    public static final Image FREE_TUNNEL = new Image("/resources/FreeTunnel.png");
    public static final Image GATE = new Image("/resources/Gate.png");
    public static final Image OCCUPIED_TUNNEL = new Image("/resources/OccupiedTunnel.png");
    public static final Image ROCK = new Image("/resources/Rock.png");
    public static final Image ROCKFORD = new Image("/resources/Rockford.png");
    public static final Image WALL = new Image("/resources/Wall.png");
    private ImageView imageView;


    public GameButton(AbstractPosition abstractPosition){
        this.imageView = new ImageView(OCCUPIED_TUNNEL);
        setButtonImage(abstractPosition);
        this.setGraphic(this.imageView);
    }

    public void setButtonImage(AbstractPosition position) {
        switch (position.print()) {
            case 'L':
                this.setFreeTunnel();
                break;
            case 'W':
                this.setWall();
                break;
            case 'O':
                this.setOccupiedTunnel();
                break;
            case 'D':
                this.setDiamond();
                break;
            case 'X':
                this.setRockford();
                break;
            case 'P':
                this.setRock();
                break;
            case 'G':
                this.setGate();
                break;
        }
    }

    public void setDiamond(){this.imageView.setImage(DIAMOND);}

    public void setFreeTunnel(){this.imageView.setImage(FREE_TUNNEL);}

    public void setGate(){this.imageView.setImage(GATE);}

    public void setOccupiedTunnel(){this.imageView.setImage(OCCUPIED_TUNNEL);}

    public void setRock(){this.imageView.setImage(ROCK);}

    public void setRockford(){this.imageView.setImage(ROCKFORD);}

    public void setWall(){this.imageView.setImage(WALL);}

}
