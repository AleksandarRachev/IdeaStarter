window.addEventListener('DOMContentLoaded', function() {
    function getCategories(){
        var x = document.getElementById("mySelect");
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/posts/categories', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    for(var i = 0;i < data.length;i++){
                        var option = document.createElement("option");
                        option.text = `${data[i].name}`;
                        x.add(option);
                    }
                }
            }
        }
        xhr.send();
    }
    if(window.addEventListener) {
        window.addEventListener('load',getCategories,false);
    } else {
        window.attachEvent('onload',getCategories);
    }
})