package free.l2j.simfactory.model.actor.ai.preference;

import java.util.Arrays;
import java.util.List;

import free.l2j.simfactory.model.actor.SimPlayer;

public class TeleportModule {
    public enum TCity {
        DWARVEN(0), ORC(1), DARK_ELVEN(2), ELVEN(3), TALKING_ISLAND(4),
            HEINE(5), DION(6), GIRAN(7), ADEN(8), GODDARD(9), GLUDIO(10), GLUDIN(11),
            SHTUTTGART(12), RUNE(13), OREN(14), HUNTER(15), NON_CITY(16);

        private final int value;

        TCity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int SCROLL_OF_SCAPE = 736;
    public static final int ROXXY = 30006;

    public static final TCity[] TCities = {
        TCity.DWARVEN,
        TCity.ORC,
        TCity.DARK_ELVEN,
        TCity.ELVEN,
        TCity.TALKING_ISLAND,
        TCity.HEINE,
        TCity.DION,
        TCity.GIRAN,
        TCity.ADEN,
        TCity.GODDARD,
        TCity.GLUDIO,
        TCity.GLUDIN,
        TCity.SHTUTTGART,
        TCity.RUNE,
        TCity.OREN,
        TCity.HUNTER,
        TCity.NON_CITY
    };

    public static final String[] CityNames = {
        "dwarven village",
        "orc village",
        "dark elf village",
        "elf village",
        "talking island",
        "heine",
        "dion",
        "giran",
        "aden",
        "goddard",
        "gludio",
        "gludin",
        "schuttgart",
        "rune",
        "oren",
        "hunter village"
    };

    // IDs of the Gatekeepers
    public static final int[] GateKeepers = {
        30540,
        30576,
        30134,
        30146,
        30006,
        30899,
        30059,
        30080,
        30848,
        31275,
        30256,
        30320,
        31964,
        31320,
        30177,
        30233
    };

    public static final int TPDelay = 10 * 1000; // Time to wait after teleporting, change according to your computer

    public static final List < String > GateKeepersStrings = Arrays.asList(
        "gludio,schuttgart,\"dark elf village\",\"talking island\",\"elf village\",\"orc village\",\"mithril mines\",\"abandoned coal mines\",\"eastern mining zone\",\"western mining zone\"", //dwarven
        "gludio,schuttgart,\"dark elf village\",\"dwarven village\",\"talking island\",\"elf village\",\"immortal plateau\",\"immortal plateau south\",\"fozen waterfall\"", //orc
        "gludio,\"dwarven village\",\"talking island\",\"orc village\",\"dark forest\",\"swampland\",\"spider nest\",\"neutral zone\"", //darkelven
        "gludio,\"dwarven village\",\"talking island\",\"orc village\",\"elven forest\",\"elven fortress\",\"neutral zone\"", //elven
        "gludin,\"dark elf village\",\"dwarven village\",\"elf village\",\"orc village\",\"elven ruins\",\"singing waterfall\",\"northen territory\",\"obelisk of victory\"", //talking island
        "giran,oren,dion,aden,goddard,rune,schuttgart,gludio,\"giran harbor\",\"field of silence\",\"field of whispers\",\"alligator island\",\"garden of eva\"", //heine
        "giran,heine,gludio,goddard,rune,schuttgart,aden,oren,\"cruma marshland\",\"cruma tower\",\"fortress of resistance\",\"plains of dion\",\"bee hive\",\"tanor canyon\"", //dion
        "oren,heine,dion,goddard,rune,schuttgart,gludio,aden,\"giran harbor\",\"hardins private academy\",\"dragon valley\",\"antharas lair\",\"devils isle\",\"breka stronghold\"", //giran
        "goddard,oren,giran,heine,schuttgart,dion,gludio,rune,\"hunter village\",\"coliseum\",\"forsaken plains\",\"seal of shilen\",\"forest of mirrors\",\"blazing swamp\",\"field of massacre\",\"ancient battleground\",\"silent valley\",\"tower of insolence\"", //aden
        "gludio,giran,dion,rune,heine,schuttgart,aden,oren,\"varka\",\"ketra\",\"hot springs\",\"wall of argos\",\"monastery of silence\"", //goddard
        "schuttgart,heine,aden,oren,dion,goddard,giran,rune,gludin,\"elf village\",\"dark elf village\",\"dwarven village\",\"orc village\",\"ruins of agony\",\"ruins of despair\",\"ant nest\",\"windawood manor\"", //gludio
        "gludio,\"talking island\",\"elf village\",\"dark elf village\",\"dwarven village\",\"orc village\",\"langk lizardman dwellings\",\"windmill hill\",\"fellmere harvesting grounds\",\"forgotten temple\",\"orc barracks\",\"windy hill\",wasteland,\"red rock ridge\"", //gludin
        "rune,goddard,aden,oren,heine,giran,dion,gludio,\"orc village\",\"dwarven village\",\"den of evil\",\"plunderous plains\",\"frozen labyrinth\",\"crypts of disgrace\",\"pavel ruines\"", //schuttgart
        "goddard,gludio,giran,dion,heine,schuttgart,aden,oren,\"wild beast pastues\",\"valley of saints\",\"forest of the dead\",\"swamp of screams\",\"monasterty of silence\"", //rune
        "aden,giran,rune,goddard,heine,dion,schuttgart,gludio,\"ivory tower\",\"hunter village\",\"hardins private academy\",\"skyshadow mead\""
    );

