package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.orbs.GoldenLightning;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class OrbOfGreed extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("OrbOfGreed");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("OrbOfGreed.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("OrbOfGreed.png"));


    public OrbOfGreed() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.tips.add(new PowerTip("Golden Lightning", "Orb: Deals damage to random enemies. NL Produces #yGold."));
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.channelOrb(new GoldenLightning());
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
