package org.dio.commons.testutils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

/**
 * User: Alex.Chumachev
 * Date: 2/19/13
 */
public class POJOAssert {
    private final Map<String, Object> values = new HashMap<String, Object>();
    private final Collection<String> ignores = new HashSet<String>();

    public POJOAssert ignore(String... properties) {
        Collections.addAll(ignores, properties);

        return this;
    }

    public POJOAssert expect(String property, Object value) {
        values.put(property, value);

        return this;
    }

	@SuppressWarnings("unchecked")
	public void assertPOJO(Object value) {
        final BeanMap map = new BeanMap(value);
        Map<String, Object> errors = new HashMap<String, Object>();

        for (Object e : map.entrySet()) {
        	Map.Entry<String, Object> entry = (Map.Entry<String, Object>) e; 
			String property = entry.getKey();
			Object actual = entry.getValue();

            if ("class".equals(property)) {
                continue;
            }

            if (ignores.contains(property)) {
                ignores.remove(property);
                continue;
            } 

            boolean found = false;
            for (Map.Entry<String, Object> expectation : values.entrySet()) {
                if (expectation.getKey().equals(property)) {
                    Object expected = values.get(property);

                    if (!compare(expected, actual)) {
                        throw new AssertionError("Property " + property + " not the same as expected. " +
                                "Expected [" + expected + "] actual [" + actual + "]");
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                errors.put(property, actual);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : errors.entrySet()) {
            sb.append("Property [")
                    .append(e.getKey())
                    .append("] wasn't expected and has value [")
                    .append(e.getValue())
                    .append("]. ");
        }

        if (!ignores.isEmpty()) {
            sb.append(ignores.size() > 1 ? "Properties " : "Property ")
                    .append(ignores)
                    .append(" was declared as ignored but never used.\r\n");
        }

        if (sb.length() > 0) {
            throw new AssertionError(sb.toString());
        }
    }

    private boolean compare(Object v1, Object v2) {
        return v1 == v2 || (v1 != null && v1.equals(v2));
    }
}
