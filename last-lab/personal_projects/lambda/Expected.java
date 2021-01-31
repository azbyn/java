package personal_projects.lambda;


import java.util.Optional;

public final class Expected<R, E extends Exception> {
    public static <R, E extends Exception> Expected<R, E> success(R value) {
        return new Expected<>(value, null);
    }
    public static <R, E extends Exception> Expected<R, E> error(E value) {
        return new Expected<>(null, value);
    }

    private final R res;
    private final E error;
    private Expected(R r, E e) {
        res = r;
        error = e;
    }
    boolean success() { return error == null; }
    R orThrow() throws E {
        if (success()) return res;
        throw error;
    }
    R result() { return res; }

    E error() { return error; }

    @Override
    public String toString() {
        if (success())
            return "Success{"+result()+"}";
        return "Error{"+error()+"}";
    }
}
