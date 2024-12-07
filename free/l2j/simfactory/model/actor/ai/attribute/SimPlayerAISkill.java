package free.l2j.simfactory.model.actor.ai.attribute;

import net.sf.l2j.commons.data.StatSet;

import net.sf.l2j.gameserver.enums.actors.ClassId;

public record SimPlayerAISkill (int classId, int skillId, int priority) {

	public SimPlayerAISkill(StatSet set) 
	{
		this(set.getInteger("classId"), set.getInteger("skillId"), set.getInteger("priority"));
	}
	
	public int getSkill()
	{
		return skillId;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public ClassId getClassID(){
		for(ClassId _classId: ClassId.VALUES) {
			if (_classId.getId() == classId)
				return _classId;
		}
		return null;
	}
}