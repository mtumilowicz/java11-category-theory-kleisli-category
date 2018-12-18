/**
 * Created by mtumilowicz on 2018-12-18.
 */
class Writer<X> {
    X result;
    String log;

    Writer(X result, String log) {
        this.result = result;
        this.log = log;
    }
}
