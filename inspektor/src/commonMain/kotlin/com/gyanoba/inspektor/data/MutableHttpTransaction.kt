package com.gyanoba.inspektor.data

import kotlinx.datetime.Instant

internal data class MutableHttpTransaction(
    var id: Long = 0L,
    var method: String? = null,
    var requestDate: Instant? = null,
    var responseDate: Instant? = null,
    var tookMs: Long? = null,
    var protocol: String? = null,
    var url: String? = null,
    var host: String? = null,
    var path: String? = null,
    var scheme: String? = null,
    var responseTlsVersion: String? = null,
    var responseCipherSuite: String? = null,
    var requestPayloadSize: Long? = null,
    var requestContentType: String? = null,
    var requestHeaders: Set<Map.Entry<String, List<String>>>? = null,
    var requestHeadersSize: Long? = null,
    var requestBody: String? = null,
    var isRequestBodyEncoded: Boolean? = null,
    var responseCode: Long? = null,
    var responseMessage: String? = null,
    var error: String? = null,
    var responsePayloadSize: Long? = null,
    var responseContentType: String? = null,
    var responseHeaders: Set<Map.Entry<String, List<String>>>? = null,
    var responseHeadersSize: Long? = null,
    var responseBody: String? = null,
    var isResponseBodyEncoded: Boolean? = null,
)

internal fun MutableHttpTransaction.toImmutable() = HttpTransaction(
    id = id,
    method = method,
    requestDate = requestDate,
    responseDate = responseDate,
    tookMs = tookMs,
    protocol = protocol,
    url = url,
    host = host,
    path = path,
    scheme = scheme,
    responseTlsVersion = responseTlsVersion,
    responseCipherSuite = responseCipherSuite,
    requestPayloadSize = requestPayloadSize,
    requestContentType = requestContentType,
    requestHeaders = requestHeaders,
    requestHeadersSize = requestHeadersSize,
    requestBody = requestBody,
    isRequestBodyEncoded = isRequestBodyEncoded,
    responseCode = responseCode,
    responseMessage = responseMessage,
    error = error,
    responsePayloadSize = responsePayloadSize,
    responseContentType = responseContentType,
    responseHeaders = responseHeaders,
    responseHeadersSize = responseHeadersSize,
    responseBody = responseBody,
    isResponseBodyEncoded = isResponseBodyEncoded,
)

//internal fun buildHttpTransaction(builder: MutableHttpTransaction.() -> Unit): HttpTransaction =
//    MutableHttpTransaction().apply(builder).toImmutable()