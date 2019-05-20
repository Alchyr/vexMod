package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.actions.TimeReclaimAction;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FutureThief extends CustomRelic implements ClickableRelic {

    public static final String ID = VexMod.makeID("FutureThief");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FutureThief.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FutureThief.png"));

    public ArrayList<AbstractCard> stolenCards = new ArrayList<>();

    public FutureThief() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.stolenCards.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new TimeReclaimAction(this));
        }
    }

    @Override
    public void onRightClick() {
        if (!isObtained) {
            return;
        }

        if (((AbstractDungeon.floorNum < 50) || (AbstractDungeon.floorNum < 55 && Settings.hasSapphireKey && Settings.hasRubyKey && Settings.hasEmeraldKey)) && !(stolenCards.size() > 4)) {
            if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                int randomChance = AbstractDungeon.cardRandomRng.random(100);
                if (randomChance <= 85) {
                    AbstractCard randomCardFromDeck = AbstractDungeon.player.masterDeck.group.get(AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.masterDeck.size() - 1));
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(randomCardFromDeck));
                    stolenCards.add(randomCardFromDeck);
                } else {
                    AbstractCard randomCard = AbstractDungeon.returnTrulyRandomCardInCombat();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(randomCard));
                    stolenCards.add(randomCard);
                }
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[2]), 0.0F, 5.0F));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}