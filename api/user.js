/* implementing the User functionalities */
var express = require('express');

/**
---------->>>> DEPRECATED

POST /login
APP => SERVER
{
  “uuid”: string,
  “username”: string,
  “password”: string
}
APP <= SERVER
{
  “result”: boolean
}
*/
exports.loginUser = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({result:true});
};


/**
POST /register
APP => SERVER
{
  “username”: string,
  “authorizationToken”: string,
  “secretToken”: string
}
SERVER => APP
{
  “result”: boolean,
  “uuid”: string
}
*/
exports.registerUser = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({result:true, uuid: "theUUID"});
    //res.send({result:false});
};