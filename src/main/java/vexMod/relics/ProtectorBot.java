package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ProtectorBot extends CustomRelic {


    public static final String ID = VexMod.makeID("ProtectorBot");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ProtectorBot.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ProtectorBot.png"));


    public ProtectorBot() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 2) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 5));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
