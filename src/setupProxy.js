const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',  // Update the port if necessary
            changeOrigin: true,
          
        })
    );
};



// No longer need http-proxy-middleware
// const request = require('request');
// module.exports = function(app) {
//     app.use(
//         '/api',
//         (req, res) => {
//             // Replace 'https://clean-pick.vercel.app' with your actual Vercel deployment URL
//             const targetUrl = 'https://clean-pick.vercel.app'; 
//             req.pipe(request({ url: targetUrl + req.url })).pipe(res);
//         }
//     );
// };
