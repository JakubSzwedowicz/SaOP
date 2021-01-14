package TestBuilder;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class TestBuilder {

    public interface SupplierWrapper {
        List<List> get() throws Throwable;
    }

    // Main function to build tests
    public static Stream<Arguments>
    buildParametersWithAnswers(SupplierWrapper a_parameters, SupplierWrapper a_answers) throws Throwable {
        return mergeParametersWithAnswers(a_parameters.get(), a_answers.get());
    }

    // After dozens of hours I cannot find any better solution for how to merge two List<List> into one Stream<Arguments>
    private static Stream<Arguments> mergeParametersWithAnswers(List<List> a_parameters, List<List> a_answers) {
        Stream.Builder<Arguments> builder = Stream.builder();
        for(int i = 0; i < a_parameters.size(); i++){
            builder.add(Arguments.of(a_parameters.get(i).get(0), a_parameters.get(i).get(1), a_answers.get(i).get(0)));
        }
        return builder.build();
    }
}
