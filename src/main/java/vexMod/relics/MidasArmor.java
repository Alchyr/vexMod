package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.LoseGoldTextEffect;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MidasArmor extends CustomRelic implements BetterOnLoseHpRelic {


    public static final String ID = VexMod.makeID("MidasArmor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldenArmor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldenArmor.png"));


    public MidasArmor() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.specialOneTimeEventList.remove(KnowingSkull.ID);
        AbstractDungeon.eventList.remove(MindBloom.ID);
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth || AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
    }

    @Override
    public void onGainGold() {
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth || AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
    }

    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.player.currentHealth > 0) {
            AbstractDungeon.player.loseGold(damageAmount * 2);
            this.flash();
            if (info.owner != null && info.owner != AbstractDungeon.player) {
                for (int i = 0; i < (damageAmount * 2); ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, info.owner.hb.cX, info.owner.hb.cY, false));
                }
                AbstractDungeon.effectList.add(new LoseGoldTextEffect(damageAmount * 2));
            }
        }
        return 0;
    }

    public int onMaxHPChange() {
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth || AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
        return 0;
    }

    @Override
    public int getPrice() {
        int cost = AbstractDungeon.player.gold - (AbstractDungeon.player.maxHealth * 2);
        if (cost < 1) {
            cost = 1;
        }
        return cost;
    }

    @Override
    public void onLoseGold() {
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        if (AbstractDungeon.player.gold <= 0) {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }
    }

    public boolean canSpawn() {
        return (!Settings.isEndless && !AbstractDungeon.player.hasRelic(LichBottle.ID) && !AbstractDungeon.player.hasRelic(RegenHeart.ID));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
