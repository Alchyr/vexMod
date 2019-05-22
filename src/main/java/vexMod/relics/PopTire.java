package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.BouncingRelic;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PopTire extends CustomRelic implements CustomSavable<Integer> {

    public static final String ID = VexMod.makeID("PopTire");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PopTire.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PopTire.png"));

    public static int highScore = 0;

    public PopTire() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new BouncingRelic(this, true, 2));
    }

    @Override
    public Integer onSave() {
        return highScore;
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onLoad(Integer highSchool) {
        highScore = highSchool;
        AbstractDungeon.effectList.add(new BouncingRelic(this, true, 2));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1] + highScore;
    }

}