package com.github.yeeun_yun97.toy.linksaver.data.dao

import androidx.room.*
import com.github.yeeun_yun97.toy.linksaver.data.model.*

@Dao
interface SjDao {
    @Query("SELECT * FROM SjDomain")
    fun getDomains(): List<SjDomain>

    @Query("SELECT * FROM SjLink")
    fun getLinks(): List<SjLink>

    @Query("SELECT * FROM SjTag")
    fun getTags(): List<SjTag>

    @Transaction
    @Query("SELECT * FROM SjLink")
    fun getLinksWithTags(): List<SjLinkWithTags>

    @Transaction
    @Query("SELECT * FROM SjLink")
    fun getLinksAndDomain(): List<SjLinkAndDomain>

//    @Transaction
//    @Query("SELECT * FROM SjLink")
//    fun getLinksWithTagsAndDomain():List<SjLinkWithTagsAndDomain>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDomain(newDomain: SjDomain)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLink(newLink:SjLink)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(newTag:SjTag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLinkTagCrossRef(newCrossRef: LinkTagCrossRef)

    @Delete
    fun deleteDomain(newDomain: SjDomain)

    @Delete
    fun deleteLink(newLink:SjLink)

    @Delete
    fun deleteTag(newTag:SjTag)

}