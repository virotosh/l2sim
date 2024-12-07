package free.l2j.simfactory.data.xml;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.commons.data.xml.IXmlReader;

import org.w3c.dom.Document;

import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAIClass;
import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAISkill;

public class SimPlayerAIData implements IXmlReader{
	private final List<SimPlayerAIClass> _classIds = new ArrayList<>();
	private final List<SimPlayerAISkill> _skillIds = new ArrayList<>();
	
	protected SimPlayerAIData() 
	{
		load();
	}
	@Override
	public void load()
	{
		parseFile("./data/xml/simPlayerAIs.xml");
		LOGGER.info("Loaded {} simPlayerAI templates.", _classIds.size());
		LOGGER.info("Loaded {} simPlayerAI skills.", _skillIds.size());
	}

	@Override
	public void parseDocument(Document doc, Path path)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "class", classNode ->
		{
			final StatSet classSet = parseAttributes(classNode);
			_classIds.add(new SimPlayerAIClass(classSet));
			forEach(classNode, "skill", skillNode ->
			{
				final StatSet skillset = parseAttributes(skillNode);
				skillset.set("classId", classSet.getInteger("classId"));
				_skillIds.add(new SimPlayerAISkill(skillset));
			});
		}));
		
	}
	
	public List<SimPlayerAIClass> getAvailableSimPlayerAIClasses(){
		return _classIds;
	}
	
	public List<SimPlayerAISkill> getAvailableSimPlayerAISkills(){
		return _skillIds;
	}
	
	public static SimPlayerAIData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final SimPlayerAIData INSTANCE = new SimPlayerAIData();
	}
}