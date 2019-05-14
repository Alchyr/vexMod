package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class StrikeShooter extends CustomRelic {


    public static final String ID = VexMod.makeID("StrikeShooter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StrikeShooter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StrikeShooter.png"));


    public StrikeShooter() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        ++this.counter;
        if (this.counter == 3) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard funCard = new Strike_Red();
            AbstractMonster sadMonster = AbstractDungeon.getRandomMonster();
            funCard.freeToPlayOnce = true;
            funCard.purgeOnUse = true;
            if (sadMonster != null) {
                AbstractDungeon.actionManager.addToBottom(new QueueCardAction(funCard, sadMonster));
            }
            this.counter = 0;
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
