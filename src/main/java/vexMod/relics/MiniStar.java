package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MiniStar extends CustomRelic {

    public static final String ID = VexMod.makeID("MiniStar");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MiniStar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MiniStar.png"));

    public MiniStar() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.flash();
        int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + 1);
        StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
        AstrologerMod.stellarUI.updateStellarPhase(woohoo);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}