package view.gui.common;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains all CardLinks and methods that can access a graphical component's
 * attributes from its image and vice versa.
 */
public class CardLinkList {

    private List<CardLink> cards;

    /**
     * Creates a new CardLinkList.
     */
    public CardLinkList() {
        cards = new LinkedList<>();
        cards.add(new CardLink("BlackPlayerBoard", null, new ImageIcon("Images/BlackPlayerBoard.png")));
        cards.add(new CardLink("BlackPlayerBoardFF", null, new ImageIcon("Images/BlackPlayerBoardFF.png")));
        cards.add(new CardLink("BluePlayerBoard", null, new ImageIcon("Images/BluePlayerBoard.png")));
        cards.add(new CardLink("BluePlayerBoardFF", null, new ImageIcon("Images/BluePlayerBoardFF.png")));
        cards.add(new CardLink("GreenPlayerBoard", null, new ImageIcon("Images/GreenPlayerBoard.png")));
        cards.add(new CardLink("GreenPlayerBoardFF", null, new ImageIcon("Images/GreenPlayerBoardFF.png")));
        cards.add(new CardLink("PurplePlayerBoard", null, new ImageIcon("Images/PurplePlayerBoard.png")));
        cards.add(new CardLink("PurplePlayerBoardFF", null, new ImageIcon("Images/PurplePlayerBoardFF.png")));
        cards.add(new CardLink("YellowPlayerBoard", null, new ImageIcon("Images/YellowPlayerBoard.png")));
        cards.add(new CardLink("YellowPlayerBoardFF", null, new ImageIcon("Images/YellowPlayerBoardFF.png")));
        cards.add(new CardLink("Left14Grid", null, new ImageIcon("Images/Left14Grid.png")));
        cards.add(new CardLink("Left23Grid", null, new ImageIcon("Images/Left23Grid.png")));
        cards.add(new CardLink("Right14Grid", null, new ImageIcon("Images/Right14Grid.png")));
        cards.add(new CardLink("Right23Grid", null, new ImageIcon("Images/Right23Grid.png")));
        cards.add(new CardLink("BRR", null, new ImageIcon("Images/BRR.png")));
        cards.add(new CardLink("BYY", null, new ImageIcon("Images/BYY.png")));
        cards.add(new CardLink("PBB", null, new ImageIcon("Images/PBB.png")));
        cards.add(new CardLink("PRB", null, new ImageIcon("Images/PRB.png")));
        cards.add(new CardLink("PRR", null, new ImageIcon("Images/PRR.png")));
        cards.add(new CardLink("PYB", null, new ImageIcon("Images/PYB.png")));
        cards.add(new CardLink("PYR", null, new ImageIcon("Images/PYR.png")));
        cards.add(new CardLink("PYY", null, new ImageIcon("Images/PYY.png")));
        cards.add(new CardLink("RBB", null, new ImageIcon("Images/RBB.png")));
        cards.add(new CardLink("RYY", null, new ImageIcon("Images/RYY.png")));
        cards.add(new CardLink("YBB", null, new ImageIcon("Images/YBB.png")));
        cards.add(new CardLink("YRR", null, new ImageIcon("Images/YRR.png")));
        cards.add(new CardLink("Newton", "BLUE", new ImageIcon("Images/BlueNewton.png")));
        cards.add(new CardLink("Newton", "RED", new ImageIcon("Images/RedNewton.png")));
        cards.add(new CardLink("Newton", "YELLOW", new ImageIcon("Images/YellowNewton.png")));
        cards.add(new CardLink("Tagback Grenade", "BLUE", new ImageIcon("Images/BlueTagbackGrenade.png")));
        cards.add(new CardLink("Tagback Grenade", "RED", new ImageIcon("Images/RedTagbackGrenade.png")));
        cards.add(new CardLink("Tagback Grenade", "YELLOW", new ImageIcon("Images/YellowTagbackGrenade.png")));
        cards.add(new CardLink("Targeting Scope", "BLUE", new ImageIcon("Images/BlueTargetingScope.png")));
        cards.add(new CardLink("Targeting Scope", "RED", new ImageIcon("Images/RedTargetingScope.png")));
        cards.add(new CardLink("Targeting Scope", "YELLOW", new ImageIcon("Images/YellowTargetingScope.png")));
        cards.add(new CardLink("Teleporter", "BLUE", new ImageIcon("Images/BlueTeleporter.png")));
        cards.add(new CardLink("Teleporter", "RED", new ImageIcon("Images/RedTeleporter.png")));
        cards.add(new CardLink("Teleporter", "YELLOW", new ImageIcon("Images/YellowTeleporter.png")));
        cards.add(new CardLink("Cyberblade", null, new ImageIcon("Images/Cyberblade.png")));
        cards.add(new CardLink("Electroscythe", null, new ImageIcon("Images/Electroscythe.png")));
        cards.add(new CardLink("Flamethrower", null, new ImageIcon("Images/Flamethrower.png")));
        cards.add(new CardLink("Furnace", null, new ImageIcon("Images/Furnace.png")));
        cards.add(new CardLink("Grenade Launcher", null, new ImageIcon("Images/GrenadeLauncher.png")));
        cards.add(new CardLink("Heatseeker", null, new ImageIcon("Images/Heatseeker.png")));
        cards.add(new CardLink("Hellion", null, new ImageIcon("Images/Hellion.png")));
        cards.add(new CardLink("Lock Rifle", null, new ImageIcon("Images/LockRifle.png")));
        cards.add(new CardLink("Machine Gun", null, new ImageIcon("Images/MachineGun.png")));
        cards.add(new CardLink("Plasma Gun", null, new ImageIcon("Images/PlasmaGun.png")));
        cards.add(new CardLink("Power Glove", null, new ImageIcon("Images/PowerGlove.png")));
        cards.add(new CardLink("Railgun", null, new ImageIcon("Images/Railgun.png")));
        cards.add(new CardLink("Rocket Launcher", null, new ImageIcon("Images/RocketLauncher.png")));
        cards.add(new CardLink("Shockwave", null, new ImageIcon("Images/Shockwave.png")));
        cards.add(new CardLink("Shotgun", null, new ImageIcon("Images/Shotgun.png")));
        cards.add(new CardLink("Sledgehammer", null, new ImageIcon("Images/Sledgehammer.png")));
        cards.add(new CardLink("T.H.O.R.", null, new ImageIcon("Images/THOR.png")));
        cards.add(new CardLink("Tractor Beam", null, new ImageIcon("Images/TractorBeam.png")));
        cards.add(new CardLink("Vortex Cannon", null, new ImageIcon("Images/VortexCannon.png")));
        cards.add(new CardLink("Whisper", null, new ImageIcon("Images/Whisper.png")));
        cards.add(new CardLink("ZX-2", null, new ImageIcon("Images/ZX2.png")));
    }

