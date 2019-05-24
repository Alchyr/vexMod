package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TanglingVine extends CustomRelic {

    public static final String ID = VexMod.makeID("TanglingVine");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TanglingVine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TanglingVine.png"));

    public TanglingVine() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }


    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.currentBlock > 0) {
            this.flash();
            try {
                Method m = AbstractCreature.class.getDeclaredMethod("decrementBlock", DamageInfo.class, Integer.TYPE);// 37
                m.setAccessible(true);// 38
                m.invoke(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.NORMAL), 3);// 39
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var3) {// 40
            }
        }
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}