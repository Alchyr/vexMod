package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import vexMod.VexMod;

public class CardEater extends AbstractMonster {
    public static final String ID = VexMod.makeID("CardEater"); // Makes the monster ID based on your mod's ID. For example: theDefault:CardEater
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] MOVES = monsterstrings.MOVES; // move names,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static int HP_MIN; // = (AbstractDungeon.player.masterDeck.size()*2); // Always good to back up your health and move values.
    private static int HP_MAX; // = (AbstractDungeon.player.masterDeck.size()*2);
    private static int A_7_HP_MIN;// MIN = (AbstractDungeon.player.masterDeck.size()*3);
    private static int A_7_HP_MAX;// MAX = (AbstractDungeon.player.masterDeck.size()*3);
    private static final float HB_X = 0.0F;
    private static final float HB_Y = -25.0F;
    private static final float HB_W = 260.0F;
    private static final float HB_H = 170.0F;
    private static final int ATTACK1_DMG = 0;
    private static final int BLOCK_BLOCK = 0;
    private static final int DEBUFF_CARDS = 3;
    private static final int DEBUFF_CARDS_ASC_17 = 5;
    private int blockBlock;
    private int attackDmg;
    private int debuffCards;
    private static final byte ATTACK1 = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte BLOCK_WOUND = 2;
    private static final byte DEBUFFCARDS = 3;
    private boolean firstTurn = true;

    public CardEater(float x, float y) {
        super(NAME, "JawWorm", 44, HB_X, HB_Y, HB_W, HB_H, (String) null, x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 7) { // Checks if your Ascension is 7 or above...
            this.setHp(AbstractDungeon.player.masterDeck.size() * 10, AbstractDungeon.player.masterDeck.size() * 10); // and increases HP if so.
        } else {
            this.setHp(AbstractDungeon.player.masterDeck.size() * 10, AbstractDungeon.player.masterDeck.size() * 10); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.debuffCards = DEBUFF_CARDS_ASC_17;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.debuffCards = DEBUFF_CARDS;
        } else {
            this.debuffCards = DEBUFF_CARDS;
        }

        this.loadAnimation("images/monsters/theBottom/jawWorm/skeleton.atlas", "images/monsters/theBottom/jawWorm/skeleton.json", 1.0F); // Loads enemy animation skeletons.
        TrackEntry e = this.state.setAnimation(0, "idle", true); // Sets initial animation state.
        e.setTime(e.getEndTime() * MathUtils.random()); // Randomizes the animation start point, so multiple identical enemies aren't in sync.
    }

    //public void usePreBattleAction() {
    //This can be used to have a monster do something at the beginning of the fight. Like enemies that start with certain Powers. Just actionmanager add to bottom the power application here!
    // }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }


        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "chomp")); // Sets attack animation.
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F)); // Plays visual effects for attack.
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(this.damage.size() - 1), AttackEffect.NONE)); // Deals the big damage.
                break;
            case 2:
                this.state.setAnimation(0, "tailslam", false);
                this.state.addAnimation(0, "idle", true, 0.0F); // Adds an animation.
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_JAW_WORM_BELLOW")); // Plays a sound.
                AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2F, ShakeDur.SHORT, ShakeIntensity.MED)); // Shakes the screen.
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F)); // Waits between actions.
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wound(), 2));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, AbstractDungeon.player.drawPile.size())); // Gives the enemy block.
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Wound(), debuffCards, true, true));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement. This is set once at the beginning of combat and never changed. It's seeded. You can use this to make a few "archetype AI"s for enemies.

        this.damage.add(new DamageInfo(this, AbstractDungeon.player.discardPile.size())); // Creates a damageInfo for each attac

        if (num < 25) { // Checks if the number is below 25.
            if (this.lastMove((byte) 1)) { // Checks if the last attack was a regular attack.
                if (AbstractDungeon.aiRng.randomBoolean(0.5625F)) { // Checks a random chance, in this case a little more than 1/2.
                    this.setMove(MOVES[0], (byte) 2, Intent.DEFEND_DEBUFF); // Sets the monster's intent to the buff if the chance goes through.
                } else {
                    this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the monster's intent to the attack and block if the chance fails.
                }
            } else {
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(this.damage.size() - 1)).base); // Sets the move to regular attack if the last move wasn't regular attack.
            }
        } else if (num < 55) { // checks if the number is below 55.
            if (this.lastTwoMoves((byte) 3)) { // Checks if the last two moves were both attack and block.
                if (AbstractDungeon.aiRng.randomBoolean(0.357F)) { // Checks a random chance. This case is around 35%.
                    this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(this.damage.size() - 1)).base); // Sets the monster's intent to the regular attack.
                } else {
                    this.setMove(MOVES[0], (byte) 2, Intent.DEFEND_DEBUFF); // Sets the monster's intent to the defensive buff.
                }
            } else {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the intent to attack and block.
            }
        } else if (this.lastMove((byte) 2)) { // Checks if the last move was the buff.
            if (AbstractDungeon.aiRng.randomBoolean(0.416F)) { // Checks a random boolean - in this case slightly less than 1/2.
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(this.damage.size() - 1)).base); // Sets the intent to the regular attack.
            } else {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the intent to the attaak and block.
            }
        } else {
            this.setMove(MOVES[0], (byte) 2, Intent.DEFEND_DEBUFF); // If none of the above things were done, sets the intent to the defensive buff.
        }

    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        CardCrawlGame.sound.play("JAW_WORM_DEATH"); // And it croaks too.
    }

}// You made it! End of monster.
