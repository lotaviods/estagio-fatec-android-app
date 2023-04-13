package com.github.lotaviods.linkfatec.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.Course
import com.github.lotaviods.linkfatec.model.User

class UserRepositoryLocal(activity: Application) : UserRepository {

    private val sharedPref: SharedPreferences = activity.getSharedPreferences(
        activity.getString(R.string.user_pref_resolver),
        Context.MODE_PRIVATE
    )


    override fun getUser(): User {
        val id = sharedPref.getInt("course_id", -1)

        val name = sharedPref.getString(
            "course_name",
            "null"
        ) ?: "null"

        val course = Course(id, name)

        return User(
            sharedPref.getInt("user_id", -1),
            sharedPref.getString("user_name", "null") ?: "null",
            course,
            sharedPref.getString("user_ra", "null") ?: "null",
            profilePicture = sharedPref.getString("profile_picture", null)
        )
    }

    override fun saveUser(user: User) {
        val editor = sharedPref.edit()

        editor.putInt("user_id", user.id)
        editor.putString("user_name", user.name)
        editor.putInt("course_id", user.course.id)
        editor.putString("course_name", user.course.name)
        editor.putString("user_ra", user.ra)
        editor.putString("profile_picture", user.profilePicture)

        editor.apply()
    }

    override fun deleteUser(user: User) {
        val editor = sharedPref.edit()

        editor.remove("user_id")
        editor.remove("user_name")
        editor.remove("course_name")
        editor.remove("user_ra")

        editor.apply()
    }
}