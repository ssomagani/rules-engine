import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

/**
 *	Test Stored Procedure that loads predicate strings from the DB and uses those rules
 *		to run IFTTT actions (nested and sequential) 
 *
 *	The individual conditions are represented by predicate Strings but organizing the rules
 *		is still programmatic. I think we should be able to define a small set and define
 *		them declaratively for storing external to the Java program. 
 */
public class TestSP extends VoltProcedure {

	private static final ScriptEngine NASHORN = new ScriptEngineManager().getEngineByName("nashorn");
	
	private static final SQLStmt SEL_COUNT = new SQLStmt("SELECT * FROM CAR_MOVEMENT WHERE car_id = ?");
	private static final SQLStmt INSERT = new SQLStmt("INSERT INTO CAR_MOVEMENT VALUES (?, ?, ?, ?) ");
	private static final SQLStmt INSERT_NEW = new SQLStmt("INSERT INTO NEW_CAR VALUES (?, ?, ?, ?)");
	private static final SQLStmt SEL_PREDICATE = new SQLStmt("SELECT string FROM predicate_strings WHERE id = ?");
	
	/**
	 * If the car's velocity fails the predicate, 
	 * 		Return;
	 * If this is a new car, 
	 * 		insert into new_car table;
	 * Regardless,
	 * 		Insert car's movement into car_movement.
	 * 
	 */
	public VoltTable[] run(int car_id, double velocity, int distance, String intersection) {
		
		SQLAction velctyPredSelAction = new SQLAction(this, SEL_PREDICATE, "velocity");
		SQLAction newCarPredSelAction = new SQLAction(this, SEL_PREDICATE, "new_car");
		SQLAction intSelAction = new SQLAction(this, SEL_COUNT, car_id);
		SQLAction insertNewAction = new SQLAction(this, INSERT_NEW, car_id, velocity, distance, intersection);
		SQLAction insertAction = new SQLAction(this, INSERT, car_id, velocity, distance, intersection);
		
		PredicateRule<SQLAction, SQLAction> newCarRule = 
				new PredicateRule<>(NASHORN, newCarPredSelAction, intSelAction, insertNewAction);
		
		SequentialActionChain actionChain = new SequentialActionChain(newCarRule, insertAction);

		PredicateRule<SQLAction, Action<?>> velocityRule = new PredicateRule<>(
				NASHORN, 
				velctyPredSelAction, 
				() -> {return velocity;}, 
				actionChain
				);
		
		velocityRule.execute();
		
		return null;
	}
}
