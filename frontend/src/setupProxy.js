const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'https://i8d206.p.ssafy.io',
      changeOrigin: true,
    })
  );
  app.use(
    '/ws',
    createProxyMiddleware({ target: 'https://i8d206.p.ssafy.io', ws: true})
  );
};