window.addEventListener('DOMContentLoaded', function() {
        function get(){
            var x = document.getElementById("mySelect");
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:9999/posts/categories', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status >= 200 && status <= 299) {
                        const data = JSON.parse(xhr.responseText);
                        for(var i = 0;i <= 10;i++){
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
        window.addEventListener('load',get,false);
    } else {
        window.attachEvent('onload',get);
    }
})