import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2018-12-18.
 */
public class KleisliWriterCategoryTest {
    
    @Test
    public void identity_right() {
        var composed = KleisliWriterCategory.compose(this::toUpperCase, KleisliWriterCategory.identity());
        
        var writer = composed.apply("test");
        
        assertThat(writer.result, is("TEST"));
        assertThat(writer.log, is("-toUpperCase"));
    }

    @Test
    public void identity_left() {
        var composed = KleisliWriterCategory.compose(KleisliWriterCategory.identity(), this::toUpperCase);

        var writer = composed.apply("test");

        assertThat(writer.result, is("TEST"));
        assertThat(writer.log, is("-toUpperCase"));
    }

    @Test
    public void composing_toUpperCase_toLetters() {
        var composed = KleisliWriterCategory.compose(this::toUpperCase, this::toLetters);

        var writer = composed.apply("test");

        assertThat(writer.result, is(new String[]{"T", "E", "S", "T"}));
        assertThat(writer.log, is("-toUpperCase-toLetters"));
    }

    private Writer<String> toUpperCase(String str) {
        return new Writer<>(str.toUpperCase(), "-toUpperCase");
    }

    private Writer<String[]> toLetters(String str) {
        return new Writer<>(str.split(""), "-toLetters");
    }
}