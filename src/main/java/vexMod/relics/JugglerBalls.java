package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.JuggleRelic;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class JugglerBalls extends CustomRelic implements CustomSavable<Integer> {

    public static final String ID = VexMod.makeID("JugglerBalls");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("JugglingBalls.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public JugglerBalls() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            AbstractDungeon.effectList.add(new JuggleRelic(r, false, 1.5F));
        }
    }

    @Override
    public Integer onSave() {
        return 1;
    }

    @Override
    public void onLoad(Integer integerthatisone) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            AbstractDungeon.effectList.add(new JuggleRelic(r, false, 1.5F));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}