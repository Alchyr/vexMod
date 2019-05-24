package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.cards.NoteCard;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.evacipated.cardcrawl.mod.bard.patches.CenterGridCardSelectScreen;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BottledTune extends CustomRelic implements CustomSavable<String> {

    public static final String ID = VexMod.makeID("BottledTune");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BottledTune.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LichPhylactery.png"));
    private boolean noteSelected = true;
    private AbstractNote noteGotten;

    public BottledTune() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void update() {
        super.update();// 65
        if (!this.noteSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 66 67
            this.noteSelected = true;// 68
            NoteCard select = (NoteCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            noteGotten = select.note;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;// 71
            AbstractDungeon.gridSelectScreen.selectedCards.clear();// 72
            this.description = this.DESCRIPTIONS[1] + "[" + noteGotten.name() + "Note]" + this.DESCRIPTIONS[2];// 73
            this.tips.clear();// 74
            this.tips.add(new PowerTip(this.name, this.description));// 75
            this.initializeTips();// 76
        }
    }// 79

    @Override
    public void onEquip() {
        this.noteSelected = false;// 35
        if (AbstractDungeon.isScreenUp) {// 36
            AbstractDungeon.dynamicBanner.hide();// 37
            AbstractDungeon.overlayMenu.cancelButton.hide();// 38
            AbstractDungeon.previousScreen = AbstractDungeon.screen;// 39
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;// 41
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);// 27
        Iterator var2 = MelodyManager.getAllNotes().iterator();// 28

        while (var2.hasNext()) {
            AbstractNote note = (AbstractNote) var2.next();
            group.addToTop(note.makeChoiceCard());// 29
        }

        group.sortAlphabetically(true);// 31
        CenterGridCardSelectScreen.centerGridSelect = true;// 33
        AbstractDungeon.gridSelectScreen.open(group, 1, "Choose a Note to Bottle", false);
    }

    public void atBattleStart() {
        this.flash();// 90
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 91
        AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(noteGotten));
    }// 92

    public String onSave() {
        return this.noteGotten.ascii();
    }

    public void onLoad(String asciinote) {
        this.noteGotten = MelodyManager.getNoteByAscii(asciinote);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
