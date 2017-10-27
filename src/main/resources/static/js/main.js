
//get the cities with trains available
var listOfCities = "";
jQuery.ajax({
    url: "http://bucd550:8080/getCities",
    success: function(response) {
        listOfCities = response.filter(function(elem, index, self) {
                           return index == self.indexOf(elem);
                       })
    },
    async:false
});

var select = document.getElementById("selectCity");

select.innerHTML = "<option disabled selected value> --please choose -- </option>";
for(var i = 0; i < listOfCities.length; i++) {
    var opt = listOfCities[i];
    select.innerHTML += "<option value=\"" + opt + "\">" + opt + "</option>";
}

function toggle(el){
    var city = el.options[el.selectedIndex].value;
    var table = document.getElementById('trainTable');
    var tbody = document.getElementById('tbody_id');
    if(tbody !== null) {
        tbody.parentNode.removeChild(tbody);
    }

    // get the trains from the db
    var trainsFromCity = "";
    jQuery.ajax({
        url: "http://bucd550:8080/getTrainsPerCity?city=" + city,
        success: function(response) {
            trainsFromCity = response;
        },
        async:false
    });

    //create the table with the trains
    var new_tbody = document.createElement('tbody');
    new_tbody.setAttribute("id", "tbody_id");
    for(i = 0; i < trainsFromCity.length; i++) {
        var newRow = new_tbody.insertRow(i);
        createRowForTrain(trainsFromCity[i], newRow);
    }
    table.appendChild(new_tbody);
    table.style.opacity = "1";
    document.getElementsByClassName("trainTableDiv")[0].style.opacity = "1";
}

function createRowForTrain(train, row) {
    // creates a row in the train table
    var cell = row.insertCell(0);
    var cellText = document.createTextNode(train.name);
    cell.appendChild(cellText);

    cell = row.insertCell(1);
    cellText = document.createTextNode(train.departureCity);
    cell.appendChild(cellText);

    cell = row.insertCell(2);
    cellText = document.createTextNode(train.arrivalCity);
    cell.appendChild(cellText);

    cell = row.insertCell(3);
    cellText = document.createTextNode(getDateFromTrain(train.date));
    cell.appendChild(cellText);

    cell = row.insertCell(4);
    cellText = document.createTextNode(getTimeFromTrain(train.departureTime));
    cell.appendChild(cellText);

    cell = row.insertCell(5);
    cellText = document.createTextNode(train.price + " RON");
    cell.appendChild(cellText);

    cell = row.insertCell(6);
    var btn = document.createElement('input');
    btn.type = "button";
    btn.className = "btn";
    btn.value = "Buy Ticket";
    btn.onclick= function() {
        buyButton(train);
    };
    cell.appendChild(btn);

}

function buyButton(train) {
    var modal = document.getElementById('buyTicketModal');
    var span = document.getElementsByClassName("close_buyTicket-span")[0];
    modal.style.display = "block";
    span.onclick = function() {
       modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    // js voodoo to not retain previous header
    var modalHeader = document.getElementsByClassName("CbuyTicketModal-content")[0];
    var previousHeader = modalHeader.getElementsByTagName("h4")[0];
    if(previousHeader != null) {
        modalHeader.removeChild(previousHeader);
    }

    // check if there are any more fares for the selected train
    if(trainHasNoMoreSeats(train)) {
        alert("The train has no more seats. Please choose another train");
        modal.style.display = "none";
        return;
    }

    //adding the title according to the train selected
    $("#Cmodal-title").text("Purchasing a ticket for train " + train.name + " leaving from "
                                               + train.departureCity + " to " + train.arrivalCity +
                                               ", leaving on " + getDateFromTrain(train.date) + " at " + getTimeFromTrain(train.departureTime) + ".");
}

function getDateFromTrain(date) {
    return date.year + "-" + date.monthValue + "-" + date.dayOfMonth;
}

function getTimeFromTrain(time) {
    if(time.minute === 0) {
        return time.hour + ":" + time.minute + "0";
    }
    else {
        return time.hour + ":" + time.minute;
    }
}

function trainHasNoMoreSeats(train) {
    if(train.available_seats == 0) {
        return true;
    }
    else return false;

}

function payOnline(form) {

    var formData = new Object();

    formData.name = form[0].value;;
    formData.docNr = form[1].value;
    formData.email = form[2].value;

    jsonFormData = JSON.stringify(formData);
    $.ajax({
         url: 'http://bucd550:8080/sendClientInfo',
         type: 'POST', 
         dataType: "json",
         data: jsonFormData,
         contentType: "application/json",
         success: function(result) {
           alert('SUCCESS');
         }
    });

    
    //triggerPayOnlineModal(); 
}

function triggerPayOnlineModal() {
    var modal = document.getElementById('pay-online');
    var span = document.getElementsByClassName("close_payOnline-span")[0];
    modal.style.display = "block";
    span.onclick = function() {
       modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

}