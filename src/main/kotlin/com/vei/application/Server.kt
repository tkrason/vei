package com.vei.application

import com.vei.application.config.Config
import com.vei.application.plugins.configureHTTP
import com.vei.application.plugins.configureMonitoring
import com.vei.application.plugins.configureSecurity
import com.vei.application.plugins.configureSerialization
import com.vei.application.plugins.registerSwagger
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.annotation.Singleton

@Singleton
class Server(
    private val router: Router,
    private val config: Config,
) {

    @Suppress("ExtractKtorModule")
    fun startServer() = embeddedServer(Netty, port = 8080) {
        configure(config)
        router.routeAll(application = this)
    }.start(wait = true)
}

private fun Application.configure(config: Config) {
    configureSecurity(config)
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    registerSwagger()
}
