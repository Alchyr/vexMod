package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.*;
import vexMod.VexMod;
import vexMod.actions.*;

import com.evacipated.cardcrawl.mod.stslib.relics.*;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BejeweledOrb extends CustomRelic implements OnChannelRelic {

    public static final String ID = VexMod.makeID("BejeweledOrb");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BejeweledOrb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BejeweledOrb.png"));
    private static final int CHANNEL_AMOUNT = 2;

    public BejeweledOrb() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onChannel(AbstractOrb orb) {
        AbstractDungeon.actionManager.addToTop(new BejeweledOrbAction()); // It got weird when I tried putting the actual stuff in both hooks. This is safer.
    }

    public void onEvokeOrb(AbstractOrb orb) {
        AbstractDungeon.actionManager.addToTop(new BejeweledOrbAction());
    }

    public AbstractRelic makeCopy() {
        return new BejeweledOrb();
    }
}