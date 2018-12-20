[![Build Status](https://travis-ci.com/mtumilowicz/java11-category-theory-kleisli-category.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-category-theory-kleisli-category)

# java11-category-theory-kleisli-category

_Reference_: https://bartoszmilewski.com/2014/12/23/kleisli-categories/

# preface
* Kleisli operator (`>=>`) is generalisation of function 
composition (associativity).

# project description
We provide:
* Java implementation of the given examples (c++, haskell)
    * category
        ```
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
        ```
        where `Writer` is simply:
        ```
        class Writer<X> {
            X result;
            String log;
        
            Writer(X result, String log) {
                this.result = result;
                this.log = log;
            }
        }
        ```
    * tests
        ```
        var composed = KleisliWriterCategory.compose(this::toUpperCase, this::toLetters);
        
        var writer = composed.apply("test");
        
        assertThat(writer.result, is(new String[]{"T", "E", "S", "T"}));
        assertThat(writer.log, is("-toUpperCase-toLetters"));
        ```
        where functions are:
        ```
        private Writer<String> toUpperCase(String str) {
            return new Writer<>(str.toUpperCase(), "-toUpperCase");
        }
        
        private Writer<String[]> toLetters(String str) {
            return new Writer<>(str.split(""), "-toLetters");
        }
        ```
* Java implementation of challenges (partial function category)
    * category
        ```
        static <A> Function<A, Optional<A>> identity() {
            return Optional::of;
        }
        
        static <A, B, C> Function<A, Optional<C>> compose(Function<A, Optional<B>> f1,
                                                        Function<B, Optional<C>> f2) {
            return f1.andThen(result -> result.flatMap(f2));
        }
        ```
    * tests
        ```
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
        ```
        where functions are:
        ```
         private Optional<Double> safeRoot(double x) {
             return (x >= 0) ? Optional.of(Math.sqrt(x)) : Optional.empty();
         }
        
         private Optional<Double> safeReciprocal(double x) {
             return x != 0 ? Optional.of(1 / x) : Optional.empty();
         }
        ```