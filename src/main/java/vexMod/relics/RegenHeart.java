package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RegenHeart extends CustomRelic {


    public static final String ID = VexMod.makeID("RegenHeart");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RegenVirus.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RegenVirus.png"));


    public RegenHeart() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.maxHealth = 30;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
    }


    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(AbstractDungeon.player.maxHealth);
        }

    }

    public boolean canSpawn() {
        return (!Settings.isEndless && !AbstractDungeon.player.hasRelic(LichBottle.ID) && !AbstractDungeon.player.hasRelic(MidasArmor.ID));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
