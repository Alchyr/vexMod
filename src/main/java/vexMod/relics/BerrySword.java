package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BerrySword extends CustomRelic {


    public static final String ID = VexMod.makeID("BerrySword");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BerrySword.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BerrySword.png"));


    public BerrySword() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
        this.counter = -1;
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(8, true);
    }

    @Override
    public void atTurnStart() {
        if (counter < 1) {
            this.beginLongPulse();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (counter < 1) {
            this.flash();
            AbstractCard c = getMasterDeckEquivalent(card);
            AbstractDungeon.player.masterDeck.removeCard(c);
            action.exhaustCard = true;
            counter++;
            this.stopPulse();
        }

    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
