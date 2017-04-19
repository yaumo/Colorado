package com.colorado.denver.services.security;

// @Component
// public class CORSFilter implements Filter {
//
// private final List<String> allowedOrigins = Arrays.asList("http://localhost:8080", "http://localhost:8081");
//
// private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);
//
// @Override
// public void init(FilterConfig filterConfig) throws ServletException {
// }
//
// @Override
// public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
// // Lets make sure that we are working with HTTP (that is, against HttpServletRequest and HttpServletResponse objects)
// if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
// HttpServletRequest request = (HttpServletRequest) req;
// HttpServletResponse response = (HttpServletResponse) res;
//
// String origin = request.getHeader("Origin");
// response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
// response.setHeader("Vary", "Origin");
// response.setHeader("Access-Control-Allow-Credentials", "true");
// response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, OPTIONS");
// response.setHeader("Access-Control-Max-Age", "3600");
// response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, *");
// }
// chain.doFilter(req, res);
// }
//
// @Override
// public void destroy() {
// }
// }