const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/login',
    createProxyMiddleware({
      target: 'http://treenovel.tk:8080/',
      changeOrigin: true,
    })
  );
};