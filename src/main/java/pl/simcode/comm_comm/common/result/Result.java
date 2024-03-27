package pl.simcode.comm_comm.common.result;

import io.vavr.control.Either;
import org.springframework.util.Assert;

public class Result<T> {

    private final Either<Error, T> outcome;

    public static <T> Result<T> failure(Error error) {
        return new Result<>(Either.left(error));
    }

    public static <T> Result<T> success(T payload) {
        return new Result<>(Either.right(payload));
    }

    public static <T> Result<T> success() {
        return new Result<>(Either.right(null));
    }

    private Result(Either<Error, T> outcome) {
        this.outcome = outcome;
    }

    public boolean isError() {
        return outcome.isLeft();
    }

    public boolean isSuccess() {
        return outcome.isRight();
    }

    public Error getError() {
        Assert.isTrue(isError(), "This is success result so cannot get Error from it");

        return outcome.getLeft();
    }

    public T getPayload() {
        Assert.isTrue(isSuccess(), "This is error result so cannot get payload from it");

        return outcome.get();
    }
}
