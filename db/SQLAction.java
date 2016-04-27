import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;

/**
 *	Class that runs one SQL statement and fetches the first column
 *
 */
public class SQLAction implements Action<Object> {

	private SQLStmt stmt;
	private Object[] args;
	private VoltProcedure proc;
	
	public SQLAction(VoltProcedure proc, SQLStmt stmt, Object... args) {
		this.proc = proc;
		this.stmt = stmt;
		this.args = args;
	}
	
	public Object execute() {
		proc.voltQueueSQL(stmt, args);
		VoltTable table = proc.voltExecuteSQL()[0];
		if(table.advanceRow()) {
			VoltType type = table.getColumnType(0);
			Object value = table.get(0, type);
			return value;
		}
		return 0;
	}
}
