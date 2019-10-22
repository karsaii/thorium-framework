package data.lazy;

import selectorSpecificity.tuples.SpecificityData;

import java.util.Objects;

import static selectorSpecificity.Specificity.getSpecificityValuesInOrder;

public class SelectorComparator {
    static SpecificityData better(SpecificityData left, SpecificityData right) {
        final var leftValues = getSpecificityValuesInOrder(left);
        final var rightValues = getSpecificityValuesInOrder(right);
        final var length = leftValues.size();
        double leftValue;
        double rightValue;
        var previous = "";
        var value = "middle";
        for(var index = 0; index < length; ++index) {
            leftValue = leftValues.get(index);
            rightValue = rightValues.get(index);
            if (leftValue == rightValue) {
                previous = "middle";
                continue;
            }

            if (Objects.equals(previous, "middle")) {
                if (rightValue > 0.0) {
                    value = leftValue < 2.0 ? "left" : "right";
                }
                if (leftValue > 0.0) {
                    value = rightValue < 2.0 ? "right" : "left";
                }
                previous = value;
                continue;
            }

            if (Objects.equals(previous, "left")) {
                if (leftValue == 0.0) {
                    value = "left";
                }
                if (rightValue == 0.0) {
                    value = "right";
                }
                previous = value;
                continue;
            }

            if (Objects.equals(previous, "right")) {
                if (leftValue == 0.0) {
                    value = "left";
                }

                if (rightValue == 0.0) {
                    value = "right";
                }

                previous = value;
                continue;
            }

            if (leftValue < 2.0) {
                value = rightValue == 1.0 ? "right" : "left";
            }

            if (rightValue < 2.0) {
                value = leftValue == 1.0 ? "left" : "right";
            }
            previous = value;
        }

        return Objects.equals(value, "middle") ? left : Objects.equals(value, "left") ? left : right;
    }
}
