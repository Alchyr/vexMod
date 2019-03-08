package vexMod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vexMod.cards.*;
import vexMod.events.GenieBoonEvent;
import vexMod.events.LookAheadEvent;
import vexMod.events.StrangeSmithEvent;
import vexMod.events.TripleChoiceEvent;
import vexMod.relics.*;
import vexMod.util.TextureLoader;
import vexMod.variables.DefaultSecondMagicNumber;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class VexMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(VexMod.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "VexMod";
    private static final String AUTHOR = "DarkVexon";
    private static final String DESCRIPTION = "A minor content mod.";

    // =============== INPUT TEXTURE LOCATION =================

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "vexModResources/images/Badge.png";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================

    // =============== SUBSCRIBE, INITIALIZE =================

    public VexMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        setModID("vexMod");

        logger.info("Done subscribing");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    // I DID IT ANYWAYS :P

    public static void setModID(String ID) { // DON'T EDIT
        if (ID.equals("theDefault")) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException("Go to your constructor in your class with SpireInitializer and change your mod ID from theDefault"); // THIS ALSO DON'T EDIT
        } else if (ID.equals("theDefaultDev")) { // NO
            modID = "theDefault"; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS NOT EVEN A LITTLE
        } // NO
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        String packageName = VexMod.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS
        if (!modID.equals("theDefaultDev")) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException("Rename your theDefault folder to match your mod ID! " + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException("Rename your theDefaultResources folder to match your mod ID! " + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        VexMod vexmod = new VexMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("VexMod doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(GenieBoonEvent.ID, GenieBoonEvent.class, TheCity.ID);
        BaseMod.addEvent(TripleChoiceEvent.ID, TripleChoiceEvent.class);
        BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, TheCity.ID);
        BaseMod.addEvent(LookAheadEvent.ID, LookAheadEvent.class, Exordium.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");

    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelic(new ColdYogurt(), RelicType.BLUE);
        BaseMod.addRelic(new ConcentrateValve(), RelicType.BLUE);
        BaseMod.addRelic(new BejeweledOrb(), RelicType.BLUE);
        BaseMod.addRelic(new CoolingFan(), RelicType.BLUE);
        BaseMod.addRelic(new CodeSmelter(), RelicType.BLUE);
        BaseMod.addRelic(new PowerCharger(), RelicType.BLUE);
        BaseMod.addRelic(new SystemVirus(), RelicType.BLUE);
        BaseMod.addRelic(new MemoryStick(), RelicType.GREEN);
        BaseMod.addRelic(new InvisibilityCloak(), RelicType.GREEN);
        BaseMod.addRelic(new SalvageTools(), RelicType.GREEN);
        BaseMod.addRelic(new StickyTar(), RelicType.GREEN);
        BaseMod.addRelic(new RetaliationKit(), RelicType.GREEN);
        BaseMod.addRelic(new ThoughtMold(), RelicType.RED);
        BaseMod.addRelic(new ChargeUpper(), RelicType.RED);
        BaseMod.addRelic(new StrikeShooter(), RelicType.RED);
        BaseMod.addRelic(new MoralityCore(), RelicType.RED);
        BaseMod.addRelic(new CursedBlade(), RelicType.RED);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new ConsolationPrize(), RelicType.SHARED);
        BaseMod.addRelic(new PlagueVial(), RelicType.SHARED);
        BaseMod.addRelic(new ShiftingSkin(), RelicType.SHARED);
        BaseMod.addRelic(new ProtectorBot(), RelicType.SHARED);
        BaseMod.addRelic(new PainConverter(), RelicType.SHARED);
        BaseMod.addRelic(new BetterTron(), RelicType.SHARED);
        BaseMod.addRelic(new GhostlyGear(), RelicType.SHARED);
        BaseMod.addRelic(new CursedCompass(), RelicType.SHARED);
        BaseMod.addRelic(new Cooldron(), RelicType.SHARED);
        BaseMod.addRelic(new TanglingVine(), RelicType.SHARED);
        BaseMod.addRelic(new MindDevourer(), RelicType.SHARED);
        BaseMod.addRelic(new SpinShoes(), RelicType.SHARED);
        BaseMod.addRelic(new HappyDrink(), RelicType.SHARED);
        BaseMod.addRelic(new ShortStop(), RelicType.SHARED);
        BaseMod.addRelic(new EnergyShake(), RelicType.SHARED);
        BaseMod.addRelic(new PukeWorm(), RelicType.SHARED);
        BaseMod.addRelic(new PitifulBounty(), RelicType.SHARED);
        BaseMod.addRelic(new FocusSash(), RelicType.SHARED);
        BaseMod.addRelic(new BigBerry(), RelicType.SHARED);
        BaseMod.addRelic(new KeyChain(), RelicType.SHARED);
        BaseMod.addRelic(new CardConverter(), RelicType.SHARED);
        // BaseMod.addRelic(new DrawConverter(), RelicType.SHARED);
        BaseMod.addRelic(new BrokenBowl(), RelicType.SHARED);
        BaseMod.addRelic(new PillTracker(), RelicType.SHARED);
        BaseMod.addRelic(new SlimeArmor(), RelicType.SHARED);
        BaseMod.addRelic(new ScavengeHelm(), RelicType.SHARED);
        BaseMod.addRelic(new BroadSword(), RelicType.SHARED);
        BaseMod.addRelic(new DiamondSword(), RelicType.SHARED);
        BaseMod.addRelic(new DoubleEdgedSword(), RelicType.SHARED);
        BaseMod.addRelic(new DrainingSword(), RelicType.SHARED);
        BaseMod.addRelic(new ImaginarySword(), RelicType.SHARED);
        BaseMod.addRelic(new ToySword(), RelicType.SHARED);
        BaseMod.addRelic(new RustySword(), RelicType.SHARED);
        BaseMod.addRelic(new InfernalSword(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(ColdYogurt.ID);
        UnlockTracker.markRelicAsSeen(ConsolationPrize.ID);
        UnlockTracker.markRelicAsSeen(PlagueVial.ID);
        UnlockTracker.markRelicAsSeen(ShiftingSkin.ID);
        UnlockTracker.markRelicAsSeen(ProtectorBot.ID);
        UnlockTracker.markRelicAsSeen(PainConverter.ID);
        UnlockTracker.markRelicAsSeen(BetterTron.ID);
        UnlockTracker.markRelicAsSeen(GhostlyGear.ID);
        UnlockTracker.markRelicAsSeen(CursedCompass.ID);
        UnlockTracker.markRelicAsSeen(ConcentrateValve.ID);
        UnlockTracker.markRelicAsSeen(BejeweledOrb.ID);
        UnlockTracker.markRelicAsSeen(MemoryStick.ID);
        UnlockTracker.markRelicAsSeen(CoolingFan.ID);
        UnlockTracker.markRelicAsSeen(Cooldron.ID);
        UnlockTracker.markRelicAsSeen(InvisibilityCloak.ID);
        UnlockTracker.markRelicAsSeen(CodeSmelter.ID);
        UnlockTracker.markRelicAsSeen(TanglingVine.ID);
        UnlockTracker.markRelicAsSeen(MindDevourer.ID);
        UnlockTracker.markRelicAsSeen(SpinShoes.ID);
        UnlockTracker.markRelicAsSeen(HappyDrink.ID);
        UnlockTracker.markRelicAsSeen(ShortStop.ID);
        UnlockTracker.markRelicAsSeen(EnergyShake.ID);
        UnlockTracker.markRelicAsSeen(PukeWorm.ID);
        UnlockTracker.markRelicAsSeen(PitifulBounty.ID);
        UnlockTracker.markRelicAsSeen(SalvageTools.ID);
        UnlockTracker.markRelicAsSeen(ThoughtMold.ID);
        UnlockTracker.markRelicAsSeen(ChargeUpper.ID);
        UnlockTracker.markRelicAsSeen(FocusSash.ID);
        UnlockTracker.markRelicAsSeen(PowerCharger.ID);
        UnlockTracker.markRelicAsSeen(StrikeShooter.ID);
        UnlockTracker.markRelicAsSeen(StickyTar.ID);
        UnlockTracker.markRelicAsSeen(MoralityCore.ID);
        UnlockTracker.markRelicAsSeen(SystemVirus.ID);
        UnlockTracker.markRelicAsSeen(BigBerry.ID);
        UnlockTracker.markRelicAsSeen(KeyChain.ID);
        UnlockTracker.markRelicAsSeen(CursedBlade.ID);
        UnlockTracker.markRelicAsSeen(CardConverter.ID);
        // UnlockTracker.markRelicAsSeen(DrawConverter.ID);
        UnlockTracker.markRelicAsSeen(BrokenBowl.ID);
        UnlockTracker.markRelicAsSeen(PillTracker.ID);
        UnlockTracker.markRelicAsSeen(SlimeArmor.ID);
        UnlockTracker.markRelicAsSeen(ScavengeHelm.ID);
        UnlockTracker.markRelicAsSeen(BroadSword.ID);
        UnlockTracker.markRelicAsSeen(DiamondSword.ID);
        UnlockTracker.markRelicAsSeen(DoubleEdgedSword.ID);
        UnlockTracker.markRelicAsSeen(DrainingSword.ID);
        UnlockTracker.markRelicAsSeen(ImaginarySword.ID);
        UnlockTracker.markRelicAsSeen(RustySword.ID);
        UnlockTracker.markRelicAsSeen(ToySword.ID);
        UnlockTracker.markRelicAsSeen(RetaliationKit.ID);
        UnlockTracker.markRelicAsSeen(InfernalSword.ID);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new BlockBeam());
        BaseMod.addCard(new DrainingDoom());
        BaseMod.addCard(new CursedBody());
        BaseMod.addCard(new CleverClash());
        BaseMod.addCard(new DeadlyDodge());
        BaseMod.addCard(new ReflexChannel());
        BaseMod.addCard(new Fear());
        BaseMod.addCard(new Greed());
        BaseMod.addCard(new StrikeStorm());
        BaseMod.addCard(new ChaoticReactor());
        BaseMod.addCard(new VenomSigh());
        BaseMod.addCard(new BurstPulse());
        BaseMod.addCard(new Overload());
        BaseMod.addCard(new CrumblingCrash());
        BaseMod.addCard(new ComboTrick());
        BaseMod.addCard(new ChargeUp());
        BaseMod.addCard(new MidasTouch());
        BaseMod.addCard(new PotOfGreed());
        BaseMod.addCard(new BloodGuard());
        BaseMod.addCard(new Jackpot());
        BaseMod.addCard(new SnowShield());
        BaseMod.addCard(new DebrisGuard());
        BaseMod.addCard(new DebrisSlash());
        // BaseMod.addCard(new EntryPlan());
        BaseMod.addCard(new PrepareVictim());
        BaseMod.addCard(new ShockKick());
        BaseMod.addCard(new Taunt());
        BaseMod.addCard(new BandageWhip());
        BaseMod.addCard(new BeamPop());
        BaseMod.addCard(new ChargeBeam());
        BaseMod.addCard(new Devastation());
        BaseMod.addCard(new Elimination());
        BaseMod.addCard(new FullService());
        BaseMod.addCard(new PinpointKick());
        BaseMod.addCard(new Plotting());
        BaseMod.addCard(new ShadowStrike());
        BaseMod.addCard(new BattleStance());
        // BaseMod.addCard(new BoulderThrow());
        BaseMod.addCard(new Camo());
        BaseMod.addCard(new CardboardBlock());
        BaseMod.addCard(new EvasiveThoughts());
        BaseMod.addCard(new GatherTools());
        BaseMod.addCard(new GroundPound());
        BaseMod.addCard(new Hatred());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new MidnightStrike());
        BaseMod.addCard(new RocketPunch());
        BaseMod.addCard(new SharpOrbs());
        BaseMod.addCard(new Sloth());
        BaseMod.addCard(new BlazeBeam());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.

        UnlockTracker.unlockCard(BlockBeam.ID);
        UnlockTracker.unlockCard(DrainingDoom.ID);
        UnlockTracker.unlockCard(CursedBody.ID);
        UnlockTracker.unlockCard(CleverClash.ID);
        UnlockTracker.unlockCard(DeadlyDodge.ID);
        UnlockTracker.unlockCard(ReflexChannel.ID);
        UnlockTracker.unlockCard(Fear.ID);
        UnlockTracker.unlockCard(Greed.ID);
        UnlockTracker.unlockCard(StrikeStorm.ID);
        UnlockTracker.unlockCard(ChaoticReactor.ID);
        UnlockTracker.unlockCard(VenomSigh.ID);
        UnlockTracker.unlockCard(BurstPulse.ID);
        UnlockTracker.unlockCard(Overload.ID);
        UnlockTracker.unlockCard(CrumblingCrash.ID);
        UnlockTracker.unlockCard(ComboTrick.ID);
        UnlockTracker.unlockCard(ChargeUp.ID);
        UnlockTracker.unlockCard(MidasTouch.ID);
        UnlockTracker.unlockCard(PotOfGreed.ID);
        UnlockTracker.unlockCard(BloodGuard.ID);
        UnlockTracker.unlockCard(Jackpot.ID);
        UnlockTracker.unlockCard(SnowShield.ID);
        UnlockTracker.unlockCard(DebrisGuard.ID);
        UnlockTracker.unlockCard(DebrisSlash.ID);
        // UnlockTracker.unlockCard(EntryPlan.ID);
        UnlockTracker.unlockCard(PrepareVictim.ID);
        UnlockTracker.unlockCard(ShockKick.ID);
        UnlockTracker.unlockCard(Taunt.ID);
        UnlockTracker.unlockCard(BandageWhip.ID);
        UnlockTracker.unlockCard(BeamPop.ID);
        UnlockTracker.unlockCard(ChargeBeam.ID);
        UnlockTracker.unlockCard(Devastation.ID);
        UnlockTracker.unlockCard(Elimination.ID);
        UnlockTracker.unlockCard(FullService.ID);
        UnlockTracker.unlockCard(PinpointKick.ID);
        UnlockTracker.unlockCard(Plotting.ID);
        UnlockTracker.unlockCard(ShadowStrike.ID);
        UnlockTracker.unlockCard(BattleStance.ID);
        // UnlockTracker.unlockCard(BoulderThrow.ID);
        UnlockTracker.unlockCard(Camo.ID);
        UnlockTracker.unlockCard(CardboardBlock.ID);
        UnlockTracker.unlockCard(EvasiveThoughts.ID);
        UnlockTracker.unlockCard(GatherTools.ID);
        UnlockTracker.unlockCard(GroundPound.ID);
        UnlockTracker.unlockCard(Hatred.ID);
        UnlockTracker.unlockCard(Hex.ID);
        UnlockTracker.unlockCard(MidnightStrike.ID);
        UnlockTracker.unlockCard(RocketPunch.ID);
        UnlockTracker.unlockCard(SharpOrbs.ID);
        UnlockTracker.unlockCard(Sloth.ID);
        UnlockTracker.unlockCard(BlazeBeam.ID);

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/vexMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/vexMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/vexMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/vexMod-Event-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

}
