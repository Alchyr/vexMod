package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Bottle extends CustomRelic {
    // ID, images, text.
    public static final String ID = VexMod.makeID("Bottle");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Bottle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Bottle.png"));
    private boolean cardSelected = true;

    public Bottle() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup group = AbstractDungeon.player.masterDeck;
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1], false, false, false, false);
    }

    @Override
    public void update() {
        super.update(); //Do all of the original update() method in AbstractRelic

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    // Standard description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}