package free.l2j.simfactory.model.actor.ai.preference;

public abstract class SimSkill {
	protected int _skillId;
	protected int _priority;
	
	public SimSkill(int skillId, int priority) {
		_skillId = skillId;
	}

	public SimSkill(int skillId) {
		_skillId = skillId;
		_priority = 0;
	}
	
	public int getSkillId() {
		return _skillId;
	}	
	
	public int getPriority() {
		return _priority;
	}
}