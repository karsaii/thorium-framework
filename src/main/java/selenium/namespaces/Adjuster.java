package selenium.namespaces;

import core.extensions.DecoratedList;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.namespaces.Formatter;
import selector.records.SelectorKeySpecificityData;
import selenium.constants.AdjusterConstants;
import selenium.records.ProbabilityData;
import selenium.records.lazy.LazyIndexedElementParameters;

import java.util.Map;

public interface Adjuster {
    static Data<Boolean> adjustProbability(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, boolean increase, ProbabilityData data) {
        final var originalProbability = parameters.probability;
        final var step = data.step;
        parameters.probability += increase ? +step : -step;

        final var probability = parameters.probability;
        final var status = probability > data.threshold;
        if (!status) {
            parameters.probability = 0.0;
        }

        final var message = Formatter.getProbabilityAdjustmentMessage(key, originalProbability, probability, increase, !typeKeys.containsKey(key), !status);
        return DataFactoryFunctions.getBoolean(status, "adjustProbability", message);
    }

    static Data<Boolean> adjustProbability(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, boolean increase, double threshold, double step) {
        return adjustProbability(parameters, typeKeys, key, increase, new ProbabilityData(step, threshold));
    }

    static Data<Boolean> adjustProbabilityDefaultStep(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, boolean increase, double threshold) {
        return adjustProbability(parameters, typeKeys, key, increase, threshold, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }

    static Data<Boolean> adjustProbabilityDefaultThreshold(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, boolean increase, double step) {
        return adjustProbability(parameters, typeKeys, key, increase, AdjusterConstants.PROBABILITY_THRESHOLD, step);
    }

    static Data<Boolean> adjustProbabilityDefault(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, boolean increase) {
        return adjustProbability(parameters, typeKeys, key, increase, AdjusterConstants.PROBABILITY_THRESHOLD, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }

    static Data<Boolean> decreaseProbabilityDefault(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key) {
        return adjustProbability(parameters, typeKeys, key, false, AdjusterConstants.PROBABILITY_THRESHOLD, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }

    static Data<Boolean> increaseProbabilityDefault(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key) {
        return adjustProbability(parameters, typeKeys, key, true, AdjusterConstants.PROBABILITY_THRESHOLD, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }

    static Data<Boolean> decreaseProbabilityDefaultStep(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, double step) {
        return adjustProbability(parameters, typeKeys, key, false, AdjusterConstants.PROBABILITY_THRESHOLD, step);
    }

    static Data<Boolean> increaseProbabilityDefaultStep(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, double step) {
        return adjustProbability(parameters, typeKeys, key, true, AdjusterConstants.PROBABILITY_THRESHOLD, step);
    }

    static Data<Boolean> decreaseProbabilityDefaultThreshold(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, double threshold) {
        return adjustProbability(parameters, typeKeys, key, false, threshold, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }

    static Data<Boolean> increaseProbabilityDefaultThreshold(LazyIndexedElementParameters parameters, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, String key, double threshold) {
        return adjustProbability(parameters, typeKeys, key, true, threshold, AdjusterConstants.ADJUST_STEP_AMOUNT);
    }
}