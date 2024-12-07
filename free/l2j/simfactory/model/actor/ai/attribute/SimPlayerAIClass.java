package free.l2j.simfactory.model.actor.ai.attribute;

import net.sf.l2j.commons.data.StatSet;

import net.sf.l2j.gameserver.enums.actors.ClassId;

public record SimPlayerAIClass (int classId, boolean isMagicClass) {
	public SimPlayerAIClass(StatSet set) {
		this(set.getInteger("classId"), set.getBool("isMagicClass"));
	}
	
	public ClassId getClassID(){
		for(ClassId _classId: ClassId.VALUES) {
			if (_classId.getId() == classId)
				return _classId;
		}
		return null;
	}
	
	public boolean isMagicClass(){
		return isMagicClass;
	}
}