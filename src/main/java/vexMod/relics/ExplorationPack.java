package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Discovery;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ExplorationPack extends CustomRelic {


    public static final String ID = VexMod.makeID("ExplorationPack");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ExplorationPack.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ExplorationPack.png"));
    private boolean calledTransform = true;
    private int count = 0;

    public ExplorationPack() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        this.calledTransform = false;
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while (true) {
            AbstractCard e;
            do {
                if (!i.hasNext()) {
                    if (this.count > 0) {
                        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        for (int v = 0; v < this.count; ++v) {
                            AbstractCard card = new Discovery().makeCopy();
                            UnlockTracker.markCardAsSeen(card.cardID);
                            card.isSeen = true;
                            group.addToBottom(card);
                        }

                        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
                    }

                    return;
                }

                e = (AbstractCard) i.next();
            } while (e.cardID.equals(Discovery.ID) || e.cardID.equals(Necronomicurse.ID) || e.cardID.equals(AscendersBane.ID));

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


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}