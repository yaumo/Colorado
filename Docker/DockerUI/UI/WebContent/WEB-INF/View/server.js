import https from 'https'
import fs from 'fs'
import path from 'path'
import express from 'express'
import session from 'express-session'
import bodyParser from 'body-parser'
import compression from 'compression'
import restInterfaceServer from './restInterfaceServer.js'

var httpProxy = require('http-proxy');
//var apiProxy = httpProxy.createProxyServer();
var apiProxy = httpProxy.createProxyServer({
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

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

let sess;

/*const users = [
    {"username": "s123456", "password": "studentPassword", "isStudent": true, "isAdmin": false},
    {"username": "student", "password": "student", "isStudent": true, "isAdmin": false},
    {"username": "dozent", "password": "dozent", "isStudent": false, "isAdmin": false},
    {"username": "HenningPagnia1", "password": "dozent", "isStudent": false, "isAdmin": false},
    {"username": "admin", "password": "admin", "isStudent": false, "isAdmin": true},
    {"username": "Mustermann", "password": "Achhhff-35667", "isStudent": false, "isAdmin": false}
  ];
*/
// serve our static stuff like index.css
app.use(express.static(path.join(__dirname, 'public')));
/*
const responseStudentCallback = (req, res) => {
  sess = req.session;

  if (!sess.username) {
      res.redirect('/');
  }
  else {
      if (sess.isAdmin) {
          res.sendStatus(403);
      }
      else if (sess.isStudent) {
          res.sendFile(path.join(__dirname, 'public', 'index.html'));
      }
      else {
          res.sendStatus(403);
      }
  }
}

const responseDozentCallback = (req, res) => {
  sess = req.session;

  if (!sess.username) {
      res.redirect('/');
  }
  else {
      if (sess.isAdmin) {
          res.sendStatus(403);
      }
      else if (sess.isStudent) {
          res.sendStatus(403);
      }
      else {
          res.sendFile(path.join(__dirname, 'public', 'index.html'));
      }
  }
}

const responseAdminCallback = (req, res) => {
  sess = req.session;

  if (!sess.username) {
      res.redirect('/');
  }
  else {
      if (sess.isAdmin) {
          res.sendFile(path.join(__dirname, 'public', 'index.html'));
      }
      else if (sess.isStudent) {
          res.sendStatus(403);
      }
      else {
          res.sendStatus(403);
      }
  }
}

app.get('/', (req, res) => {
    sess = req.session;

    if (!sess.username) {
        res.redirect('/');
    }
    else {
        if (sess.isAdmin) {
            res.redirect('/Admin');
        }
        else if (sess.isStudent) {
            res.redirect('/Student');
        }
        else {
            res.redirect('/Dozent');
        }
    }
});

//login
app.post('/login', (req, res) => {
    sess = req.session;

    let user = req.body;

    for (let i = 0; i < users.length; i++) {
        console.log('Checking user: ' + users[i].username);
        if (users[i].username === user.username) {
            console.log('Found a user');
            sess.username = users[i].username;
            sess.isStudent = users[i].isStudent;
            sess.isAdmin = users[i].isAdmin;

            sess.cookie.expires = new Date(Date.now() + hour);
            sess.cookie.maxAge = hour;
            break;
        }
    }

    if (sess.isAdmin) {
        res.sendStatus(901);
        //res.redirect('/Admin');
    }
    else if (sess.isStudent) {
        res.sendStatus(902);
        //res.redirect('/Student');
    }
    else {
        res.sendStatus(903);
        //res.redirect('/Dozent');
    }

    console.log('Custom post working');
});

//student
app.get('/Student', responseStudentCallback);

app.get('/SolutionSubmitted', responseStudentCallback);

app.get('/TaskEditing', responseStudentCallback);

app.get('/Studentprofil', responseStudentCallback);

//lecturer
app.get('/Dozent', responseDozentCallback);

app.get('/Kursverwaltung', responseDozentCallback);

app.get('/Dozentprofil', responseDozentCallback);

app.get('/DozentEditTask', responseDozentCallback);

app.get('/CreateStudent', responseDozentCallback);

app.get('/Aufgabenverwaltung', responseDozentCallback);

//admin
app.get('/Admin', responseAdminCallback);

app.get('/EditProfile', responseAdminCallback);

app.get('/CreateProfile', responseAdminCallback);

app.get('/AdminProfil', responseAdminCallback);
*/
//logout
app.get('/logout', (req, res) => {
    req.session.destroy(function (err) {
        if (err) {
            console.log(err);
        }
    });
    res.redirect('/');
});

// using restInterfaceServer for creating REST Endpoints
//app.use('/api',restInterfaceServer)

app.all("/api/**", function(req, res) {
    req.url = '/' + req.url.split('/').slice(2).join('/');
    console.log('redirecting to Server1');
    apiProxy.web(req, res, {target: serverOne});
});

const httpsServer = https.createServer(options,app); 

const PORT = process.env.PORT || 8081
httpsServer.listen(PORT, () => {
    console.log('SEC Server running on port ' + PORT);
});
