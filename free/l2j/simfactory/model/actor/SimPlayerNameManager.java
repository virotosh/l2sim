package free.l2j.simfactory.model.actor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.commons.data.xml.IXmlReader;
import net.sf.l2j.commons.random.Rnd;

import net.sf.l2j.gameserver.data.sql.PlayerInfoTable;

import org.w3c.dom.Document;

public class SimPlayerNameManager implements IXmlReader {
	
	private List<String> _simPlayerNames;
	
	protected SimPlayerNameManager() {
		load();
	}
	
	public String getRandomAvailableName() {
		String name = getRandomNameFromWordlist();
		
		while(nameAlreadyExists(name)) {
			name = getRandomNameFromWordlist();
		}
		
		return name;
	}
	
	private String getRandomNameFromWordlist() {
		return _simPlayerNames.get(Rnd.get(0, _simPlayerNames.size() - 1));
	}
	
	public List<String> getSimPlayerNames() {
		return _simPlayerNames;
	}
	
	@Override
	public void load()
    {
        try(LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(new File("./data/simnameworldlist.txt"))));)
        {
            String line;
            ArrayList<String> playersList = new ArrayList<String>();
            while((line = lnr.readLine()) != null)
            {
                if(line.trim().length() == 0 || line.startsWith("#"))
                    continue;
                playersList.add(line);
            }
            _simPlayerNames = playersList;
            LOGGER.info("Loaded {} SimPlayer names.", _simPlayerNames.size());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
	
	private boolean nameAlreadyExists(String name) {
		return PlayerInfoTable.getInstance().getPlayerObjectId(name) > 0;
	}
	
	public static SimPlayerNameManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final SimPlayerNameManager INSTANCE = new SimPlayerNameManager();
	}

	@Override
	public void parseDocument(Document doc, Path path)
	{
		// TODO Auto-generated method stub
		
	}
}