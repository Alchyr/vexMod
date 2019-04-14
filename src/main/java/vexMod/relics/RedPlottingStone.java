package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Finesse;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RedPlottingStone extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     */

    // ID, images, text.
    public static final String ID = VexMod.makeID("RedPlottingStone");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RedPlottingStone.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RedPlottingStone.png"));

    private static boolean gainRelic = false;
    private boolean usedThisTurn = false; // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private static int chosenFloor;

    public RedPlottingStone() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        chosenFloor = AbstractDungeon.cardRandomRng.random((AbstractDungeon.floorNum + 1), 50);
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained || usedThisTurn) {// If it has been used this turn, or the player doesn't actually have the relic (i.e. it's on display in the shop room)
            return; // Don't do anything.
        }
         if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.floorNum == chosenFloor) { // Only if you're in combat
            usedThisTurn = true; // Set relic as "Used this turn"
            flash(); // Flash
            stopPulse(); // And stop the pulsing animation (which is started in atPreBattle() below)

            if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractMonster c = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF")); // Sound Effect Action of The Collector Nails
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(c.hb.cX, c.hb.cY), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(AbstractDungeon.player, 66, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }

            CardCrawlGame.sound.play("GOLD_GAIN");
            AbstractDungeon.player.gainGold(200);
            AbstractDungeon.player.increaseMaxHp(5, true);
            AbstractDungeon.player.heal(10, true);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FlashOfSteel(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Finesse(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            gainRelic = true;
        } else if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.floorNum != chosenFloor) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF")); // Sound Effect Action of The Collector Nails
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 2.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 9999, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

    }

    public static void FuckShitPoo() {
        if (gainRelic) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(RelicTier.COMMON));
            gainRelic = false;
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.floorNum == chosenFloor) {
            this.flash();
            this.beginLongPulse();
        } else {
            this.stopPulse();
        }
    }

    @Override
    public boolean canSpawn() {
        return (!Settings.isEndless || AbstractDungeon.floorNum <= 40);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}