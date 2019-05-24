package vexMod.relics;

import ThePokerPlayer.cards.PokerCard;
import ThePokerPlayer.interfaces.IShowdownEffect;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.notes.AttackNote;
import com.evacipated.cardcrawl.mod.bard.notes.BlockNote;
import com.evacipated.cardcrawl.mod.bard.notes.BuffNote;
import com.evacipated.cardcrawl.mod.bard.notes.DebuffNote;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PokerSong extends CustomRelic implements IShowdownEffect {

    public static final String ID = VexMod.makeID("PokerSong");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PokerSong.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PokerSong.png"));

    public PokerSong() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onShowdownStart() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof PokerCard) {
                if (((PokerCard) c).suit == PokerCard.Suit.Diamond) {
                    AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(AttackNote.get()));
                } else if (((PokerCard) c).suit == PokerCard.Suit.Spade) {
                    AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(BlockNote.get()));
                } else if (((PokerCard) c).suit == PokerCard.Suit.Heart) {
                    AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(BuffNote.get()));
                } else if (((PokerCard) c).suit == PokerCard.Suit.Club) {
                    AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(DebuffNote.get()));
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
