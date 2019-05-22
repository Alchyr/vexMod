package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class StarEater extends CustomRelic {

    public static final String ID = VexMod.makeID("StarEater");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StarEater.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public StarEater() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void onPlayerEndTurn() {
        if (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) == 1) {
            this.flash();
            int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) - 1);
            StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
            AstrologerMod.stellarUI.updateStellarPhase(woohoo);
            AstrologerMod.stellarUI.updateTooltip();
            if (AbstractDungeon.cardRandomRng.random(19) == 0) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, DESCRIPTIONS[1], 0.0F, 5.0F));
            }
        } else if (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) > 1) {
            this.flash();
            int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) - 2);
            StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
            AstrologerMod.stellarUI.updateStellarPhase(woohoo);
            AstrologerMod.stellarUI.updateTooltip();
            if (AbstractDungeon.cardRandomRng.random(19) == 0) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, DESCRIPTIONS[1], 0.0F, 5.0F));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}