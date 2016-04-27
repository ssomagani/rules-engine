
/**
 * 
 * Execute actions one after the other
 * 
 */
public class SequentialActionChain implements Action<Void> {

	Action<?>[] actions;
	
	public SequentialActionChain(Action<?>... actions) {
		this.actions = actions;
	}
	
	@Override
	public Void execute() {
		for(Action<?> action : actions) {
			action.execute();
		}
		return null;
	}

}
