package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class CoolingFan extends CustomRelic implements OnChannelRelic {


    public static final String ID = VexMod.makeID("CoolingFan");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CoolingFan.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CoolingFan.png"));


    public CoolingFan() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 2));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
