package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class CodeSmelter extends CustomRelic implements OnChannelRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("CodeSmelter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CodeSmelter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CodeSmelter.png"));


    public CodeSmelter() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        this.flash();
        orb.onStartOfTurn();
        orb.onEndOfTurn();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
