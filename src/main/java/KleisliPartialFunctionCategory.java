import java.util.Optional;
import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-12-18.
 */
class KleisliPartialFunctionCategory {
    
    static <A> Function<A, Optional<A>> identity() {
        return Optional::of;
    }
    
    static <A, B, C> Function<A, Optional<C>> compose(Function<A, Optional<B>> f1,
                                                    Function<B, Optional<C>> f2) {
        return f1.andThen(result -> result.flatMap(f2));
    }
}
