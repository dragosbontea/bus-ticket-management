
var listOfCities = "";
jQuery.ajax({
    url: "http://localhost:8080/getCities",
    success: function(response) {
        listOfCities = response;
    },
    async:false
});
console.log(listOfCities);

var citiesDropdownList = document.getElementById("select-city-dropdown").getElementsByClassName("dropdown")[0];
var a = $("div.cityDropdown > div.dropdown > div.dropdown ul");
for(var i = 0; len = listOfCities.length; i++) {
//    citiesDropdownList.append("<li><a href=\"#\">" + listOfCities[i] + "</a></li>");
    a.append($("<li>" + listOfCities[i] + "</li>"));
}

