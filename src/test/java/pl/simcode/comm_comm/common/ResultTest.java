package pl.simcode.comm_comm.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.simcode.comm_comm.common.result.Error;
import pl.simcode.comm_comm.common.result.Result;

import static org.junit.jupiter.api.Assertions.*;
import static pl.simcode.comm_comm.common.result.ErrorType.APPLICATION_ERROR;

class ResultTest {

    @Test
    @DisplayName("Should throw exception when trying to get Error from successful Result")
    void testErrorFromSuccessfulResult() {
        // given
        var result = Result.success(somePayload());

        // then
        assertThrows(IllegalArgumentException.class, result::getError);
    }

    @Test
    @DisplayName("Should throw exception when trying to get payload from failure Result")
    void testPayloadFromFailureResult() {
        // given
        var result = Result.failure(someError());

        // then
        assertThrows(IllegalArgumentException.class, result::getPayload);
    }

    @Test
    @DisplayName("Should return payload from successful Result")
    void testPayloadFromSuccessResult() {
        // given
        var expectedPayload = somePayload();
        var result = Result.success(expectedPayload);

        // when
        var actualPayload = result.getPayload();

        // then
        assertEquals(expectedPayload, actualPayload);
    }

    @Test
    @DisplayName("Should return Error from failure Result")
    void testErrorFromFailureResult() {
        // given
        var expectedError = someError();
        var result = Result.failure(expectedError);

        // when
        var actualError = result.getError();

        // then
        assertEquals(expectedError, actualError);
    }

    @Test
    @DisplayName("Should return false when calling isSuccess on failure Result")
    void testIsSuccessOnFailureResult() {
        // given
        var result = Result.failure(someError());

        // when
        boolean isSuccess = result.isSuccess();

        // then
        assertFalse(isSuccess);
    }

    @Test
    @DisplayName("Should return true when calling isSuccess on successful Result")
    void testIsSuccessOnSuccessResult() {
        // given
        var result = Result.success(somePayload());

        // when
        boolean isSuccess = result.isSuccess();

        // then
        assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Should return false when calling isError on successful Result")
    void testIsErrorOnSuccessResult() {
        // given
        var result = Result.success(somePayload());

        // when
        boolean isError = result.isError();

        // then
        assertFalse(isError);
    }

    @Test
    @DisplayName("Should return true when calling isError on failure Result")
    void testIsErrorOnFailureResult() {
        // given
        var result = Result.failure(someError());

        // when
        boolean isError = result.isError();

        // then
        assertTrue(isError);
    }

    private static String somePayload() {
        return "Success!";
    }

    private static Error someError() {
        return new Error("123", "xyz", APPLICATION_ERROR);
    }

}