    public static TCity getCity(SimPlayer player, boolean shouldGoHome, boolean inCityEvt) {
        TCity city = TCity.NON_CITY;

        if (Functions.inRange(player, 147509, 25928, player.getZ(), 5000)) city = TCity.ADEN;
        if (Functions.inRange(player, 11416, 16856, player.getZ(), 5000)) city = TCity.DARK_ELVEN;
        if (Functions.inRange(player, 18591, 144708, player.getZ(), 5000)) city = TCity.DION;
        if (Functions.inRange(player, 116569, -179848, player.getZ(), 7000)) city = TCity.DWARVEN;
        if (Functions.inRange(player, 44904, 49800, player.getZ(), 7000)) city = TCity.ELVEN;
        if (Functions.inRange(player, 82876, 148960, player.getZ(), 5000)) city = TCity.GIRAN;
        if (Functions.inRange(player, -81311, 152663, player.getZ(), 5000)) city = TCity.GLUDIN;
        if (Functions.inRange(player, -14236, 123701, player.getZ(), 5000)) city = TCity.GLUDIO;
        if (Functions.inRange(player, 147713, -56202, player.getZ(), 5000)) city = TCity.GODDARD;
        if (Functions.inRange(player, 111434, 220426, player.getZ(), 5000)) city = TCity.HEINE;
        if (Functions.inRange(player, 117144, 76824, player.getZ(), 5000)) city = TCity.HUNTER;
        if (Functions.inRange(player, -45080, -113576, player.getZ(), 5000)) city = TCity.ORC;
        if (Functions.inRange(player, 81150, 54880, player.getZ(), 5000)) city = TCity.OREN;
        if (Functions.inRange(player, 43838, -48222, player.getZ(), 5000) ||
            Functions.inRange(player, 38283, -48102, player.getZ(), 5000)) city = TCity.RUNE;
        if (Functions.inRange(player, 88041, -142502, player.getZ(), 5000)) city = TCity.SHTUTTGART;
        if (Functions.inRange(player, -83139, 243145, player.getZ(), 5000)) city = TCity.TALKING_ISLAND;

        // If it isn't in one of the previous locations then goHome and try again
        /*if (city.equals(TCity.NON_CITY) && shouldGoHome) {
            //goHome(-1, -1, inCityEvt);
            city = getCity(player, shouldGoHome, inCityEvt);
        }*/
        return city;
    }

    public static void addWalkNode(SimPlayer player, int x, int y, int z) {
        player.getWalkNodes().add(new WalkNode(x, y, z));
    }

