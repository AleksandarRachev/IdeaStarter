window.addEventListener('DOMContentLoaded', function() {
        function getInfo() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:9999/posts/all', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status >= 200 && status <= 299) {
                        const data = JSON.parse(xhr.responseText);
                        for(var i = 0;i<data.length;i++){
                        const content = `<h1>Title ${data[i].title} </h1> 
                        <p> Description: ${data[i].description}</p>
                        <p> Start date: ${data[i].startDate}</p>
                        <p> End date: ${data[i].endDate}</p>
                        <p>Post owner: ${(data[i].user.firstName)+" "+(data[i].user.lastName)}</p>`;
    
                        document.getElementById('container').innerHTML += content;
                        }
                    }
                }
            }
            xhr.send();
        }
        if(window.addEventListener) {
            window.addEventListener('load',getInfo,false);
        } else {
            window.attachEvent('onload',getInfo);
        }
    });