//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.events;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.BustedCrown;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.cards.*;

import java.util.Iterator;
import java.util.List;

import static vexMod.VexMod.makeEventPath;

public class ChampiresEvent extends AbstractImageEvent {
    public static final String ID = VexMod.makeID("ChampiresEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("ChampiresEvent.png");
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final String GIVE_VIAL;
    private int screenNum = 0;
    private boolean hasVial;
    private List<String> bites;

    public ChampiresEvent() {
        super(NAME, "test", "images/events/vampires.jpg");
        this.body = DESCRIPTIONS[0];
        this.hasVial = AbstractDungeon.player.hasRelic("Busted Crown");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        if (this.hasVial) {
            String vialName = (new BustedCrown()).name;
            this.imageEventText.setDialogOption(OPTIONS[2] + vialName + OPTIONS[3]);
        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.player.maxHealth = 42;
                        if (AbstractDungeon.player.maxHealth <= 0) {
                            AbstractDungeon.player.maxHealth = 1;
                        }

                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        this.replaceAttacks();
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        if (this.hasVial) {
                            CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                            this.imageEventText.updateBodyText(GIVE_VIAL);
                            AbstractDungeon.player.loseRelic("Busted Crown");
                            this.replaceAttacks();
                            this.screenNum = 1;
                            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                            this.imageEventText.clearRemainingOptions();
                            return;
                        }
                    default:
                        this.imageEventText.updateBodyText(EXIT_BODY);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }

    private void replaceAttacks() {
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while (true) {
            AbstractCard e;
            do {
                if (!i.hasNext()) {
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FightResponse(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new DefensiveStance(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Execute(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FaceSlap(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Gloat(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new HeavySlash(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Insult(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new DefensiveStance(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                    return;
                }

                e = (AbstractCard) i.next();
            } while (!(e.hasTag(BaseModCardTags.BASIC_DEFEND)));

            i.remove();
        }
    }

    static {
        ACCEPT_BODY = DESCRIPTIONS[1];
        EXIT_BODY = DESCRIPTIONS[2];
        GIVE_VIAL = DESCRIPTIONS[3];
    }
}
