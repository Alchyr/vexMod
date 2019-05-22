package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.cards.Sonata;
import com.evacipated.cardcrawl.mod.bard.notes.WildCardNote;
import com.evacipated.cardcrawl.mod.bard.relics.BagPipes;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MusicMaker extends CustomRelic {

    public static final String ID = VexMod.makeID("MusicMaker");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MusicMaker.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MusicMaker.png"));

    public MusicMaker() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize -= 1;
        AbstractDungeon.rareCardPool.removeCard(Sonata.ID);
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize += 1;
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BagPipes.ID)) {// 52
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {// 53
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BagPipes.ID)) {// 54
                    this.instantObtain(AbstractDungeon.player, i, true);// 55
                    break;// 56
                }
            }
        } else {
            super.obtain();// 60
        }

    }// 62

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(WildCardNote.get()));
    }// 39

    @Override
    public boolean canSpawn() {
        boolean doTheyHaveSonata = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof Sonata) {
                doTheyHaveSonata = true;
            }
        }
        return (AbstractDungeon.player.hasRelic(BagPipes.ID) && !doTheyHaveSonata);
    }

    @Override
    public String getUpdatedDescription() {
        String name = (new BagPipes()).name;// 38
        StringBuilder sb = new StringBuilder();// 39
        String[] var3 = name.split(" ");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {// 40
            String word = var3[var5];
            sb.append("[#").append(BardMod.COLOR.toString()).append("]").append(word).append("[] ");// 41
        }

        sb.setLength(sb.length() - 1);// 43
        sb.append("[#").append(BardMod.COLOR.toString()).append("]");// 44
        return this.DESCRIPTIONS[0] + sb.toString() + this.DESCRIPTIONS[1];// 46
    }

}
