package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Powers.Starlit;
import Astrologer.Util.Sounds;
import ThePokerPlayer.cards.PokerCard;
import ThePokerPlayer.interfaces.IShowdownEffect;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class StarPokerPlayer extends CustomRelic implements IShowdownEffect {

    public static final String ID = VexMod.makeID("StarPokerPlayer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StarPokerPlayer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PokerSong.png"));

    public StarPokerPlayer() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onShowdownStart() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof PokerCard) {
                if (((PokerCard) c).suit == PokerCard.Suit.Diamond) {
                    AstrologerMod.drawStellarUI = true;
                    int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + 1);
                    StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
                    CardCrawlGame.sound.play(Sounds.Sparkle.getKey(), 0.3F);
                    AstrologerMod.stellarUI.updateStellarPhase(woohoo);
                    AstrologerMod.stellarUI.updateTooltip();
                } else if (((PokerCard) c).suit == PokerCard.Suit.Spade) {
                    AbstractMonster m = AbstractDungeon.getRandomMonster();// 41
                    if (m != null) {// 42
                        AstrologerMod.drawStellarUI = true;
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, 1), 1));// 43
                    }
                } else if (((PokerCard) c).suit == PokerCard.Suit.Club) {
                    AstrologerMod.drawStellarUI = true;
                    int woohoo = (StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + 1);
                    StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, woohoo);
                    AstrologerMod.stellarUI.updateStellarPhase(woohoo);
                    AstrologerMod.stellarUI.updateTooltip();
                    AbstractMonster m = AbstractDungeon.getRandomMonster();// 41
                    if (m != null) {// 42
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, 1), 1));// 43
                    }
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
