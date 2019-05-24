package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import vexMod.VexMod;
import vexMod.actions.KillEnemyAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MegatonBomb extends CustomRelic implements ClickableRelic {

    public static final String ID = VexMod.makeID("MegatonBomb");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MegatonBomb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MegatonBomb.png"));

    public MegatonBomb() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        if (!this.usedUp) {
            this.flash();
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS));
        }
    }

    @Override
    public void onRightClick() {
        if (!isObtained) {
            return;
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !this.usedUp) {
            this.setCounter(-2);
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 0.2F));
            int boohoo = 0;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDead && !m.isDying) {
                    boohoo++;
                }
            }
            for (int i = 0; i < ((AbstractDungeon.cardRandomRng.random(20, 40)) / boohoo); i++) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    this.flash();
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                        for (int p = 0; p < 6; p++) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
                        }
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + MathUtils.random(200, 250) * Settings.scale, m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.FIRE)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
                    }
                }
            }
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new KillEnemyAction(m));
            }
            AbstractDungeon.getCurrRoom().cannotLose = false;
        } else {
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;// 40
        if (counter == -2) {// 41
            this.img = TextureLoader.getTexture(makeRelicPath("MegatonBombUsed.png"));
            this.usedUp();// 43
            this.counter = -2;// 44
        }
    }// 46


    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.floorNum <= 18);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}