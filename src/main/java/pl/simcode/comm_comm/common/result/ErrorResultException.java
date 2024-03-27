package pl.simcode.comm_comm.common.result;

public class ErrorResultException extends RuntimeException {

    private final Error error;

    public ErrorResultException(Error error) {
        super(String.format("Error occurred: %s", error.toString()));
        this.error = error;
    }

    public Error getError() {
        return error;
    }

}
