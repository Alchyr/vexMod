package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class HatredEngine extends CustomRelic {


    public static final String ID = VexMod.makeID("HatredEngine");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HatredEngine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HatredEngine.png"));

    public HatredEngine() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40 && !AbstractDungeon.player.hasRelic(MallPass.ID) && !AbstractDungeon.player.hasRelic(TreasureMap.ID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}