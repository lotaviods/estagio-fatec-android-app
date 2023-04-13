package com.github.lotaviods.linkfatec.data.repository

import com.github.lotaviods.linkfatec.resource.AppResource

interface ProfileRepository {
    suspend fun sendProfileResume(studentId: Int, pdfBytes: ByteArray?): AppResource<Any>
    suspend fun sendProfilePicture(studentId: Int, bytes: ByteArray?): AppResource<Any>
}