/* implementing the User functionalities */
var express = require('express');

/**
GET /filter/:uuid
SERVER => APP
{
  “filters”: 
  [
    “filterid”: int,
    “filtername”: string,
    “filterdescription”: string
  ],
  [
    “filterid”: int,
    “filtername”: string,
    “filterdescription”: string
  ]
}
*/
exports.getAllFilters = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({filters: [
    {filterid: "1", filtername: "SLBenfica", filterdescription: "Filtro que contém informações sobre o Sport Lisboa e Benfica"}, 
    {filterid: "2", filtername: "MotoGP", filterdescription: "Informações gerais sobre o campeonato mundial de MotoGP"}
    ]});
};

/**
GET /filter/:filterid/:uuid
SERVER => APP
{
  “filterid”: int,
  “filtername”: string,
  “filterdescription”: string
  “filterdetails”: string,
  “filterstartdate”: string,
  “filterenddate”: string
}
*/
exports.getFilterById = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({
      filterid:"1", 
      filtername:"Ferrari F1",
      filterdescription: "List all the relevant information about Scuderia Farrari on F1",
      filterdetails: "ferrari, f1, gp, vettel, raikonnen",
      filterstartdate: "1-1-2015",
      filterenddate: "31-12-2015"
      });

};

/**
POST /filter
APP => SERVER
{
  “uuid”: string,
  “filtername”: string,
  “filterdescription”: string
  “filterdetails”: string,
  “filterstartdate”: string,
  “filterenddate”: string
}
SERVER => APP
{
  “result”: boolean,
  “filterid”: int
}
*/
exports.addFilter = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({result:true, filterid:123});

};

/**
DELETE /filter/:filterid/:uuid
SERVER => APP
{
  “result”: boolean
}
*/
exports.deleteFilter = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({result:true});

};

/**
PUT /filter/:filterid/:uuid
APP => SERVER
{
  “filterdetails”: string
}
SERVER => APP
{
  “result”: boolean
}
*/
exports.modifyFilter = function(req, res) {
    console.log("Received a request ---->");
    console.log(req.body);

    res.send({result:true});

};