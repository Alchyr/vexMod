package vexMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

public class SlowDoomPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = VexMod.makeID("SlowDoomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("vexModResources/images/powers/SlowDoom_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("vexModResources/images/powers/SlowDoom_32.png");
    public AbstractCreature source;

    public SlowDoomPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (AbstractDungeon.player.cardsPlayedThisTurn < 1) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SlowDoomPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];


    }
}