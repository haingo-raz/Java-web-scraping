const mysql = require('mysql');

const connectionPool = mysql.createPool({
    connectionLimit: 3,
    host: "localhost",
    user: "root",
    password: "",
    database: "cw1",
    debug: false
});

//Status codes defined in external file
require('./http_status.js');

//Search entered keyword through database
async function getSearchResults(userSearch, response){

    //sql query
	let sql = "SELECT * FROM laptops INNER JOIN comparison ON laptops.laptop_id = comparison.laptop_id WHERE (laptops.laptop_brand = '" + userSearch.keyword + "' OR laptops.laptop_model LIKE '%" + userSearch.keyword + "%' OR laptops.laptop_description LIKE '%" + userSearch.keyword + "%' OR comparison.price LIKE '%" + userSearch.keyword + "%' );";

    //Wrap the execution of the query in a promise
	let selectPromise = new Promise((resolve, reject) => {
		connectionPool.query(sql, (err, result) => {
			if (err) { //Check for errors
			   reject("Error executing query: " + JSON.stringify(err));
			} else { //Resolve promise with results
			   resolve(result);
			}
		});
	});

	try {
	   //Execute promise and output result
	   let allProducts = await selectPromise;
	   response.send(JSON.stringify(allProducts)); //Server response to the client side
       console.log(JSON.stringify(allProducts))

	} catch (err) {
	   throw err;
	}
}

//Get the laptop to be compared
async function loadComparisonSection(laptopId, response){
    
	//This query gets the data of the specific laptop clicked
    let sql="SELECT * FROM laptops INNER JOIN comparison ON laptops.laptop_id = comparison.laptop_id WHERE laptops.laptop_id = " + laptopId;

	console.log(sql);

	//Wrap the execution of the query in a promise
	let selectPromise = new Promise((resolve, reject) => {
		connectionPool.query(sql, (err, result) => {
			if (err) { //Check for errors
			   reject("Error executing query: " + JSON.stringify(err));
			} else { //Resolve promise with results
			   resolve(result);
			}
		});
	});

	try {
	   //Execute promise and output result
	   let allComparisons = await selectPromise;
	   response.send(JSON.stringify(allComparisons)); //Server response to the client side
       console.log(JSON.stringify(allComparisons))

	} catch (err) {
		//Send response with status 400 for errors
		response.status(400).json(err);
	   throw err;
	}
}

//Get laptops with similar brands and models 
async function loadSimilarLaptops(laptopBrand, laptopModel, response){

    //sql query
    let sql="SELECT * FROM laptops INNER JOIN comparison ON laptops.laptop_id = comparison.laptop_id WHERE laptops.laptop_brand = '" + laptopBrand + "' AND laptops.laptop_model = '" + laptopModel + "'";

	console.log(sql);

	//Wrap the execution of the query in a promise
	let selectPromise = new Promise((resolve, reject) => {
		connectionPool.query(sql, (err, result) => {
			if (err) { //Check for errors
			   reject("Error executing query: " + JSON.stringify(err));
			} else { //Resolve promise with results
			   resolve(result);
			}
		});
	});

	try {
	   //Execute promise and output result
	   let allComparisons = await selectPromise;
	   response.send(JSON.stringify(allComparisons)); //Server response to the client side
       console.log(JSON.stringify(allComparisons))

	} catch (err) {
		//Send response with status 400 for errors
		response.status(400).json(err);
	   throw err;
	}
}


/** Retrieve total number of laptops */
function getTotalLaptopsCount(response, numItems, offset){
	var sql = "SELECT COUNT(*) FROM laptops";

	//Execute the query and call the anonymous callback function.
	connectionPool.query(sql, function (err, result) {

		//Check for errors
		if (err){
			//Not an ideal error code, but we don't know what has gone wrong.
			response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
			response.json({'error': true, 'message': + err});
			return;
		}

		//Get the total number of items from the result
		var totNumItems = result[0]['COUNT(*)'];

		//Call the function that retrieves all laptops 
		getAllLaptops(response, totNumItems, numItems, offset);
	});
}


function getAllLaptops(response, totNumItems, numItems, offset){

    //Select the laptops data using JOIN to convert foreign keys into useful data.
	var sql = "SELECT laptops.laptop_id, laptops.laptop_brand, laptops.laptop_description, comparison.price " +
	"FROM laptops INNER JOIN comparison ON laptops.laptop_id = comparison.laptop_id ";


    //Limit the number of results returned, if this has been specified in the query string
    if(numItems !== undefined && offset !== undefined ){
        sql += "ORDER BY laptops.laptop_id LIMIT " + numItems + " OFFSET " + offset;
    }

    //Execute the query
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Create JavaScript object that combines total number of items with data
        var returnObj = {totNumItems: totNumItems};
        returnObj.data = result; //Array of data from database

        //Return results in JSON format
        response.json(returnObj);
    });
}
  
    
/** Returns a laptop with a specified ID */
function getLaptop(response, id){
	//Build SQL query to select laptop with specified id.
	var sql = "SELECT * FROM laptops " +
		"WHERE laptops.laptop_id=" + id;

	//Execute the query
	connectionPool.query(sql, function (err, result) {

		//Check for errors
		if (err){
			//Not an ideal error code, but we don't know what has gone wrong.
			response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
			response.json({'error': true, 'message': + err});
			return;
		}

		//Output results in JSON format
		response.json(result);
	});
}

module.exports.getSearchResults = getSearchResults;
module.exports.loadComparisonSection = loadComparisonSection;
module.exports.loadSimilarLaptops = loadSimilarLaptops;
module.exports.getTotalLaptopsCount =getTotalLaptopsCount;
module.exports.getLaptop = getLaptop; 