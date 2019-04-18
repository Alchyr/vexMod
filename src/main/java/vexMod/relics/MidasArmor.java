package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.BloodyIdol;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MidasArmor extends CustomRelic implements BetterOnLoseHpRelic {

    // ID, images, text.
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
        if (damageAmount > 0 && AbstractDungeon.player.currentHealth > 0 && damageAmount!=999) {
            AbstractDungeon.player.loseGold(damageAmount * 2);
            this.flash();
            if (info.owner != null && info.owner != AbstractDungeon.player) {
                for (int i = 0; i < (damageAmount * 2); ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, info.owner.hb.cX, info.owner.hb.cY, false));
                }
            }

        }
        return 0;
    }

    public int onMaxHPChange(int amount)
    {
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth || AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
        return 0;
    }

    @Override
    public int getPrice() {
        int cost = AbstractDungeon.player.gold - (AbstractDungeon.player.maxHealth*2);
        if (cost<0)
        {
            cost = 0;
        }
        return cost;
    }

    @Override
    public void onLoseGold() {
        if (AbstractDungeon.player.gold == 0)
        {
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 999, DamageInfo.DamageType.HP_LOSS));
        }
        AbstractDungeon.player.maxHealth = AbstractDungeon.player.gold;
        AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
    }

    public boolean canSpawn() {
        return (!Settings.isEndless && AbstractDungeon.floorNum <= 40 && !AbstractDungeon.player.hasRelic(GoldenIdol.ID) && !AbstractDungeon.player.hasRelic(BloodyIdol.ID));// 50 51
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
