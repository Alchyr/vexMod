package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PowerCharger extends CustomRelic {


    public static final String ID = VexMod.makeID("PowerCharger");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PowerCharger.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PowerCharger.png"));


    public PowerCharger() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.maxOrbs - AbstractDungeon.player.filledOrbCount() > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
