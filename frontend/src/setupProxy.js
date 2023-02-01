const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/login',
    createProxyMiddleware({
      target: 'https://i8d206.p.ssafy.io/',
      changeOrigin: true,
    })
  );
};