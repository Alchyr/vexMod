package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import vexMod.VexMod;
import vexMod.cards.StarBlast;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Starfruit extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("Starfruit");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Starfruit.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Starfruit.png"));


    public Starfruit() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.tips.add(new PowerTip("Magical Fruit", "Magical Fruit are 0-cost Attacks that deal damage, heal you, draw a card, and Exhaust."));
    }

    @Override
    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(6, true);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new StarBlast(), 3, true, true));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
