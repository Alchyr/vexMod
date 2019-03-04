package vexMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

public class MidasTouchPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = VexMod.makeID("MidasTouchPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("vexModResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("vexModResources/images/powers/placeholder_power32.png");

    public MidasTouchPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS ) {
            this.flash();
            AbstractDungeon.player.gainGold(damageAmount);
            if (MathUtils.randomBoolean(0.05f))
            {
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getTaunt(), 0.0F, 2.0F));
            }
        }
    }

    private String getTaunt() {
        ArrayList<String> taunts = new ArrayList();
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
        return new MidasTouchPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}