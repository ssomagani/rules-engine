/**
 * 
 */
package org.voltdb.example;

import java.util.function.Function;
import java.util.function.IntPredicate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author seetasomagani
 *
 */
public class RuleRunner {

	private static final String INT_PRED = "function(x) { if(x > 5)	return true; else return false;	}";
	
	public static void main(String[] args) {

		IntPredicate one;

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager(); 
		ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn"); 

		try {
			
			one = (IntPredicate) nashorn.eval(
		            String.format("new java.util.function.IntPredicate(%s)", INT_PRED));
			
			System.out.println(one.test(6));
			System.out.println(one.test(3));
		} catch(ScriptException e){
			System.out.println("Error executing script: "+ e.getMessage());
		}
		//	      System.out.println(result.toString());
	}
}
