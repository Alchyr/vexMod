package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ThirteenHourStone extends CustomRelic {


    public static final String ID = VexMod.makeID("ThirteenHourStone");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ThirteenHourStone.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ThirteenHourStone.png"));


    public ThirteenHourStone() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.counter = -1;
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        counter++;
        if (counter == 13) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            counter = 0;
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
