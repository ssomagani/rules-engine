import java.util.function.Predicate;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 *	Definition of a rule that fetches a predicate using U and evaluates it against 
 *		the result of V. Executes action if predicate succeeds.
 */
public class PredicateRule<U extends Action<?>, V extends Action<?>> implements Action<Void> {

	private final ScriptEngine nashorn;
	private U predicateAction;
	private V intSelAction;
	private Action<?> action;

	public PredicateRule(ScriptEngine nashorn, 
			U predicateAction,
			V intSelAction, 
			Action<?> action) {
		this.nashorn = nashorn;
		this.predicateAction = predicateAction;
		this.intSelAction = intSelAction;
		this.action = action;
	}
	
	private Predicate<Object> instantiatePredicate(String predicateStr) {
		try {
			return (Predicate<Object>) nashorn.eval(
			        "new java.util.function.Predicate(" + predicateStr + ")");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Fetch the predicate String and test it against the return value of
	 * 	intSelAction.
	 * Execute action if predicate succeeds.
	 */
	public Void execute() {
		String predicateStr = (String) predicateAction.execute();
		Object predicateParam = intSelAction.execute();

		if(instantiatePredicate(predicateStr).test(predicateParam)) {
			action.execute();
		}
		return null;
	}
}
