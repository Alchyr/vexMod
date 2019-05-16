package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
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

    private static boolean loseRelic = false;

    public MegatonBomb() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public static void relicBullshit() {
        if (loseRelic) {
            AbstractDungeon.player.loseRelic(MegatonBomb.ID);
            loseRelic = false;
        }
    }

    @Override
    public void onPlayerEndTurn() {
        this.flash();
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public void onRightClick() {
        if (!isObtained) {
            return;
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 0.2F));
            int boohoo = 0;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDead && !m.isDying) {
                    boohoo++;
                }
            }
            for (int i = 0; i < (30/boohoo); i++) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    this.flash();
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + MathUtils.random(200, 250) * Settings.scale, m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                    }
                }
            }
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new KillEnemyAction(m));
            }
            AbstractDungeon.getCurrRoom().cannotLose = false;
        }

        CardCrawlGame.sound.play("UI_CLICK_1");
        this.flash();
        loseRelic = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}