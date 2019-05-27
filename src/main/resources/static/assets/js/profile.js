window.addEventListener('DOMContentLoaded', function() {
    function getUser() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/users/profile', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    var imageSrc;
                    if(data.imageUrl == null){
                        imageSrc  = "/assets/images/icon-122x122.png";
                    }
                    else{
                        imageSrc  = "http://localhost:9999/images/users/"+data.id;
                    }
                    const content = `<p>User id: ${data.id}</p>
                    <p>First name: ${data.firstName}</p>
                    <img src=${imageSrc} height="50" width="50">
                         <form method="POST" action="/images" enctype="multipart/form-data">
                            <input type="file" name="image" placeholder="Change profile pic"/>
                            <button type="submit">Upload</button>
                         </form>`;
                    document.getElementById('user').innerHTML = content;
                }
            }
        }
        xhr.send();
    }
    if(window.addEventListener) {
        window.addEventListener('load',getUser,false);
    } else {
        window.attachEvent('onload',getUser);
    }
});