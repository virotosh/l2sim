package free.l2j.simfactory.model.actor.ai.preference;

import java.util.Arrays;
import java.util.List;

import net.sf.l2j.commons.logging.CLogger;

import net.sf.l2j.gameserver.data.xml.RestartPointData;
import net.sf.l2j.gameserver.data.xml.TeleportData;
import net.sf.l2j.gameserver.geoengine.GeoEngine;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.location.Location;
import net.sf.l2j.gameserver.model.location.TeleportLocation;
import net.sf.l2j.gameserver.model.restart.RestartPoint;

import free.l2j.simfactory.model.actor.SimPlayer;

public class TeleportModule {
	private static final CLogger LOGGER = new CLogger(TeleportModule.class.getName());
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

    public static final int SCROLL_OF_ESCAPE = 736;
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
        "Dwarven Village",
        "Orc Village",
        "Dark Elf Village",
        "Elven Village",
        "Talking Island Village",
        "Heine",
        "The Town of Dion",
        "The Town of Giran",
        "Town of Aden",
        "Town of Goddard",
        "The Town of Gludio",
        "The Village of Gludin",
        "Town of Schuttgart",
        "Rune Township",
        "Town of Oren",
        "Hunters Village"
    };
    
    public static final String[] RestartPointNames = {
        "dwarf_town",
        "orc_town",
        "darkelf_town",
        "elf_town",
        "talking_island_town",
        "heiness_town",
        "dion_castle_town",
        "giran_castle_town",
        "aden_town",
        "godard_town",
        "gludio_castle_town",
        "gludin_town",
        "schuttgard_town",
        "rune_town",
        "oren_castle_town",
        "hunter_town"
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
        if (Functions.inRange(player, 82876, 148960, player.getZ(), 8000)) city = TCity.GIRAN;
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
        if (city.equals(TCity.NON_CITY) && shouldGoHome) {
            goHome(player, -1, -1, false);
        }
        return city;
    }

    public static void goHome(SimPlayer player, int itemID, int skillID, boolean move) {
    	player.resetWalk();
    	player.getAI().tryToCast(player, 2100, 1);
    	//player.getAI().tryToUseItem(SCROLL_OF_ESCAPE);
    }

    public static void addWalkNode(SimPlayer player, int x, int y, int z) {
        player.getWalkNodes().add(new WalkNode(x, y, z));
    }

    public static void MoveInCity(SimPlayer player) {
        TCity city = getCity(player, false, false);
        if (city.equals(TCity.NON_CITY))
        	return;
    	
        player.walk(); 
    	
        if (player.isWalk()) {
        	return;
        }
        

        Npc GK = World.getInstance().getNpc(GateKeepers[city.getValue()]);

        if (Functions.distanceBetween(player.getPosition(), GK.getPosition()) < 250)
        	return;
        
        List<Location> path = GeoEngine.getInstance().findPath(player.getX(), player.getY(), player.getZ(), GK.getX(), GK.getY(), GK.getZ(), true, null);
		if (path.isEmpty()) {
			//goHome(player, -1, -1, false);
			return;
		}
		
		for (Location loc : path)
			addWalkNode(player, loc.getX(), loc.getY(), loc.getZ());
    	
    }
    
    public static void MoveToLocation(SimPlayer player, int x, int y, int z){
		player.walk(); 
    	
        if (player.isWalk())
        	return;
        
    	List<Location> path = GeoEngine.getInstance().findPath(player.getX(), player.getY(), player.getZ(), x, y, z, true, null);
		if (path.isEmpty()) {
			//goHome(player, -1, -1, false);
			return;
		}
		
		for (Location loc : path)
			addWalkNode(player, loc.getX(), loc.getY(), loc.getZ());
		// last node precise position
		addWalkNode(player, x, y, z);
    }
    
    public static void TeleportToLocation(SimPlayer player, String loc, boolean shouldGoHome) {
    	TCity city = getCity(player, shouldGoHome, false);
        
        if (city.equals(TCity.NON_CITY))
        	return;
        
        if (player.isWalk())
        	return;
        
		TeleportGraph TGraph = new TeleportGraph();

		for (String TNode : TGraph.shortestPath(CityNames[city.getValue()], loc).getFirst()){
			if (TNode.equals(CityNames[city.getValue()])) continue;
			TeleportTo(player, TNode);
			break;
		}
    }
    
    public static void TeleportToLocation(SimPlayer player, int x, int y, int z, boolean shouldGoHome) {
    	final RestartPoint rp = RestartPointData.getInstance().getRestartPoint(new Location(x,y,z));
    	TCity city = TCity.NON_CITY;
    	
    	for (int index = 0; index < RestartPointNames.length; index++)
		{
    		if (RestartPointNames[index].equals(rp.getName())) {
    			city=TCities[index];
    			break;
    		}
		}
    	if (city.equals(TCity.NON_CITY))
        	return;
        
        Npc GK = World.getInstance().getNpc(GateKeepers[city.getValue()]);
        if (player.isWalk() && Functions.distanceBetween(player.getPosition(), GK.getPosition()) < 250)
        	return;
        
        final List<TeleportLocation> teleports = TeleportData.getInstance().getTeleports(GK.getNpcId());
		if (teleports == null)
			return;
		double minDistance = 1000000000;
		TeleportLocation closestTeleport = null;
		for (int index = 0; index < teleports.size(); index++)
		{
			final TeleportLocation teleport = teleports.get(index);
			if (teleport == null)
				continue;
			double distance = Functions.distanceBetween(x, y, z, teleport.getX(), teleport.getY(), teleport.getZ());
			if (distance<minDistance) {
				minDistance=distance;
				closestTeleport = teleport;
			}
		}
		if (closestTeleport==null) {
			LOGGER.info("closestTeleport is null");
			return;
		}
		String targetLoc = closestTeleport.getDesc();
		
		if (Functions.distanceBetween(player.getPosition(), new Location(x,y,z)) < 10000){
			MoveToLocation(player, x, y, z);
			return;
		}
		
		TeleportToLocation(player, targetLoc, true);
    }
    
    public static void TeleportTo(SimPlayer player, String loc) {
    	TCity city = getCity(player, false, false);
        
        if (city.equals(TCity.NON_CITY))
        	return;

        if (player.isWalk())
        	return;
        
        Npc GK = World.getInstance().getNpc(GateKeepers[city.getValue()]);

        if (Functions.distanceBetween(player.getPosition(), GK.getPosition()) > 250)
        	return;
        
    	final List<TeleportLocation> teleports = TeleportData.getInstance().getTeleports(GK.getNpcId());
		if (teleports == null)
			return;
        
		for (int index = 0; index < teleports.size(); index++)
		{
			final TeleportLocation teleport = teleports.get(index);
			if (teleport == null)
				continue;
			
	    	player.resetWalk();
			
			if (loc.equals(teleport.getDesc()))
				player.teleportTo(teleport.getX(), teleport.getY(), teleport.getZ(), 0);
		}
    }
    
}