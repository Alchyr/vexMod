package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TimesightGlass extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("TimesightGlass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TimesightGlass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TimesightGlass.png"));


    public TimesightGlass() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        ++this.counter;
        if (this.counter % 2 == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new RandomCardFromDiscardPileToHandAction());
        }

    }

    public void onVictory() {
        this.counter = -1;
        this.stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
