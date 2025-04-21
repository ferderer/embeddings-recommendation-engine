package de.ferderer.ere.support;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestMonitor implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestMonitor.class);
    private static final DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
    private static final String[] ignoredPatterns = {"^/_/.*$", "^/favicon.ico$"};

    static {
        df.applyPattern("0.00");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest && !isIgnored(httpRequest)) {
            long start = System.nanoTime();

            chain.doFilter(request, response);

            log.info("Time: {} ms; Memory: {} MiB; {} {} ",
                df.format((System.nanoTime() - start) / 1_000_000.0),
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024*1024),
                httpRequest.getMethod(),
                httpRequest.getRequestURI());
        }
        else {
            chain.doFilter(request, response);
        }
    }

    private boolean isIgnored(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for(String pattern : ignoredPatterns)
            if (uri.matches(pattern))
                return true;
        return false;
    }
}
