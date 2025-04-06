package com.kk.letterme.data.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.kk.letterme.data.model.User
import kotlinx.coroutines.tasks.await

class UserDao {

    private val db = FirebaseFirestore.getInstance()


    suspend fun getUserByUserName(username: String): Result<List<User>> {



        try {
            val querySnapshot =
                db.collection("users").whereEqualTo("username", username).get().await()
            val users: List<User> =
                querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }
            return Result.success(users)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)

        }
    }

    suspend fun isUsernameAvailable(username: String): Result<Boolean> {

        val result = getUserByUserName(username)

        return if (result.isSuccess) {
            val users = result.getOrNull()

            if (users.isNullOrEmpty()) {
                Result.success(true)
            } else {
                Result.success(false)
            }

        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }


    suspend fun getUserByCredentials (email: String, password: String): Result<List<User>>{

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
        }catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }


        suspend fun setUserByCredentials (user: User): Result<String>{

            try {


                db.collection("users").add(user).await()
                return Result.success("User added successfully")
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.failure(e)
            }


        }




    }
}