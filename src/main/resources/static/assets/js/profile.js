window.addEventListener('DOMContentLoaded', function() {
    function getUser() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:9999/users/profile', true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && status <= 299) {
                    const data = JSON.parse(xhr.responseText);
                    const content = `<p>First name: ${data.firstName}</p>
                         <form method="POST" action="/images" enctype="multipart/form-data">
                            <input type="file" name="image" />
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