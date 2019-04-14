package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class WellWornAnklet extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("WellWornAnklet");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WellWornAnklet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WellWornAnklet.png"));


    public WellWornAnklet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        ++this.counter;
        if (this.counter == 3 || this.counter == 7) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 7));
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