    /**
     * Gets image icons from names (and possibly colours).
     *
     * @param cardName   name list
     * @param cardColour colour list
     * @return icon list
     */
    public List<ImageIcon> getImageIconsFromNames(List<String> cardName, List<String> cardColour) {
        List<ImageIcon> l = new LinkedList<>();
        for(int i = 0; i < cardName.size(); i++) {
            for(CardLink c : cards) {
                if(c.getCard().equals(cardName.get(i)) && (c.getColour() == null || (cardColour.size() == cardName.size() && c.getColour().equals(cardColour.get(i)))))
                    l.add(c.getImage());
            }
        }
        return l;
    }

    /**
     * Gets image icon from name (and possibly colour).
     *
     * @param cardName   name
     * @param cardColour colour
     * @return icon (default: null)
     */
    public ImageIcon getImageIconFromName(String cardName, String cardColour) {
        for(CardLink c : cards) {
            if(c.getCard().equals(cardName) && (c.getColour() == null || (!cardColour.isEmpty() && c.getColour().equals(cardColour))))
                return c.getImage();
            }
            return null;
        }

    /**
     * Gets name from image icon.
     *
     * @param cardImages image list
     * @return name list
     */
    public List<String> getNamesFromImageIcons(List<ImageIcon> cardImages) {
        List<String> l = new LinkedList<>();
        for(ImageIcon i : cardImages) {
            for(CardLink c : cards) {
                if(c.getImage().equals(i))
                    l.add(c.getCard());
            }
        }
        return l;
    }

    /**
     * Gets name from image icon.
     *
     * @param cardImage image
     * @return name (default: null)
     */
    public String getNameFromImageIcon(ImageIcon cardImage) {
        for(CardLink c : cards) {
            if(c.getImage().equals(cardImage))
                return c.getCard();
        }

        return null;
    }

    /**
     * Gets colour from image icon.
     *
     * @param cardImage image
     * @return colour (default: null)
     */
    public String getColourfromImageIcon(ImageIcon cardImage) {
        for(CardLink c : cards) {
            if(c.getImage().equals(cardImage))
                return c.getColour();
        }

        return null;
    }
}
