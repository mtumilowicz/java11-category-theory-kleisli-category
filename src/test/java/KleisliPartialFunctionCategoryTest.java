import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-12-18.
 */
public class KleisliPartialFunctionCategoryTest {
    
    @Test
    public void identity_left() {
        var composed = KleisliPartialFunctionCategory.compose(KleisliPartialFunctionCategory.identity(), this::safeReciprocal);
        
        assertTrue(composed.apply(0d).isEmpty());
        
        assertThat(composed.apply(0.25d).orElseThrow(), is(4d));
    }

    @Test
    public void identity_right() {
        var composed = KleisliPartialFunctionCategory.compose(this::safeReciprocal, KleisliPartialFunctionCategory.identity());

        assertTrue(composed.apply(0d).isEmpty());

        assertThat(composed.apply(0.25d).orElseThrow(), is(4d));
    }
    

    @Test
    public void composing_safeRoot_safeReciprocal_negativeNumber() {
        var composed = KleisliPartialFunctionCategory.compose(this::safeRoot, this::safeReciprocal);

        var reciprocalRoot = composed.apply(-1d);
        
        assertTrue(reciprocalRoot.isEmpty());
    }

    @Test
    public void composing_safeRoot_safeReciprocal_zero() {
        var composed = KleisliPartialFunctionCategory.compose(this::safeRoot, this::safeReciprocal);

        var reciprocalRoot = composed.apply(0d);

        assertTrue(reciprocalRoot.isEmpty());
    }

    @Test
    public void composing_safeRoot_safeReciprocal_81() {
        var composed = KleisliPartialFunctionCategory.compose(this::safeRoot, this::safeReciprocal);

        var reciprocalRoot = composed.apply(0.25d);

        assertThat(reciprocalRoot.orElseThrow(), is(2d));
    }
    
    private Optional<Double> safeRoot(double x) {
        return (x >= 0) ? Optional.of(Math.sqrt(x)) : Optional.empty();
    }

    private Optional<Double> safeReciprocal(double x) {
        return x != 0 ? Optional.of(1 / x) : Optional.empty();
    }
}