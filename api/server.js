var express = require('express'),
    bodyParser = require('body-parser'),
    user = require('./user.js'),
    feed = require('./feed.js'),
    filter = require ('./filter.js');

var app = express();

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

/* in User.js */
app.post('/logmein', user.loginUser);  // ----> this one is DEPRECATED!!!
app.post('/register', user.registerUser);

/* in feed.js */
app.get('/feed/:uuid/:timestamp', feed.getFeed);

/* in filter.js */
app.get('/filter/:uuid', filter.getAllFilters);
app.get('/filter/:filterid/:uuid', filter.getFilterById);
app.post('/filter', filter.addFilter);
app.delete('/filter/:filterid/:uuid', filter.deleteFilter);
app.put('/filter/:filterid/:uuid', filter.modifyFilter);

var server = app.listen(3000, function () {

    var host = server.address().address
    var port = server.address().port

    console.log('Listening at http://%s:%s', host, port)

});
