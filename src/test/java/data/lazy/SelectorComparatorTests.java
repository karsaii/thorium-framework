package data.lazy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import selector.extensions.SelectorComparator;
import selectorSpecificity.tuples.SpecificityData;

import java.util.stream.Stream;

public class SelectorComparatorTests {
    public static final SpecificityData zeroes = new SpecificityData(0.0, 0.0, 0.0, 0.0);
    public static final SpecificityData zeroes2 = new SpecificityData(0.0, 0.0, 0.0, 0.0);
    public static final SpecificityData ones = new SpecificityData(1.0, 1.0, 1.0, 1.0);
    public static final SpecificityData onlyId = new SpecificityData(1.0, 0.0, 0.0, 0.0);
    public static final SpecificityData twoId = new SpecificityData(2.0, 0.0, 0.0, 0.0);
    public static final SpecificityData threeIdsAndEverythingElseRandomly = new SpecificityData(3.0, 0.0, 6.0, 7.0);
    public static final SpecificityData threeIdsAndEverythingElseRandomly2 = new SpecificityData(3.0, 9.0, 6.0, 0.0);
    public static final SpecificityData threeIdsAndEverythingElseRandomly3 = new SpecificityData(3.0, 9.0, 6.0, 10.0);

    public static final SpecificityData onlyXpath = new SpecificityData(0.0, 0.0, 0.0, 1.0);
    public static final SpecificityData twoXpath = new SpecificityData(0.0, 0.0, 0.0, 2.0);
    public static final SpecificityData oneClassAndXpath = new SpecificityData(0.0, 1.0, 0.0, 1.0);
    public static final SpecificityData oneIdAndElement = new SpecificityData(1.0, 0.0, 1.0, 0.0);

    public static final SpecificityData onlyClass = new SpecificityData(0.0, 1.0, 0.0, 0.0);
    public static final SpecificityData onlyElement = new SpecificityData(0.0, 0.0, 1.0, 0.0);
    public static final SpecificityData idAndClass = new SpecificityData(1.0, 1.0, 0.0, 0.0);
    public static final SpecificityData twoIds = new SpecificityData(2.0, 0.0, 0.0, 0.0);
    public static final SpecificityData noIdAndEverythingElseRandomly = new SpecificityData(0.0, 9.0, 2.0, 1.0);

    public static Stream<Arguments> betterProvider() {
        return Stream.of(
                Arguments.of("Zeroes equal to self, same", zeroes, zeroes2, zeroes2, "any", "Zeroes weren't the same as self."),
                Arguments.of("Only single ID better than zeroes", onlyId, zeroes, onlyId, "left", "Single ID weren't better than zeroes."),
                Arguments.of("Only single ID better than two IDs", onlyId, twoId, onlyId, "left", "Single ID weren't better than Two IDs."),
                Arguments.of("Only single ID better than random 1 all (3 IDs)", onlyId, threeIdsAndEverythingElseRandomly, onlyId, "left", "Single ID weren't better than random 1 all (3 IDs)."),
                Arguments.of("Only single ID better than random 1 all (3 IDs) - switched", threeIdsAndEverythingElseRandomly, onlyId, onlyId, "right", "Single ID weren't better than random 1 all (3 IDs) - switched."),
                Arguments.of("Only single ID better than random 2 all (3 IDs)", onlyId, threeIdsAndEverythingElseRandomly2, onlyId, "left", "Single ID weren't better than random 2 all (3 IDs)."),
                Arguments.of("Only single ID better than random 2 all (3 IDs) - switched", threeIdsAndEverythingElseRandomly2, onlyId, onlyId, "right", "Single ID weren't better than random 2 all (3 IDs) - switched."),
                Arguments.of("Only single ID better than random 3 all (3 IDs)", onlyId, threeIdsAndEverythingElseRandomly3, onlyId, "left", "Single ID weren't better than random 3 all (3 IDs)."),
                Arguments.of("Only single ID better than random 3 all (3 IDs) - switched", threeIdsAndEverythingElseRandomly3, onlyId, onlyId, "right", "Single ID weren't better than random 3 all (3 IDs) - switched."),
                Arguments.of("Only single ID better than two IDs - switched", twoId, onlyId, onlyId, "left", "Single ID weren't better than Two IDs."),
                Arguments.of("Only single ID better than zeroes - switched", zeroes, onlyId, onlyId, "right", "Single ID weren't better than zeroes, switched."),
                Arguments.of("Only single ID better than only ones", onlyId, ones, onlyId, "left", "Single ID weren't better than only ones."),
                Arguments.of("Only single ID better than id and class", onlyId, idAndClass, onlyId, "left", "Single ID weren't better than Id and Class."),
                Arguments.of("Only single ID better than id and selenium.element", onlyId, oneIdAndElement, onlyId, "left", "Single ID weren't better than Id and selenium.element."),
                Arguments.of("Only single ID better than id and selenium.element  - switched", oneIdAndElement, onlyId, onlyId, "right", "Single ID weren't better than Id and selenium.element - switched."),
                Arguments.of("Only single ID better than two ids", onlyId, twoIds, onlyId, "left", "Single ID weren't better than zeroes."),
                Arguments.of("Only single ID better than xpath", onlyId, onlyXpath, onlyId, "left", "Single ID weren't better than xpath."),
                Arguments.of("Only single ID better than class", onlyId, onlyClass, onlyId, "left", "Single ID weren't better than single class."),
                Arguments.of("Id and class better than xpath", idAndClass, onlyXpath, idAndClass, "left", "Id and Class weren't better than xpath."),
                Arguments.of("Only single Id better than everything else", onlyId, noIdAndEverythingElseRandomly, onlyId, "left", "Id weren't better than everything else."),
                Arguments.of("xpath better than anything else", noIdAndEverythingElseRandomly, onlyXpath, onlyXpath, "left", "Anything other than ID was better than xpath."),
                Arguments.of("Only class better than only xpath", onlyClass, onlyXpath, onlyClass, "left", "Only class wasn't better than only xpath."),
                Arguments.of("Only class better than only xpath - switched", onlyXpath, onlyClass , onlyClass, "right", "Only class wasn't better than only xpath."),
                Arguments.of("Only class better than only selenium.element", onlyClass, onlyElement, onlyClass, "left", "Only class wasn't better than only selenium.element."),
                Arguments.of("Only class better than only selenium.element - switched", onlyElement, onlyClass, onlyClass, "right", "Only class wasn't better than only selenium.element.")
        );
    }
    @ParameterizedTest(name = "{0}")
    @MethodSource("betterProvider")
    public void better(String name, SpecificityData left, SpecificityData right, SpecificityData expected, String expectedOutcome, String message) {
        final var result = SelectorComparator.better(left, right);
        Assertions.assertEquals(expected, result, message + "\n EXPECTED: " + expectedOutcome + "\n Left:" + left.toString() + "\nRight:" + right.toString() + "\n");
    }
}
