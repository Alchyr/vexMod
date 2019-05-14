package vexMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.monsters.city.Healer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

public class MidasTouchPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = VexMod.makeID("MidasTouchPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("vexModResources/images/powers/MidasTouch_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("vexModResources/images/powers/MidasTouch_32.png");
    public AbstractCreature source;

    public MidasTouchPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = false;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && !target.hasPower(MinionPower.POWER_ID) && !target.id.equals(Darkling.ID) && !target.id.equals(Centurion.ID) && !target.id.equals(Healer.ID)) {
            this.flash();
            int goldGain = damageAmount;
            if (target.hasPower(IntangiblePower.POWER_ID)) {
                goldGain = 1;
            }
            if (target.currentHealth < damageAmount) {
                goldGain = target.currentHealth;
            }
            AbstractDungeon.player.gainGold(goldGain);
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < goldGain; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, target.hb.cX, target.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
            }
            if (MathUtils.randomBoolean(0.05f)) {
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getTaunt(), 0.0F, 2.0F));
            }
        }
    }

    private String getTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(powerStrings.DESCRIPTIONS[1]);
        taunts.add(powerStrings.DESCRIPTIONS[2]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1C"));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MidasTouchPower(owner, source);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}