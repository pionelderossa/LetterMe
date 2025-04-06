package com.kk.letterme.utils

class UserInputValidator {

    fun isPasswordValid(password: String): Boolean {

        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()

        /*
        This regex defines a password that must:
            .Be at least 8 characters long.
            .Contain at least one digit.
            .Contain at least one lowercase letter.
            .Contain at least one uppercase letter.
            .Contain at least one special character from the set @#$%^&+=.
            .Contain no whitespace characters.



        */

        return passwordRegex.matches(password)

    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        val emailRegex_2 =  "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,})$".toRegex()
        /*
        This regex defines an email that must:
            .Start with a combination of letters, numbers, and certain special characters.
            .Contain an @ symbol.
            .Have a domain name consisting of letters, numbers, dots, and hyphens.
            .End with a dot followed by a top-level domain (TLD) of at least two letters.
         */
        return emailRegex_2.matches(email)


    }

    fun isUsernameValid (username: String): Boolean {

        val usernameRegex = "^(?=[a-zA-Z0-9_.]{2,32}$)(?!.*[_.]{2})[^_.].*[^_.]$".toRegex() //discord regex for username
        /*
        This regex defines a username that must:

            .Be between 2 and 32 characters long.
            .Consist of letters (uppercase or lowercase), digits, underscores, or dots.
            .Not contain consecutive underscores or dots.
            .Not start or end with an underscore or dot.
         */
        return usernameRegex.matches(username)

    }

}