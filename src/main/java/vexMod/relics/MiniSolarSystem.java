package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.RelicGetSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.BouncingRelic;
import vexMod.vfx.OrbitalRelics;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MiniSolarSystem extends CustomRelic implements CustomSavable<Integer> {

    public static final String ID = VexMod.makeID("MiniSolarSystem");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MiniSolarSystem.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public MiniSolarSystem() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new OrbitalRelics());
    }

    @Override
    public Integer onSave() {
        return 1;
    }

    @Override
    public void onLoad(Integer integerthatisone) {
        AbstractDungeon.effectList.add(new OrbitalRelics());
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}