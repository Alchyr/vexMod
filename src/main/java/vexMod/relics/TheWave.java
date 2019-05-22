package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.RelicWave;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TheWave extends CustomRelic implements CustomSavable<Integer> {

    public static final String ID = VexMod.makeID("TheWave");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TheWave.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TheWave.png"));

    public TheWave() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new RelicWave((float) Settings.HEIGHT - 102.0F * Settings.scale));
    }

    @Override
    public Integer onSave() {
        return 1;
    }

    @Override
    public void onLoad(Integer integerthatisone) {
        AbstractDungeon.effectList.add(new RelicWave((float) Settings.HEIGHT - 102.0F * Settings.scale));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}