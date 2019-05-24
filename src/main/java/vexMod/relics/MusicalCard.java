package vexMod.relics;

import ThePokerPlayer.actions.MakePokerCardInHandAction;
import ThePokerPlayer.cards.PokerCard;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnMelodyPlayedHook;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MusicalCard extends CustomRelic implements OnMelodyPlayedHook {

    public static final String ID = VexMod.makeID("MusicalCard");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MusicalCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GenericCardOutline.png"));

    public MusicalCard() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onMelodyPlayed(AbstractMelody melody) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakePokerCardInHandAction(PokerCard.Suit.values()[AbstractDungeon.cardRandomRng.random(3)], AbstractDungeon.cardRandomRng.random(1, 10), true));// 43 44 45
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
