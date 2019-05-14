package vexMod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeOrbPath;

public class GoldenLightning extends AbstractOrb {
    private static final String ORB_ID = "GoldenLightning";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final String[] DESC = orbString.DESCRIPTION;
    private float vfxTimer = 1.0F;
    private int GOLD_MADE;

    public GoldenLightning() {
        this.ID = "GoldenLightning";
        this.img = TextureLoader.getTexture(makeOrbPath("default_orb.png"));
        this.name = orbString.NAME;
        this.baseEvokeAmount = 10;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 5;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        this.GOLD_MADE = 0;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = DESC[0] + this.passiveAmount + DESC[1] + this.passiveAmount + DESC[2] + this.evokeAmount + DESC[3] + this.evokeAmount + DESC[4];
    }

    public void onEvoke() {
        if (AbstractDungeon.player.hasPower("Electro")) {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), true));
        } else {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), false));
        }
        for (int i = 0; i < this.evokeAmount; i++) {
            if (GOLD_MADE < 20) {
                AbstractDungeon.player.gainGold(1);
                if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
                GOLD_MADE++;
            }
        }

    }

    public void onEndOfTurn() {
        if (AbstractDungeon.player.hasPower("Electro")) {
            float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE) {
                speedTime = 0.0F;
            }

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.LIGHTNING), speedTime));
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageType.THORNS), true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageType.THORNS), this, false));
        }

        for (int i = 0; i < this.passiveAmount; i++) {
            if (GOLD_MADE < 20) {
                AbstractDungeon.player.gainGold(1);
                if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
                GOLD_MADE++;
            }
        }


    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }

            float vfxIntervalMin = 0.15F;
            float vfxIntervalMax = 0.8F;
            this.vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new GoldenLightning();
    }

}
