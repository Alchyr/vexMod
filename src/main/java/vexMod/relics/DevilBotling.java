package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import vexMod.VexMod;
import vexMod.actions.*;
import vexMod.powers.EndOfTurnDamagePower;
import vexMod.util.TextureLoader;
import vexMod.vfx.RelicSpeechBubble;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
import static vexMod.VexMod.*;

public class DevilBotling extends CustomRelic {


    public static final String ID = VexMod.makeID("DevilBotling");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DevilBotling.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DevilBotling.png"));
    private static int downsideRoll;
    private static int summonRoll;

    public DevilBotling() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 2;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 2;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.player.relics.size() == 2) {
            if (!(AbstractDungeon.player.relics.get(1) instanceof DevilBotling)) {
                AbstractRelic r1 = AbstractDungeon.player.getRelic(DevilBotling.ID);
                AbstractRelic r2 = AbstractDungeon.player.relics.get(1);
                moveRelic(r1, r2);
                Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 1);
            }
        } else if (AbstractDungeon.player.relics.size() >= 3) {
            if (!(AbstractDungeon.player.relics.get(2) instanceof DevilBotling)) {
                AbstractRelic r1 = AbstractDungeon.player.getRelic(DevilBotling.ID);
                AbstractRelic r2 = AbstractDungeon.player.relics.get(2);
                moveRelic(r1, r2);
                Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 2);
            }
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        downsideRoll = AbstractDungeon.cardRandomRng.random(0, 7);
        if (downsideRoll == 0) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(1, 4)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 2, false), 2));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else if (downsideRoll == 1) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(5, 8)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 2, false), 2));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else if (downsideRoll == 2) {
            if (AbstractDungeon.player.gold > 0) {
                int goldTaken = AbstractDungeon.cardRandomRng.random(15, 30);
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(9, 12)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
                this.flash();
                AbstractDungeon.player.loseGold(goldTaken);
                for (int i = 0; i < goldTaken; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, false));
                }
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DevilBlockAction(AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(10, 15)));
            }
        } else if (downsideRoll == 3) {
            if (AbstractDungeon.player.hand.size() > 0) {
                AbstractDungeon.actionManager.addToBottom(new GrabCardAction(this));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DevilBlockAction(AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(10, 15)));
            }
        } else if (downsideRoll == 4) {
            AbstractDungeon.actionManager.addToBottom(new BotlingNerfAction(this));
        } else if (downsideRoll == 5) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(21, 24)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(2));
            AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else if (downsideRoll == 6) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, (DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(25, 28)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndOfTurnDamagePower(AbstractDungeon.player, AbstractDungeon.player, 10), 10));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else if (downsideRoll == 7) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DevilBlockAction(AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(10, 15)));
        }
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
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(73, 75)], 0.0F, 5.0F));
        summonRoll = AbstractDungeon.cardRandomRng.random(0, 1);
        this.flash();
        if (summonRoll == 0) {
            float offsetX = 0;
            float offsetY = 0;
            {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    offsetX = min(((m.drawX - ((float) Settings.WIDTH * 0.75F)) / Settings.scale), offsetX);
                    offsetY = min(m.drawY, offsetY);
                }
                AbstractMonster c = new GremlinWarrior((offsetX - (300F * Settings.scale)), (offsetY - (10F * Settings.scale)));
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(c, false));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(c, c, new AngryPower(c, 1)));// 65
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(c, this));
            }

        } else {
            float offsetX = 0;
            float offsetY = 0;
            {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    offsetX = min(((m.drawX - ((float) Settings.WIDTH * 0.75F)) / Settings.scale), offsetX);
                    offsetY = min(m.drawY, offsetY);
                }
                AbstractMonster c = new GremlinTsundere((offsetX - (300F * Settings.scale)), (offsetY - (10F * Settings.scale)));
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(c, false));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(c, this));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        if (enablePlaceholder) {
            return DESCRIPTIONS[0];
        } else {
            return DESCRIPTIONS[76];
        }
    }

}