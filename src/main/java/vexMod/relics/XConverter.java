package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class XConverter extends CustomRelic {


    public static final String ID = VexMod.makeID("XConverter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("XConverter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public XConverter() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost == -1)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
