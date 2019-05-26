function compare() {
    var startDt = document.getElementById("startDate").value;
    var endDt = document.getElementById("endDate").value;

    if( (new Date(startDt).getTime() > new Date(endDt).getTime()))
    {
        alert("Wrong date");
        document.getElementById("startDate").valueAsDate = null;
        document.getElementById("endDate").valueAsDate = null;
    }
}