package com.github.lotaviods.linkfatec.data.remote.repository

import com.github.lotaviods.linkfatec.data.remote.client.JobOfferWebClient
import com.github.lotaviods.linkfatec.data.remote.model.JobOffer
import com.github.lotaviods.linkfatec.data.repository.interfaces.JobOfferRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.resource.AppResource
import org.koin.core.component.KoinComponent

class JobOfferRepositoryImpl(
    private val webClient: JobOfferWebClient
) : JobOfferRepository, KoinComponent {

    override suspend fun getAllAvailableJobOffers(courseId: Int): AppResource<List<JobOffer>> {
        try {
            val response = webClient.getAllAvailableJobOffers(courseId)

            if (response.isSuccessful) {
                return AppResource(response.data, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.InternetConnection
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return AppResource(
                null, true,
                ErrorState.Unexpected
            )
        }
    }

    override suspend fun getAppliedJobOffers(
        studentId: Int
    ): AppResource<List<JobOffer>> {
        try {
            val response = webClient.getAppliedJobOffers(studentId)

            if (response.isSuccessful) {
                return AppResource(response.data, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.InternetConnection
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return AppResource(
                null, true,
                ErrorState.Unexpected
            )
        }
    }

    override suspend fun likeJob(jobId: Int, studentId: Int, like: Boolean): AppResource<Any> {
        try {
            val response = webClient.likeJob(jobId, studentId, like)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        }
    }

    override suspend fun subscribeJob(jobId: Int, studentId: Int): AppResource<Any> {
        try {
            val response = webClient.subscribeJob(jobId, studentId)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        } catch (e: Exception) {
            e.printStackTrace()

            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        }
    }

    override suspend fun unSubscribeJob(jobId: Int, studentId: Int): AppResource<Any> {
        try {
            val response = webClient.unSubscribeJob(jobId, studentId)

            if (response.isSuccessful) {
                return AppResource(null, false)
            }
            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        } catch (e: Exception) {
            e.printStackTrace()

            return AppResource(
                null,
                true,
                ErrorState.Unexpected
            )
        }
    }
}