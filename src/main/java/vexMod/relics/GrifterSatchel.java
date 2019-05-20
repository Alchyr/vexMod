package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GrifterSatchel extends CustomRelic {


    public static final String ID = VexMod.makeID("GrifterSatchel");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GrifterSatchel.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GrifterSatchel.png"));


    public GrifterSatchel() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.getCurrRoom().addCardReward(new RewardItem(AbstractCard.CardColor.COLORLESS));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
