package com.github.karsaii.selenium.selector.extensions;

import selectorSpecificity.tuples.SpecificityData;

import java.util.Objects;

import static com.github.karsaii.selenium.selector.constants.SelectorComparatorConstants.LEFT;
import static com.github.karsaii.selenium.selector.constants.SelectorComparatorConstants.MIDDLE;
import static com.github.karsaii.selenium.selector.constants.SelectorComparatorConstants.RIGHT;
import static selectorSpecificity.Specificity.getSpecificityValuesInOrder;

public interface SelectorComparator {
    static SpecificityData better(SpecificityData left, SpecificityData right) {
        final var leftValues = getSpecificityValuesInOrder(left);
        final var rightValues = getSpecificityValuesInOrder(right);
        final var length = leftValues.size();
        double leftValue;
        double rightValue;
        var previous = "";
        var value = MIDDLE;
        for(var index = 0; index < length; ++index) {
            leftValue = leftValues.get(index);
            rightValue = rightValues.get(index);
            if (leftValue == rightValue) {
                previous = MIDDLE;
                continue;
            }

            if (Objects.equals(previous, MIDDLE)) {
                if (rightValue > 0.0) {
                    value = leftValue < 2.0 ? LEFT : RIGHT;
                }
                if (leftValue > 0.0) {
                    value = rightValue < 2.0 ? RIGHT : LEFT;
                }
                previous = value;
                continue;
            }

            if (Objects.equals(previous, LEFT)) {
                if (leftValue == 0.0) {
                    value = LEFT;
                }
                if (rightValue == 0.0) {
                    value = RIGHT;
                }
                previous = value;
                continue;
            }

            if (Objects.equals(previous, RIGHT)) {
                if (leftValue == 0.0) {
                    value = LEFT;
                }

                if (rightValue == 0.0) {
                    value = RIGHT;
                }

                previous = value;
                continue;
            }

            if (leftValue < 2.0) {
                value = rightValue == 1.0 ? RIGHT : LEFT;
            }

            if (rightValue < 2.0) {
                value = leftValue == 1.0 ? LEFT : RIGHT;
            }
            previous = value;
        }

        return Objects.equals(value, MIDDLE) ? left : Objects.equals(value, LEFT) ? left : right;
    }
}