    public static void MoveInCity(SimPlayer player) {
        TCity city = getCity(player, false, false);
        if (!player.getWalkNodes().isEmpty())
            return;

        if (city == TCity.GIRAN) {
            if (Functions.inRange(player, 81376, 148095, -3464, 250)) {
                addWalkNode(player, 81376, 148095, -3464);
                addWalkNode(player, 81881, 148025, -3467);
                addWalkNode(player, 83027, 148020, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 82292, 149450, -3464, 250)) {
                addWalkNode(player, 82292, 149450, -3464);
                addWalkNode(player, 82865, 148876, -3467);
                addWalkNode(player, 83054, 148281, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 81562, 147782, -3464, 250)) {
                addWalkNode(player, 81562, 147782, -3464);
                addWalkNode(player, 82284, 148077, -3467);
                addWalkNode(player, 83077, 148159, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 83409, 148578, -3400, 250)) {
                addWalkNode(player, 83409, 148578, -3400);
                addWalkNode(player, 83427, 148206, -3403);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 81440, 149119, -3464, 250)) {
                addWalkNode(player, 81440, 149119, -3464);
                addWalkNode(player, 82200, 149222, -3467);
                addWalkNode(player, 82722, 148485, -3467);
                addWalkNode(player, 83087, 148101, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 82496, 148095, -3464, 250)) {
                addWalkNode(player, 82496, 148095, -3464);
                addWalkNode(player, 83092, 148094, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 83473, 149223, -3400, 250)) {
                addWalkNode(player, 83473, 149223, -3400);
                addWalkNode(player, 83355, 148728, -3403);
                addWalkNode(player, 83358, 148292, -3403);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 82272, 147801, -3464, 250)) {
                addWalkNode(player, 82272, 147801, -3464);
                addWalkNode(player, 82565, 148080, -3467);
                addWalkNode(player, 83101, 148099, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 82480, 149087, -3464, 250)) {
                addWalkNode(player, 82480, 149087, -3464);
                addWalkNode(player, 82623, 148694, -3467);
                addWalkNode(player, 83087, 148157, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 81637, 149427, -3464, 250)) {
                addWalkNode(player, 81637, 149427, -3464);
                addWalkNode(player, 82229, 149197, -3467);
                addWalkNode(player, 82610, 148669, -3467);
                addWalkNode(player, 83088, 148170, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 81062, 148144, -3464, 250)) {
                addWalkNode(player, 81062, 148144, -3464);
                addWalkNode(player, 81574, 147997, -3467);
                addWalkNode(player, 82302, 147975, -3467);
                addWalkNode(player, 83070, 148109, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 83426, 148835, -3400, 250)) {
                addWalkNode(player, 83426, 148835, -3400);
                addWalkNode(player, 83422, 148276, -3403);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 81033, 148883, -3464, 250)) {
                addWalkNode(player, 81033, 148883, -3464);
                addWalkNode(player, 81769, 149191, -3467);
                addWalkNode(player, 82322, 149192, -3467);
                addWalkNode(player, 82622, 148656, -3467);
                addWalkNode(player, 83079, 148163, -3467);
                addWalkNode(player, 83402, 147946, -3403);
            }
            if (Functions.inRange(player, 83415, 148235, -3400, 250)) {
                addWalkNode(player, 83415, 148235, -3400);
                addWalkNode(player, 83402, 147946, -3403);
            }
        }

        if (city == TCity.ADEN) {
            if (Functions.inRange(player, 149800, 24000, 2100, 600)) { // Bottom of north-east stairs
                addWalkNode(player, 148611, 24319, -2039); // climb stairs
            }
            if (Functions.inRange(player, 148611, 24319, -2039, 500)) { // Top of east stairs
                addWalkNode(player, 148437, 25683, -2008); // church corner
            }
            if (Functions.inRange(player, 148437, 25683, -2008, 500)) {
                addWalkNode(player, 147604, 25774, -2008); // top of central stairs
            }
            if (Functions.inRange(player, 146400, 25500, -2000, 500)) {
                addWalkNode(player, 146600, 25692, -2008);
                addWalkNode(player, 147044, 25732, -2039);
            }
            if (Functions.inRange(player, 150000, 27600, -2270, 500)) { // East
                addWalkNode(player, 148385, 27571, -2256); // move to east of the central square
            }
            if (Functions.inRange(player, 148000, 27550, -2300, 450)) { // right of central square
                addWalkNode(player, 147707, 26581, -2200); // move to the base of the central stairs
            }
            if (Functions.inRange(player, 147707, 26581, -2200, 500)) { // from the bottom of the central stairs
                addWalkNode(player, 147604, 25774, -2008); // move to top of central stairs
            }
            if (Functions.inRange(player, 146811, 27109, -2231, 500)) {
                addWalkNode(player, 146811, 27109, -2231);
                addWalkNode(player, 147289, 26926, -2229);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 146810, 28059, -2294, 500)) {
                addWalkNode(player, 146810, 28059, -2294);
                addWalkNode(player, 146847, 27874, -2294);
                addWalkNode(player, 147004, 27611, -2294);
                addWalkNode(player, 147004, 27611, -2294);
                addWalkNode(player, 147004, 27611, -2232);
                addWalkNode(player, 147244, 26822, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 145686, 21112, -2167, 500)) {
                addWalkNode(player, 145686, 21112, -2167);
                addWalkNode(player, 145697, 21616, -2167);
                addWalkNode(player, 145050, 22609, -2167);
                addWalkNode(player, 145218, 23947, -2167);
                addWalkNode(player, 146377, 24455, -2039);
                addWalkNode(player, 146484, 25685, -2039);
                addWalkNode(player, 147130, 25700, -2039);
                addWalkNode(player, 147112, 25921, -2039);
            }
            if (Functions.inRange(player, 148076, 27069, -2231, 500)) {
                addWalkNode(player, 148076, 27069, -2231);
                addWalkNode(player, 147666, 27110, -2230);
                addWalkNode(player, 147308, 26954, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 148949, 21125, -2167, 500)) {
                addWalkNode(player, 148949, 21125, -2167);
                addWalkNode(player, 146122, 21227, -2167);
                addWalkNode(player, 145697, 21616, -2167);
                addWalkNode(player, 145050, 22609, -2167);
                addWalkNode(player, 145218, 23947, -2167);
                addWalkNode(player, 146377, 24455, -2039);
                addWalkNode(player, 146484, 25685, -2039);
                addWalkNode(player, 147130, 25700, -2039);
                addWalkNode(player, 147112, 25921, -2039);
            }
            if (Functions.inRange(player, 144549, 22828, -2167, 500)) {
                addWalkNode(player, 144549, 22828, -2167);
                addWalkNode(player, 145697, 21616, -2167);
                addWalkNode(player, 145050, 22609, -2167);
                addWalkNode(player, 145218, 23947, -2167);
                addWalkNode(player, 146377, 24455, -2039);
                addWalkNode(player, 146484, 25685, -2039);
                addWalkNode(player, 147130, 25700, -2039);
                addWalkNode(player, 147112, 25921, -2039);
            }
            if (Functions.inRange(player, 144543, 24666, -2167, 500)) {
                addWalkNode(player, 144543, 24666, -2167);
                addWalkNode(player, 145279, 24307, -2167);
                addWalkNode(player, 146377, 24455, -2039);
                addWalkNode(player, 146484, 25685, -2039);
                addWalkNode(player, 147130, 25700, -2039);
                addWalkNode(player, 147112, 25921, -2039);
            }
            if (Functions.inRange(player, 146497, 30582, -2487, 500)) {
                addWalkNode(player, 146497, 30582, -2487);
                addWalkNode(player, 147058, 30323, -2487);
                addWalkNode(player, 147427, 29959, -2487);
                addWalkNode(player, 147427, 29339, -2295);
                addWalkNode(player, 147373, 28351, -2294);
                addWalkNode(player, 147100, 27643, -2294);
                addWalkNode(player, 147246, 26930, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 144648, 29158, -2487, 500)) {
                addWalkNode(player, 144648, 29158, -2487);
                addWalkNode(player, 144966, 28868, -2487);
                addWalkNode(player, 145110, 27659, -2295);
                addWalkNode(player, 146220, 27622, -2231);
                addWalkNode(player, 147108, 27272, -2231);
                addWalkNode(player, 147224, 26929, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 150247, 29109, -2487, 500)) {
                addWalkNode(player, 150247, 29109, -2487);
                addWalkNode(player, 149921, 28836, -2487);
                addWalkNode(player, 149859, 28063, -2339);
                addWalkNode(player, 149776, 27675, -2295);
                addWalkNode(player, 148731, 27683, -2231);
                addWalkNode(player, 148224, 27383, -2231);
                addWalkNode(player, 147306, 26932, -2230);
            }
            if (Functions.inRange(player, 147848, 30309, -2487, 500)) {
                addWalkNode(player, 147848, 30309, -2487);
                addWalkNode(player, 147677, 30089, -2487);
                addWalkNode(player, 147462, 29870, -2447);
                addWalkNode(player, 147462, 29413, -2295);
                addWalkNode(player, 147335, 28272, -2294);
                addWalkNode(player, 147108, 27664, -2294);
                addWalkNode(player, 147194, 26961, -2229);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 144651, 26680, -2295, 500)) {
                addWalkNode(player, 144651, 26680, -2295);
                addWalkNode(player, 144681, 27050, -2294);
                addWalkNode(player, 144936, 27135, -2295);
                addWalkNode(player, 145145, 27605, -2295);
                addWalkNode(player, 146182, 27682, -2231);
                addWalkNode(player, 146921, 27360, -2231);
                addWalkNode(player, 147218, 26948, -2229);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 148557, 30461, -2487, 500)) {
                addWalkNode(player, 148557, 30461, -2487);
                addWalkNode(player, 148114, 30076, -2487);
                addWalkNode(player, 147453, 30049, -2487);
                addWalkNode(player, 147474, 29710, -2368);
                addWalkNode(player, 147397, 28461, -2294);
                addWalkNode(player, 147044, 27389, -2231);
                addWalkNode(player, 147242, 26823, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
                addWalkNode(player, 147138, 25906, -2039);
            }
            if (Functions.inRange(player, 147971, 27982, -2294, 500)) {
                addWalkNode(player, 147971, 27982, -2294);
                addWalkNode(player, 147846, 27354, -2231);
                addWalkNode(player, 147265, 26878, -2230);
                addWalkNode(player, 147267, 26400, -2185);
                addWalkNode(player, 147266, 26195, -2103);
                addWalkNode(player, 147250, 25969, -2039);
                addWalkNode(player, 147206, 25899, -2039);
            }
            if (Functions.inRange(player, 147604, 25774, -2008, 600)) { // top of the stairs
                addWalkNode(player, 147138, 25906, -2039); // go from top of central stairs to gatekeeper
                //Moved := true;
            }
        }

        if (city == TCity.DION) {
            if (Functions.inRange(player, 19134, 144847, -3096, 250)) {
                addWalkNode(player, 19134, 144847, -3096);
                addWalkNode(player, 18041, 144052, -3057);
                addWalkNode(player, 16727, 144111, -2980);
                addWalkNode(player, 15933, 143331, -2771);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 18954, 144428, -3096, 250)) {
                addWalkNode(player, 18954, 144428, -3096);
                addWalkNode(player, 18600, 144387, -3070);
                addWalkNode(player, 17733, 143924, -3037);
                addWalkNode(player, 16711, 144167, -2980);
                addWalkNode(player, 16622, 144034, -2932);
                addWalkNode(player, 15963, 143381, -2784);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 19012, 145140, -3120, 250)) {
                addWalkNode(player, 19012, 145140, -3120);
                addWalkNode(player, 19021, 145126, -3123);
                addWalkNode(player, 17600, 145497, -3079);
                addWalkNode(player, 16468, 144129, -2964);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 19613, 145607, -3104, 250)) {
                addWalkNode(player, 19613, 145607, -3104);
                addWalkNode(player, 17638, 145591, -3084);
                addWalkNode(player, 16214, 143659, -2863);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 18576, 145136, -3104, 250)) {
                addWalkNode(player, 18576, 145136, -3104);
                addWalkNode(player, 17727, 145541, -3082);
                addWalkNode(player, 16414, 144174, -2969);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 17398, 145456, -3048, 250)) {
                addWalkNode(player, 17398, 145456, -3048);
                addWalkNode(player, 17727, 145541, -3082);
                addWalkNode(player, 16414, 144174, -2969);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 17144, 145055, -3024, 250)) {
                addWalkNode(player, 17144, 145055, -3024);
                addWalkNode(player, 16350, 143905, -2923);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 18078, 145925, -3112, 250)) {
                addWalkNode(player, 18078, 145925, -3112);
                addWalkNode(player, 16559, 144353, -2987);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 19150, 143941, -3056, 250)) {
                addWalkNode(player, 19150, 143941, -3056);
                addWalkNode(player, 18112, 145633, -3104);
                addWalkNode(player, 17108, 145059, -3031);
                addWalkNode(player, 16469, 144176, -2969);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 18512, 145536, -3120, 250)) {
                addWalkNode(player, 18512, 145536, -3120);
                addWalkNode(player, 17226, 145360, -3048);
                addWalkNode(player, 16419, 143955, -2935);
                addWalkNode(player, 15628, 142920, -2704);
            }
            if (Functions.inRange(player, 18717, 145711, -3080, 250)) {
                addWalkNode(player, 18717, 145711, -3080);
                addWalkNode(player, 17226, 145360, -3048);
                addWalkNode(player, 16419, 143955, -2935);
                addWalkNode(player, 15628, 142920, -2704);
            }
            //if (Functions.inRange(player,15628,142920,-2704, 250) then Moved := true;
        }

        if (city == TCity.GLUDIN) {
            if (Functions.inRange(player, -82909, 150357, -3120, 250)) {
                addWalkNode(player, -82909, 150357, -3120);
                addWalkNode(player, -82293, 150405, -3127);
                addWalkNode(player, -81099, 150292, -3048);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -83520, 150560, -3120, 250)) {
                addWalkNode(player, -83520, 150560, -3120);
                addWalkNode(player, -82640, 150552, -3127);
                addWalkNode(player, -81440, 150392, -3127);
                addWalkNode(player, -81054, 150149, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -82195, 150489, -3120, 250)) {
                addWalkNode(player, -82195, 150489, -3120);
                addWalkNode(player, -81832, 150490, -3101);
                addWalkNode(player, -81023, 150193, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -80053, 154348, -3168, 250)) {
                addWalkNode(player, -80053, 154348, -3168);
                addWalkNode(player, -80686, 153974, -3176);
                addWalkNode(player, -81784, 153952, -3176);
                addWalkNode(player, -83049, 153943, -3176);
                addWalkNode(player, -83023, 152370, -3127);
                addWalkNode(player, -82637, 151293, -3127);
                addWalkNode(player, -81296, 150467, -3127);
                addWalkNode(player, -81042, 150263, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -82035, 152647, -3168, 250)) {
                addWalkNode(player, -82035, 152647, -3168);
                addWalkNode(player, -82975, 152676, -3176);
                addWalkNode(player, -82932, 151752, -3127);
                addWalkNode(player, -81276, 150510, -3127);
                addWalkNode(player, -81036, 150260, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -83529, 151205, -3120, 250)) {
                addWalkNode(player, -83529, 151205, -3120);
                addWalkNode(player, -82130, 150895, -3127);
                addWalkNode(player, -81088, 150361, -3044);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -81721, 151202, -3120, 250)) {
                addWalkNode(player, -81721, 151202, -3120);
                addWalkNode(player, -81403, 150675, -3127);
                addWalkNode(player, -81057, 150318, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -82575, 151025, -3120, 250)) {
                addWalkNode(player, -82575, 151025, -3120);
                addWalkNode(player, -81540, 150540, -3127);
                addWalkNode(player, -80989, 150147, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -84064, 150864, -3120, 250)) {
                addWalkNode(player, -84064, 150864, -3120);
                addWalkNode(player, -83114, 150635, -3127);
                addWalkNode(player, -81390, 150478, -3127);
                addWalkNode(player, -81017, 150133, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -82241, 151163, -3120, 250)) {
                addWalkNode(player, -82241, 151163, -3120);
                addWalkNode(player, -81391, 150602, -3127);
                addWalkNode(player, -80999, 150174, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            if (Functions.inRange(player, -81787, 150780, -3120, 250)) {
                addWalkNode(player, -81787, 150780, -3120);
                addWalkNode(player, -81049, 150378, -3042);
                addWalkNode(player, -81073, 150110, -3042);
            }
            //if (Functions.inRange(player,-81073,150110,-3042, 250) then Moved := true;
        }


        if (city == TCity.GLUDIO) {
            // Next to Black Marketeer of Mammon
            if (Functions.inRange(player, -12240, 121807, -3010, 250)) {
                addWalkNode(player, -12712, 122189, -2984);
            }
            // Stairs in front of church close to gatekeeper
            if (Functions.inRange(player, -12712, 122189, -2984, 250)) {
                addWalkNode(player, -13257, 122669, -3048);
                addWalkNode(player, -12782, 122689, -3143);
            }
            if (Functions.inRange(player, -14704, 122032, -3056, 250)) {
                addWalkNode(player, -14704, 122032, -3056);
                addWalkNode(player, -14538, 122671, -3116);
                addWalkNode(player, -14599, 123458, -3113);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -13709, 123563, -3112, 250)) {
                addWalkNode(player, -13709, 123563, -3112);
                addWalkNode(player, -14115, 123802, -3119);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -14465, 124434, -3120, 250)) {
                addWalkNode(player, -14465, 124434, -3120);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -13513, 123752, -3112, 250)) {
                addWalkNode(player, -13513, 123752, -3112);
                addWalkNode(player, -14245, 123748, -3109);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -15564, 123896, -3112, 250)) {
                addWalkNode(player, -15564, 123896, -3112);
                addWalkNode(player, -14813, 123869, -3124);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -14288, 122752, -3112, 250)) {
                addWalkNode(player, -14288, 122752, -3112);
                addWalkNode(player, -14525, 123547, -3109);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -15314, 124131, -3112, 250)) {
                addWalkNode(player, -15314, 124131, -3112);
                addWalkNode(player, -14710, 123973, -3122);
                addWalkNode(player, -14510, 124033, -3125);
            }
            if (Functions.inRange(player, -14592, 123232, -3112, 250)) {
                addWalkNode(player, -14592, 123232, -3112);
                addWalkNode(player, -14510, 124033, -3125);
            }
            //if (Functions.inRange(player,-14510,124033,-3125, 250) then Moved :=  true;
        }

        if (city == TCity.GODDARD) {
            if (Functions.inRange(player, 146272, -58176, -2976, 250)) { // Near storage
                addWalkNode(player, 147593, -58103, -3007);
                addWalkNode(player, 147727, -57141, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 145264, -57680, -2976, 250)) { // Near storage 2
                addWalkNode(player, 145588, -56926, -3007);
                addWalkNode(player, 146673, -56095, -2807);
                addWalkNode(player, 147543, -56054, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 145696, -57696, -2976, 250)) { // Near storage 3
                addWalkNode(player, 145510, -56930, -3007);
                addWalkNode(player, 146499, -56202, -2807);
                addWalkNode(player, 147481, -56031, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 144944, -55392, -2976, 250)) { // The very edge
                addWalkNode(player, 145153, -56813, -3007);
                addWalkNode(player, 145569, -56855, -3007);
                addWalkNode(player, 146467, -56271, -2807);
                addWalkNode(player, 147566, -56034, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 144752, -56752, -2976, 250)) { // The very edge
                addWalkNode(player, 145534, -56884, -3007);
                addWalkNode(player, 146265, -56418, -2807);
                addWalkNode(player, 147407, -56063, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            // East stairs
            if (Functions.inRange(player, 149872, -57424, -2976, 250)) {
                addWalkNode(player, 149897, -56910, -2979);
                addWalkNode(player, 149230, -56412, -2779);
                addWalkNode(player, 147939, -55999, -2772);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 149120, -58064, -2976, 250)) { // score
                addWalkNode(player, 147706, -58107, -3007);
                addWalkNode(player, 147751, -56737, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 150400, -56752, -2976, 250)) { // The very edge
                addWalkNode(player, 149935, -56870, -3007);
                addWalkNode(player, 149139, -56390, -2807);
                addWalkNode(player, 147929, -56063, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 150704, -55744, -2976, 250)) { // The very edge 2
                addWalkNode(player, 149935, -56870, -3007);
                addWalkNode(player, 149139, -56390, -2807);
                addWalkNode(player, 147929, -56063, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 147700, -58400, -2976, 500)) {
                addWalkNode(player, 147727, -57141, -2807);
            }
            if (Functions.inRange(player, 147727, -57141, -2807, 250)) {
                addWalkNode(player, 147757, -55301, -2759);
            }
            if (Functions.inRange(player, 148288, -58304, -2976, 250)) { // Bottom center stairs slightly to the right
                addWalkNode(player, 147738, -58050, -3007);
                addWalkNode(player, 147727, -57141, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 147232, -58480, -2976, 250)) { // Bottom center stairs to the left
                addWalkNode(player, 147677, -58063, -3007);
                addWalkNode(player, 147727, -57141, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 149088, -56256, -2776, 250)) {
                addWalkNode(player, 147854, -56054, -2807);
                addWalkNode(player, 147934, -55354, -2760);
            }
            // Area
            if (Functions.inRange(player, 146832, -55904, -2776, 250)) { // Area to the left of the stairs
                addWalkNode(player, 147529, -56046, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 146368, -56256, -2776, 250)) { // Area to the left of the stairs 2
                addWalkNode(player, 147529, -56046, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 147664, -56464, -2776, 250)) { // Center area
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 147680, -56928, -2776, 250)) { // Center area 2
                addWalkNode(player, 147810, -56110, -2807);
                addWalkNode(player, 147936, -55368, -2760);
            }
            if (Functions.inRange(player, 148560, -55904, -2776, 250)) { // Area to the right of the stairs
                addWalkNode(player, 147936, -55368, -2760);
            }
            // East
            if (Functions.inRange(player, 150200, -57100, -2976, 600)) {
                addWalkNode(player, 149897, -56910, -2979); // go to the base of the stairs
                addWalkNode(player, 149230, -56412, -2779); // climb the stairs
                addWalkNode(player, 147637, -56074, -2776); // go to the center
                addWalkNode(player, 147728, -55332, -2728); // go to the gatekeeper
            }
            if (Functions.inRange(player, 144960, -56224, -2976, 250)) {
                addWalkNode(player, 145458, -56853, -2979);
                addWalkNode(player, 146343, -56326, -2779);
                addWalkNode(player, 147625, -55995, -2772);
                addWalkNode(player, 147936, -55368, -2760); // Move near gk
            }
            if (Functions.inRange(player, 144496, -55088, -2976, 250)) {
                addWalkNode(player, 145327, -56873, -2979);
                addWalkNode(player, 146337, -56445, -2779);
                addWalkNode(player, 147533, -55963, -2766);
                addWalkNode(player, 147936, -55368, -2760); // Move near gk
            }
            if (Functions.inRange(player, 145392, -56960, -2976, 250)) {
                addWalkNode(player, 146347, -56305, -2779);
                addWalkNode(player, 147514, -56003, -2772);
                addWalkNode(player, 147936, -55368, -2760); // Move near gk
            }
            if (Functions.inRange(player, 147800, -55300, -2700, 250)) {
                //Moved :=  true;
            }
        }

        if (city == TCity.HEINE) {
            if (Functions.inRange(player, 110912, 219584, -3664, 250)) {
                addWalkNode(player, 110912, 219584, -3664);
                addWalkNode(player, 111154, 219735, -3675);
                addWalkNode(player, 111176, 219395, -3546);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 111888, 219584, -3664, 250)) {
                addWalkNode(player, 111888, 219584, -3664);
                addWalkNode(player, 111617, 219703, -3674);
                addWalkNode(player, 111591, 219371, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 112064, 219792, -3664, 250)) {
                addWalkNode(player, 112064, 219792, -3664);
                addWalkNode(player, 111665, 219800, -3675);
                addWalkNode(player, 111580, 219329, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 107808, 217856, -3672, 250)) {
                addWalkNode(player, 107808, 217856, -3672);
                addWalkNode(player, 107769, 217524, -3673);
                addWalkNode(player, 109387, 217509, -3747);
                addWalkNode(player, 110037, 217257, -3747);
                addWalkNode(player, 110072, 219029, -3477);
                addWalkNode(player, 111202, 219130, -3541);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 110896, 220768, -3664, 250)) {
                addWalkNode(player, 110896, 220768, -3664);
                addWalkNode(player, 111191, 219621, -3663);
                addWalkNode(player, 111190, 219303, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 110768, 219824, -3664, 250)) {
                addWalkNode(player, 110768, 219824, -3664);
                addWalkNode(player, 111163, 219763, -3671);
                addWalkNode(player, 111199, 219319, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 112112, 220576, -3664, 250)) {
                addWalkNode(player, 112112, 220576, -3664);
                addWalkNode(player, 111600, 219666, -3669);
                addWalkNode(player, 111586, 219305, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 110688, 220576, -3664, 250)) {
                addWalkNode(player, 110688, 220576, -3664);
                addWalkNode(player, 111183, 219655, -3669);
                addWalkNode(player, 111201, 219292, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 108032, 218048, -3672, 250)) {
                addWalkNode(player, 108032, 218048, -3672);
                addWalkNode(player, 107862, 218003, -3673);
                addWalkNode(player, 107840, 217532, -3673);
                addWalkNode(player, 109383, 217465, -3747);
                addWalkNode(player, 110036, 217280, -3747);
                addWalkNode(player, 110096, 219040, -3478);
                addWalkNode(player, 111086, 219100, -3541);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 107568, 218256, -3672, 250)) {
                addWalkNode(player, 107568, 218256, -3672);
                addWalkNode(player, 107542, 217872, -3673);
                addWalkNode(player, 107840, 217532, -3673);
                addWalkNode(player, 109383, 217465, -3747);
                addWalkNode(player, 110036, 217280, -3747);
                addWalkNode(player, 110096, 219040, -3478);
                addWalkNode(player, 111086, 219100, -3541);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 107552, 218000, -3672, 250)) {
                addWalkNode(player, 107552, 218000, -3672);
                addWalkNode(player, 107862, 218003, -3673);
                addWalkNode(player, 107840, 217532, -3673);
                addWalkNode(player, 109383, 217465, -3747);
                addWalkNode(player, 110036, 217280, -3747);
                addWalkNode(player, 110096, 219040, -3478);
                addWalkNode(player, 111086, 219100, -3541);
                addWalkNode(player, 111387, 219387, -3544);
            }
            if (Functions.inRange(player, 111856, 220752, -3664, 250)) {
                addWalkNode(player, 111856, 220752, -3664);
                addWalkNode(player, 111573, 219730, -3675);
                addWalkNode(player, 111589, 219342, -3544);
                addWalkNode(player, 111387, 219387, -3544);
            }
            //if (Functions.inRange(player,111387,219387,-3544, 250) then Moved := true;
        }

        if (city == TCity.OREN) {
            if (Functions.inRange(player, 80334, 54400, -1552, 250)) {
                addWalkNode(player, 80334, 54400, -1552);
                addWalkNode(player, 80969, 53698, -1558);
                addWalkNode(player, 81540, 53454, -1494);
                addWalkNode(player, 82480, 53340, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82323, 55466, -1520, 250)) {
                addWalkNode(player, 82323, 55466, -1520);
                addWalkNode(player, 82403, 54498, -1523);
                addWalkNode(player, 82589, 53861, -1486);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82123, 53535, -1488, 250)) {
                addWalkNode(player, 82123, 53535, -1488);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 80304, 56241, -1552, 250)) {
                addWalkNode(player, 80304, 56241, -1552);
                addWalkNode(player, 81470, 55682, -1523);
                addWalkNode(player, 82256, 55294, -1523);
                addWalkNode(player, 82510, 54035, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82445, 56012, -1520, 250)) {
                addWalkNode(player, 82445, 56012, -1520);
                addWalkNode(player, 82341, 54950, -1523);
                addWalkNode(player, 82574, 53833, -1486);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 80054, 53209, -1552, 250)) {
                addWalkNode(player, 80054, 53209, -1552);
                addWalkNode(player, 80624, 53611, -1558);
                addWalkNode(player, 81772, 53468, -1494);
                addWalkNode(player, 82289, 53472, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 80513, 52980, -1552, 250)) {
                addWalkNode(player, 80513, 52980, -1552);
                addWalkNode(player, 80165, 53065, -1558);
                addWalkNode(player, 80630, 53610, -1558);
                addWalkNode(player, 81688, 53447, -1494);
                addWalkNode(player, 82303, 53338, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 80267, 55497, -1552, 250)) {
                addWalkNode(player, 80267, 55497, -1552);
                addWalkNode(player, 80359, 54536, -1558);
                addWalkNode(player, 80792, 53785, -1558);
                addWalkNode(player, 81495, 53421, -1494);
                addWalkNode(player, 82323, 53362, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 79933, 55752, -1552, 250)) {
                addWalkNode(player, 79933, 55752, -1552);
                addWalkNode(player, 80349, 55208, -1558);
                addWalkNode(player, 80470, 54217, -1558);
                addWalkNode(player, 81053, 53602, -1558);
                addWalkNode(player, 81974, 53465, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 80594, 55837, -1552, 250)) {
                addWalkNode(player, 80594, 55837, -1552);
                addWalkNode(player, 81899, 55492, -1523);
                addWalkNode(player, 82240, 55142, -1523);
                addWalkNode(player, 82531, 53946, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82213, 53964, -1488, 250)) {
                addWalkNode(player, 82213, 53964, -1488);
                addWalkNode(player, 82668, 53709, -1486);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 83327, 55410, -1520, 250)) {
                addWalkNode(player, 83327, 55410, -1520);
                addWalkNode(player, 82607, 55592, -1523);
                addWalkNode(player, 82184, 54809, -1523);
                addWalkNode(player, 82563, 53882, -1488);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 79706, 52986, -1552, 250)) {
                addWalkNode(player, 79706, 52986, -1552);
                addWalkNode(player, 80427, 53549, -1558);
                addWalkNode(player, 80812, 53655, -1558);
                addWalkNode(player, 81979, 53439, -1494);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82880, 55390, -1520, 250)) {
                addWalkNode(player, 82880, 55390, -1520);
                addWalkNode(player, 82375, 55524, -1523);
                addWalkNode(player, 82367, 54313, -1493);
                addWalkNode(player, 82651, 53734, -1486);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82552, 53447, -1488, 250)) {
                addWalkNode(player, 82552, 53447, -1488);
                addWalkNode(player, 82950, 53186, -1494);
            }
            if (Functions.inRange(player, 82638, 53885, -1488, 250)) {
                addWalkNode(player, 82638, 53885, -1488);
                addWalkNode(player, 82950, 53186, -1494);
            }
            //if (Functions.inRange(player,82950,53186,-1494, 250) then Moved :=  true;
        }

        if (city == TCity.RUNE) {
            if (Functions.inRange(player, 38608, -47168, 896, 250) ||
                Functions.inRange(player, 38272, -49008, 896, 250) ||
                Functions.inRange(player, 38752, -47792, 896, 250) ||
                Functions.inRange(player, 38112, -49792, 896, 250) ||
                Functions.inRange(player, 38848, -48640, 896, 250)) {
                addWalkNode(player, 39113, -48225, 898);
                addWalkNode(player, 38294, -48089, 898);

                /*NpcList.ByID(31698,NPC);
                Engine.SetTarget(NPC);
                Engine.DlgOpen;
                Engine.DlgSel(1);
                Engine.DlgSel(1);
                Engine.CancelTarget;
                Delay(3000);*/

                addWalkNode(player, 39522, -48234, -784);
                addWalkNode(player, 41591, -48221, -801);
                addWalkNode(player, 43323, -48185, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 44368, -50592, -792, 250)) {
                addWalkNode(player, 44368, -50592, -792);
                addWalkNode(player, 43997, -49427, -795);
                addWalkNode(player, 43831, -48480, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 44864, -47824, -792, 250)) {
                addWalkNode(player, 44864, -47824, -792);
                addWalkNode(player, 44025, -47893, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 43536, -50416, -792, 250)) {
                addWalkNode(player, 43536, -50416, -792);
                addWalkNode(player, 43833, -49293, -795);
                addWalkNode(player, 43847, -48397, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 45632, -47968, -792, 250)) {
                addWalkNode(player, 45632, -47968, -792);
                addWalkNode(player, 44070, -47930, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 45824, -49056, -792, 250)) {
                addWalkNode(player, 45824, -49056, -792);
                addWalkNode(player, 46022, -48351, -795);
                addWalkNode(player, 45441, -48000, -795);
                addWalkNode(player, 44189, -47900, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 44000, -49952, -792, 250)) {
                addWalkNode(player, 44000, -49952, -792);
                addWalkNode(player, 43862, -48669, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 43792, -48928, -792, 250)) {
                addWalkNode(player, 43792, -48928, -792);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 45072, -49936, -792, 250)) {
                addWalkNode(player, 45072, -49936, -792);
                addWalkNode(player, 44258, -49647, -795);
                addWalkNode(player, 43864, -48552, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 43408, -51120, -792, 250)) {
                addWalkNode(player, 43408, -51120, -792);
                addWalkNode(player, 43805, -49830, -795);
                addWalkNode(player, 43870, -48626, -795);
                addWalkNode(player, 43827, -47698, -794);
            }
            if (Functions.inRange(player, 43744, -47920, -792, 250)) {
                addWalkNode(player, 43744, -47920, -792);
                addWalkNode(player, 43827, -47698, -794);
            }
            //if (Functions.inRange(player,43827,-47698,-794, 250) then Moved :=  true;
        }
        
        if (city == TCity.SHTUTTGART) {
            if (Functions.inRange(player, 87184, -140256, -1536, 250)) {
                addWalkNode(player, 87184, -140256, -1536);
                addWalkNode(player, 87368, -140838, -1512);
                addWalkNode(player, 87319, -141796, -1338);
                addWalkNode(player, 87145, -142916, -1313);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 87408, -142304, -1336, 250)) {
                addWalkNode(player, 87408, -142304, -1336);
                addWalkNode(player, 87145, -142916, -1313);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 88240, -142736, -1336, 250)) {
                addWalkNode(player, 88240, -142736, -1336);
                addWalkNode(player, 87585, -142662, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 85056, -141328, -1528, 250)) {
                addWalkNode(player, 85056, -141328, -1528);
                addWalkNode(player, 85181, -141845, -1539);
                addWalkNode(player, 85804, -142279, -1339);
                addWalkNode(player, 87157, -142636, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 88624, -142480, -1336, 250)) {
                addWalkNode(player, 88624, -142480, -1336);
                addWalkNode(player, 87548, -142686, -1331);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 86400, -142672, -1336, 250)) {
                addWalkNode(player, 86400, -142672, -1336);
                addWalkNode(player, 87143, -142570, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 86560, -140320, -1536, 250)) {
                addWalkNode(player, 86560, -140320, -1536);
                addWalkNode(player, 87300, -140655, -1539);
                addWalkNode(player, 87385, -141436, -1339);
                addWalkNode(player, 87246, -142537, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 88448, -140512, -1536, 250)) {
                addWalkNode(player, 88448, -140512, -1536);
                addWalkNode(player, 87410, -140642, -1539);
                addWalkNode(player, 87299, -141493, -1338);
                addWalkNode(player, 87291, -142607, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 89712, -141472, -1528, 250)) {
                addWalkNode(player, 89712, -141472, -1528);
                addWalkNode(player, 89497, -141893, -1539);
                addWalkNode(player, 88963, -142272, -1339);
                addWalkNode(player, 87737, -142631, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 87344, -141696, -1336, 250)) {
                addWalkNode(player, 87344, -141696, -1336);
                addWalkNode(player, 87354, -142594, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 85472, -140752, -1536, 250)) {
                addWalkNode(player, 85472, -140752, -1536);
                addWalkNode(player, 85191, -141803, -1539);
                addWalkNode(player, 85909, -142322, -1338);
                addWalkNode(player, 87096, -142671, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 89360, -140944, -1536, 250)) {
                addWalkNode(player, 89360, -140944, -1536);
                addWalkNode(player, 89539, -141762, -1539);
                addWalkNode(player, 88910, -142276, -1339);
                addWalkNode(player, 87637, -142734, -1332);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 87776, -140384, -1536, 250)) {
                addWalkNode(player, 87776, -140384, -1536);
                addWalkNode(player, 87403, -140707, -1539);
                addWalkNode(player, 87351, -141645, -1338);
                addWalkNode(player, 87310, -142568, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 84720, -141936, -1536, 250)) {
                addWalkNode(player, 84720, -141936, -1536);
                addWalkNode(player, 85201, -141842, -1539);
                addWalkNode(player, 85807, -142262, -1339);
                addWalkNode(player, 87116, -142704, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            if (Functions.inRange(player, 85968, -142384, -1336, 250)) {
                addWalkNode(player, 85968, -142384, -1336);
                addWalkNode(player, 87116, -142704, -1338);
                addWalkNode(player, 87070, -143418, -1290);
            }
            //if (Functions.inRange(player,87070,-143418,-1290, 250) then Moved :=  true;
        }

        if (city == TCity.TALKING_ISLAND) {
            if (Functions.inRange(player, -84376, 242824, -3712, 3000)) {
                addWalkNode(player, -84184, 243448, -3712);
                addWalkNode(player, -84280, 243800, -3712);
                addWalkNode(player, -84552, 244040, -3712);
                addWalkNode(player, -84184, 244504, -3712);
            }
            //if (Functions.inRange(player,-84200, 244488, -3728, 500) then Moved := true;
        }

        if (city == TCity.DWARVEN) {
            // North entrance
            if (Functions.inRange(player, 116499, -183985, -1588, 500)) {
                addWalkNode(player, 116643, -180342, -1360);
                addWalkNode(player, 116322, -179598, -1104);
            }
            // South entrance
            if (Functions.inRange(player, 116514, -183912, -1560, 500)) {
                addWalkNode(player, 116611, -179919, -1152);
                addWalkNode(player, 116322, -179598, -1104);
            }
            // In the middle
            if (Functions.inRange(player, 116666, -179540, -1122, 500)) {
                addWalkNode(player, 115698, -178876, -968);
                addWalkNode(player, 116322, -179598, -1104);
            }
            //if (Functions.inRange(player,116322, -179598, -1104, 500) then Moved := true;
        }

        if (city == TCity.DARK_ELVEN) {
            // Next to newbie guide
            if (Functions.inRange(player, 12094, 16709, -4580, 500)) {
                addWalkNode(player, 11631, 16756, -4656);
                addWalkNode(player, 10759, 16654, -4608);
            }
            // West entrance
            if (Functions.inRange(player, 7439, 17778, -4376, 500)) {
                addWalkNode(player, 10759, 16654, -4608);
            }
            // Last turn to gatekeeper
            if (Functions.inRange(player, 10759, 16654, -4608, 500)) {
                addWalkNode(player, 10182, 16799, -4584);
            }
            //if (Functions.inRange(player,12094, 16709, -4580, 500)  then Moved := true;
        }
    }
}