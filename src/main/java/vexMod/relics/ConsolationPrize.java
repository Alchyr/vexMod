package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ConsolationPrize
        extends CustomRelic
        implements BetterOnLoseHpRelic {


    public static final String ID = VexMod.makeID("ConsolationPrize");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ConsolationPrize.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ConsolationPrize.png"));

    public ConsolationPrize() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }


    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.player.currentHealth > 0) {
            AbstractDungeon.player.gainGold(damageAmount);
            this.flash();
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < damageAmount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
            }
        }
        return damageAmount;
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 40) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
