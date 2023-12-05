//Database code that we are testing
let db = require('../database');

//Server code that we are testing
let server = require ('../index');

//Set up Chai library 
let chai = require('chai');
let should = chai.should();
let assert = chai.assert;
let expect = chai.expect;

//Set up Chai for testing web service
let chaiHttp = require ('chai-http');
chai.use(chaiHttp);

//Import the mysql module and create a connection pool with the user details
const mysql = require('mysql');
const connectionPool = mysql.createPool({
    connectionLimit: 5,
    host: "localhost",
    user: "root",
    password: "",
    database: "cw1",
    debug: false
});


describe('Database', () => {

    //test 1
    describe("Connection to database", () => {
        it("Return all the laptops", (done) => {
          chai
            .request(server)
            .get("/laptops")
            .end((err, res) => {
              res.status.should.equal(200);
              done();
            });
        });
    });

    //test 2
    describe("GET /laptops", () => {
        it("should GET all the laptops", (done) => {
        chai
            .request(server)
            .get("/laptops")
            .end((err, res) => {
                res.status.should.equal(200);
           
                let resObj = JSON.parse(res.text);

                if (resObj.length > 1) {
                    resObj[0].should.have.property('laptop_id');
                    resObj[0].should.have.property('laptop_brand');                      
                    resObj[0].should.have.property('laptop_description');                  
                    resObj[0].should.have.property('price');
                }

                done();
            });
        });
    });


    //test  3
    describe('Get search results from query', () => {
      it('Returns search results from query', (done) => {
          //Mock response object for test
          let response= {};


          response.status = (errorCode) => {
              return {
                  json: (errorMessage) => {
                      console.log("Error code: " + errorCode + "; Error message: " + errorMessage);
                      assert.fail("Error code: " + errorCode + "; Error message: " + errorMessage);
                      done();
                  }
              }
          };

          //Add send function to mock object
          response.send = (result) => {
              //Convert result to JavaScript object
              let resObj = JSON.parse(result);

              //Check that an array of customers is returned
              resObj.should.be.a('array');

              //Check that appropriate properties are returned
              if(resObj.length > 1){
                resObj[0].should.have.property('laptop_id');
                resObj[0].should.have.property('laptop_brand');                      
                resObj[0].should.have.property('laptop_description');                  
                resObj[0].should.have.property('price');
              }

              //End of test
              done();
          };

          let keyword = "dell";

          //Call function that we are testing
          db.getSearchResults(keyword, response);
      });
  });


    //test 4
    describe('Get laptop with specified id', () => {
      it('Returns laptop with specified id', (done) => {
          //Mock response object for test
          let response= {};

          response.status = (errorCode) => {
              return {
                  json: (errorMessage) => {
                      console.log("Error code: " + errorCode + "; Error message: " + errorMessage);
                      assert.fail("Error code: " + errorCode + "; Error message: " + errorMessage);
                      done();
                  }
              }
          };

          //Add send function to mock object
          response.send = (result) => {
              //Convert result to JavaScript object
              let resObj = JSON.parse(result);

              //Check that an array of customers is returned
              resObj.should.be.a('array');

              //Check that appropriate properties are returned
              if(resObj.length > 1){
                resObj[0].should.have.property('laptop_id');
                resObj[0].should.have.property('laptop_brand');   
                resObj[0].should.have.property('laptop_model');                    
                resObj[0].should.have.property('laptop_description'); 
                resObj[0].should.have.property('laptop_imgUrl');    
                resObj[0].should.have.property('comparison_id');              
                resObj[0].should.have.property('price');
                resObj[0].should.have.property('source_url'); 
                resObj[0].should.have.property('logo_url'); 
              }

              //End of test
              done();
          };

          let randomId = 10;

          //Call function that we are testing
          db.loadComparisonSection(randomId, response);
      });
    });


    //test 5
    describe('Get laptops with similar brand and model', () => {
        it('Returns laptops with similar brands and models', (done) => {
            //Mock response object for test
            let response= {};
  
            response.status = (errorCode) => {
                return {
                    json: (errorMessage) => {
                        console.log("Error code: " + errorCode + "; Error message: " + errorMessage);
                        assert.fail("Error code: " + errorCode + "; Error message: " + errorMessage);
                        done();
                    }
                }
            };
  
            //Add send function to mock object
            response.send = (result) => {
                //Convert result to JavaScript object
                let resObj = JSON.parse(result);
  
                //Check that an array of customers is returned
                resObj.should.be.a('array');
  
                //Check that appropriate properties are returned
                if(resObj.length > 1){
                  resObj[0].should.have.property('laptop_id');
                  resObj[0].should.have.property('laptop_brand');   
                  resObj[0].should.have.property('laptop_model');                    
                  resObj[0].should.have.property('laptop_description'); 
                  resObj[0].should.have.property('laptop_imgUrl');    
                  resObj[0].should.have.property('comparison_id');              
                  resObj[0].should.have.property('price');
                  resObj[0].should.have.property('source_url'); 
                  resObj[0].should.have.property('logo_url'); 
                }
  
                //End of test
                done();
            };
  
            let testBrand = "Dell";
            let testModel = "Inspiron";
  
            //Call function that we are testing
            db.loadSimilarLaptops(testBrand, testModel, response);
        });
      });

 
});

