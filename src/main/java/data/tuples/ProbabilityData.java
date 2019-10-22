package data.tuples;

import java.util.Objects;

public class ProbabilityData {
    public final double step;
    public final double threshold;

    public ProbabilityData(double step, double threshold) {
        this.step = step;
        this.threshold = threshold;
    }

    public ProbabilityData() {
        this(0.0, 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbabilityData that = (ProbabilityData) o;
        return Double.compare(that.step, step) == 0 &&
                Double.compare(that.threshold, threshold) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, threshold);
    }
}
