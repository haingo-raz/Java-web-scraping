window.onload= loadSearchResults(); 

//Access different sections on the page
const searchContainer = document.getElementById("home");
const resultsContainer = document.getElementById("resultsContainer");

//Function called after a user search for a keyword on the search bar
function loadSearchResults(){

    let keyword = document.getElementById("searchInput").value; //get input from user

    if(keyword == ""){
        //if empty do nothing
    }
    else{
        try{
            //Set up XMLHttpRequest
            let xhttp = new XMLHttpRequest();

            //object populated with user input 
            let result = {
                keyword: keyword
            };

            //Send data to the server
            xhttp.open("POST", "/search", true);  //POST
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.send(JSON.stringify(result)); //send object data to the server in JSON format

            //Upon receiving server's response
            xhttp.onload = () => {

                if (xhttp.readyState == 4 && xhttp.status == 200) {

                    //Store data from server into global variable
                    postsJson = xhttp.responseText;
                    
                    //Call display function with data received from server
                    displaySearchedProducts(postsJson, keyword);

                } else {              
                    //error
                }
            };
        } catch(error) {
            console.log("error: " + error);
        }
    }
} 


//Display the laptops that correspond to the user search
function displaySearchedProducts(jsonPost, keyword) {

    //Convert JSON response from server into array
    let postArray = JSON.parse(jsonPost);

    htmlStr = "";

    htmlStr += "<hr class='separator'>";

    if(postArray.length > 0) {

        htmlStr += '<h1>Search results for "' + keyword + '"</h1>';
        htmlStr += '<div class="searchResults" id="searchResults">';

        for (let i = 0; i < postArray.length; i++) {
            htmlStr += '<div class="searchItem">'; 
            htmlStr += '<div class="imgContainer">';
            htmlStr += '<img src=' + postArray[i].laptop_imgUrl + ' alt="">';
            htmlStr += '<div class="info">';
            htmlStr += '<button onclick="loadComparedProducts(' + postArray[i].laptop_id + ');loadSimilarProducts(\'' + postArray[i].laptop_brand + '\', \'' + postArray[i].laptop_model + '\');">View item</button>';
            htmlStr += '</div>';
            htmlStr += '</div>';
            htmlStr += '<p class="productName">' + postArray[i].laptop_brand + ' ' + postArray[i].laptop_model + '</p>';
            htmlStr += '<p class="productDescription">' + postArray[i].laptop_description + '</p>';
            htmlStr += '</div>';
        }        
    }
    else{
        htmlStr += '<h1>No results found for "' + keyword + '"</h1>';

        htmlStr += '<div class="searchResults" id="searchResults">';
        htmlStr += '<p>Please check your spelling and try again!</p>';
    }
    
    htmlStr += "</div>";
            
    //Add all div into the search result
    document.getElementById("resultsContainer").innerHTML = htmlStr;
}


//Function called when a user view a laptop product from the search results
function loadComparedProducts(laptopId){

    //hide page sections
    searchContainer.style.display = "none";
    resultsContainer.style.display = "none";

    console.log(laptopId); //has the id of the specific laptop clicked 

    if(laptopId == ""){
        //if empty do nothing
    }
    else{

        //Set up XMLHttpRequest
        let xhttp = new XMLHttpRequest();

        const search = new URLSearchParams();
        search.append("laptopId", laptopId); //clicked laptop's id


        //Upon receiving server's response
        xhttp.onload = () => {

            if (xhttp.readyState == 4 && xhttp.status == 200) {

                //Store data from server into global variable
                postsJson = xhttp.responseText;
                
                //Call display function with data received from server
                displayComparedProducts(postsJson, laptopId);


            } else {              
                console.log("error");
            }
        };

        xhttp.open("POST", "/displayDetails", true);
        xhttp.send(search); //send the laptopId to the server
    }
}

//Allows to display the corresponding laptop image and information in the comparison section
function displayComparedProducts(jsonPost, laptopId) {

    let postArray = JSON.parse(jsonPost);

    htmlStr = "";

    if(postArray.length > 0) {

        for (let i = 0; i < postArray.length; i++) {
            htmlStr += '<div>';
            htmlStr += '<img src="' + postArray[i].laptop_imgUrl + '" alt="' + postArray[i].laptop_model + '">';
            htmlStr += '</div>';
            htmlStr += '<div class="productInfo">';
            htmlStr += '<h1>' + postArray[i].laptop_brand + ' ' + postArray[i].laptop_model +' </h1>';
            htmlStr += '</div>';
        }        
    }
    document.getElementById("productDescription").innerHTML = htmlStr;

}


//Allows to display laptops with similar brands and models in the comparison section 
function loadSimilarProducts(laptopBrand, laptopModel){

    if(laptopBrand == "" || laptopModel == ""){
        //if empty do nothing
    }
    else{

        //Set up XMLHttpRequest
        let xhttp = new XMLHttpRequest();

        const search = new URLSearchParams();
        search.append("laptopBrand", laptopBrand); 
        search.append("laptopModel", laptopModel); 


        //Upon receiving server's response
        xhttp.onload = () => {

            if (xhttp.readyState == 4 && xhttp.status == 200) {

                //Store data from server into global variable
                postsJson = xhttp.responseText;
                
                //Call display function with data received from server
                displaySimilarProducts(postsJson, laptopBrand, laptopModel);

            } else {              
                console.log("error");
            }
        };

        xhttp.open("POST", "/displaySimilar", true);
        xhttp.send(search); //send the laptopId to the server
    }
}

//Allows to display laptops with similar brands and models in the comparison section 
function displaySimilarProducts(jsonPost, laptopBrand, laptopModel) {

    let postArray = JSON.parse(jsonPost);

    sourceStr = " ";

    if(postArray.length > 0) {
        
        for (let i = 0; i < postArray.length; i++) {
            sourceStr += '<div class="singleProductSource">';
            sourceStr += '    <div class="webLogo">';
            sourceStr += '<img src="' + postArray[i].logo_url + '" alt="Source logo"/>';
            sourceStr += '</div>';
            sourceStr += '<p class="productPrice">' + postArray[i].price + '</p>';
            sourceStr += '<a class="viewProduct" href="' + postArray[i].source_url + '" target="_blank"> View Product </a>';
            sourceStr += '</div>';
        }        
    }
    document.getElementById("productSource").innerHTML = sourceStr;                                                                              
}