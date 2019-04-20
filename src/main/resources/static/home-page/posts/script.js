// window.addEventListener('DOMContenteLoaded',function(){
//     function getInfo(town){
//         var xhr = new XMLHttpRequest();
//         xhr.open('GET','http://api.openweathermap.org/data/2.5/weather?q='+town+',bg&appid=f631fd357c75163a46154773a513dd64',true);
//         xhr.onreadystatechange = function(){
//             if(xhr.readyState === 4){
//                 if(xhr.status >= 200 && xhr.status < 300){
//                     const data = JSON.parse(xhr.responseText);
//                     const content = `<h1>Town: ${town}</h1>
//                     <p>Temp: ${data.main.temp}</p>`;

//                     document.getElementById('container').innerHTML = content;
//                 }
//             }
//         }
//         xhr.send(null);
//     }
//     document.querySelector('#show').addEventListener('click', () => {
//         var town = document.querySelector('#town').value;
//         getInfo(town);
//     })
// })

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
                            // console.log(data[i].id);
                        }
                        // console.log(data);
                    }
                }
            }
            xhr.send();
        }
    
        // document.querySelector('#show').addEventListener('onload', () => {
        //     // var town = document.querySelector('#town').value;
        //     getInfo();
        // });
        if(window.addEventListener) {
            window.addEventListener('load',getInfo,false); //W3C
        } else {
            window.attachEvent('onload',getInfo); //IE
        }
    });