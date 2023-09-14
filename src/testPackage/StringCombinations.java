package testPackage;

/****************************************************************************
 * <b>Title:</b> StringCombinations.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 23, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

import java.util.ArrayList;
import java.util.List;

class Phase {
    private List<String> values;

    public Phase(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}

public class StringCombinations {

    public static List<List<String>> generateCombinations(List<Phase> phases) {
        List<List<String>> combinations = new ArrayList<>();
        generateCombinationsHelper(phases, new ArrayList<>(), 0, combinations);
        return combinations;
    }

    private static void generateCombinationsHelper(
            List<Phase> phases,
            List<String> currentCombination, int phaseIndex,
            List<List<String>> combinations) {

        if (phaseIndex == phases.size()) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        Phase currentPhase = phases.get(phaseIndex);
        List<String> phaseValues = currentPhase.getValues();
        for (String value : phaseValues) {
            currentCombination.add(value);
            System.out.println("before recursive call: " + currentCombination);
            generateCombinationsHelper(phases, currentCombination, phaseIndex + 1, combinations);
            currentCombination.remove(currentCombination.size() - 1);
            System.out.println("after recursive call backtracking: " + currentCombination);
        }
    }

    public static void main(String[] args) {
        List<Phase> phases = new ArrayList<>();
        phases.add(new Phase(List.of("a1", "a2")));
        phases.add(new Phase(List.of("b1", "b2", "b3")));
//        phases.add(new Phase(List.of("c1", "c2")));

        List<List<String>> combinations = generateCombinations(phases);
//        for (List<String> combination : combinations) {
//            System.out.println(combination);
//        }
    }
}
