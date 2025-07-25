package com.elektrocodx.auravindex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elektrocodx.auravindex.model.local.GenderEntity
import com.elektrocodx.auravindex.model.local.RoleEntity
import com.elektrocodx.auravindex.model.local.UserEntity
import com.elektrocodx.auravindex.model.local.relations.UserWithRelations


@Dao
interface UserDao {

    @Transaction
    suspend fun insertUserWithRelations(
        user: UserEntity,
        gender: GenderEntity,
        role: RoleEntity
    ) {
        insertUser(user)
        insertGender(gender)
        insertRole(role)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGender(gender: GenderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: RoleEntity)

    @Transaction
    @Query("SELECT * FROM users WHERE _id = :userId")
    suspend fun getUserWithRelations(userId: String): UserWithRelations

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getAllUsersWithRelations(): List<UserWithRelations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE _id = :userId")
    suspend fun getUserById(userId: String): UserEntity

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}
