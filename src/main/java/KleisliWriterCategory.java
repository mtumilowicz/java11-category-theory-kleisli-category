import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-12-18.
 */
class KleisliWriterCategory {
    
    static <A> Function<A, Writer<A>> identity() {
        return (A a) -> new Writer<>(a, "");
    }

    static <A, B, C> Function<A, Writer<C>> compose(Function<A, Writer<B>> f1,
                                                    Function<B, Writer<C>> f2) {
        return (A a) -> {
            Writer<B> f1value = f1.apply(a);
            Writer<C> f2value = f2.apply(f1value.result);

            return new Writer<>(f2value.result, f1value.log + f2value.log);
        };
    }
}
