package expressionTree.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.Assert;

/**
 * An Environment maintains a list of variable name and a mapping from those
 * variable names to the boolean values (false and true).
 *
 * @author theo
 */
public class Environment {
    private HashMap<String, Boolean> map = new HashMap<>();
    private ArrayList<String> names = new ArrayList<>();

    /**
     * Is the given name among the set of variable names in the Environment?
     *
     * @param name -- a name to check.
     * @return
     */
    public boolean has(String name) {
        return map.containsKey(name);
    }

    /**
     * Add the given name to the set of variable names.
     * <p>
     * Precondition: The name must not be mapped:  ! has(name)
     * <p>
     * Initially the name will be mapped to "false".
     *
     * @param name -- a name to add.
     */
    public void add(String name) {
        Assert.check(!has(name));
        map.put(name, false);
        names.add(name);
    }

    /**
     * Get the value that a given name maps to
     * <p>
     * Precondition: The name must be mapped: has(name)
     * <p>
     * Result is the the current mapping for the name.
     *
     * @param name
     * @return
     */
    public boolean get(String name) {
        Assert.check(has(name));
        return map.get(name);
    }

    /**
     * Get a list of all names.
     * <p>
     * The list returned is a copy; so modifying it will not corrupt the data structure.
     *
     * @return
     */
    public List<String> getNames() {
		return List.copyOf(names);
        // return (List<String>) names.clone();
    }

    /**
     * Change the mapping of one variable name.
     *
     * @param name
     * @param value
     */
    public void set(String name, boolean value) {
        Assert.check(has(name));
        map.put(name, value);
    }

    /**
     * Change the mapping so that it is the next mapping in "binary order".
     * <p>
     * You can consider the mapping as representing a number in binary.
     * Each name corresponds to a unique power of 2 and number is
     * obtained by adding the powers that correspond to names that
     * map to true.
     */
    public void advanceToNextState() {
        for (int i = names.size() - 1; i >= 0; --i) {
            if (!map.get(names.get(i))) {
                map.put(names.get(i), true);
                break;
            } else {
                map.put(names.get(i), false);
            }
        }
    }

}
