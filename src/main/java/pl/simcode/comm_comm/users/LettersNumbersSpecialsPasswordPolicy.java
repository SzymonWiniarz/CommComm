package pl.simcode.comm_comm.users;

class LettersNumbersSpecialsPasswordPolicy implements PasswordPolicy {

    private final int minPasswordLength;

    LettersNumbersSpecialsPasswordPolicy(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    @Override
    public boolean matches(String password) {
        if (password == null || password.isBlank() || password.length() < minPasswordLength) {
            return false;
        }

        if (password.codePoints().noneMatch(Character::isUpperCase)) {
            return false;
        }

        if (password.codePoints().noneMatch(Character::isLowerCase)) {
            return false;
        }

        if (password.codePoints().noneMatch(Character::isDigit)) {
            return false;
        }

        return password.codePoints().anyMatch(character ->
                !Character.isLetter(character) && !Character.isDigit(character) && !Character.isWhitespace(character));
    }

}
