package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;
import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ThirteenHourStone extends CustomRelic {

    // ID, images, text.
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

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
