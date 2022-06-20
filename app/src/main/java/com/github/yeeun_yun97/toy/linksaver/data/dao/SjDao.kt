package com.github.yeeun_yun97.toy.linksaver.data.dao

import androidx.room.*
import com.github.yeeun_yun97.toy.linksaver.data.model.SjDomain
import com.github.yeeun_yun97.toy.linksaver.data.model.SjLinksAndDomainsWithTags

@Dao
interface SjDao {

    @Insert
    suspend fun insertDomain(newDomain: SjDomain): Long

    @Update
    suspend fun updateDomain(domain: SjDomain)

    @Delete
    suspend fun deleteDomain(newDomain: SjDomain)

    @Query("DELETE FROM SjLink WHERE did = :did")
    suspend fun deleteLinksByDid(did: Int)

    @Query("SELECT * FROM SjDomain WHERE did = :did")
    suspend fun getDomainByDid(did: Int): SjDomain

    @Transaction
    @Query("SELECT * FROM SjLink WHERE did = :did")
    fun getLinkAndDomainWithTagsByDid(did: Int): List<SjLinksAndDomainsWithTags>

/*
    // get All Entities from Database
    @Query("SELECT * FROM SjDomain WHERE did NOT IN (1)")
    fun getAllDomainsExceptDefault(): LiveData<List<SjDomain>>

    @Query("SELECT * FROM SjDomain")
    fun getAllDomains(): LiveData<List<SjDomain>>

    @Query("SELECT * FROM SjTag ORDER BY name")
    fun getAllTags(): LiveData<List<SjTag>>

    @Query("SELECT * FROM SjTagGroup ORDER BY gid")
    fun getAllTagGroups(): LiveData<List<SjTagGroup>>


    @Transaction
    @Query("SELECT * FROM SjLink ORDER BY lid DESC")
    fun getAllLinksAndDomainsWithTags()
            : LiveData<List<SjLinksAndDomainsWithTags>>

    @Transaction
    @Query("SELECT * FROM SjLink WHERE lid NOT IN (SELECT ref.lid FROM LinkTagCrossRef as ref WHERE ref.tid NOT IN (SELECT tag.tid FROM SjTag as tag WHERE tag.gid NOT IN (SELECT g.gid FROM SjTagGroup as g WHERE is_private = 1))) ORDER BY lid DESC")
    fun getPublicLinksAndDomainsWithTags()
            : LiveData<List<SjLinksAndDomainsWithTags>>


    @Transaction
    @Query("SELECT * FROM SjTagGroup ORDER BY name")
    fun getAllTagGroupsWithTags()
            : LiveData<List<SjTagGroupWithTags>>

    @Transaction
    @Query("SELECT * FROM SjTagGroup WHERE is_private=0 ORDER BY gid")
    fun getNotPrivateTagGroupsWithTags()
            : LiveData<List<SjTagGroupWithTags>>

*/


}