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

public class Trinoculars extends CustomRelic {


    public static final String ID = VexMod.makeID("Trinoculars");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Trinoculars.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public Trinoculars() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) + 42);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
