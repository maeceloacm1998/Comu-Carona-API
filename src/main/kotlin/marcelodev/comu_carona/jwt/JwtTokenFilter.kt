package marcelodev.comu_carona.jwt

import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(private val tokenProvider: JwtTokenProvider)
    : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        try {
            val token = tokenProvider.resolveToken(request as HttpServletRequest)
            if(!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
                val auth = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
            chain.doFilter(request, response)
        } catch (e: TokenExpiredException) {
            // Propagate the TokenExpiredException
            response?.characterEncoding = "UTF-8"
            response?.writer?.write("{\"error\": \"Token is expired\"}")
            (response as HttpServletResponse).status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }
}