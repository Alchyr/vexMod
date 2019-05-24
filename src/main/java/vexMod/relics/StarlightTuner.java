package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Powers.Starlit;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnMelodyPlayedHook;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class StarlightTuner extends CustomRelic implements OnMelodyPlayedHook {

    public static final String ID = VexMod.makeID("StarlightTuner");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StarlightTuner.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public StarlightTuner() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onMelodyPlayed(AbstractMelody melody) {
        this.flash();
        int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + 1);
        StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
        AstrologerMod.stellarUI.updateStellarPhase(woohoo);
        AstrologerMod.stellarUI.updateTooltip();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDead && !m.isDying) {// 26
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, 1), 1));// 27
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
