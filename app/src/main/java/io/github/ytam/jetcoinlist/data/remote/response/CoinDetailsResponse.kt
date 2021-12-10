package io.github.ytam.jetcoinlist.data.remote.response

import io.github.ytam.jetcoinlist.domain.model.CoinDetails

@Suppress("ConstructorParameterNaming")
data class CoinDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val first_data_at: String,
    val last_data_at: String,
    val symbol: String,
    val type: String
)

fun CoinDetailsResponse.toCoinDetails(): CoinDetails {
    return CoinDetails(
        id = id,
        name = name,
        description = description,
        firstDataDate = first_data_at,
        lastDataDate = last_data_at,
        symbol = symbol,
        type = type
    )
}
