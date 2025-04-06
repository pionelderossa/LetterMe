package com.kk.letterme.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.kk.letterme.data.dao.UserDao
import com.kk.letterme.data.dto.UserInputForm
import com.kk.letterme.data.model.User
import kotlinx.coroutines.tasks.await
import kotlin.Result


class UserInputRepository() {

    private val db = FirebaseFirestore.getInstance()

    fun mapUserInputFormToUser(userInputForm: UserInputForm): User {
        return User(
            username = userInputForm.username,
            email = userInputForm.email,
            password = userInputForm.password,
        )
    }


    suspend fun getUserByUserName(username: String): Result<User?> {


        try {
            val querySnapshot =
                db.collection("users").whereEqualTo("username", username).limit(1).get().await()
            val user = if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject(User::class.java)
            } else {
                null
            }
            return Result.success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)

        }
    }

    suspend fun isUsernameAvailable(username: String): Result<Boolean> {

        val result = getUserByUserName(username)

        return if (result.isSuccess) {
            // If result.getOrNull() returns null, no user was found
            // If it returns a User, then a user was found
            Result.success(result.getOrNull() == null)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }


    suspend fun getUserByCredentials(email: String, password: String): Result<List<User>> {

        try {
            val querySnapshot =
                db.collection("users").whereEqualTo("email", email)  // Filtering by email
                    .whereEqualTo("password", password)  // Filtering by password
                    .get().await()

            val users: List<User> =
                querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }

            // Return success if the list is not empty; otherwise, return failure
            if (users.isEmpty()) {
                return Result.failure(Exception("No users found with email $email"))
            }

            return Result.success(users) // Return found users

            return Result.success(users)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }


    suspend fun setUserByCredentials(userinput: UserInputForm): Result<String> {

        try {

            val user: User = mapUserInputFormToUser(userinput)
            db.collection("users").add(user).await()
            return Result.success("User added successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }


    }


}

