import https from 'https'
import fs from 'fs'
import path from 'path'
import express from 'express'
import session from 'express-session'
import compression from 'compression'
import restInterfaceServer from './restInterfaceServer.js'

var httpProxy = require('http-proxy');
//var apiProxy = httpProxy.createProxyServer();
var apiProxy = httpProxy.createProxyServer(
{
  ssl: {
    key: fs.readFileSync('ssl/backend/backend-key.pem', 'utf8'),
    cert: fs.readFileSync('ssl/backend/backend-crt.pem', 'utf8')
  },
  //target: 'https://localhost:9010',
  secure: false // Depends on your needs, could be false.
}); 
var serverOne = 'https://backend:8443';

var FileStore = require('session-file-store')(session);

const options = {
    key: fs.readFileSync('ssl/https/colorado.key'),
    cert: fs.readFileSync('ssl/https/colorado.crt')
};

const app = express();

app.use(compression());

const hour = 3600000;


const session_options = {
    path: "./tmp/sessions",
    useAsync: true,
    reapInterval: 5000,
    maxAge: hour
};

app.use(session({store: new FileStore(session_options), secret: 'b386464b-351e-4732-a439-e00c1324eb0c', saveUninitialized: false, resave: true}));
let sess;

// serve our static stuff like index.css
app.use(express.static(path.join(__dirname, 'public')));

//logout
app.get('/logout', (req, res) => {
    req.session.destroy(function (err) {
        if (err) {
            console.log(err);
        }
    });
    res.redirect('/');
});

app.all("/api/**", function(req, res) {
    req.url = '/' + req.url.split('/').slice(2).join('/');
    console.log('redirecting to Server1');
    apiProxy.web(req, res, {target: serverOne});
});

const httpsServer = https.createServer(options,app); 

const PORT = process.env.PORT || 8081
httpsServer.listen(PORT, () => {
    console.log('Colorado Server running on port ' + PORT);
});
