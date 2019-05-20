package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.util.TextureLoader;

import java.util.Collections;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockFriend extends CustomRelic {


    public static final String ID = VexMod.makeID("RockFriend");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockFriend.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockFriend.png"));

    private int turnNum;

    public RockFriend() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    private static void moveRelic(AbstractRelic r1, AbstractRelic r2) {
        float oldX = r1.currentX;
        float oldHBX = r1.hb.x;
        r1.currentX = r2.currentX;
        r1.hb.x = r2.hb.x;
        r2.hb.x = oldHBX;
        r2.currentX = oldX;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.player.relics.size() == 3) {
            if (!(AbstractDungeon.player.relics.get(2) instanceof RockFriend) && (AbstractDungeon.player.relics.get(0) instanceof RockFriend || AbstractDungeon.player.relics.get(1) instanceof RockFriend)) {
                AbstractRelic r1 = AbstractDungeon.player.getRelic(RockFriend.ID);
                AbstractRelic r2 = AbstractDungeon.player.relics.get(2);
                moveRelic(r1, r2);
                Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 2);
            }
        } else if (AbstractDungeon.player.relics.size() >= 4) {
            if (!(AbstractDungeon.player.relics.get(3) instanceof RockFriend) && (AbstractDungeon.player.relics.get(0) instanceof RockFriend || AbstractDungeon.player.relics.get(1) instanceof RockFriend || AbstractDungeon.player.relics.get(2) instanceof RockFriend)) {
                AbstractRelic r1 = AbstractDungeon.player.getRelic(RockFriend.ID);
                AbstractRelic r2 = AbstractDungeon.player.relics.get(3);
                moveRelic(r1, r2);
                Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 3);
            }
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        turnNum++;
        if (turnNum % 3 == 0) {
            int upsideRoll = AbstractDungeon.cardRandomRng.random(0, 7);
            if (upsideRoll == 0) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(1, 4)]), 0.0F, 5.0F));
                this.flash();
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false), 1));
                        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
                    }
                }
            } else if (upsideRoll == 1) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(5, 8)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(7, 9)));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (upsideRoll == 2) {
                int goldTaken = AbstractDungeon.cardRandomRng.random(5, 15);
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(9, 12)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.player.gainGold(goldTaken);
                for (int i = 0; i < goldTaken; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (upsideRoll == 3) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(13, 16)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (upsideRoll == 4) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(17, 20)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (upsideRoll == 5) {
                if (AbstractDungeon.player.potions.size() < (AbstractDungeon.player.potionSlots - 1)) {
                    AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(21, 24)]), 0.0F, 5.0F));
                    this.flash();
                    AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion());
                    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                } else {
                    int goldTaken = AbstractDungeon.cardRandomRng.random(5, 15);
                    AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(9, 12)]), 0.0F, 5.0F));
                    this.flash();
                    AbstractDungeon.player.gainGold(goldTaken);
                    for (int i = 0; i < goldTaken; ++i) {
                        AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                    }
                    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                }
            } else if (upsideRoll == 6) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(25, 28)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(7, 10), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (upsideRoll == 7) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(29, 32)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

}