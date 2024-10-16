import com.gyanoba.inspektor.ClientCallLogger
import com.gyanoba.inspektor.LogLevel
import com.gyanoba.inspektor.data.FixedRequestAction
import com.gyanoba.inspektor.data.FixedResponseAction
import com.gyanoba.inspektor.data.HostMatcher
import com.gyanoba.inspektor.data.HttpMethod
import com.gyanoba.inspektor.data.HttpRequest
import com.gyanoba.inspektor.data.Override
import com.gyanoba.inspektor.data.PathMatcher
import io.github.xxfast.kstore.extensions.plus
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import utils.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class OverridingTest : TestBase() {
    @Test
    fun requestOverridesWork() = runTest {
        db.httpTransactionQueries.deleteAll()
        store.reset()
        store.plus(
            Override(
                type = HttpRequest(HttpMethod.Post),
                matchers = listOf(HostMatcher("localhost"), PathMatcher("/text")),
                action = FixedRequestAction(
                    headers = mutableMapOf("Custom" to listOf("Overridden Request-Header")),
                    body = "Overridden-Request",
                ),
            ),
            Override(
                type = HttpRequest(HttpMethod.Post),
                matchers = listOf(HostMatcher("localhost"), PathMatcher("/text")),
                action = FixedResponseAction(
                    headers = mutableMapOf("Custom" to listOf("Overridden Response-Header")),
                    body = "Overridden-Response",
                ),
            ),
        )
        val client = createMockClient(logLevel = LogLevel.BODY) {
            respond(
                "Response",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    "Content-Type" to listOf("text/plain"),
                    "Custom" to listOf("Response-Header")
                )
            )
        }

        val response = client.post("http://localhost/text") {
            headers.append("Content-Type", "text/plain")
            headers.append("Custom", "Request-Header")
            setBody("Request")
        }
        response.call.attributes[ClientCallLogger].joinResponseLogged()

        val transaction = db.httpTransactionQueries.getAll().executeAsOne()

        mapOf(
            "request headers" to ("Overridden Request-Header" to transaction.requestHeaders?.firstOrNull { it.key == "Custom" }?.value?.first()),
            "request" to ("Overridden-Request" to transaction.requestBody),
            "response headers" to ("Overridden Response-Header" to transaction.responseHeaders?.firstOrNull { it.key == "Custom" }?.value?.first()),
            "response" to ("Overridden-Response" to transaction.responseBody),
        ).forEach {
            assertEquals(it.value.first, it.value.second, "${it.key} are not equal")
        }
    }

}