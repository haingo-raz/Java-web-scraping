const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
var url = require("url");

const db = require('./database');

//Status codes defined in external file
require('./http_status.js');

const app = express();
app.use(bodyParser.json());

app.use(express.urlencoded({ extended: true }));

app.use(express.static(path.join(__dirname, 'public'))); // static files 


//Set up the paths - Get requests
app.get('/', loadHome);
app.get('/laptops/*', loadLaptops);
app.get('/laptops', loadLaptops);

//Render the index page 
function loadHome(request, response) {
	response.sendFile(path.join(__dirname + '/index.html'));
}

function loadLaptops(request, response) {

    //Parse the URL
    var urlObj = url.parse(request.url, true);
    var queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    var numItems = queries['num_items'];
    var offset = queries['offset'];
    
    var pathArray = urlObj.pathname.split("/"); //Split the path
    var pathEnd = pathArray[pathArray.length - 1]; //Get the last part of the path

    if(pathEnd === 'laptops'){ //return all laptops if path ends with laptops
        db.getTotalLaptopsCount(response, numItems, offset);//This function calls the getAllLaptops function in its callback
        return;
    }

    //If path ends with laptops/, we return all laptops
    if (pathEnd === '' && pathArray[pathArray.length - 2] === 'laptops'){
        db.getTotalLaptopsCount(response, numItems, offset);//This function calls the getAllLaptops function in its callback
        return;
    }

    //If the last part of the path is a valid user id, return data about that user
    var regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
    if(regEx.test(pathEnd)){
        db.getLaptop(response, pathEnd);
        return;
    }

    //error message
    response.status(HTTP_STATUS.NOT_FOUND);
    response.send("{error: 'Path not recognized', url: " + request.url + "}");
}

//Post requests
app.post('/search', searchProduct); //Handle searching  //post 
app.post('/displayDetails', displayDetails);
app.post('/displaySimilar', displaySimilar);

//Searching a product
async function searchProduct(request, response) { 
    let userSearch = request.body; //keyword received from client
    db.getSearchResults(userSearch, response); //call the function that will get all matching results with keywords
}

//Display laptop with specific id 
async function displayDetails(request, response){

    let laptopId = request.body.laptopId; //receive laptopId from client 
    console.log(laptopId);

    db.loadComparisonSection(laptopId, response);
}

//Allows to display laptops with similar brands and models in the comparison section 
async function displaySimilar(request, response){

    let laptopBrand = request.body.laptopBrand; //received from client 
    let laptopModel = request.body.laptopModel; //received from client 

    db.loadSimilarLaptops(laptopBrand, laptopModel, response);
}

//Export server for testing
module.exports = app;

//listen on the selected port
app.listen(5000);