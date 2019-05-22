package vexMod.relics;

import Astrologer.Actions.Generic.BottomToTopAction;
import Astrologer.Actions.Generic.DrawAndSaveCardsAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GravityRainbow extends CustomRelic {


    public static final String ID = VexMod.makeID("GravityRainbow");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GravityRainbow.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GravityRainbow.png"));

    public GravityRainbow() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        ++this.counter;
        if (this.counter % 2 == 1) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            DrawAndSaveCardsAction drawAction;
            if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.player.discardPile.isEmpty()) {// 75
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());// 77
                AbstractDungeon.actionManager.addToBottom(new BottomToTopAction(AbstractDungeon.player.drawPile));// 78
                drawAction = new DrawAndSaveCardsAction(AbstractDungeon.player, 1);// 79
                AbstractDungeon.actionManager.addToBottom(drawAction);// 80
            } else if (!AbstractDungeon.player.drawPile.isEmpty()) {// 83
                AbstractDungeon.actionManager.addToBottom(new BottomToTopAction(AbstractDungeon.player.drawPile));// 85
                drawAction = new DrawAndSaveCardsAction(AbstractDungeon.player, 1);// 86
                AbstractDungeon.actionManager.addToBottom(drawAction);// 87
            }
        }
    }

    public void onVictory() {
        this.counter = -1;
        this.stopPulse();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
