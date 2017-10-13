
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
    console.log(opt);
    select.innerHTML += "<option value=\"" + opt + "\">" + opt + "</option>";
}

function toggle(el){
    var city = el.options[el.selectedIndex].value;
    var table = document.getElementById('trainTable');
    var tbody = document.getElementById('tbody_id');
    if(tbody !== null) {
        tbody.parentNode.removeChild(tbody);
    }

    var trainsFromCity = "";
    jQuery.ajax({
        url: "http://bucd550:8080/getTrainsPerCity?city=" + city,
        success: function(response) {
            trainsFromCity = response;
        },
        async:false
    });

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
    var date = train.date;
    cellText = document.createTextNode(date.year + "-" + date.monthValue + "-" + date.dayOfMonth);
    cell.appendChild(cellText);

    cell = row.insertCell(4);
    var time = train.departureTime;
    if(time.minute === 0) {
        cellText = document.createTextNode(time.hour + ":" + time.minute + "0");
    }
    else {
        cellText = document.createTextNode(time.hour + ":" + time.minute);
    }

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
    var span = document.getElementsByClassName("close_buyTicketModal")[0];
    modal.style.display = "block";
    span.onclick = function() {
       modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    var modalHeader = document.getElementsByClassName("modal-header-buyTicket");
    var h4 = document.createElement("h4");
    h4.textContent= "Purchasing a ticket for train " + train.name + " going from "
                    + train.departureCity + " to " + train.arrivalCity +
                    " leaving on " + train.date + " at " + train.departureTime;
    modalHeader.appendChild(h4);
}