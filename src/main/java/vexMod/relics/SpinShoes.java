package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class SpinShoes extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("SpinShoes");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private boolean calledTransform=true;
    private int count = 0;


    public SpinShoes() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        this.calledTransform = false;
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while(true) {
            AbstractCard e;
            do {
                if (!i.hasNext()) {
                    if (this.count > 0) {
                        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        for(int v = 0; v < this.count; ++v) {
                            AbstractCard card = new Clumsy();
                            UnlockTracker.markCardAsSeen(card.cardID);
                            card.isSeen = true;
                            group.addToBottom(card);
                        }

                        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
                    }

                    return;
                }

                e = (AbstractCard)i.next();
            } while(e.cardID.equals("Clumsy"));

            i.remove();
            ++this.count;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!this.calledTransform && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            this.calledTransform = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
